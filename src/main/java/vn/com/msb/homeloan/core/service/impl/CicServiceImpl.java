package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.api.dto.request.CMSCicRequest;
import vn.com.msb.homeloan.core.client.CicClient;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.constant.cic.CicGroupEnum;
import vn.com.msb.homeloan.core.constant.cic.CicQueryStatusEnum;
import vn.com.msb.homeloan.core.constant.cic.CustomerType;
import vn.com.msb.homeloan.core.constant.cic.ProductCode;
import vn.com.msb.homeloan.core.entity.*;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.helper.CalcCicHelper;
import vn.com.msb.homeloan.core.model.CMSCic;
import vn.com.msb.homeloan.core.model.CMSCicItem;
import vn.com.msb.homeloan.core.model.CMSGetCicInfo;
import vn.com.msb.homeloan.core.model.CmsUserReqCIC;
import vn.com.msb.homeloan.core.model.cic.CicInformation;
import vn.com.msb.homeloan.core.model.cic.CicQueryResult;
import vn.com.msb.homeloan.core.model.cic.IdentityCicQueryResult;
import vn.com.msb.homeloan.core.model.cic.content.CicContent;
import vn.com.msb.homeloan.core.model.mapper.CicItemMapper;
import vn.com.msb.homeloan.core.model.mapper.CicMapper;
import vn.com.msb.homeloan.core.model.parser.CicParser;
import vn.com.msb.homeloan.core.model.parser.XmlCicParser;
import vn.com.msb.homeloan.core.model.request.UploadPresignedUrlRequest;
import vn.com.msb.homeloan.core.model.request.cic.CicCodeRequest;
import vn.com.msb.homeloan.core.model.request.cic.CicQueryRequest;
import vn.com.msb.homeloan.core.model.response.cic.CicCodeDetail;
import vn.com.msb.homeloan.core.model.response.cic.CicCodeResponse;
import vn.com.msb.homeloan.core.model.response.cic.CicQueryData;
import vn.com.msb.homeloan.core.model.response.cic.CicQueryResponse;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CicService;
import vn.com.msb.homeloan.core.service.FileService;
import vn.com.msb.homeloan.core.service.PDFGenerationService;
import vn.com.msb.homeloan.core.util.*;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static liquibase.repackaged.org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Slf4j
@Service
@AllArgsConstructor
public class CicServiceImpl implements CicService {

  private final CicClient cicClient;
  private final CicRepository cicRepository;
  private final CicItemRepository cicItemRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final LoanPayerRepository loanPayerRepository;
  private final MarriedPersonRepository marriedPersonRepository;
  private final EnvironmentProperties envProperties;
  private final CMSTabActionService cmsTabActionService;
  private final BusinessIncomeRepository businessIncomeRepository;
  private final CollateralOwnerRepository collateralOwnerRepository;
  private final CmsUserRepository cmsUserRepository;
  private final OrganizationRepository organizationRepository;
  private final PDFGenerationService pdfGenerationService;
  private final EnvironmentProperties environmentProperties;
  private final FileService fileService;
  private final FileConfigRepository fileConfigRepository;
  private final LoanUploadFileRepository loanUploadFileRepository;

  @Override
  public List<CMSCic> checkTypeDebt(List<CMSCicRequest.Info> infos, String loanApplicationId)
      throws Exception {
    LoanApplicationEntity loanApplicationEntity =
    loanApplicationRepository.findById(loanApplicationId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    cmsTabActionService.save(loanApplicationId, CMSTabEnum.CUSTOMER_CHECK);

    CmsUserReqCIC user = getCmsUserReqCIC();

    List<CMSCic> cmsCics = new ArrayList<>();
    for (CMSCicRequest.Info info : infos) {
      List<CicInformation> cicInformations = new ArrayList<>();
      List<String> identityCards = info.getIdentityCards();
      for (String identityCard : identityCards) {
        CicInformation cicInformation;
        if (CICRelationshipTypeEnum.ENTERPRISE.equals(info.getRelationshipType())
            || CICRelationshipTypeEnum.WITH_REGISTRATION.equals(info.getRelationshipType())) {
          cicInformation = new CicInformation(identityCard, CustomerType.BUSINESS,
              info.getCustomerName());
        } else {
          cicInformation = new CicInformation(identityCard, CustomerType.INDIVIDUAL,
              info.getCustomerName());
        }
        cicInformations.add(cicInformation);
      }
      CMSCic cmsCic = checkIdentityIds(cicInformations, loanApplicationId,
          info.getRelationshipType(), user);
      for (CMSCicItem cicItem : cmsCic.getCicItems()) {
        if (cicItem.getActive().equals(false)) {
          throw new ApplicationException(ErrorEnum.CALL_API_CIC_ERROR);
        }
        if (!info.getRelationshipType().equals(CICRelationshipTypeEnum.ENTERPRISE)
            && !info.getRelationshipType().equals(CICRelationshipTypeEnum.WITH_REGISTRATION))
          uploadCic(cicItem, loanApplicationEntity, info.getRelationshipType());
      }
      cmsCic.setName(info.getCustomerName());
      cmsCics.add(cmsCic);
    }
    return cmsCics;
  }

  private void uploadCic(CMSCicItem cicItem,LoanApplicationEntity loanApplicationEntity,CICRelationshipTypeEnum type){
    byte[] cicPdf = exportPDFFile(cicItem.getCicId(),cicItem.getIdentityCard());
    String path = String.format("%s/%s/%s/", Constants.SERVICE_NAME,
      DateUtils.convertToSimpleFormat(Date.from(loanApplicationEntity.getCreatedAt()), "yyyyMMdd"),
      loanApplicationEntity.getUuid());
    UploadPresignedUrlRequest uploadPresignedUrlRequest = UploadPresignedUrlRequest.builder()
      .clientCode(environmentProperties.getClientCode())
      .scopes(environmentProperties.getClientScopes())
      .documentType("document")
      .priority(1)
      .filename(Constants.FILE_NAME_CIC)
      .path(path)
      .build();
    fileService.upload(uploadPresignedUrlRequest, cicPdf, true);

    FileConfigEntity fileConfigEntity = fileConfigRepository.findByCode(
      type.getCicCode()).orElseThrow(
      () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "file_config:code",
        type.getCicCode()));
    // cap nhat loan upload file
    LoanUploadFileEntity loanUploadFileEntity =
        loanUploadFileRepository
            .findFirstByLoanApplicationIdAndFileConfigIdAndStatus(
                loanApplicationEntity.getUuid(),
                fileConfigEntity.getUuid(),
                LoanUploadFileStatusEnum.UPLOADED)
            .map(
                obj -> {
                  obj.setUpdatedAt(Instant.now());
                  return Optional.of(obj);
                })
            .orElseGet(
                () ->
                    Optional.of(
                        LoanUploadFileEntity.builder()
                            .fileConfigId(fileConfigEntity.getUuid())
                            .loanApplicationId(loanApplicationEntity.getUuid())
                            .folder(
                                String.format(
                                    "%s/%s/%s/",
                                    Constants.SERVICE_NAME,
                                    DateUtils.convertToSimpleFormat(
                                        Date.from(loanApplicationEntity.getCreatedAt()),
                                        "yyyyMMdd"),
                                    loanApplicationEntity.getUuid()))
                            .fileName(Constants.FILE_NAME_CIC)
                            .status(LoanUploadFileStatusEnum.UPLOADED)
                            .build()))
            .get();

    loanUploadFileRepository.save(loanUploadFileEntity);
  }

  private byte[] exportPDFFile(String cicId, String identityCard){
    String path = pdfGenerationService.convertToPDF(cicId, identityCard);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (InputStream is = Files.newInputStream(Paths.get(path))) {
      int len;
      byte[] buffer = new byte[4096];
      while ((len = is.read(buffer, 0, buffer.length)) != -1) {
        baos.write(buffer, 0, len);
      }
    } catch (Exception exception) {
      log.error("Exception: ", exception);
    }
    return baos.toByteArray();
  }

  @Override
  public List<CMSGetCicInfo> getCic(String loanId) {
    List<CMSGetCicInfo> result = new ArrayList<>();
    LoanApplicationEntity loanApplication = loanApplicationRepository.findByUuid(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    CMSGetCicInfo cicInfoCustomer = new CMSGetCicInfo();
    cicInfoCustomer.setName(loanApplication.getFullName());
    cicInfoCustomer.setRelationshipType(CICRelationshipTypeEnum.CUSTOMER);
    List<String> identityCardsCustomer = new ArrayList<>(
        Arrays.asList(loanApplication.getIdNo(), loanApplication.getOldIdNo(),
            loanApplication.getOldIdNo2(), loanApplication.getOldIdNo3()));
    cicInfoCustomer.setCicItems(getCicInfoByIdentityCards(identityCardsCustomer, loanId));
    result.add(cicInfoCustomer);

    CMSGetCicInfo cicInfoMarriedPerson = new CMSGetCicInfo();
    MarriedPersonEntity marriedPerson = marriedPersonRepository.findOneByLoanId(loanId);
    if (marriedPerson != null) {
      cicInfoMarriedPerson.setName(marriedPerson.getFullName());
      cicInfoMarriedPerson.setRelationshipType(CICRelationshipTypeEnum.WIFE_HUSBAND);
      List<String> identityCardsMarriedPerson = new ArrayList<>(
          Arrays.asList(marriedPerson.getIdNo(), marriedPerson.getOldIdNo(),
              marriedPerson.getOldIdNo2(), marriedPerson.getOldIdNo3()));
      cicInfoMarriedPerson.setCicItems(
          getCicInfoByIdentityCards(identityCardsMarriedPerson, loanId));
      result.add(cicInfoMarriedPerson);
    }

    List<LoanPayerEntity> loanPayers = loanPayerRepository.findByLoanId(loanId);
    if (!CollectionUtils.isEmpty(loanPayers)) {
      for (LoanPayerEntity loanPayerEntity : loanPayers) {
        CMSGetCicInfo cicInfoLoanPayer = new CMSGetCicInfo();
        cicInfoLoanPayer.setName(loanPayerEntity.getFullName());
        cicInfoLoanPayer.setRelationshipType(CICRelationshipTypeEnum.LOAN_PAYER);
        List<String> identityCardsLoanPayer = new ArrayList<>(
            Arrays.asList(loanPayerEntity.getIdNo(), loanPayerEntity.getOldIdNo(),
                loanPayerEntity.getOldIdNo2(), loanPayerEntity.getOldIdNo3()));
        cicInfoLoanPayer.setCicItems(getCicInfoByIdentityCards(identityCardsLoanPayer, loanId));
        result.add(cicInfoLoanPayer);
      }
    }

    List<BusinessIncomeEntity> businessIncomes = businessIncomeRepository.findByLoanApplicationIdAndBusinessTypeIn(
        loanId, Arrays.asList(BusinessTypeEnum.ENTERPRISE, BusinessTypeEnum.WITH_REGISTRATION));
    if (!CollectionUtils.isEmpty(businessIncomes)) {
      for (BusinessIncomeEntity businessIncomeEntity : businessIncomes) {
        CMSGetCicInfo cicInfoBusiness = new CMSGetCicInfo();
        cicInfoBusiness.setName(businessIncomeEntity.getName());
        cicInfoBusiness.setRelationshipType(
            CICRelationshipTypeEnum.valueOf(businessIncomeEntity.getBusinessType().getCode()));
        List<String> businessCodes = new ArrayList<>(
            Arrays.asList(businessIncomeEntity.getBusinessCode()));
        cicInfoBusiness.setCicItems(getCicInfoByIdentityCards(businessCodes, loanId));
        result.add(cicInfoBusiness);
      }
    }

    List<CollateralOwnerEntity> collateralOwners = collateralOwnerRepository.getByLoanId(loanId);
    if (!CollectionUtils.isEmpty(collateralOwners)) {
      for (CollateralOwnerEntity collateralOwnerEntity : collateralOwners) {
        CMSGetCicInfo cicInfoCollateralOwner = new CMSGetCicInfo();
        cicInfoCollateralOwner.setName(collateralOwnerEntity.getFullName());
        cicInfoCollateralOwner.setRelationshipType(CICRelationshipTypeEnum.COLLATERAL_OWNER);
        List<String> identityCardsCollateralOwner = new ArrayList<>(
            Arrays.asList(collateralOwnerEntity.getIdNo(), collateralOwnerEntity.getOldIdNo(),
                collateralOwnerEntity.getOldIdNo2(), collateralOwnerEntity.getOldIdNo3()));
        cicInfoCollateralOwner.setCicItems(
            getCicInfoByIdentityCards(identityCardsCollateralOwner, loanId));
        result.add(cicInfoCollateralOwner);
      }
    }
    return result;
  }

  private CmsUserReqCIC getCmsUserReqCIC() {
    CmsUserReqCIC userReqCIC = new CmsUserReqCIC();
    String email = AuthorizationUtil.getEmail();
    if (ObjectUtil.isNotEmpty(email)) {
      userReqCIC.setEmail(email.split("@")[0]);
    }
    CmsUserEntity cmsUser = cmsUserRepository.findByEmail(email)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.CMS_USER_EMAIL_FOUND));
    OrganizationEntity organization = organizationRepository.findByCode(cmsUser.getBranchCode());
    if (organization.getType().equalsIgnoreCase(OrganizationTypeEnum.DVKD.getCode())) {
      OrganizationEntity branch = organizationRepository.findByCode(organization.getBranchCode());
      userReqCIC.setDealingRoomCode(organization.getCicCode());
      userReqCIC.setBranchCode(branch.getCicCode());
    } else {
      userReqCIC.setBranchCode(organization.getCicCode());
    }
    return userReqCIC;
  }

  private List<CMSCicItem> getCicInfoByIdentityCards(List<String> identityCards, String loanId) {
    identityCards.removeIf(id -> StringUtils.isEmpty(id));
    List<String> identityCardsClone = new ArrayList<>(identityCards);
    List<CicItemEntity> cicItemEntities = cicItemRepository.findByLoanApplicationAndIdentityCardInAndActive(
        loanId, new HashSet<>(identityCards), true);
    List<CMSCicItem> cmsCicItems = CicItemMapper.INSTANCE.toModels(cicItemEntities);

    emptyIfNull(cicItemEntities).forEach(
        cicItemEntity -> identityCards.removeIf(i -> i.equals(cicItemEntity.getIdentityCard())));

    emptyIfNull(identityCards).forEach(
        identityCard -> cmsCicItems.add(CMSCicItem.builder().identityCard(identityCard).build()));

    List<CMSCicItem> result = new ArrayList<>();
    for (String id : identityCardsClone) {
      result.add(cmsCicItems.stream().filter(ct -> ct.getIdentityCard().equals(id))
          .collect(Collectors.toList()).get(0));
    }
    return result;
  }

  @Transactional
  private CMSCic checkIdentityIds(List<CicInformation> cicInformations, String loanApplicationId,
      CICRelationshipTypeEnum relationshipType, CmsUserReqCIC user) throws Exception {
    log.info("Begin check cic with input: " + cicInformations.toString());
    List<IdentityCicQueryResult> identityCicQueryResults = new ArrayList<>();
    Set<String> cicCodeSet = new HashSet<>();
    Set<String> identityCards = new HashSet<>();
    for (CicInformation request : cicInformations) {
      identityCards.add(request.getCustomerUniqueId());
    }
    checkIdentityCards(identityCards, loanApplicationId, relationshipType);
    CicEntity cic = cicRepository.findByLoanApplicationIdAndIdentityCards(loanApplicationId,
        identityCards);
    if (cic == null) {
      cic = new CicEntity();
      cic.setLoanApplicationId(loanApplicationId);
      cicRepository.save(cic);
    }
    List<CicItemEntity> cicItemEntities = new ArrayList<>();
    for (CicInformation request : cicInformations) {
      CicItemEntity cicItem = cicItemRepository.findByCicIdAndIdentityCard(cic.getUuid(),
          request.getCustomerUniqueId());
      IdentityCicQueryResult result = new IdentityCicQueryResult();
      try {
        result = checkIdentityId(request, cicCodeSet, user);
      } catch (Exception e) {
        if (cicItem == null) {
          cicItem = CicItemEntity.builder()
              .cicCode(result.getCicCode())
              .cicId(cic.getUuid())
              .identityCard(request.getCustomerUniqueId())
              .description(CicQueryStatusEnum.SYNC_ERROR)
              .build();
        }
        log.error("Call api cic error: ", e);
        cicItem.setActive(false);
        cicItemEntities.add(cicItem);
        continue;
      }
      if (cicItem == null) {
        cicItem = CicItemEntity.builder()
            .cicCode(result.getCicCode())
            .cicId(cic.getUuid())
            .identityCard(request.getCustomerUniqueId())
            .build();
      } else {
        cicItem = CicItemEntity.builder()
            .uuid(cicItem.getUuid())
            .cicId(cicItem.getCicId())
            .identityCard(cicItem.getIdentityCard())
            .cicCode(cicItem.getCicCode())
            .build();
      }
      cicItem.setActive(true);
      cicItem.setCheckDate(new Date());
      //trường hợp ng có 2cmt cùng cicCode
      if (result != null && result.getCicCode() != null) {
        IdentityCicQueryResult finalResult = result;
        List<CicItemEntity> cicItems = cicItemEntities.stream().filter(
                ci -> ci.getCicCode() != null && ci.getCicCode().equals(finalResult.getCicCode()))
            .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(cicItems)) {
          CicItemEntity ci = cicItems.get(0);
          cicItem.setTypeDebt(ci.getTypeDebt());
          cicItem.setTypeDebt12(ci.getTypeDebt12());
          cicItem.setTypeDebt24(ci.getTypeDebt24());
          cicItem.setDescription(ci.getDescription());
          cicItem.setPass(ci.getPass());
          cicItem.setAmountLoanMsbSecure(ci.getAmountLoanMsbSecure());
          cicItem.setAmountLoanMsbUnsecure(ci.getAmountLoanMsbUnsecure());
          cicItem.setAmountLoanNonMsbSecure(ci.getAmountLoanNonMsbSecure());
          cicItem.setAmountLoanNonMsbUnsecure(ci.getAmountLoanNonMsbUnsecure());
          cicItem.setAmountCardMsb(ci.getAmountCardMsb());
          cicItem.setAmountCardNonMsb(ci.getAmountCardNonMsb());
          cicItem.setAmountOverDraftMsb(ci.getAmountOverDraftMsb());
          cicItemEntities.add(cicItem);
          continue;
        }
      }

      if (result.getCicQueryResult() != null && result.getMetaData() != null) {
        if (result.getCicQueryResult().getCicCode() != null) {
          cicCodeSet.add(result.getCicQueryResult().getCicCode());
          result.setPass(false);
          evaluatePassCic(result);
        } else {
          cicCodeSet.add(result.getCicQueryResult().getCicCode());
          cicItem.setPass(true);
          cicItem.setDescription(result.getCicQueryResult().getValue().getStatus());
          cicItem.setMetaData(result.getMetaData());
          cicItemEntities.add(cicItem);
          continue;
        }
      }
      identityCicQueryResults.add(result);
      if (result != null && result.getCicQueryResult() != null) {
        CicQueryData.CicQueryDetail cicQueryDetail = result.getCicQueryResult().getValue();

        if (!CicQueryStatusEnum.isOkData(result.getCicQueryResult().getValue().getStatus(),
            result.getCicQueryResult().getValue().getContent())) {
          cicItem.setDescription(cicQueryDetail == null ? null : cicQueryDetail.getStatus());
          cicItem.setMetaData(result.getMetaData());
          cicItem.setPass(true);
          if (CicQueryStatusEnum.WAITING_RESPONSE.equals(
              result.getCicQueryResult().getValue().getStatus())) {
            cicItem.setPass(null);
          }
          cicItemEntities.add(cicItem);
        } else {
          if (cicQueryDetail.getContent() != null) {
            CicContent cicContent = CalcCicHelper.getCicContent(cicQueryDetail.getContent());
            if (CustomerType.INDIVIDUAL.equals(request.getCustomerType())) {
              CalcCicHelper.calculate(cicContent, cicItem);
            }
            cicItem.setCicContent(cicQueryDetail.getContent());
            cicItem.setTypeDebt(cicQueryDetail == null ? null : cicQueryDetail.getTypeDebt());
            cicItem.setTypeDebt12(cicQueryDetail == null ? null : cicQueryDetail.getTypeDebt12());
            cicItem.setTypeDebt24(cicQueryDetail == null ? null : cicQueryDetail.getTypeDebt24());
            cicItem.setPass(result.isPass());
          } else {
            cicItem.setPass(null);
          }
          cicItem.setDescription(cicQueryDetail == null ? null : cicQueryDetail.getStatus());
          cicItem.setMetaData(result.getMetaData());
          cicItemEntities.add(cicItem);
        }
      }
    }
    cicItemRepository.saveAll(cicItemEntities);
    CMSCic cmsCic = CicMapper.INSTANCE.toModel(cic);
    List<CMSCicItem> cmsCicItems = CicItemMapper.INSTANCE.toModels(cicItemEntities);
    cmsCic.setCicItems(cmsCicItems);
    return cmsCic;
  }

  //tránh trường hợp call đơn lẻ tạo ra các records cic khác nhau
  private void checkIdentityCards(Set<String> identityCards, String loanApplicationId,
      CICRelationshipTypeEnum relationshipType) {
    if (identityCards.size() == 1) {
      List<String> ids = new ArrayList<>();
      if (relationshipType.equals(CICRelationshipTypeEnum.CUSTOMER)) {
        LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
                loanApplicationId)
            .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
        if (loanApplication != null) {
          ids = Arrays.asList(loanApplication.getIdNo(), loanApplication.getOldIdNo(),
              loanApplication.getOldIdNo2(), loanApplication.getOldIdNo3());
        }
      } else if (relationshipType.equals(CICRelationshipTypeEnum.LOAN_PAYER)) {
        LoanPayerEntity loanPayerEntity = loanPayerRepository.findByLoanIdAndIdentities(
            loanApplicationId, identityCards);
        if (loanPayerEntity != null) {
          ids = Arrays.asList(loanPayerEntity.getIdNo(), loanPayerEntity.getOldIdNo(),
              loanPayerEntity.getOldIdNo2(), loanPayerEntity.getOldIdNo3());
        }
      } else if (relationshipType.equals(CICRelationshipTypeEnum.WIFE_HUSBAND)) {
        MarriedPersonEntity marriedPersonEntity = marriedPersonRepository.findOneByLoanId(
            loanApplicationId);
        if (marriedPersonEntity != null) {
          ids = Arrays.asList(marriedPersonEntity.getIdNo(), marriedPersonEntity.getOldIdNo(),
              marriedPersonEntity.getOldIdNo2(), marriedPersonEntity.getOldIdNo3());
        }
      } else if (relationshipType.equals(CICRelationshipTypeEnum.COLLATERAL_OWNER)) {
        CollateralOwnerEntity collateralOwnerEntity = collateralOwnerRepository.findByLoanIdAndIdentities(
            loanApplicationId, identityCards);
        if (collateralOwnerEntity != null) {
          ids = Arrays.asList(collateralOwnerEntity.getIdNo(), collateralOwnerEntity.getOldIdNo());
        }
      }
      identityCards = new HashSet<>(ids);
    }
  }

  private IdentityCicQueryResult checkIdentityId(
      CicInformation cicInformation, Set<String> cicCodeSet, CmsUserReqCIC user) throws Exception {
    IdentityCicQueryResult result = new IdentityCicQueryResult();
    result.setIdentifyNumber(cicInformation.getCustomerUniqueId());
    ModelMapper mapper = new ModelMapper();
    CicCodeRequest cicCodeRequest = mapper.map(cicInformation, CicCodeRequest.class);
    CicCodeResponse cicCodeResponse = searchCode(cicCodeRequest);
    //trường hợp ko tồn tại cicCode
    if (cicCodeResponse != null && CollectionUtils.isEmpty(cicCodeResponse.getData())) {
      result.setIdentifyNumber(cicInformation.getCustomerUniqueId());
      CicQueryResult cicQueryResult = new CicQueryResult();
      CicQueryData.CicQueryDetail cicQueryDetail = new CicQueryData.CicQueryDetail();
      cicQueryDetail.setStatus(CicQueryStatusEnum.NO_CIC_CODE);
      cicQueryResult.setValue(cicQueryDetail);
      result.setCicQueryResult(cicQueryResult);
      return result;
    }
    result.setCicCodeDetails(cicCodeResponse != null ? cicCodeResponse.getData() : null);
    final CicCodeDetail codeDetail =
        this.getCicCodeDetail(
            cicCodeResponse != null ? cicCodeResponse.getData() : new ArrayList<>(),
            cicInformation.getCustomerName());
    if (codeDetail == null) {
      result.setIdentifyNumber(cicInformation.getCustomerUniqueId());
      CicQueryResult cicQueryResult = new CicQueryResult();
      CicQueryData.CicQueryDetail cicQueryDetail = new CicQueryData.CicQueryDetail();
      cicQueryDetail.setStatus(CicQueryStatusEnum.NO_CIC_NO_INFO);
      cicQueryResult.setValue(cicQueryDetail);
      result.setCicQueryResult(cicQueryResult);
      return result;
    } else {
      //trường hợp ng có 2cmt cùng cicCode
      if (!StringUtils.isEmpty(codeDetail.getCicCode())
          && cicCodeSet.contains(codeDetail.getCicCode())) {
        return result;
      } else if (StringUtils.isEmpty(
          codeDetail.getCicCode())) {//trường hợp ko có mã cicCode phù hợp
        result.setIdentifyNumber(cicInformation.getCustomerUniqueId());
        CicQueryResult cicQueryResult = new CicQueryResult();
        CicQueryData.CicQueryDetail cicQueryDetail = new CicQueryData.CicQueryDetail();
        cicQueryDetail.setStatus(CicQueryStatusEnum.NO_CIC_CODE);
        cicQueryResult.setValue(cicQueryDetail);
        result.setCicQueryResult(cicQueryResult);
        return result;
      }
      result.setCicCode(codeDetail.getCicCode());
      CicQueryRequest queryRequest =
          new CicQueryRequest(
              codeDetail.getCicCode(),
              cicInformation.getCustomerUniqueId(),
              cicInformation.getCustomerType(),
              ProductCode.TONG_HOP,
              30,
              codeDetail.getAddress(),
              codeDetail.getCustomerName(),
              user.getEmail(),
              user.getBranchCode(),
              user.getDealingRoomCode()
          );
      final CicQueryResponse cicQueryResponse = search(queryRequest);
      CicQueryResult cicQueryResult = new CicQueryResult();
      if (cicQueryResponse != null) {
        result.setMetaData(cicQueryResponse.toString());
        cicQueryResult = mapper.map(cicQueryResponse.getData(), CicQueryResult.class);
      }
      result.setCicQueryResult(cicQueryResult);
      final CicQueryData.CicQueryDetail cicQueryDetail =
          (cicQueryResponse != null && cicQueryResponse.getData() != null)
              ? cicQueryResponse.getData().getValue() : null;
      setKnockout(result, cicInformation, cicQueryDetail, cicQueryResult, codeDetail);
    }
    return result;
  }

  private IdentityCicQueryResult setKnockout(IdentityCicQueryResult result,
      CicInformation cicInformation, CicQueryData.CicQueryDetail cicQueryDetail,
      CicQueryResult cicQueryResult, CicCodeDetail codeDetail) {
    if (cicQueryDetail != null && cicQueryDetail.getContent() != null
        && CicQueryStatusEnum.isOkData(cicQueryDetail.getStatus(), cicQueryDetail.getContent())) {
      boolean isKnockout = checkContent(cicQueryDetail.getContent(), cicQueryDetail,
          cicInformation.getCustomerType());
      cicQueryResult.setKnockout(isKnockout);
      cicQueryResult.setCicCode(codeDetail.getCicCode());
      return result;
    } else if (cicQueryDetail != null && cicQueryDetail.getContent() == null
        && CicQueryStatusEnum.isOkData(cicQueryDetail.getStatus(), cicQueryDetail.getContent())) {
      cicQueryResult.setKnockout(true);
      cicQueryResult.setCicCode(codeDetail.getCicCode());
      return result;
    } else {
      cicQueryResult.setKnockout(false);
      cicQueryResult.setCicCode(codeDetail.getCicCode());
      return result;
    }
  }

  @SneakyThrows
  private CicCodeDetail getCicCodeDetail(@Nonnull List<CicCodeDetail> codeResponse,
      String customerName) {
    CicCodeDetail outputCode = null;
    // case only one CIC code
    if (codeResponse.size() == 1) {
      outputCode = codeResponse.get(0);
    } else if (codeResponse.size() == 0) {
      outputCode = new CicCodeDetail();
    }
    // case many CIC code => check customer name
    else {
      log.info("getCicCodeDetail: There are many codes, filtering customer names.");
      final List<CicCodeDetail> listCode = getDistinctCicCodeDetails(codeResponse);
      if (listCode.size() == 1) {
        outputCode = codeResponse.get(0);
      } else {
        final Predicate<CicCodeDetail> matchName =
            item -> {
              final String sourceName =
                  NameUtils.removeVietnameseChars(customerName.replaceAll("\\s", ""));
              final String targetName =
                  NameUtils.removeVietnameseChars(item.getCustomerName().replaceAll("\\s", ""));
              return targetName.equalsIgnoreCase(sourceName);
            };
        if (listCode.stream().anyMatch(matchName)) {
          Optional<CicCodeDetail> op = listCode.stream().filter(matchName).findFirst();
          if (op.isPresent()) {
            outputCode = op.get();
          }
        } else {
          return null;
        }
      }
    }
    return outputCode;
  }

  @NotNull
  private List<CicCodeDetail> getDistinctCicCodeDetails(List<CicCodeDetail> codeResponse) {
    final List<CicCodeDetail> listCode = codeResponse.stream()
        .filter(StreamUtils.distinctBy(CicCodeDetail::getCicCode))
        .collect(Collectors.toList());
    return listCode;
  }

  private CicCodeResponse searchCode(CicCodeRequest request) throws Exception {
    log.info("search code cic: " + request);
    ResponseEntity<CicCodeResponse> cicCode = cicClient.searchCode(request,
        envProperties.getClientCode());
    if (cicCode != null) {
      CicCodeResponse cicCodeResponse = cicCode.getBody();
      log.info("search cic code response: " + cicCodeResponse);
      return cicCodeResponse;
    } else {
      throw new Exception("Api cic search-code fail");
    }
  }

  private CicQueryResponse search(CicQueryRequest request) throws Exception {
    log.info("search cic: " + request);
    ResponseEntity<CicQueryResponse> cicQuery = cicClient.search(request,
        envProperties.getClientCode());
    if (cicQuery != null) {
      CicQueryResponse cicQueryResponse = cicQuery.getBody();
      log.info("search cic response: " + cicQueryResponse);
      return cicQueryResponse;
    } else {
      throw new Exception("Api cic search fail");
    }
  }

  private boolean checkContent(String xmlContent, CicQueryData.CicQueryDetail cicQueryDetail,
      CustomerType customerType) {
    CicParser cicParser = XmlCicParser.of(xmlContent);
//        CicDebtReport debtReport = cicParser.findCurrentTotalDebtReport();
    CicGroupEnum currentGroup;
    CicGroupEnum lastYearGroup;
    CicGroupEnum last2YearsGroup;
    if (CustomerType.INDIVIDUAL.equals(customerType)) {
      currentGroup = CicGroupEnum.max(cicParser.findCurrentDebtGroupNaturalPerson());
      lastYearGroup = CicGroupEnum.max(
          cicParser.findLastXMonthsDebtGroupNaturalPerson(12, xmlContent));
      last2YearsGroup = CicGroupEnum.max(
          cicParser.findLastXMonthsDebtGroupNaturalPerson(24, xmlContent));
    } else {
      currentGroup = CicGroupEnum.max(cicParser.findCurrentDebtGroupLegalPerson());
      lastYearGroup = CicGroupEnum.max(
          cicParser.findLastXMonthsDebtGroupLegalPerson(12, xmlContent));
      last2YearsGroup = CicGroupEnum.max(
          cicParser.findLastXMonthsDebtGroupLegalPerson(24, xmlContent));
    }
    cicQueryDetail.setTypeDebt(currentGroup);
    cicQueryDetail.setTypeDebt12(
        currentGroup.getValue() > lastYearGroup.getValue() ? currentGroup : lastYearGroup);
    List<CicGroupEnum> cicGroupEnums = Arrays.asList(currentGroup, lastYearGroup, last2YearsGroup);
    Optional<CicGroupEnum> opCicGroupEnum = cicGroupEnums.stream()
        .max(Comparator.comparingInt(CicGroupEnum::getValue));
    if (opCicGroupEnum.isPresent()) {
      cicQueryDetail.setTypeDebt24(opCicGroupEnum.get());
    }

    if (currentGroup.getValue() > 1
        || lastYearGroup.getValue() > 1
        || last2YearsGroup.getValue() > 2) {
      return false;
    }
    return true;
  }

  private void evaluatePassCic(IdentityCicQueryResult identityCicQueryResult) {
    if (isPassByCicCodeResponse(identityCicQueryResult)) {
      identityCicQueryResult.setPass(true);
    } else {
      CicQueryResult result = identityCicQueryResult.getCicQueryResult();
      if (result.isKnockout()) {
        identityCicQueryResult.setPass(true);
      }
    }
  }

  private boolean isPassByCicCodeResponse(IdentityCicQueryResult identityCicQueryResult) {
    List<CicCodeDetail> codeDetails = identityCicQueryResult.getCicCodeDetails();
    if (codeDetails.size() > 0) {
      return false;
    }
    return true;
  }
}
