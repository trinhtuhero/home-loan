package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import vn.com.msb.homeloan.api.dto.request.CMSAmlCheckRequest;
import vn.com.msb.homeloan.core.constant.CICRelationshipTypeEnum;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.*;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.Aml;
import vn.com.msb.homeloan.core.model.CMSGetAmlInfo;
import vn.com.msb.homeloan.core.model.mapper.AmlMapper;
import vn.com.msb.homeloan.core.model.request.aml.CheckAmlRequest;
import vn.com.msb.homeloan.core.model.response.aml.AmlResponse;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.AmlService;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.util.Client;
import vn.com.msb.homeloan.core.util.StringUtils;
import vn.com.msb.homeloan.infras.configs.feign.AmlConfig;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Slf4j
@Service
@AllArgsConstructor
public class AmlServiceImpl implements AmlService {

  private final Client client;
  private final AmlConfig amlConfig;
  private final EnvironmentProperties envProperties;
  private final AmlRepository amlRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final MarriedPersonRepository marriedPersonRepository;
  private final LoanPayerRepository loanPayerRepository;
  private final CollateralOwnerRepository collateralOwnerRepository;
  private final CMSTabActionService cmsTabActionService;

  @Override
  public List<Aml> checkAmls(String loanId, List<CMSAmlCheckRequest.AmlRequest> checkAmls) {
    loanApplicationRepository.findById(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    cmsTabActionService.save(loanId, CMSTabEnum.CUSTOMER_CHECK);
    List<Aml> result = new ArrayList<>();
    for (CMSAmlCheckRequest.AmlRequest checkAml : checkAmls) {
      Set<String> idPassports = checkAml.getIdPassports();
      for (String idPassport : idPassports) {
        Aml aml = checkAml(loanId, idPassport);
        result.add(aml);
      }
    }
    return result;
  }

  private Aml checkAml(String loanId, String idPassport) {
    CheckAmlRequest request = new CheckAmlRequest(idPassport);

    final String url = UriComponentsBuilder
        .fromHttpUrl(amlConfig.getBaseUrl())
        .path(amlConfig.getApiCheck())
        .build().toUri().toString();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("client_code", envProperties.getClientCode());
    HttpEntity<CheckAmlRequest> entity = new HttpEntity<>(request, headers);
    AmlEntity amlEntity = amlRepository.findByLoanApplicationIdAndIdPassport(loanId, idPassport);
    if (amlEntity == null) {
      amlEntity = AmlEntity.builder()
          .loanApplicationId(loanId)
          .idPassport(idPassport).build();
    }
    try {
      log.info("Call aml request: {}", entity);
      ResponseEntity<AmlResponse> response = client.getWithBody(url, entity, AmlResponse.class);
      AmlResponse amlResponse = response.getBody();
      if (amlResponse != null) {
        amlEntity.setActive(amlResponse.getDataAml() == null ? false : true);
        amlEntity.setMetaData(
            amlResponse.getDataAml() == null ? null : amlResponse.getDataAml().toString());
        checkPassAml(amlEntity, amlResponse);
      }
    } catch (Exception e) {
      log.error("AML error: ", e);
      amlEntity.setActive(false);
      amlEntity.setPass(null);
    }
    amlEntity.setCheckDate(new Date());
    amlRepository.save(amlEntity);
    return AmlMapper.INSTANCE.toModel(amlEntity);
  }

  private void checkPassAml(AmlEntity amlEntity, AmlResponse amlResponse) {
    if (amlResponse.getDataAml() != null && "200".equals(amlResponse.getDataAml().getStatus())) {
      amlEntity.setPass(true);
    } else {
      amlEntity.setPass(false);
    }
  }

  @Override
  public List<CMSGetAmlInfo> getAmlInfo(String loanId) {
    List<CMSGetAmlInfo> result = new ArrayList<>();
    LoanApplicationEntity loanApplication = loanApplicationRepository.findByUuid(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    CMSGetAmlInfo amlInfoCustomer = new CMSGetAmlInfo();
    amlInfoCustomer.setRelationshipType(CICRelationshipTypeEnum.CUSTOMER);
    amlInfoCustomer.setName(loanApplication.getFullName());
    List<String> identityCardsCustomer = new ArrayList<>(
        Arrays.asList(loanApplication.getIdNo(), loanApplication.getOldIdNo(),
            loanApplication.getOldIdNo2(), loanApplication.getOldIdNo3()));
    amlInfoCustomer.setAmls(getAmlInfoByIdentityCards(identityCardsCustomer, loanId));
    result.add(amlInfoCustomer);

    CMSGetAmlInfo amlInfoMarriedPerson = new CMSGetAmlInfo();
    MarriedPersonEntity marriedPerson = marriedPersonRepository.findOneByLoanId(loanId);
    if (marriedPerson != null) {
      amlInfoMarriedPerson.setName(marriedPerson.getFullName());
      amlInfoMarriedPerson.setRelationshipType(CICRelationshipTypeEnum.WIFE_HUSBAND);
      List<String> identityCardsMarriedPerson = new ArrayList<>(
          Arrays.asList(marriedPerson.getIdNo(), marriedPerson.getOldIdNo(),
              marriedPerson.getOldIdNo2(), marriedPerson.getOldIdNo3()));
      amlInfoMarriedPerson.setAmls(getAmlInfoByIdentityCards(identityCardsMarriedPerson, loanId));
      result.add(amlInfoMarriedPerson);
    }

    List<LoanPayerEntity> loanPayers = loanPayerRepository.findByLoanId(loanId);
    if (!CollectionUtils.isEmpty(loanPayers)) {
      for (LoanPayerEntity loanPayerEntity : loanPayers) {
        CMSGetAmlInfo amlInfoLoanPayer = new CMSGetAmlInfo();
        amlInfoLoanPayer.setName(loanPayerEntity.getFullName());
        amlInfoLoanPayer.setRelationshipType(CICRelationshipTypeEnum.LOAN_PAYER);
        List<String> identityCardsLoanPayer = new ArrayList<>(
            Arrays.asList(loanPayerEntity.getIdNo(), loanPayerEntity.getOldIdNo(),
                loanPayerEntity.getOldIdNo2(), loanPayerEntity.getOldIdNo3()));
        amlInfoLoanPayer.setAmls(getAmlInfoByIdentityCards(identityCardsLoanPayer, loanId));
        result.add(amlInfoLoanPayer);
      }
    }

    List<CollateralOwnerEntity> collateralOwners = collateralOwnerRepository.getByLoanId(loanId);
    if (!CollectionUtils.isEmpty(collateralOwners)) {
      for (CollateralOwnerEntity collateralOwnerEntity : collateralOwners) {
        CMSGetAmlInfo amlInfoCollateralOwner = new CMSGetAmlInfo();
        amlInfoCollateralOwner.setName(collateralOwnerEntity.getFullName());
        amlInfoCollateralOwner.setRelationshipType(CICRelationshipTypeEnum.COLLATERAL_OWNER);
        List<String> identityCardsCollateralOwner = new ArrayList<>(
            Arrays.asList(collateralOwnerEntity.getIdNo(), collateralOwnerEntity.getOldIdNo(),
                collateralOwnerEntity.getOldIdNo2(), collateralOwnerEntity.getOldIdNo3()));
        amlInfoCollateralOwner.setAmls(
            getAmlInfoByIdentityCards(identityCardsCollateralOwner, loanId));
        result.add(amlInfoCollateralOwner);
      }
    }
    return result;
  }

  private List<Aml> getAmlInfoByIdentityCards(List<String> identityCards, String loanId) {
    identityCards.removeIf(id -> StringUtils.isEmpty(id));
    List<String> identityCardsClone = new ArrayList<>(identityCards);

    List<AmlEntity> amlEntities = amlRepository.findByLoanApplicationIdAndIdPassportIn(loanId,
        new HashSet<>(identityCards));

    List<Aml> amls = AmlMapper.INSTANCE.toModels(amlEntities);

    emptyIfNull(amlEntities).forEach(
        identityCard -> identityCards.removeIf(i -> i.equals(identityCard.getIdPassport())));

    emptyIfNull(identityCards).forEach(
        identityCard -> amls.add(Aml.builder().idPassport(identityCard).build()));

    List<Aml> result = new ArrayList<>();
    for (String id : identityCardsClone) {
      result.add(
          amls.stream().filter(op -> op.getIdPassport().equals(id)).collect(Collectors.toList())
              .get(0));
    }
    return result;
  }
}
