package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.client.OpRiskClient;
import vn.com.msb.homeloan.core.constant.CICRelationshipTypeEnum;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.oprisk.OpRiskCheckTypeEnum;
import vn.com.msb.homeloan.core.constant.oprisk.OpRiskCollateralTypeEnum;
import vn.com.msb.homeloan.core.constant.oprisk.OpRiskStatusEnum;
import vn.com.msb.homeloan.core.entity.*;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CMSGetOpRiskCollateralInfo;
import vn.com.msb.homeloan.core.model.CMSGetOpRiskInfo;
import vn.com.msb.homeloan.core.model.OpRisk;
import vn.com.msb.homeloan.core.model.mapper.OpRiskMapper;
import vn.com.msb.homeloan.core.model.request.OpRiskAuthInfo;
import vn.com.msb.homeloan.core.model.request.OpsRiskCheckCRequest;
import vn.com.msb.homeloan.core.model.request.OpsRiskCheckP;
import vn.com.msb.homeloan.core.model.request.OpsRiskCheckPRequest;
import vn.com.msb.homeloan.core.model.response.opRisk.OpsRiskCheckCResponse;
import vn.com.msb.homeloan.core.model.response.opRisk.OpsRiskCheckPResponse;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.OpRiskService;
import vn.com.msb.homeloan.core.util.DateUtils;
import vn.com.msb.homeloan.core.util.StringUtils;
import vn.com.msb.homeloan.infras.configs.feign.OpRiskConfig;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Slf4j
@Service
@AllArgsConstructor
public class OpRiskServiceImpl implements OpRiskService {

  private final OpRiskClient opRisk;
  private final OpRiskConfig opRiskConfig;
  private final OpRiskRepository opRiskRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final MarriedPersonRepository marriedPersonRepository;
  private final LoanPayerRepository loanPayerRepository;

  private final EnvironmentProperties envProperties;
  private final CMSTabActionService cmsTabActionService;
  private final CollateralOwnerRepository collateralOwnerRepository;
  private final CollateralRepository collateralRepository;

  @Override
  public List<OpRisk> checkP(List<OpsRiskCheckP> opsRiskCheckPs, String loanApplicationId)
      throws ParseException {
    loanApplicationRepository.findById(loanApplicationId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    cmsTabActionService.save(loanApplicationId, CMSTabEnum.CUSTOMER_CHECK);
    SimpleDateFormat format = new SimpleDateFormat(DateUtils.REQ_TIME_FORMAT);
    OpRiskAuthInfo opRiskAuthInfo = new OpRiskAuthInfo(opRiskConfig.getReqId(),
        format.format(Date.from(Instant.now())), opRiskConfig.getReqApp(),
        opRiskConfig.getAuthorizer(), opRiskConfig.getPassword(), opRiskConfig.getSrvCheckP());
    List<OpRiskEntity> opRiskEntitiesInDB = opRiskRepository.findByLoanApplicationIdAndCheckType(
        loanApplicationId, OpRiskCheckTypeEnum.CHECK_P);
    List<OpRiskEntity> opRiskEntitiesResult = new ArrayList<>();
    for (OpsRiskCheckP opsRiskCheckP : opsRiskCheckPs) {
      List<OpRiskEntity> opRiskEntities = checkIdentityIds(opsRiskCheckP, opRiskAuthInfo,
          opRiskEntitiesInDB, loanApplicationId);
      for (OpRiskEntity op : opRiskEntities) {
        if (op.getActive().equals(false)) {
          throw new ApplicationException(ErrorEnum.CALL_API_OPRISK_CHECK_P_ERROR);
        }
      }
      opRiskEntitiesResult.addAll(opRiskEntities);
    }
    return OpRiskMapper.INSTANCE.toModels(opRiskEntitiesResult);
  }

  @Override
  public List<OpRisk> checkC(List<OpsRiskCheckCRequest.OpsRiskCheckC> opsRiskCheckCs,
      String loanApplicationId) throws ParseException {
    loanApplicationRepository.findById(loanApplicationId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    cmsTabActionService.save(loanApplicationId, CMSTabEnum.CUSTOMER_CHECK);
    SimpleDateFormat format = new SimpleDateFormat(DateUtils.REQ_TIME_FORMAT);
    OpRiskAuthInfo opRiskAuthInfo = new OpRiskAuthInfo(opRiskConfig.getReqId(),
        format.format(Date.from(Instant.now())), opRiskConfig.getReqApp(),
        opRiskConfig.getAuthorizer(), opRiskConfig.getPassword(), opRiskConfig.getSrvCheckC());
    List<OpRiskEntity> opRiskEntitiesInDB = opRiskRepository.findByLoanApplicationIdAndCheckType(
        loanApplicationId, OpRiskCheckTypeEnum.CHECK_C);
    List<OpRiskEntity> opRiskEntitiesResult = new ArrayList<>();
    for (OpsRiskCheckCRequest.OpsRiskCheckC opsRiskCheckC : opsRiskCheckCs) {
      List<OpRiskEntity> opRiskEntities = checkIdentifiesInfo(opsRiskCheckC, opRiskAuthInfo,
          opRiskEntitiesInDB, loanApplicationId);
      for (OpRiskEntity op : opRiskEntities) {
        if (op.getActive().equals(false)) {
          throw new ApplicationException(ErrorEnum.CALL_API_OPRISK_CHECK_C_ERROR);
        }
      }
      opRiskEntitiesResult.addAll(opRiskEntities);
    }
    return OpRiskMapper.INSTANCE.toModels(opRiskEntitiesResult);
  }

  @Transactional
  private List<OpRiskEntity> checkIdentityIds(OpsRiskCheckP opsRiskCheckP,
      OpRiskAuthInfo opRiskAuthInfo, List<OpRiskEntity> opRiskEntitiesInDB,
      String loanApplicationId) throws ParseException {
    List<OpRiskEntity> opRiskEntitiesInsert = new ArrayList<>();
    List<String> identityCards = new ArrayList<>(opsRiskCheckP.getIdentityCards());
    for (String identityCard : identityCards) {
      opsRiskCheckP.setAuthInfo(opRiskAuthInfo);
      opsRiskCheckP.setIdentityCard(identityCard);
      List<OpRiskEntity> opRiskEntities = opRiskEntitiesInDB.stream()
          .filter(or -> or.getIdentityCard().equals(identityCard)).collect(Collectors.toList());
      OpRiskEntity opRiskEntity;
      if (!CollectionUtils.isEmpty(opRiskEntities)) {
        opRiskEntity = opRiskEntities.get(0);
      } else {
        opRiskEntity = new OpRiskEntity();
      }
      opRiskEntity.setIdentityCard(identityCard);
      opRiskEntity.setLoanApplicationId(loanApplicationId);
//            opRiskEntity.setName(opsRiskCheckP.getName());
//            opRiskEntity.setBirthday(DateUtils.convertToDateFormat(opsRiskCheckP.getBirthday()));
      opRiskEntity.setEndDate(DateUtils.convertToDateFormat((opsRiskCheckP.getEndDate())));
      opRiskEntity.setCheckType(OpRiskCheckTypeEnum.CHECK_P);
      opRiskEntity.setCheckDate(new Date());
      try {
        checkBlacklistP(opsRiskCheckP, opRiskEntity);
        opRiskEntity.setActive(true);
      } catch (Exception e) {
        log.error("Call api opRisk check-p error: ", e);
        opRiskEntity.setActive(false);
      }
      opRiskEntitiesInsert.add(opRiskEntity);
    }
    opRiskRepository.saveAll(opRiskEntitiesInsert);
    return opRiskEntitiesInsert;
  }

  @Transactional
  private List<OpRiskEntity> checkIdentifiesInfo(OpsRiskCheckCRequest.OpsRiskCheckC opsRiskCheckC,
      OpRiskAuthInfo opRiskAuthInfo, List<OpRiskEntity> opRiskEntitiesInDB,
      String loanApplicationId) throws ParseException {
    List<OpRiskEntity> opRiskEntitiesInsert = new ArrayList<>();
    List<String> lstIdentifiesInfo = new ArrayList<>(opsRiskCheckC.getIdentifiesInfoSet());
    for (String identifiesInfo : lstIdentifiesInfo) {
      opsRiskCheckC.setAuthInfo(opRiskAuthInfo);
      opsRiskCheckC.setIdentifiesInfo(identifiesInfo);
      List<OpRiskEntity> opRiskEntities = opRiskEntitiesInDB.stream()
          .filter(or -> or.getIdentifyInfo().equals(identifiesInfo)).collect(Collectors.toList());
      OpRiskEntity opRiskEntity;
      if (!CollectionUtils.isEmpty(opRiskEntities)) {
        opRiskEntity = opRiskEntities.get(0);
      } else {
        opRiskEntity = new OpRiskEntity();
      }
      opRiskEntity.setIdentifyInfo(identifiesInfo);
      opRiskEntity.setLoanApplicationId(loanApplicationId);
      opRiskEntity.setCheckType(OpRiskCheckTypeEnum.CHECK_C);
      opRiskEntity.setCollateralType(
          OpRiskCollateralTypeEnum.ofValue(opsRiskCheckC.getCollateralType()));
      opRiskEntity.setCheckDate(new Date());
      try {
        checkBlacklistC(opsRiskCheckC, opRiskEntity);
        opRiskEntity.setActive(true);
      } catch (Exception e) {
        log.error("Call api opRisk check-c error: ", e);
        opRiskEntity.setActive(false);
        opRiskEntity.setStatus(OpRiskStatusEnum.FAIL);
      }
      opRiskEntitiesInsert.add(opRiskEntity);
    }
    opRiskRepository.saveAll(opRiskEntitiesInsert);
    return opRiskEntitiesInsert;
  }

  @Override
  public List<CMSGetOpRiskInfo> getOpRiskInfo(String loanId) {
    List<CMSGetOpRiskInfo> result = new ArrayList<>();
    LoanApplicationEntity loanApplication = loanApplicationRepository.findByUuid(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    CMSGetOpRiskInfo opRiskInfoCustomer = new CMSGetOpRiskInfo();
    opRiskInfoCustomer.setRelationshipType(CICRelationshipTypeEnum.CUSTOMER);
    opRiskInfoCustomer.setName(loanApplication.getFullName());
    opRiskInfoCustomer.setBirthday(loanApplication.getBirthday());
    List<String> identityCardsCustomer = new ArrayList<>(
        Arrays.asList(loanApplication.getIdNo(), loanApplication.getOldIdNo(),
            loanApplication.getOldIdNo2(), loanApplication.getOldIdNo3()));
    opRiskInfoCustomer.setOpRisks(getOpRiskInfoByIdentityCards(identityCardsCustomer, loanId));
    result.add(opRiskInfoCustomer);

    CMSGetOpRiskInfo opRiskInfoMarriedPerson = new CMSGetOpRiskInfo();
    MarriedPersonEntity marriedPerson = marriedPersonRepository.findOneByLoanId(loanId);
    if (marriedPerson != null) {
      opRiskInfoMarriedPerson.setName(marriedPerson.getFullName());
      opRiskInfoMarriedPerson.setRelationshipType(CICRelationshipTypeEnum.WIFE_HUSBAND);
      opRiskInfoMarriedPerson.setBirthday(marriedPerson.getBirthday());
      List<String> identityCardsMarriedPerson = new ArrayList<>(
          Arrays.asList(marriedPerson.getIdNo(), marriedPerson.getOldIdNo(),
              marriedPerson.getOldIdNo2(), marriedPerson.getOldIdNo3()));
      opRiskInfoMarriedPerson.setOpRisks(
          getOpRiskInfoByIdentityCards(identityCardsMarriedPerson, loanId));
      result.add(opRiskInfoMarriedPerson);
    }

    List<LoanPayerEntity> loanPayers = loanPayerRepository.findByLoanId(loanId);
    if (!CollectionUtils.isEmpty(loanPayers)) {
      for (LoanPayerEntity loanPayerEntity : loanPayers) {
        CMSGetOpRiskInfo opRiskInfoLoanPayer = new CMSGetOpRiskInfo();
        opRiskInfoLoanPayer.setName(loanPayerEntity.getFullName());
        opRiskInfoLoanPayer.setRelationshipType(CICRelationshipTypeEnum.LOAN_PAYER);
        opRiskInfoLoanPayer.setBirthday(loanPayerEntity.getBirthday());
        List<String> identityCardsLoanPayer = new ArrayList<>(
            Arrays.asList(loanPayerEntity.getIdNo(), loanPayerEntity.getOldIdNo(),
                loanPayerEntity.getOldIdNo2(), loanPayerEntity.getOldIdNo3()));
        opRiskInfoLoanPayer.setOpRisks(
            getOpRiskInfoByIdentityCards(identityCardsLoanPayer, loanId));
        result.add(opRiskInfoLoanPayer);
      }
    }

    List<CollateralOwnerEntity> collateralOwners = collateralOwnerRepository.getByLoanId(loanId);
    if (!CollectionUtils.isEmpty(collateralOwners)) {
      for (CollateralOwnerEntity collateralOwnerEntity : collateralOwners) {
        CMSGetOpRiskInfo opRiskInfoCollateralOwner = new CMSGetOpRiskInfo();
        opRiskInfoCollateralOwner.setName(collateralOwnerEntity.getFullName());
        opRiskInfoCollateralOwner.setRelationshipType(CICRelationshipTypeEnum.COLLATERAL_OWNER);
        List<String> identityCardsCollateralOwner = new ArrayList<>(
            Arrays.asList(collateralOwnerEntity.getIdNo(), collateralOwnerEntity.getOldIdNo(),
                collateralOwnerEntity.getOldIdNo2(), collateralOwnerEntity.getOldIdNo3()));
        opRiskInfoCollateralOwner.setOpRisks(
            getOpRiskInfoByIdentityCards(identityCardsCollateralOwner, loanId));
        result.add(opRiskInfoCollateralOwner);
      }
    }
    return result;
  }

  @Override
  public List<CMSGetOpRiskCollateralInfo> getOpRiskCollateralInfo(String loanId) {
    loanApplicationRepository.findByUuid(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    List<CMSGetOpRiskCollateralInfo> result = new ArrayList<>();
    List<CollateralEntity> collateralEntities = collateralRepository.findByLoanId(loanId);
    List<OpRiskEntity> opRiskEntitiesInDB = opRiskRepository.findByLoanApplicationIdAndCheckTypeAndActive(
        loanId, OpRiskCheckTypeEnum.CHECK_C, true);
    for (CollateralEntity collateralEntity : collateralEntities) {
      List<OpRiskEntity> opRiskEntitiesMatch = emptyIfNull(opRiskEntitiesInDB).stream()
          .filter(op -> op.getIdentifyInfo().equals(collateralEntity.getLegalDoc()) ||
              op.getIdentifyInfo().equals(collateralEntity.getRegistrationOrContractNo()) ||
              op.getIdentifyInfo().equals(collateralEntity.getSavingBookNo()))
          .collect(Collectors.toList());
      CMSGetOpRiskCollateralInfo opRiskCollateralInfo = CMSGetOpRiskCollateralInfo.builder()
          .collateralStatus(collateralEntity.getStatus())
          .collateralType(collateralEntity.getType())
          .mvalueId(collateralEntity.getMvalueId())
          .identifyInfo(collateralEntity.getLegalDoc())
          .savingBookNo(collateralEntity.getSavingBookNo())
          .registrationOrContractNo(collateralEntity.getRegistrationOrContractNo())
          .opRisk(CollectionUtils.isEmpty(opRiskEntitiesMatch) ? null
              : OpRiskMapper.INSTANCE.toModel(opRiskEntitiesMatch.get(0))).build();
      result.add(opRiskCollateralInfo);
    }
    return result;
  }

  private List<OpRisk> getOpRiskInfoByIdentityCards(List<String> identityCards, String loanId) {
    identityCards.removeIf(id -> StringUtils.isEmpty(id));
    List<String> identityCardsClone = new ArrayList<>(identityCards);

    List<OpRiskEntity> opRiskEntities = opRiskRepository.findByLoanApplicationIdAndIdentityCardInAndActive(
        loanId, new HashSet<>(identityCards), true);

    List<OpRisk> opRisks = OpRiskMapper.INSTANCE.toModels(opRiskEntities);

    emptyIfNull(opRiskEntities).forEach(
        identityCard -> identityCards.removeIf(i -> i.equals(identityCard.getIdentityCard())));

    emptyIfNull(identityCards).forEach(
        identityCard -> opRisks.add(OpRisk.builder().identityCard(identityCard).build()));

    List<OpRisk> result = new ArrayList<>();
    for (String id : identityCardsClone) {
      result.add(opRisks.stream().filter(op -> op.getIdentityCard().equals(id))
          .collect(Collectors.toList()).get(0));
    }
    return result;
  }

  private void checkBlacklistP(OpsRiskCheckP opsRiskCheckP, OpRiskEntity opRiskEntity) {
    OpsRiskCheckPResponse response = callApiP(opsRiskCheckP);
    if (response != null) {
      opRiskEntity.setPass(true);
      opRiskEntity.setMetaData(response.toString());
      if (response.getData() != null) {
        if (Boolean.parseBoolean(
            response.getData().getCheckBlackListP().getRespDomain().getBlackList4LosP()
                .getResult())) {
          opRiskEntity.setPass(false);
        }
        if ("0".equals(response.getData().getCheckBlackListP().getRespMessage().getRespCode())) {
          opRiskEntity.setStatus(OpRiskStatusEnum.SUCCESS);
        } else {
          opRiskEntity.setStatus(OpRiskStatusEnum.FAIL);
          opRiskEntity.setPass(null);
        }
      } else {
        opRiskEntity.setStatus(OpRiskStatusEnum.NO_DATA);
      }
    }
  }

  private void checkBlacklistC(OpsRiskCheckCRequest.OpsRiskCheckC opsRiskCheckC,
      OpRiskEntity opRiskEntity) {
    OpsRiskCheckCResponse response = callApiC(opsRiskCheckC);
    if (response != null) {
      opRiskEntity.setPass(true);
      opRiskEntity.setMetaData(response.toString());
      if (response.getData() != null) {
        if (Boolean.parseBoolean(
            response.getData().getCheckBlackListC().getRespDomain().getBlackList4LosC()
                .getResult())) {
          opRiskEntity.setPass(false);
        }
        if ("0".equals(response.getData().getCheckBlackListC().getRespMessage().getRespCode())) {
          opRiskEntity.setStatus(OpRiskStatusEnum.SUCCESS);
        } else {
          opRiskEntity.setStatus(OpRiskStatusEnum.FAIL);
          opRiskEntity.setPass(null);
        }
      } else {
        opRiskEntity.setStatus(OpRiskStatusEnum.NO_DATA);
      }
    }
  }

  private OpsRiskCheckPResponse callApiP(OpsRiskCheckP opsRiskCheckP) {
    OpsRiskCheckPRequest opsRiskCheckPRequest = new OpsRiskCheckPRequest(opsRiskCheckP);
    OpsRiskCheckPResponse response = opRisk.checkP(opsRiskCheckPRequest,
        envProperties.getClientCode()).getBody();
    log.info("OpsRiskResponse: " + response);
    return response;
  }

  private OpsRiskCheckCResponse callApiC(OpsRiskCheckCRequest.OpsRiskCheckC opsRiskCheckC) {
    OpsRiskCheckCRequest opsRiskCheckCRequest = new OpsRiskCheckCRequest(opsRiskCheckC);
    OpsRiskCheckCResponse response = opRisk.checkC(opsRiskCheckCRequest,
        envProperties.getClientCode()).getBody();
    log.info("OpsRiskResponse: " + response);
    return response;
  }
}
