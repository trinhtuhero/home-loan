package vn.com.msb.homeloan.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.msb.homeloan.api.dto.mapper.CMSLoanApplicationResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.LandTransactionMapper;
import vn.com.msb.homeloan.api.dto.request.DownloadPresignedUrlRequest;
import vn.com.msb.homeloan.api.dto.request.SendMailRequest;
import vn.com.msb.homeloan.api.dto.response.ApiInternalResponse;
import vn.com.msb.homeloan.api.dto.response.CMSCustomerInfoResponse;
import vn.com.msb.homeloan.api.dto.response.DataCommonResponse;
import vn.com.msb.homeloan.api.dto.response.LandTransactionResponse;
import vn.com.msb.homeloan.core.client.NotificationFeignClient;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.entity.*;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.*;
import vn.com.msb.homeloan.core.model.mapper.*;
import vn.com.msb.homeloan.core.model.request.UploadPresignedUrlRequest;
import vn.com.msb.homeloan.core.model.response.DownloadPresignedUrlResponse;
import vn.com.msb.homeloan.core.model.response.cms.role.Role;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.*;
import vn.com.msb.homeloan.core.util.*;
import vn.com.msb.homeloan.core.util.export.ExcelExporter;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class LoanApplicationServiceImpl implements LoanApplicationService {

  private final LoanApplicationItemRepository loanApplicationItemRepository;

  private final LoanApplicationRepository loanApplicationRepository;
  private final MarriedPersonRepository marriedPersonsRepository;
  private final ContactPersonRepository contactPersonsRepository;
  private final ProfileRepository profileRepository;
  private final BusinessIncomeRepository businessIncomeRepository;
  private final SalaryIncomeRepository salaryIncomeRepository;
  private final OtherIncomeRepository otherIncomeRepository;
  private final CollateralRepository collateralRepository;
  private final LoanPayerRepository loanPayerRepository;
  private final LoanApplicationSequenceRepository sequenceRepository;
  private final SendMailService sendMailService;
  private final EnvironmentProperties environmentProperties;
  private final CmsUserRepository cmsUserRepository;
  @Lazy
  private final LoanUploadFileService loanUploadFileService;
  private final FileService fileService;
  private final CmsUserService cmsUserService;
  private final LoanStatusChangeRepository loanStatusChangeRepository;
  private final ExportExcelService exportExcelService;
  private final PlaceOfIssueIdCardRepository placeOfIssueIdCardRepository;

  private final ModelMapper modelMapper;

  private final FileConfigCategoryService fileConfigCategoryService;

  private final LoanApplicationCommentRepository loanApplicationCommentRepository;
  private final UploadFileCommentRepository uploadFileCommentRepository;
  private final UploadFileStatusRepository uploadFileStatusRepository;
  private final LoanUploadFileRepository loanUploadFileRepository;
  private final ObjectMapper objectMapper;
  private final LoanPreApprovalRepository loanPreApprovalRepository;
  private final LoanApprovalHisRepository loanApprovalHisRepository;
  private final NotificationFeignClient notificationFeignClient;

  @Qualifier("fixedThreadPool")
  private final ExecutorService executorService;

  private final AssetEvaluateRepository assetEvaluateRepository;
  private final AssetEvaluateItemRepository assetEvaluateItemRepository;
  private final OtherEvaluateRepository otherEvaluateRepository;

  @Lazy
  private final CMSTabActionService cmsTabActionService;
  private final CreditAppraisalRepository creditAppraisalRepository;
  private final CicService cicService;
  private final CollateralOwnerRepository collateralOwnerRepository;
  private final FieldSurveyItemRepository fieldSurveyItemRepository;
  private final ExceptionItemRepository exceptionItemRepository;

  private final CMSTabActionRepository cmsTabActionRepository;
  private final CicRepository cicRepository;
  private final CicItemRepository cicItemRepository;
  private final OpRiskRepository opRiskRepository;
  private final CollateralOwnerMapRepository collateralOwnerMapRepository;
  private final CssRepository cssRepository;

  private final HomeLoanUtil homeLoanUtil;
  private final FileConfigRepository fileConfigRepository;
  private final CreditworthinessItemService creditworthinessItemService;
  private final CreditCardRepository creditCardRepository;

  private final CollateralService collateralService;
  private final LoanApplicationItemService loanApplicationItemService;

  private final CommonIncomeService commonIncomeService;

  private final OrganizationRepository organizationRepository;
  private final CreditInstitutionService creditInstitutionService;
  private final CardTypeService cardTypeService;
  private final CardPolicyService cardPolicyService;
  private final CreditAppraisalService creditAppraisalService;
  private final ProvinceRepository provinceRepository;

  private final CommonCMSService commonCMSService;
  private final CacheManager cacheManager;

  private final PicRmHistoryRepository picRmHistoryRepository;
  private final LoanAdviseCustomerRepository loanAdviseCustomerRepository;

  private final InsuranceService insuranceService;
  private final OverdraftService overdraftService;

  private final LandTransactionService landTransactionService;

  @Override
  public LoanApplication findById(String uuid) {
    LoanApplicationEntity entity = loanApplicationRepository.findById(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));

    return LoanApplicationMapper.INSTANCE.toModel(entity);
  }

  @Override
  public LoanApplication save(LoanApplication loanInformation, ClientTypeEnum clientType) {
    String currentStep = LoanCurrentStepEnum.LOAN_CUSTOMER.getCode();
    String profileId;
    boolean isInsert = false;
    LoanInfoStatusEnum status = LoanInfoStatusEnum.DRAFT;
    if (!StringUtils.isEmpty(loanInformation.getUuid())) {
      LoanApplicationEntity entity = loanApplicationRepository.findById(loanInformation.getUuid())
          .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
      checkEditLoanApp(entity, clientType);
      currentStep = entity.getCurrentStep();
      status = entity.getStatus();
      profileId = entity.getProfileId();
    } else {
      isInsert = true;
      if (clientType.equals(ClientTypeEnum.LDP)) {
        profileId = AuthorizationUtil.getUserId();
        List<LoanApplicationEntity> entities = loanApplicationRepository.findByProfileId(profileId);
        if (!CollectionUtils.isEmpty(entities)) {
          List<LoanApplicationEntity> entitiesTemp = entities.stream()
              .filter(
                  x -> x.getStatus().getCode().equalsIgnoreCase(LoanInfoStatusEnum.DRAFT.getCode()))
              .collect(Collectors.toList());
          if (!CollectionUtils.isEmpty(entitiesTemp)) {
            throw new ApplicationException(ErrorEnum.LOAN_APPLICATION_INSERT_NOT_ALLOW);
          }
        }
      } else {
        profileId = loanInformation.getProfileId();
        status = LoanInfoStatusEnum.PROCESSING;
        loanInformation.setRefCode(AuthorizationUtil.getEmail());
        Optional<CmsUserEntity> cmsUser = cmsUserRepository.findByEmail(
            AuthorizationUtil.getEmail());
        loanInformation.setPicRm(
            cmsUser.isPresent() ? cmsUser.get().getEmplId() : AuthorizationUtil.getUserId());
        loanInformation.setReceiveDate((new Date()).toInstant());
      }
    }

    ProfileEntity profile;
    if (StringUtils.isEmpty(profileId)) {
      profile = profileRepository.findOneByPhone(loanInformation.getPhone())
          .orElseThrow(() -> new ApplicationException(ErrorEnum.PROFILE_NOT_FOUND,
              loanInformation.getProfileId()));
      profileId = profile.getUuid();
    } else {
      profile = profileRepository.findById(profileId)
          .orElseThrow(() -> new ApplicationException(ErrorEnum.PROFILE_NOT_FOUND,
              loanInformation.getProfileId()));
    }

    LoanApplicationEntity entity = LoanApplicationMapper.INSTANCE.toEntity(loanInformation);
    entity.setPhone(profile.getPhone());
    entity.setCurrentStep(currentStep);
    entity.setStatus(status);
    entity.setProfileId(profileId);
    entity.setReceiveChannel(
        clientType.equals(ClientTypeEnum.LDP) ? ReceiveChannelEnum.LDP : ReceiveChannelEnum.CMS);

    //Update profile
    if (LoanInfoStatusEnum.DRAFT.getCode().equalsIgnoreCase(status.getCode())) {
      ProfileEntity profileSave = loanToProfileEntity(loanInformation);
      profileSave.setUuid(profile.getUuid());
      profileSave.setPhone(profile.getPhone());
      profileSave.setStatus(profile.getStatus());
      profileSave.setNumberOfDependents(entity.getNumberOfDependents());
      profileRepository.save(profileSave);
    }

    String loanCode = StringUtils.isEmpty(entity.getLoanCode()) ? getLoanCode(clientType.toString())
        : entity.getLoanCode();
    if (StringUtils.isEmpty(loanCode)) {
      throw new ApplicationException(ErrorEnum.LOAN_APPLICATION_GEN_LOAN_CODE_FAIL);
    }
    entity.setLoanCode(loanCode);
    if (!StringUtils.isEmpty(entity.getRefCode()) && !entity.getRefCode().endsWith("@msb")) {
      entity.setRefCode(entity.getRefCode().toLowerCase().trim().replace("@msb.com.vn", ""));
      entity.setRefCode(String.format("%s%s", entity.getRefCode(), "@msb"));
    }
    entity = loanApplicationRepository.save(entity);
    //Save his
    if (isInsert) {
      LoanStatusChangeEntity loanStatusChangeEntity = LoanStatusChangeEntity.builder()
          .loanApplicationId(entity.getUuid())
          .statusTo(entity.getStatus())
          .note("add new loan")
          .build();
      loanStatusChangeRepository.save(loanStatusChangeEntity);
    }

    return LoanApplicationMapper.INSTANCE.toModel(entity);
  }

  private <T> ProfileEntity loanToProfileEntity(T obj) {
    ProfileEntity.ProfileEntityBuilder<?, ?> profileEntity = ProfileEntity.builder();
    if (obj == null) {
      return profileEntity.build();
    }
    if (obj instanceof LoanApplication) {
      LoanApplication model = (LoanApplication) obj;
      profileEntity.phone(model.getPhone());
      profileEntity.fullName(model.getFullName());
      profileEntity.gender(model.getGender());
      if (model.getNationality() != null) {
        profileEntity.nationality(model.getNationality());
      }
      profileEntity.email(model.getEmail());
      profileEntity.idNo(model.getIdNo());
      profileEntity.issuedOn(model.getIssuedOn());
      profileEntity.birthday(model.getBirthday());
      profileEntity.placeOfIssue(model.getPlaceOfIssue());
      profileEntity.oldIdNo(model.getOldIdNo());
      profileEntity.province(model.getProvince());
      profileEntity.provinceName(model.getProvinceName());
      profileEntity.district(model.getDistrict());
      profileEntity.districtName(model.getDistrictName());
      profileEntity.ward(model.getWard());
      profileEntity.wardName(model.getWardName());
      profileEntity.numberOfDependents(model.getNumberOfDependents());
      profileEntity.address(model.getAddress());
      profileEntity.refCode(model.getRefCode());
      profileEntity.maritalStatus(model.getMaritalStatus());
    }
    if (obj instanceof LoanApplicationEntity) {
      LoanApplicationEntity model = (LoanApplicationEntity) obj;
      profileEntity.phone(model.getPhone());
      profileEntity.fullName(model.getFullName());
      profileEntity.gender(model.getGender());
      if (model.getNationality() != null) {
        profileEntity.nationality(model.getNationality());
      }
      profileEntity.email(model.getEmail());
      profileEntity.idNo(model.getIdNo());
      profileEntity.issuedOn(model.getIssuedOn());
      profileEntity.birthday(model.getBirthday());
      profileEntity.placeOfIssue(model.getPlaceOfIssue());
      profileEntity.oldIdNo(model.getOldIdNo());
      profileEntity.province(model.getProvince());
      profileEntity.provinceName(model.getProvinceName());
      profileEntity.district(model.getDistrict());
      profileEntity.districtName(model.getDistrictName());
      profileEntity.ward(model.getWard());
      profileEntity.wardName(model.getWardName());
      profileEntity.numberOfDependents(model.getNumberOfDependents());
      profileEntity.address(model.getAddress());
      profileEntity.refCode(model.getRefCode());
      profileEntity.maritalStatus(model.getMaritalStatus());
    }
    return profileEntity.build();
  }

  @Override
  public LoanApplication updateCustomer(LoanApplication loanApplication) {
    LoanApplicationEntity entity = loanApplicationRepository.findById(loanApplication.getUuid())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));

    ProfileEntity profile = profileRepository.findById(loanApplication.getProfileId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.PROFILE_NOT_FOUND,
            loanApplication.getProfileId()));

    checkEditLoanApp(entity, ClientTypeEnum.LDP);

    entity = mapUpdateCustomer(loanApplication, entity);

    if (LoanInfoStatusEnum.NEED_ADDITIONAL_INFO.equals(entity.getStatus()) ||
        LoanInfoStatusEnum.DRAFT.equals(entity.getStatus())) {
      ProfileEntity profileSave = loanToProfileEntity(loanApplication);
      profileSave.setUuid(profile.getUuid());
      profileSave.setPhone(profile.getPhone());
      profileSave.setStatus(profile.getStatus());
      profileSave.setOldIdNo3(loanApplication.getOldIdNo3());
      profileSave.setNumberOfDependents(entity.getNumberOfDependents());
      profileRepository.save(profileSave);
    }

    return LoanApplicationMapper.INSTANCE.toModel(loanApplicationRepository.save(entity));
  }

  @Override
  public LoanApplicationReview review(String loanId) {
    LoanApplicationReview review = new LoanApplicationReview();
    LoanApplication loanApplication = findById(loanId);
    loanApplication.setPlaceOfIssueName(getPlaceOfIssueName(loanApplication.getPlaceOfIssue()));
    if (loanApplication.getNationality() != null) {
      loanApplication.setNationalityName(loanApplication.getNationality().getName());
    }

    List<LoanApplicationItem> loanApplicationItems = loanApplicationItemService.getItemCollateralDistribute(
        loanApplication.getUuid());
    if (!CollectionUtils.isEmpty(loanApplicationItems)) {
      List<String> codes = loanApplicationItems.stream()
          .map(LoanApplicationItem::getBeneficiaryBank)
          .filter(type -> !StringUtils.isEmpty(type))
          .collect(Collectors.toList());
      Map<String, CreditInstitutionEntity> mapTemp = creditInstitutionService.getMapByCodes(codes);
      loanApplicationItems.forEach(item -> {
        if (!StringUtils.isEmpty(item.getBeneficiaryBank())) {
          CreditInstitutionEntity creditInstitutionEntity = mapTemp.get(item.getBeneficiaryBank());
          if (creditInstitutionEntity != null) {
            item.setBeneficiaryBankName(creditInstitutionEntity.getName());
            item.setBeneficiaryBankShortName(creditInstitutionEntity.getShortName());
          }
        }
      });
      review.setLoanApplicationItems(loanApplicationItems);
    }

    review.setOverdrafts(overdraftService.getListOverdraft(loanId));

    ContactPersonEntity contactPersonEntity = contactPersonsRepository.findOneByLoanId(loanId);
    if (contactPersonEntity != null) {
      review.setContactPerson(ContactPersonInfoMapper.INSTANCE.toModel(contactPersonEntity));
    }

    if (MaritalStatusEnum.MARRIED.equals(loanApplication.getMaritalStatus())) {
      MarriedPersonEntity marriedPersonEntity = marriedPersonsRepository.findOneByLoanId(loanId);
      if (marriedPersonEntity != null) {
        review.setMarriedPerson(MarriedPersonInfoMapper.INSTANCE.toModel(marriedPersonEntity));
        review.getMarriedPerson()
            .setPlaceOfIssueName(getPlaceOfIssueName(review.getMarriedPerson().getPlaceOfIssue()));
      }
    }

    List<BusinessIncomeEntity> businessIncomeEntities = businessIncomeRepository.findByLoanApplicationIdOrderByCreatedAtAsc(
        loanId);
    if (!CollectionUtils.isEmpty(businessIncomeEntities)) {
      review.setBusinessIncomes(BusinessIncomeMapper.INSTANCE.toModels(businessIncomeEntities));
    }

    List<Collateral> collaterals = collateralService.findCollateralInfoByLoanId(loanId);
    if (!CollectionUtils.isEmpty(collaterals)) {
      collaterals.forEach(item -> {
        if (item.getDocPlaceOfIssue() != null) {
          Optional<ProvinceEntity> provinceEntity = provinceRepository.findByCode(
              item.getDocPlaceOfIssue());
          item.setDocPlaceOfIssueName(
              provinceEntity.isPresent() ? provinceEntity.get().getName() : "");
        }
      });
      review.setCollaterals(collaterals);
    }

    List<LoanPayerEntity> loanPayerEntities = loanPayerRepository.findByLoanIdOrderByCreatedAtAsc(
        loanId);
    if (!CollectionUtils.isEmpty(loanPayerEntities)) {
      List<LoanPayer> loanPayers = LoanPayerMapper.INSTANCE.toModels(loanPayerEntities);
      loanPayers.forEach(item -> {
        item.setPlaceOfIssueName(getPlaceOfIssueName(item.getPlaceOfIssue()));
      });
      review.setLoanPayers(loanPayers);
    }

    List<SalaryIncomeEntity> salaryIncomes = salaryIncomeRepository.findByLoanApplicationIdOrderByCreatedAtAsc(
        loanId);
    if (!CollectionUtils.isEmpty(salaryIncomes)) {
      review.setSalaryIncomes(SalaryIncomeMapper.INSTANCE.toModels(salaryIncomes));
    }

    List<OtherIncomeEntity> otherIncomes = otherIncomeRepository.findByLoanApplicationIdOrderByCreatedAtAsc(
        loanId);
    if (!CollectionUtils.isEmpty(otherIncomes)) {
      List<OtherIncome> otherIncomeList = OtherIncomeMapper.INSTANCE.toModels(otherIncomes);
      for (OtherIncome otherIncome : otherIncomeList) {
        List<LandTransactionResponse> responses = landTransactionService.getByOtherIncomeId(
            otherIncome.getUuid());
        otherIncome.setLandTransactions(
            LandTransactionMapper.INSTANCE.responseToRequest(responses));
      }
      review.setOtherIncomes(otherIncomeList);
    }

    //get credit card info
    List<CreditCardEntity> creditCardEntities = creditCardRepository.findByLoanIdOrderByCreatedAtAsc(
        loanId);
    List<CreditCard> lsCreditCards = creditCardEntities.stream()
        .map(CreditCardMapper.INSTANCE::toModels).collect(Collectors.toList());
    if (!CollectionUtils.isEmpty(creditCardEntities)) {
      review.setCreditCards(lsCreditCards);

      List<String> cardTypeCodes = creditCardEntities.stream()
          .map(CreditCardEntity::getType)
          .filter(type -> !StringUtils.isEmpty(type))
          .collect(Collectors.toList());
      Map<String, String> mapCardType = cardTypeService.getCardPolicyMapCodes(cardTypeCodes);

      List<String> cardPolicyCodes = creditCardEntities.stream()
          .map(CreditCardEntity::getCardPolicyCode)
          .filter(code -> !StringUtils.isEmpty(code))
          .collect(Collectors.toList());
      Map<String, String> mapCardPolicy = cardPolicyService.getCardPolicyMapCodes(cardPolicyCodes);

      review.getCreditCards().forEach(entity -> {
        entity.setTypeName(mapCardType.get(entity.getType()));
        entity.setCardPolicyName(mapCardPolicy.get(entity.getCardPolicyCode()));
      });
    }

    // Set total income
    loanApplication.setTotalIncome(
        totalIncome(salaryIncomes, businessIncomeEntities, otherIncomes));
    review.setLoanApplication(loanApplication);

    CommonIncome commonIncome = commonIncomeService.findByLoanApplicationId(loanId);
    if (commonIncome != null) {
      review.setCommonIncome(commonIncome);
    }

    return review;
  }

  @Override
  public CMSLoanApplicationReview cmsReview(String loanId) {
    LoanApplicationReview review = review(loanId);
    CMSLoanApplicationReview cmsReview = CMSLoanApplicationReview.builder()
        .loanApplication(review.getLoanApplication())
        .contactPerson(review.getContactPerson())
        .marriedPerson(review.getMarriedPerson())
        .businessIncomes(review.getBusinessIncomes())
        .salaryIncomes(review.getSalaryIncomes())
        .otherIncomes(review.getOtherIncomes())
        .collaterals(review.getCollaterals())
        .loanPayers(review.getLoanPayers())
        .creditCards(review.getCreditCards())
        .loanApplicationItems(review.getLoanApplicationItems())
        .commonIncome(review.getCommonIncome())
        .overdrafts(review.getOverdrafts())
        .build();

    CreditAppraisal creditAppraisal = creditAppraisalService.getByLoanId(loanId);
    if (creditAppraisal != null) {
      cmsReview.setCreditAppraisal(creditAppraisal);
    }

    List<CMSGetCicInfo> cics = cicService.getCic(loanId);
    if (!CollectionUtils.isEmpty(cics)) {
      cmsReview.setCics(cics);
    }

    if (checkExistSelectedIncome(cmsReview.getCommonIncome(),
        CMSTabEnum.ASSUMING_TOTAL_ASSETS_INCOME, ReceiveChannelEnum.CMS)) {
      AssetEvaluateEntity assetEvaluateEntity = assetEvaluateRepository.findByLoanApplicationId(
          loanId);
      AssetEvaluate assetEvaluate;
      if (assetEvaluateEntity != null) {
        List<AssetEvaluateItemEntity> assetEvaluateItems = assetEvaluateItemRepository.findByAssetEvalIdOrderByCreatedAtAsc(
            assetEvaluateEntity.getUuid());
        List<AssetEvaluateItem> assetEvaluateItem = AssetEvaluateItemMapper.INSTANCE.toModels(
            assetEvaluateItems);
        assetEvaluate = AssetEvaluateMapper.INSTANCE.toModel(assetEvaluateEntity);
        assetEvaluate.setAssetEvaluateItems(assetEvaluateItem);
        cmsReview.setAssetEvaluate(assetEvaluate);
      }
    }

    if (checkExistSelectedIncome(cmsReview.getCommonIncome(), CMSTabEnum.ASSUMING_OTHERS_INCOME,
        ReceiveChannelEnum.CMS)) {
      List<OtherEvaluateEntity> otherEvaluateEntities = otherEvaluateRepository.findByLoanApplicationIdOrderByCreatedAtAsc(
          loanId);
      cmsReview.setOtherEvaluates(OtherEvaluateMapper.INSTANCE.toModels(otherEvaluateEntities));
    }

    cmsReview.setFieldSurveyItems(FieldSurveyItemMapper.INSTANCE.toModels(
        fieldSurveyItemRepository.findByLoanApplicationIdOrderByCreatedAt(loanId)));
    List<ExceptionItemEntity> exceptionItemEntities = exceptionItemRepository.findByLoanApplicationId(
        loanId);
    cmsReview.setExceptionItems(ExceptionItemMapper.INSTANCE.toModels(exceptionItemEntities));
    List<CreditworthinessItem> creditworthinessItems = creditworthinessItemService.getByLoanId(
        loanId);
    if (!CollectionUtils.isEmpty(creditworthinessItems)) {
      cmsReview.setCreditworthinessItems(creditworthinessItems);
    }

    return removeIncome(cmsReview);
  }

  private CMSLoanApplicationReview removeIncome(CMSLoanApplicationReview cmsReview) {
    if (!checkExistSelectedIncome(cmsReview.getCommonIncome(), CMSTabEnum.BUSINESS_INCOME,
        cmsReview.getLoanApplication().getReceiveChannel())
        && !CollectionUtils.isEmpty(cmsReview.getBusinessIncomes())) {
      List<BusinessIncome> incomes = cmsReview.getBusinessIncomes().stream()
          .filter(x -> !BusinessTypeEnum.ENTERPRISE.equals(x.getBusinessType()))
          .collect(Collectors.toList());
      cmsReview.setBusinessIncomes(incomes);
    }

    if (!checkExistSelectedIncome(cmsReview.getCommonIncome(), CMSTabEnum.PERSONAL_BUSINESS_INCOME,
        cmsReview.getLoanApplication().getReceiveChannel())
        && !CollectionUtils.isEmpty(cmsReview.getBusinessIncomes())) {
      List<BusinessIncome> incomes = cmsReview.getBusinessIncomes().stream()
          .filter(x -> BusinessTypeEnum.ENTERPRISE.equals(x.getBusinessType()))
          .collect(Collectors.toList());

      cmsReview.setBusinessIncomes(incomes);
    }

    if (!checkExistSelectedIncome(cmsReview.getCommonIncome(), CMSTabEnum.SALARY_INCOME,
        cmsReview.getLoanApplication().getReceiveChannel())) {
      cmsReview.setSalaryIncomes(Collections.emptyList());
    }

    if (!checkExistSelectedIncome(cmsReview.getCommonIncome(), CMSTabEnum.OTHERS_INCOME,
        cmsReview.getLoanApplication().getReceiveChannel())) {
      cmsReview.setOtherIncomes(Collections.emptyList());
    }

    return cmsReview;
  }

  private boolean checkExistSelectedIncome(CommonIncome commonIncome, CMSTabEnum tabEnum,
      ReceiveChannelEnum receiveChannel) {
    if (commonIncome == null
        || commonIncome.getSelectedIncomes() == null
        || commonIncome.getSelectedIncomes().length == 0) {
      if (ReceiveChannelEnum.LDP.equals(receiveChannel)) {
        return true;
      }
      return false;
    }
    List<String> selectedIncomes = Arrays.asList(commonIncome.getSelectedIncomes());
    return selectedIncomes.contains(tabEnum.getCode());
  }

  private long totalIncome(List<SalaryIncomeEntity> salaryIncomeList,
      List<BusinessIncomeEntity> businessIncomeList, List<OtherIncomeEntity> otherIncomeList) {
    return salaryIncomeList.stream().filter(salaryIncome -> salaryIncome.getValue() != null)
        .mapToLong(salaryIncome -> salaryIncome.getValue()).sum()
        + businessIncomeList.stream().filter(businessIncome -> businessIncome.getValue() != null)
        .mapToLong(businessIncome -> businessIncome.getValue()).sum()
        + otherIncomeList.stream().filter(otherIncome -> otherIncome.getValue() != null)
        .mapToLong(otherIncome -> otherIncome.getValue()).sum();
  }

  @Override
  public LoanApplicationReview ldpReview(String loanId) {
    Optional<LoanApplicationEntity> optionalLoanApplication = loanApplicationRepository.findById(
        loanId);
    if (!optionalLoanApplication.isPresent()) {
      throw new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND);
    }

    // Nếu khách hàng từ chối hoặc RM đang soạn thảo tờ trình
    if (LoanInfoStatusEnum.DECLINED_TO_CONFIRM.equals(optionalLoanApplication.get().getStatus()) ||
        LoanInfoStatusEnum.PROCESSING.equals(optionalLoanApplication.get().getStatus())) {
      Optional<LoanPreApprovalEntity> preApproval = loanPreApprovalRepository.findById(loanId);
      if (preApproval.isPresent()) {
        try {
          LoanApplicationReview review = objectMapper.readValue(preApproval.get().getMetaData(),
              LoanApplicationReview.class);
          review.getLoanApplication()
              .setStatus(optionalLoanApplication.get().getStatus().getCode());
          return review;
        } catch (Exception exception) {
          log.error("Convert json to object error");
        }
      }
    }
    return review(loanId);
  }

  @Override
  public void checkEditLoanApp(LoanApplicationEntity loanApplication, ClientTypeEnum clientType) {
    LoanInfoStatusEnum loanStatus = loanApplication.getStatus();
    if (ClientTypeEnum.LDP.equals(clientType)) {
      if (!LoanInfoStatusEnum.DRAFT.equals(loanStatus)
          && !LoanInfoStatusEnum.NEED_ADDITIONAL_INFO.equals(loanStatus)) {
        throw new ApplicationException(ErrorEnum.LOAN_APPLICATION_EDIT_NOT_ALLOW);
      }
    } else {
      if (!LoanInfoStatusEnum.PROCESSING.equals(loanStatus)) {
        throw new ApplicationException(ErrorEnum.LOAN_APPLICATION_EDIT_NOT_ALLOW);
      }
      checkPicRm(loanApplication, clientType);
    }
  }

  public void checkPicRm(LoanApplicationEntity loanApplication, ClientTypeEnum clientType) {
    if (ClientTypeEnum.CMS.equals(clientType)) {
      CmsUser cmsUser = cmsUserService.findByEmail(AuthorizationUtil.getEmail());
      if (!cmsUser.getEmplId().equalsIgnoreCase(loanApplication.getPicRm())) {
        List<Role> roles;
        try {
          //Can phai co co che cache role (cache dau common service)
          roles = commonCMSService.getAuthorInfo(
              String.format("%s%s", Constants.HEADER_AUTHORIZATION_BEARER,
                  AuthorizationUtil.getToken()));
        } catch (IOException e) {
          log.error("Call API commonCMSService.getAuthorInfo ERROR: {}", e.getMessage());
          roles = new ArrayList<>();
        }
        if (!AuthorizationUtil.containRole(roles, environmentProperties.getCj5Admin())) {
          throw new ApplicationException(ErrorEnum.LOAN_APPLICATION_EDIT_NOT_ALLOW);
        }
      }
    }
  }

  @Override
  @Transactional
  public int customerSubmitFile(String loanId, String status, String refEmail) {
    LoanApplicationEntity entity = loanApplicationRepository.findById(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    checkEditLoanApp(entity, ClientTypeEnum.LDP);

    if (LoanInfoStatusEnum.DRAFT.getCode().equalsIgnoreCase(entity.getStatus().getCode())) {
      if (!LoanInfoStatusEnum.SUBMIT_LOAN_REQUEST.getCode().equalsIgnoreCase(status)
          && !LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION.getCode().equalsIgnoreCase(status)) {
        throw new ApplicationException(ErrorEnum.LOAN_APPLICATION_SUBMIT_STATUS_NOT_ALLOW,
            String.format("%s|%s", LoanInfoStatusEnum.SUBMIT_LOAN_REQUEST,
                LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION));
      }
    }

    if (LoanInfoStatusEnum.NEED_ADDITIONAL_INFO.getCode()
        .equalsIgnoreCase(entity.getStatus().getCode())) {
      if (!LoanInfoStatusEnum.ACCEPT_LOAN_APPLICATION.getCode().equalsIgnoreCase(status)
          && !LoanInfoStatusEnum.ACCEPT_LOAN_REQUEST.getCode().equalsIgnoreCase(status)) {
        throw new ApplicationException(ErrorEnum.LOAN_APPLICATION_SUBMIT_STATUS_NOT_ALLOW,
            String.format("%s|%s", LoanInfoStatusEnum.ACCEPT_LOAN_APPLICATION,
                LoanInfoStatusEnum.ACCEPT_LOAN_REQUEST));
      }
    }

    String currentStep = entity.getCurrentStep();
    if (LoanCurrentStepEnum.LOAN_FILE.getCode().equalsIgnoreCase(entity.getCurrentStep())
        || LoanCurrentStepEnum.LOAN_COLLATERAL.getCode()
        .equalsIgnoreCase(entity.getCurrentStep())) {
      currentStep = LoanCurrentStepEnum.LOAN_SUBMIT.getCode();
    }

    List<LoanUploadFileEntity> loanUploadFiles = loanUploadFileRepository.findByLoanApplicationId(
        loanId);
    boolean isUploadFileEmpty = false;
    if (!CollectionUtils.isEmpty(loanUploadFiles)) {
      List<LoanUploadFileEntity> lstTemp = loanUploadFiles.stream()
          .filter(x -> x.getStatus().equals(LoanUploadFileStatusEnum.UPLOADED))
          .collect(Collectors.toList());
      if (CollectionUtils.isEmpty(lstTemp)) {
        isUploadFileEmpty = true;
      }
    } else {
      isUploadFileEmpty = true;
    }

    if (isUploadFileEmpty) {
      if (LoanInfoStatusEnum.DRAFT.getCode().equalsIgnoreCase(entity.getStatus().getCode())) {
        status = LoanInfoStatusEnum.SUBMIT_LOAN_REQUEST.getCode();
      }

      if (LoanInfoStatusEnum.NEED_ADDITIONAL_INFO.getCode()
          .equalsIgnoreCase(entity.getStatus().getCode())) {
        status = LoanInfoStatusEnum.ACCEPT_LOAN_REQUEST.getCode();
      }
    }

    String loanCode =
        StringUtils.isEmpty(entity.getLoanCode()) ? getLoanCode(ClientTypeEnum.LDP.toString())
            : entity.getLoanCode();
    if (StringUtils.isEmpty(loanCode)) {
      throw new ApplicationException(ErrorEnum.LOAN_APPLICATION_SUBMIT_ERROR);
    }

    List<MultipartFile> fileList = new ArrayList<>();
    String htmlContent = sendMailService.genHtmlContentOfAttachFile(loanId, null);
    byte[] content = HomeLoanUtil.exportFilePdf(loanId, htmlContent);
    fileList.add(new BASE64DecodedMultipartFile(content, Constants.FILE_NAME_DNVV));

    //danh sách hồ sơ cần chuẩn bị
    String htmlContentDocuments = sendMailService.genHtmlContentDocumentNeedProvide(loanId,
        FileRuleEnum.LDP);
    if (!StringUtils.isEmpty(htmlContentDocuments)) {
      byte[] contentDocuments = HomeLoanUtil.exportFilePdf(loanId, htmlContentDocuments);
      fileList.add(new BASE64DecodedMultipartFile(contentDocuments, Constants.FILE_NAME_DMHS));
    }

    // Upload de_nghi_vay_von.pdf to S3
    uploadDNVV(content, entity);

    FileConfigEntity fileConfigEntity = fileConfigRepository.findByCode(
        Constants.FILE_CONFIG_CODE_DNVV).orElseThrow(
        () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "file_config:code",
            Constants.FILE_CONFIG_CODE_DNVV));
    // cap nhat loan upload file
    LoanUploadFileEntity loanUploadFileEntity = loanUploadFileRepository.getDNVVByLoanId(loanId)
        .map(obj -> {
          obj.setUpdatedAt(Instant.now());
          return Optional.of(obj);
        }).orElseGet(() -> Optional.of(LoanUploadFileEntity.builder()
            .fileConfigId(fileConfigEntity.getUuid())
            .loanApplicationId(loanId)
            .folder(String.format("%s/%s/%s/", Constants.SERVICE_NAME,
                DateUtils.convertToSimpleFormat(Date.from(entity.getCreatedAt()), "yyyyMMdd"),
                loanId))
            .fileName(Constants.FILE_NAME_DNVV)
            .status(LoanUploadFileStatusEnum.UPLOADED)
            .build())).get();

    // 14/12/2022
    loanUploadFileEntity.setStatus(LoanUploadFileStatusEnum.UPLOADED);

    loanUploadFileRepository.save(loanUploadFileEntity);

    if (LoanInfoStatusEnum.SUBMIT_LOAN_REQUEST.getCode().equals(status)) {
      status = LoanInfoStatusEnum.ACCEPT_LOAN_REQUEST.getCode();
    }
    if (LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION.getCode().equals(status)) {
      status = LoanInfoStatusEnum.ACCEPT_LOAN_APPLICATION.getCode();
    }
    log.info("submit refEmail: {}", refEmail);
    int updateStatusInt = loanApplicationRepository.updateStatus(status, entity.getLoanCode(),
        entity.getUuid(), currentStep,
        DownloadStatus.NEED_UPLOAD_ZIP.getValue(),
        MobioStatusEnum.WAITING.getCode(), refEmail);

    //update his
    LoanStatusChangeEntity changeEntity = LoanStatusChangeEntity.builder()
        .loanApplicationId(loanId)
        .statusFrom(entity.getStatus())
        .statusTo(LoanInfoStatusEnum.valueOf(status))
        .cause("Customer submit loan")
        .note("Customer submit loan")
        .build();
    loanStatusChangeRepository.save(changeEntity);

    // Send mail
    executorService.execute(() -> {
      try {
        log.info("send mail start");
        sendMailService.sendMailWhenSubmitLoanSuccess(entity, fileList);
      } catch (Exception ex) {
        log.error("Send mail Exception: ", ex);
      }
    });

    // Gửi lead sang CJ4
    executorService.execute(() -> {
      try {
        insuranceService.sendLead(loanId, null);
      } catch (Exception ex) {
        log.error("Send lead to CJ4 error: ", ex);
      }
    });

    return updateStatusInt;
  }

  private void saveLoanApplication(
      LoanApplicationEntity entity,
      String status, String currentStep,
      Integer downloadStatus, MobioStatusEnum mobioStatus, String refEmail) {
    entity.setStatus(LoanInfoStatusEnum.valueOf(status));
    entity.setCurrentStep(currentStep);
    entity.setDownloadStatus(downloadStatus);
    if (ObjectUtil.isNotEmpty(mobioStatus)) {
      entity.setMobioStatus(mobioStatus);
    }
    entity.setRefCode(ObjectUtil.isEmpty(refEmail) ? "" : refEmail);
    loanApplicationRepository.save(entity);
  }

  private void uploadDNVV(byte[] content, LoanApplicationEntity entity) {
    // Upload de_nghi_vay_von.pdf to S3
    String path = String.format("%s/%s/%s/", Constants.SERVICE_NAME,
        DateUtils.convertToSimpleFormat(Date.from(entity.getCreatedAt()), "yyyyMMdd"),
        entity.getUuid());
    UploadPresignedUrlRequest uploadPresignedUrlRequest = UploadPresignedUrlRequest.builder()
        .clientCode(environmentProperties.getClientCode())
        .scopes(environmentProperties.getClientScopes())
        .documentType("document")
        .priority(1)
        .filename(Constants.FILE_NAME_DNVV)
        .path(path)
        .build();
    fileService.generatePreSignedUrlToUpload(uploadPresignedUrlRequest);
    fileService.upload(uploadPresignedUrlRequest, content, true);
  }

  @Override
  public int closeLoanApplication(String loanId, String reason) {
    LoanApplicationEntity entity = loanApplicationRepository.findById(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));

    if (!environmentProperties.getLoanStatusAllowClose().contains(entity.getStatus().getCode())) {
      throw new ApplicationException(ErrorEnum.LOAN_APPLICATIONS_CLOSE_NOT_ALLOW);
    }

    String email = AuthorizationUtil.getEmail();

    CmsUserEntity cmsUsersEntity = cmsUserRepository.findByEmail(email)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.CMS_USER_NOT_EXIST, email));

    int update = loanApplicationRepository.updateStatusByUuid(LoanInfoStatusEnum.CLOSED.getCode(),
        loanId);
    if (update > 0) {
      LoanStatusChangeEntity changeEntity = LoanStatusChangeEntity.builder()
          .loanApplicationId(loanId)
          .statusFrom(entity.getStatus())
          .statusTo(LoanInfoStatusEnum.CLOSED)
          .note(reason)
          .emplId(cmsUsersEntity.getEmplId())
          .build();
      loanStatusChangeRepository.save(changeEntity);
    }

    return update;
  }

  @Override
  public Map exportProposalLetter(String loanId) {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    Map result = new HashMap();
    byte[] bytes;
    try {
      log.info("[exportProposalLetter] Start export {}",
          DateUtils.convertToSimpleFormat(new Date(), "dd-M-yyyy hh:mm:ss"));
      CMSLoanApplicationReview loanApplicationReview = cmsReview(loanId);
      if (loanApplicationReview != null && loanApplicationReview.getLoanApplication() != null
          && !LoanInfoStatusEnum.RM_COMPLETED.getCode()
          .equalsIgnoreCase(loanApplicationReview.getLoanApplication().getStatus())) {
        throw new ApplicationException(ErrorEnum.EXPORT_PROPOSAL_LETTER_NOT_ALLOW,
            LoanInfoStatusEnum.RM_COMPLETED.getName());
      }
      log.info("[exportProposalLetter] End get data export {}",
          DateUtils.convertToSimpleFormat(new Date(), "dd-M-yyyy hh:mm:ss"));
      //TODO day len S3 public and get by url
      String path = "export-templates/MB_ToTrinhRPA_ver1.6.9.xlsm";
      XSSFWorkbook wb = loadTemplateExcel(path);
      log.info("[exportProposalLetter] End load template {}",
          DateUtils.convertToSimpleFormat(new Date(), "dd-M-yyyy hh:mm:ss"));
      wb = exportExcelService.exportProposalLetter(loanApplicationReview, wb);
      wb.write(bos);
      bytes = bos.toByteArray();
      result.put("data", bytes);

      String fullName = VNCharacterUtils.removeAccent(loanApplicationReview != null
          && loanApplicationReview.getLoanApplication() != null ?
          loanApplicationReview.getLoanApplication().getFullName() : "");
      if (!StringUtils.isEmpty(fullName)) {
        fullName = fullName.replaceAll(" ", "").trim().toUpperCase();
      }
      String fileName = String.format("%s_TOTRINH_%s.xlsm",
          DateUtils.convertToSimpleFormat(new Date(), "ddMMyyyy"), fullName);
      result.put("report-name", fileName);
      log.info("[exportProposalLetter] End export template {}",
          DateUtils.convertToSimpleFormat(new Date(), "dd-M-yyyy hh:mm:ss"));
    } catch (IOException e) {
      log.error("Exception : {}", e.getMessage(), e);
    } finally {
      try {
        bos.close();
      } catch (Exception ignored) {
      }
    }

    return result;
  }

  @Override
  public CMSLoanApplicationReview cmsReviewAndFile(String loanApplicationId) {
    CMSLoanApplicationReview loanApplication = cmsReview(loanApplicationId);
    loanApplication.setFiles(
        fileConfigCategoryService.getFileConfigCategories(loanApplicationId, FileRuleEnum.CMS));
    return loanApplication;
  }

  @Override
  @Transactional
  public void submitFeedback(String loanApplicationId) {
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(loanApplicationId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    // Cập nhật loan_application_comment -> NEW
    List<LoanApplicationCommentEntity> loanApplicationCommentEntities = loanApplicationCommentRepository.findByLoanApplicationIdAndStatusOrderByField(
        loanApplicationId, CommentStatusEnum.DRAFT.toString());
    loanApplicationCommentEntities.forEach(entity -> entity.setStatus(CommentStatusEnum.NEW));
    loanApplicationCommentRepository.saveAll(loanApplicationCommentEntities);
    // Cập nhật upload_file_comment -> NEW
    List<UploadFileCommentEntity> uploadFileCommentEntities = uploadFileCommentRepository.findByLoanApplicationIdAndStatusKeepOrder(
        loanApplicationId, CommentStatusEnum.DRAFT.toString());
    uploadFileCommentEntities.forEach(entity -> entity.setStatus(CommentStatusEnum.NEW));
    uploadFileCommentRepository.saveAll(uploadFileCommentEntities);
    // Cập nhật upload_file_status -> NEW
    List<UploadFileStatusEntity> uploadFileStatusEntities = uploadFileStatusRepository.findByLoanApplicationIdAndStatus(
        loanApplicationId, CommentStatusEnum.DRAFT.toString());
    uploadFileStatusEntities.forEach(entity -> entity.setStatus(CommentStatusEnum.NEW));
    uploadFileStatusRepository.saveAll(uploadFileStatusEntities);
    // Cập nhật trạng thái hồ sơ
    loanApplication.setStatus(LoanInfoStatusEnum.NEED_ADDITIONAL_INFO);
    loanApplicationRepository.save(loanApplication);
    if (!loanApplicationCommentEntities.isEmpty() || !uploadFileCommentEntities.isEmpty()
        || !uploadFileStatusEntities.isEmpty()) {
      // Gửi mail
      try {
        sendMailService.sendMailWhenSubmitSubmitFeedback(loanApplication,
            loanApplicationCommentEntities, uploadFileCommentEntities, uploadFileStatusEntities);
      } catch (Exception ex) {
        log.error("Send mail register fail: " + ex.getMessage());
      }
    } else {
      throw new ApplicationException(ErrorEnum.NO_COMMENTS_FOUND);
    }
  }

  @Override
  public void zipLoans(List<String> list) {
    loanApplicationRepository.zipLoans(list);
  }

  @Transactional
  @Retryable(value = {OptimisticLockException.class,
      SQLException.class}, maxAttempts = 3, backoff = @Backoff(delay = 100))
  private String getLoanCode(String prefix) {
    Long day = Long.valueOf(DateUtils.convertToSimpleFormat(new Date(), "ddMMyyyy"));
    Optional<LoanApplicationSequenceEntity> entityOptional = sequenceRepository.findByDay(day);
    LoanApplicationSequenceEntity entity;
    long sq;
    if (entityOptional.isPresent()) {
      entity = entityOptional.get();
      sq = entity.getSequence() + 1;
    } else {
      entity = new LoanApplicationSequenceEntity();
      sq = 1;
    }

    entity.setDay(day);
    entity.setSequence(sq);
    sequenceRepository.save(entity);
    return String.format("%s%s%s", prefix, intToString(day, 8), intToString(sq, 4));
  }

  @Recover
  private String recover(Exception e) {
    return null;
  }

  private String intToString(long num, int digits) {
    String output = Long.toString(num);
    while (output.length() < digits) {
      output = "0" + output;
    }
    return output;
  }

  @Override
  public List<LoanApplication> checkAndAssignLoanAppNeedToUploadZip() {
    List<LoanApplicationEntity> listNeedToUploadZip = loanApplicationRepository.getLoanApplicationListNeedToUploadZip(
        PageRequest.of(0, environmentProperties.getUploadLoanZipJobSelectTop()),
        DownloadStatus.NEED_UPLOAD_ZIP.getValue());
    return LoanApplicationMapper.INSTANCE.toModels(listNeedToUploadZip);
  }

  @Override
  public CmsLoanApplicationSearch cmsSearch(CmsLoanApplicationSearchParam search,
      HttpServletRequest httpRequest) throws IOException {
    return loanApplicationRepository.cmsLoanApplicationSearch(search,
        cmsUserService.getAllChildrenUser(httpRequest));
  }

  @Override
  public ExcelExporter export(CmsLoanApplicationExportExcelParam search,
      HttpServletRequest httpRequest) throws IOException {
    List<Role> roles = commonCMSService.getAuthorInfo(httpRequest.getHeader("Authorization"));
    List<String> picRms = Collections.EMPTY_LIST;
    if (!AuthorizationUtil.containRole(roles, environmentProperties.getCj5Admin())) {
      picRms = cmsUserService.getAllChildrenUser();
    }

    CmsLoanApplicationExportExcel cmsLoanApplicationExportExcel = loanApplicationRepository.cmsLoanApplicationExportExcel(
        search, picRms);
    List<CmsLoanApplication> lstData = cmsLoanApplicationExportExcel.getContents();
    List<String> headers = Arrays.asList(Constants.ExportLoanApplication.STT,
        Constants.ExportLoanApplication.LOAN_CODE,
        Constants.ExportLoanApplication.FULL_NAME,
        Constants.ExportLoanApplication.PHONE_NUMBER,
        Constants.ExportLoanApplication.ID_NO,
        Constants.ExportLoanApplication.LOAN_PURPOSE,
        Constants.ExportLoanApplication.LOAN_AMOUNT,
        Constants.ExportLoanApplication.PIC_RM,
        Constants.ExportLoanApplication.BRANCH_CODE,
        Constants.ExportLoanApplication.STATUS,
        Constants.ExportLoanApplication.RECEIVE_DATE);
    return new ExcelExporter(lstData, null, headers);
  }

  @Override
  public DownloadPresignedUrlResponse preZipFile(String loanApplicationId) {
    LoanApplication loanApplication = findById(loanApplicationId);

    //Kiểm tra trạng thái được download
    if (Objects.isNull(loanApplication.getDownloadStatus())
        || loanApplication.getDownloadStatus() != DownloadStatus.READY_FOR_DOWNLOAD.getValue()) {
      throw new ApplicationException(ErrorEnum.ZIP_NOT_READY);
    }

    String folder = loanUploadFileService.getFolderForLoanApp(loanApplication.getUuid());
    if (folder == null) {
      folder = String.format("%s/%s/%s/", Constants.SERVICE_NAME,
          DateUtils.convertToSimpleFormat(Date.from(loanApplication.getCreatedAt()), "yyyyMMdd"),
          loanApplication.getUuid());
    }
    log.info("Pre Zip File folder: " + folder);

    String zipName = HomeLoanUtil.getZipNameForLoanApplication(loanApplication);

    if (folder == null || StringUtils.isEmpty(folder) || zipName == null) {
      throw new ApplicationException(ErrorEnum.ZIP_NOT_FOUND);
    }

    String fullPath = String.format("%s%s", folder, zipName);

    DownloadPresignedUrlRequest request = DownloadPresignedUrlRequest.builder()
        .clientCode(environmentProperties.getClientCode())
        .scopes(environmentProperties.getClientScopes()).path(fullPath).build();

    return fileService.generatePreSignedUrlToDownload(request);
  }

  private LoanApplicationEntity mapUpdateCustomer(LoanApplication model,
      LoanApplicationEntity entity) {
    if (entity == null) {
      entity = new LoanApplicationEntity();
    }
    entity.setPhone(model.getPhone());
    entity.setFullName(model.getFullName());
    if (model.getGender() != null) {
      entity.setGender(model.getGender());
    }
    if (model.getNationality() != null) {
      entity.setNationality(model.getNationality());
    }
    entity.setEmail(model.getEmail());
    entity.setIdNo(model.getIdNo());
    entity.setIssuedOn(model.getIssuedOn());
    entity.setBirthday(model.getBirthday());
    entity.setPlaceOfIssue(model.getPlaceOfIssue());
    entity.setOldIdNo(model.getOldIdNo());
    entity.setProvince(model.getProvince());
    entity.setProvinceName(model.getProvinceName());
    entity.setDistrict(model.getDistrict());
    entity.setDistrictName(model.getDistrictName());
    entity.setWard(model.getWard());
    entity.setOldIdNo3(model.getOldIdNo3());
    entity.setWardName(model.getWardName());
    entity.setAddress(model.getAddress());
    if (model.getMaritalStatus() != null) {
      entity.setMaritalStatus(model.getMaritalStatus());
    }
    entity.setRefCode(model.getRefCode());
    entity.setNumberOfDependents(model.getNumberOfDependents());
    if (!StringUtils.isEmpty(entity.getRefCode())) {
      entity.setRefCode(entity.getRefCode().toLowerCase().trim().replace("@msb.com.vn", ""));
      if (!entity.getRefCode().endsWith("@msb")) {
        entity.setRefCode(String.format("%s%s", entity.getRefCode(), "@msb"));
      }
    }
    return entity;
  }

  @Override
  public LoanApplication updateRmForLoan(String loanId, String hash)
      throws NoSuchAlgorithmException {
    if (SHA256EncryptUtil.valid(hash, environmentProperties.getSha256Secret(), loanId)) {
      String email = AuthorizationUtil.getEmail();

      CmsUser cmsUser = cmsUserService.findByEmail(email);
      LoanApplication model = findById(loanId);

      String picFrom = model.getPicRm();

      model.setPicRm(cmsUser.getEmplId());
      model.setReceiveDate(Instant.now());
      model.setLoanProduct(LoanProductEnum.HOME);
      loanApplicationRepository.save(LoanApplicationMapper.INSTANCE.toEntity(model));

      // history
      PicRmHistoryEntity picRmHistoryEntity = PicRmHistoryEntity.builder()
          .loanApplicationId(model.getUuid())
          .picFrom(picFrom)
          .picTo(cmsUser.getEmplId())
          .system("CMS")
          .build();
      picRmHistoryRepository.save(picRmHistoryEntity);

      // cập nhật lead tới CJ4
      executorService.execute(() -> {
        try {
          String picEmplId = null;
          PicRmHistoryEntity firstPicEntity = picRmHistoryRepository.findFirstPic(loanId);
          if (firstPicEntity != null) {
            picEmplId =
                !StringUtils.isEmpty(firstPicEntity.getPicFrom()) ? firstPicEntity.getPicFrom()
                    : firstPicEntity.getPicTo();
          } else {
            picEmplId = cmsUser.getEmplId();
          }
          insuranceService.updateLead(loanId, picEmplId,
              InterestedEnum.INTERESTED.equals(model.getCj4Interested()) ? InterestedEnum.INTERESTED
                  : InterestedEnum.NOT_INTERESTED, null);
        } catch (Exception ex) {
          log.error("update lead to CJ4 error: ", ex);
        }
      });

      return model;
    } else {
      throw new ApplicationException(ErrorEnum.SERVER_ERROR);
    }
  }

  @Override
  public List<LoanApplication> getLoansByProfileId(String loanCode, List<String> status,
      Long loanAmountFrom, Long loanAmountTo, Instant from, Instant to) {
    String userId = AuthorizationUtil.getUserId();
    List<LoanApplication> loanApplications = loanApplicationRepository.getLoansByProfileId(loanCode,
        status, loanAmountFrom, loanAmountTo, from, to, userId);

    if (CollectionUtils.isEmpty(loanApplications)) {
      return new ArrayList<>();
    }

    loanApplications = loanApplications.stream()
        .filter(x -> ReceiveChannelEnum.LDP.equals(x.getReceiveChannel())
            || !(LoanInfoStatusEnum.PROCESSING.getCode().equals(x.getStatus())
            && ReceiveChannelEnum.CMS.equals(x.getReceiveChannel())))
        .collect(Collectors.toList());

    List<String> lstRegisterInformation = Arrays.asList(LoanInfoStatusEnum.DRAFT.getCode(),
        LoanInfoStatusEnum.NEED_ADDITIONAL_INFO.getCode(),
        LoanInfoStatusEnum.WAITING_FOR_CONFIRM.getCode());

    List<String> lstReceive = Arrays.asList(LoanInfoStatusEnum.SUBMIT_LOAN_REQUEST.getCode(),
        LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION.getCode(),
        LoanInfoStatusEnum.ACCEPT_LOAN_REQUEST.getCode(),
        LoanInfoStatusEnum.ACCEPT_LOAN_APPLICATION.getCode(),
        LoanInfoStatusEnum.CLOSED.getCode(),
        LoanInfoStatusEnum.PROCESSING.getCode());

    List<String> lstExpertise = Collections.singletonList(
        LoanInfoStatusEnum.RM_COMPLETED.getCode());
    List<String> lstApprove = Collections.emptyList();
    List<String> lstDisbursement = Collections.emptyList();

    // trạng thái KH submit hồ sơ vay tới ngân hàng
    List<String> lstSubmit = Arrays.asList(LoanInfoStatusEnum.SUBMIT_LOAN_REQUEST.getCode(),
        LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION.getCode());
    List<String> loanIds = loanApplications.stream()
        .map(LoanApplication::getUuid)
        .collect(Collectors.toList());

    List<LoanPreApprovalEntity> loanPreApprovalEntities = loanPreApprovalRepository.findByLoanIds(
        loanIds);
    Map<String, LoanPreApprovalEntity> mapPreApproval = new HashMap<>();
    if (!CollectionUtils.isEmpty(loanPreApprovalEntities)) {
      mapPreApproval = loanPreApprovalEntities.stream()
          .collect(
              Collectors.toMap(LoanPreApprovalEntity::getLoanId, Function.identity(), (a, b) -> a));
    }

    List<LoanJourneyStatus> loanJourneyStatuses;
    for (LoanApplication loanApplication : loanApplications) {
      loanJourneyStatuses = new ArrayList<>();
      List<LoanStatusChangeEntity> loanStatusChanges = loanStatusChangeRepository.findByLoanApplicationId(
          loanApplication.getUuid());

      LoanJourneyStatus submit = getCustomerJourney(loanStatusChanges, lstSubmit,
          LoanCJEnum.REGISTER_INFORMATION);
      if (submit != null) {
        loanApplication.setSubmitLoanRequestTime(Date.from(submit.getCreatedAt()));
      }

      // Register Information
      LoanJourneyStatus registerInformation = getCustomerJourney(loanStatusChanges,
          lstRegisterInformation, LoanCJEnum.REGISTER_INFORMATION);
      if (registerInformation != null) {
        loanJourneyStatuses.add(registerInformation);
      }

      LoanJourneyStatus receive = getCustomerJourney(loanStatusChanges, lstReceive,
          LoanCJEnum.RECEIVE);
      if (receive != null) {
        loanJourneyStatuses.add(receive);
      }

      LoanJourneyStatus expertise = getCustomerJourney(loanStatusChanges, lstExpertise,
          LoanCJEnum.EXPERTISE);
      if (expertise != null) {
        loanJourneyStatuses.add(expertise);
      }

      LoanJourneyStatus approve = getCustomerJourney(loanStatusChanges, lstApprove,
          LoanCJEnum.APPROVE);
      if (approve != null) {
        loanJourneyStatuses.add(approve);
      }

      LoanJourneyStatus disbursement = getCustomerJourney(loanStatusChanges, lstDisbursement,
          LoanCJEnum.DISBURSEMENT);
      if (disbursement != null) {
        loanJourneyStatuses.add(disbursement);
      }

      loanApplication.setLoanJourneyStatuses(loanJourneyStatuses);

      //Map pre approval
      LoanPreApprovalEntity preApproval = mapPreApproval.get(loanApplication.getUuid());
      if (preApproval != null) {
        try {
          LoanApplicationReview loanApplicationReview = objectMapper.readValue(
              preApproval.getMetaData(), LoanApplicationReview.class);
          if (loanApplicationReview != null && loanApplicationReview.getLoanApplication() != null) {
            loanApplication.setLoanCode(loanApplicationReview.getLoanApplication().getLoanCode());
            List<LoanApplicationItemEntity> loanApplicationItemEntities = loanApplicationItemRepository.findByLoanApplicationIdAndLoanPurposeNotIn(
                loanApplication.getUuid(),
                Arrays.asList(LoanPurposeEnum.THAU_CHI, LoanPurposeEnum.CREDIT_CARD));
            loanApplication.setLoanApplicationItems(
                LoanApplicationItemMapper.INSTANCE.toModels(loanApplicationItemEntities));
          }
        } catch (Exception exception) {
          List<LoanApplicationItem> loanApplicationItems = loanApplicationItemService.findByLoanApplicationIdOrderByCreatedAtAsc(
              loanApplication.getUuid());
          loanApplication.setLoanApplicationItems(loanApplicationItems);
          log.error("Convert json to object error");
        }
      } else {
        List<LoanApplicationItem> loanApplicationItems = loanApplicationItemService.findByLoanApplicationIdOrderByCreatedAtAsc(
            loanApplication.getUuid());
        loanApplication.setLoanApplicationItems(loanApplicationItems);
      }

      //get cms user
      if (!StringUtils.isEmpty(loanApplication.getPicRm())) {
        Optional<CmsUserEntity> cmsUser = cmsUserRepository.findById(loanApplication.getPicRm());
        cmsUser.ifPresent(cmsUserEntity -> loanApplication.setCmsUser(
            CmsUserMapper.INSTANCE.toModel(cmsUserEntity)));
      } else if (!StringUtils.isEmpty(loanApplication.getRefCode())) {
        String email = String.format("%s%s", loanApplication.getRefCode(), ".com.vn");
        Optional<CmsUserEntity> cmsUser = cmsUserRepository.findByEmail(email);
        cmsUser.ifPresent(cmsUserEntity -> loanApplication.setCmsUser(
            CmsUserMapper.INSTANCE.toModel(cmsUserEntity)));
      }

      if (loanApplication.getCmsUser() != null && !StringUtils.isEmpty(
          loanApplication.getCmsUser().getBranchCode())) {
        OrganizationEntity organizationEntity = organizationRepository.findByCode(
            loanApplication.getCmsUser().getBranchCode());
        loanApplication.getCmsUser()
            .setBranchName(organizationEntity == null ? "" : organizationEntity.getName());
      }

      // Thông tin tư vấn
      LoanAdviseCustomerEntity loanAdviseCustomerEntity = loanAdviseCustomerRepository.findByLoanApplicationId(
          loanApplication.getUuid());
      loanApplication.setLoanAdviseCustomer(
          LoanAdviseCustomerMapper.INSTANCE.toModel(loanAdviseCustomerEntity));
    }
    return loanApplications;
  }

  private LoanJourneyStatus getCustomerJourney(List<LoanStatusChangeEntity> statusChangeEntities,
      List<String> lstStatus, LoanCJEnum loanCJEnum) {
    List<LoanStatusChangeEntity> lstCustomerJourney = statusChangeEntities.stream()
        .filter(lsc -> lstStatus.contains(lsc.getStatusTo().getCode()))
        .sorted(Comparator.comparing(LoanStatusChangeEntity::getCreatedAt).reversed())
        .collect(Collectors.toList());

    if (!CollectionUtils.isEmpty(lstCustomerJourney)) {
      return LoanJourneyStatus.builder()
          .status(loanCJEnum.getCode())
          .createdAt(lstCustomerJourney.get(0).getCreatedAt())
          .build();
    }

    return null;
  }

  private XSSFWorkbook loadTemplateExcel(String pathFile) {
    InputStream in = null;
    try {
      ClassPathResource classPathResource = new ClassPathResource(pathFile);
      in = classPathResource.getInputStream();
      return new XSSFWorkbook(in);
    } catch (IOException e) {
      log.error("[loadTemplateExcel] file {} dose not exist", pathFile, e);
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (Exception ignored) {
      }
    }
    return new XSSFWorkbook();
  }

  private String getPlaceOfIssueName(String code) {
    if (StringUtils.isEmpty(code)) {
      return "";
    }

    Optional<PlaceOfIssueIdCardEntity> place = placeOfIssueIdCardRepository.findByCode(code);
    if (place.isPresent()) {
      return place.get().getName();
    }
    return "";
  }

  @Override
  @Transactional
  public CMSCustomerInfoResponse makeProposalCustomerInfo(LoanApplication loanApplication) {
    LoanApplicationEntity entity = loanApplicationRepository.findByUuid(loanApplication.getUuid())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    MaritalStatusEnum maritalStatusInDb = entity.getMaritalStatus();
    entity = copyCustomerInfo(loanApplication, entity);
    loanApplicationRepository.save(entity);
    if (!loanApplication.getMaritalStatus().equals(MaritalStatusEnum.MARRIED)
        && maritalStatusInDb.equals(MaritalStatusEnum.MARRIED)) {
      MarriedPersonEntity marriedPersonEntity = marriedPersonsRepository.findOneByLoanId(
          entity.getUuid());
      if (marriedPersonEntity != null) {
        Set<String> identityCardsMarriedPerson = new HashSet<>(
            Arrays.asList(marriedPersonEntity.getIdNo(), marriedPersonEntity.getOldIdNo()));
        identityCardsMarriedPerson.remove(null);
        marriedPersonsRepository.delete(marriedPersonEntity);
        CicEntity cicEntity = cicRepository.findByLoanApplicationIdAndIdentityCards(
            entity.getUuid(), identityCardsMarriedPerson);
        if (cicEntity != null) {
          cicRepository.delete(cicEntity);
        }
        List<CicItemEntity> cicItemEntities = cicItemRepository.findByLoanApplicationAndIdentityCardIn(
            entity.getUuid(), identityCardsMarriedPerson);
        if (!CollectionUtils.isEmpty(cicItemEntities)) {
          cicItemRepository.deleteAll(cicItemEntities);
        }
        List<OpRiskEntity> opRiskEntities = opRiskRepository.findByLoanApplicationIdAndIdentityCardIn(
            entity.getUuid(), identityCardsMarriedPerson);
        if (!CollectionUtils.isEmpty(opRiskEntities)) {
          opRiskRepository.deleteAll(opRiskEntities);
        }
      }
      cmsTabActionService.delete(entity.getUuid(), CMSTabEnum.MARRIED_PERSON);
    }
    //Save tab action
    cmsTabActionService.save(entity.getUuid(), CMSTabEnum.CUSTOMER_INFO);
    return CMSLoanApplicationResponseMapper.INSTANCE.toDTO(entity);
  }

  @Override
  public CMSCustomerAndRelatedPersonInfo getCustomerAndRelatedPersonInfo(String loanId) {
    CMSCustomerAndRelatedPersonInfo cmsCustomerAndRelatedPersonInfo = new CMSCustomerAndRelatedPersonInfo();
    LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findByUuid(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    MarriedPersonEntity marriedPersonEntity = null;
    if (loanApplicationEntity.getMaritalStatus().equals(MaritalStatusEnum.MARRIED)) {
      marriedPersonEntity = marriedPersonsRepository.findOneByLoanId(loanId);
    }
    ContactPersonEntity contactPersonEntity = contactPersonsRepository.findOneByLoanId(loanId);
    List<LoanPayerEntity> loanPayerEntities = loanPayerRepository.findByLoanIdOrderByCreatedAtAsc(
        loanId);

    if (loanApplicationEntity.getAge() == null && loanApplicationEntity.getBirthday() != null) {
      loanApplicationEntity.setAge(DateUtils.calculateAge(loanApplicationEntity.getBirthday()));
    }
    if (marriedPersonEntity != null && marriedPersonEntity.getAge() == null
        && loanApplicationEntity.getBirthday() != null) {
      marriedPersonEntity.setAge(DateUtils.calculateAge(loanApplicationEntity.getBirthday()));
    }
    for (LoanPayerEntity loanPayerEntity : loanPayerEntities) {
      if (loanPayerEntity.getAge() == null && loanPayerEntity.getBirthday() != null) {
        loanPayerEntity.setAge(DateUtils.calculateAge(loanPayerEntity.getBirthday()));
      }
    }
    cmsCustomerAndRelatedPersonInfo.setCustomerInfo(loanApplicationEntity);
    cmsCustomerAndRelatedPersonInfo.setMarriedPerson(marriedPersonEntity);
    cmsCustomerAndRelatedPersonInfo.setContactPerson(contactPersonEntity);
    cmsCustomerAndRelatedPersonInfo.setLoanPayers(loanPayerEntities);
    return cmsCustomerAndRelatedPersonInfo;
  }

  @Override
  @Transactional
  public String updateStatus(String loanId, String status, String note, ClientTypeEnum clientType)
      throws JsonProcessingException {
    log.info("Start updateStatus by loanId: {} with status: {}", loanId, status);
    LoanApplicationEntity entity = loanApplicationRepository.findById(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));

    // Kiểm tra các trạng thái được chuyển sang PROCESSING
    checkUpdateStatus(status, entity);

    // Chỉ pic mới được cập nhật hồ sơ
    checkPicRm(entity, clientType);

    Integer downloadStatus = null;
    if (LoanInfoStatusEnum.RM_COMPLETED.getCode().equalsIgnoreCase(status)) {
      validateWhenRmComplete(entity);

      if (entity.getIsCustomerDeclinedToConfirm() != null
          && entity.getIsCustomerDeclinedToConfirm()) {
        status = LoanInfoStatusEnum.WAITING_FOR_CONFIRM.getCode();
      } else {
        LoanApplicationReview loanApplication = review(loanId);
        status = LoanInfoStatusEnum.WAITING_FOR_CONFIRM.getCode();
        downloadStatus = DownloadStatus.NEED_UPLOAD_ZIP.getValue();

        // fileConfigCategoryService.cmsCheckUploadFile(loanId);
        // Nếu RM thay đổi thông tin -> WAITING_FOR_CONFIRM
        // log for trace
        Optional<LoanPreApprovalEntity> preApprovalEntity = loanPreApprovalRepository.findById(
            loanId);
        if (preApprovalEntity.isPresent()) {
          LoanApplicationReview loanPreApproval = objectMapper.readValue(
              preApprovalEntity.get().getMetaData(), LoanApplicationReview.class);
          log.info("loanApplication: {}", objectMapper.writeValueAsString(loanApplication));
          log.info("loanPreApproval: {}", preApprovalEntity.get().getMetaData());
          if (loanApplication.equals(loanPreApproval)) {
            log.info("loanApplication equals loanPreApproval");
            status = LoanInfoStatusEnum.RM_COMPLETED.getCode();
          }
        }
      }
    }

    int result = loanApplicationRepository.updateStatus(status, null, loanId, null, downloadStatus,
        null, null);

    // Thông tin RM
    String emplId = null;
    try {
      Optional<CmsUserEntity> cmsUserEntity = cmsUserRepository.findByEmail(
          AuthorizationUtil.getEmail());
      if (cmsUserEntity.isPresent()) {
        emplId = cmsUserEntity.get().getEmplId();
      }
    } catch (Exception ex) {
      log.error("[LoanApplicationServiceImpl] Find CMS user by email: ", ex);
    }

    if (result > 0) {
      // RM lập tờ trình
      if (!LoanInfoStatusEnum.PROCESSING.equals(entity.getStatus())
          && LoanInfoStatusEnum.PROCESSING.getCode().equalsIgnoreCase(status)) {
        note = "RM lap to trinh";

        // Cập nhật common income
        if (LoanInfoStatusEnum.ACCEPT_LOAN_REQUEST.equals(entity.getStatus())
            || LoanInfoStatusEnum.ACCEPT_LOAN_APPLICATION.equals(entity.getStatus())
            || LoanInfoStatusEnum.NEED_ADDITIONAL_INFO.equals(entity.getStatus())) {
          commonIncomeService.save(CommonIncome.builder().approvalFlow(ApprovalFlowEnum.NORMALLY)
              .loanApplicationId(loanId)
              .recognitionMethod1(RecognitionMethod1Enum.ACTUALLY_RECEIVED)
              .selectedIncomes(commonIncomeService.getSelectedIncomeSubmittedByCustomer(loanId))
              .build());
        }

        // Trường hợp chuyển từ trạng thái RM_COMPLETED sang PROCESSING không ảnh hưởng do KH đã confirmed
        // hoặc thông tin kH cung cấp không bị thay đổi
        try {
          //save pre approval
          LoanApplicationReview loanApplicationReview = review(loanId);
          LoanPreApprovalEntity loanPreApprovalEntity = LoanPreApprovalEntity.builder()
              .loanId(loanId)
              .metaData(objectMapper.writeValueAsString(loanApplicationReview))
              .build();
          loanPreApprovalRepository.save(loanPreApprovalEntity);
        } catch (Exception ex) {
          log.error("[LoanApplicationServiceImpl] Save pre approval error: ", ex);
        }
      }

      if (LoanInfoStatusEnum.WAITING_FOR_CONFIRM.getCode().equalsIgnoreCase(status)
          || LoanInfoStatusEnum.RM_COMPLETED.getCode().equalsIgnoreCase(status)) {
        // delete unnecessary data
        deleteDataWhenRmSubmit(entity);

        // Cập nhật profile khi RM hoàn thành tờ trình
        ProfileEntity profile = profileRepository.findById(entity.getProfileId())
            .orElseThrow(
                () -> new ApplicationException(ErrorEnum.PROFILE_NOT_FOUND, entity.getProfileId()));
        ProfileEntity profileSave = loanToProfileEntity(entity);
        profileSave.setUuid(profile.getUuid());
        profileSave.setPhone(profile.getPhone());
        profileSave.setStatus(profile.getStatus());
        profileRepository.save(profileSave);

        // đẩy lead
          try {
            // pic đầu tiên
            PicRmHistoryEntity picRmHistoryEntity = picRmHistoryRepository.findFirstPic(loanId);
            if (Objects.equals(entity.getCj4SendLeadStatus(), "SUCCESS")) {
              insuranceService.updateLeadCms(loanId, entity.getCj4CmsInterested());
            } else {
              insuranceService.sendLead(loanId,
                      (picRmHistoryEntity != null ? picRmHistoryEntity.getPicTo() : entity.getPicRm()));
            }
          } catch (Exception ex) {
            log.error("Send lead to CJ4 error: ", ex);
          }
      }

      if (LoanInfoStatusEnum.WAITING_FOR_CONFIRM.getCode().equalsIgnoreCase(status)) {
        // send mail for confirmation
        executorService.execute(() -> {
          sendMailService.sendMailWhenRMCompleteToTrinh(entity, null);
        });
      }

      if (LoanInfoStatusEnum.RM_COMPLETED.getCode().equalsIgnoreCase(status)) {
        // retrieve file from s3
        String fullPath = String.format("%s/%s/%s/%s", Constants.SERVICE_NAME,
            DateUtils.convertToSimpleFormat(Date.from(entity.getCreatedAt()), "yyyyMMdd"),
            entity.getUuid(), Constants.FILE_NAME_DNVV);
        byte[] downloadFileContent = homeLoanUtil.downloadFileFromS3(fullPath);

        // send mail
        executorService.execute(() -> {
          List<MultipartFile> fileList = new ArrayList<>();
          if (downloadFileContent != null) {
            fileList.add(
                new BASE64DecodedMultipartFile(downloadFileContent, Constants.FILE_NAME_DNVV));
          }
          sendMailService.sendMailWhenCustomerApprovalChange(entity, fileList);
        });

        // send mail for confirmation
        executorService.execute(() -> {
          sendMailService.sendMailToBMWhenRMCompleteToTrinh(entity);
        });
      }

      // Trace loan status change
      LoanStatusChangeEntity loanStatusChangeEntity = LoanStatusChangeEntity.builder()
          .loanApplicationId(loanId)
          .statusFrom(entity.getStatus())
          .statusTo(LoanInfoStatusEnum.valueOf(status))
          .emplId(emplId)
          .note(note)
          .build();
      loanStatusChangeRepository.save(loanStatusChangeEntity);
    }

    return status;
  }

  @Override
  public void regenerateDNVV(String loanId) {
    // Lấy thời điểm khách hàng submit
    LoanStatusChangeEntity loanStatusChangeEntity = loanStatusChangeRepository.getLastestSubmitByCustomer(
        loanId);
    if (loanStatusChangeEntity == null) {
      throw new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND,
          "loan_status_change:loan_application_id", loanId);
    }
    // Generate DNVV
    String htmlContent = sendMailService.genHtmlContentOfAttachFile(loanId,
        Date.from(loanStatusChangeEntity.getCreatedAt()));
    byte[] content = HomeLoanUtil.exportFilePdf(loanId, htmlContent);

    // Send mail
    List<MultipartFile> fileList = null;
    if (content.length > 0) {
      fileList = new ArrayList<>(
          Arrays.asList(new BASE64DecodedMultipartFile(content, Constants.FILE_NAME_DNVV)));
    }
    SendMailRequest request = SendMailRequest.builder()
        .to(environmentProperties.getCustomerSupportEmail())
        .title("[Support CJ5 -Tech] Regenerate ĐNVV")
        .content("Please find attached")
        .files(fileList).build();
    ApiInternalResponse<DataCommonResponse> response = notificationFeignClient.sendMail(
        environmentProperties.getClientCode(), request);
    log.info("response send loan application success mail: status {}, message: {}",
        response.getStatus(),
        response.getData().getMessage() == null ? "" : response.getData().getMessage());
  }

  private void checkUpdateStatus(String status, LoanApplicationEntity entity) {
    //  Accept ACCEPT_LOAN_REQUEST, ACCEPT_LOAN_APPLICATION, RM_COMPLETED, NEED_ADDITIONAL_INFO, DECLINED_TO_CONFIRM to PROCESSING
    if (LoanInfoStatusEnum.PROCESSING.getCode().equalsIgnoreCase(status)) {
      if (!LoanInfoStatusEnum.ACCEPT_LOAN_APPLICATION.equals(entity.getStatus())
          && !LoanInfoStatusEnum.ACCEPT_LOAN_REQUEST.equals(entity.getStatus())
          && !LoanInfoStatusEnum.RM_COMPLETED.equals(entity.getStatus())
          && !LoanInfoStatusEnum.NEED_ADDITIONAL_INFO.equals(entity.getStatus())
          && !LoanInfoStatusEnum.DECLINED_TO_CONFIRM.equals(entity.getStatus())) {
        throw new ApplicationException(ErrorEnum.LOAN_APPLICATION_UPDATE_STATUS_NOT_ALLOW,
            entity.getStatus().getCode(), status);
      }
    }
  }

  @Override
  @Transactional
  public boolean customerApprovalChange(String loanId, String status, String note)
      throws JsonProcessingException {
    LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findById(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));

    LoanInfoStatusEnum statusInDb = loanApplicationEntity.getStatus();
    if (status.equalsIgnoreCase(CustomerApprovalEnum.APPROVAL.getCode())) {
      loanApplicationEntity.setStatus(LoanInfoStatusEnum.RM_COMPLETED);

      if (loanApplicationEntity.getIsCustomerDeclinedToConfirm() != null
          && loanApplicationEntity.getIsCustomerDeclinedToConfirm()) {
        loanApplicationEntity.setIsCustomerDeclinedToConfirm(false);
      }

      loanApplicationRepository.save(loanApplicationEntity);
      Optional<LoanPreApprovalEntity> loanPreApprovalEntityOpt = loanPreApprovalRepository.findOneByLoanId(
          loanId);

      // Nếu tồn tại thì delete trong bảng preApproval
      // loanPreApprovalEntity.ifPresent(preApprovalEntity -> loanPreApprovalRepository.deleteById(preApprovalEntity.getLoanId()));

      // update
      LoanApplicationReview loanApplicationReview = review(loanId);
      if (loanPreApprovalEntityOpt.isPresent()) {
        loanPreApprovalEntityOpt.get()
            .setMetaData(objectMapper.writeValueAsString(loanApplicationReview));
        log.info("loanPreApprovalEntity: {}", loanPreApprovalEntityOpt.get());
        loanPreApprovalRepository.save(loanPreApprovalEntityOpt.get());
      }

      // save loan approval his
      LoanApprovalHisEntity loanApprovalHisEntity = LoanApprovalHisEntity.builder()
          .loanId(loanId)
          .status(status)
          .metaDataFrom(
              loanPreApprovalEntityOpt.isPresent() ? loanPreApprovalEntityOpt.get().getMetaData()
                  : null)
          .metaDataTo(objectMapper.writeValueAsString(loanApplicationReview))
          .build();
      loanApprovalHisRepository.save(loanApprovalHisEntity);

      List<MultipartFile> fileList = new ArrayList<>();
      String htmlContent = sendMailService.genHtmlContentOfAttachFile(loanId, null);
      byte[] content = HomeLoanUtil.exportFilePdf(loanId, htmlContent);
      fileList.add(new BASE64DecodedMultipartFile(content, Constants.FILE_NAME_DNVV));

      // Upload de_nghi_vay_von.pdf to S3
      uploadDNVV(content, loanApplicationEntity);

      FileConfigEntity fileConfigEntity = fileConfigRepository.findByCode(
          Constants.FILE_CONFIG_CODE_DNVV).orElseThrow(
          () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "file_config:code",
              Constants.FILE_CONFIG_CODE_DNVV));
      // cap nhat loan upload file
      LoanUploadFileEntity loanUploadFileEntity = loanUploadFileRepository.getDNVVByLoanId(loanId)
          .map(obj -> {
            obj.setUpdatedAt(Instant.now());
            return Optional.of(obj);
          }).orElseGet(() -> Optional.of(LoanUploadFileEntity.builder()
              .fileConfigId(fileConfigEntity.getUuid())
              .loanApplicationId(loanId)
              .folder(String.format("%s/%s/%s/", Constants.SERVICE_NAME,
                  DateUtils.convertToSimpleFormat(Date.from(loanApplicationEntity.getCreatedAt()),
                      "yyyyMMdd"), loanId))
              .fileName(Constants.FILE_NAME_DNVV)
              .status(LoanUploadFileStatusEnum.UPLOADED)
              .build())).get();

      // 14/12/2022
      loanUploadFileEntity.setStatus(LoanUploadFileStatusEnum.UPLOADED);

      loanUploadFileRepository.save(loanUploadFileEntity);

      // need upload zip
      loanApplicationRepository.updateStatus(loanApplicationEntity.getStatus().getCode(), null,
          loanId, null, DownloadStatus.NEED_UPLOAD_ZIP.getValue(),
          MobioStatusEnum.WAITING.getCode(), null);

      // Send mail when approval
      executorService.execute(() -> {
        sendMailService.sendMailToBMWhenRMCompleteToTrinh(loanApplicationEntity);
        sendMailService.sendMailWhenCustomerApprovalChange(loanApplicationEntity, fileList);
      });

    } else {
      loanApplicationEntity.setStatus(LoanInfoStatusEnum.DECLINED_TO_CONFIRM);
      loanApplicationEntity.setIsCustomerDeclinedToConfirm(true);
      loanApplicationRepository.save(loanApplicationEntity);
    }

    LoanStatusChangeEntity loanStatusChangeEntity = LoanStatusChangeEntity.builder()
        .loanApplicationId(loanApplicationEntity.getUuid())
        .statusTo(loanApplicationEntity.getStatus())
        .statusFrom(statusInDb)
        .note("Customer approval change")
        .build();
    loanStatusChangeRepository.save(loanStatusChangeEntity);

    return true;
  }


  @Override
  public IncomeInfo getIncomeInfo(String loanId, String type) {
    LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findById(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    LoanApplication loanApplication = new LoanApplication();

    //hold on -> Ok

    CommonIncome commonIncome = commonIncomeService.findByLoanApplicationId(loanId);

    //hold on -> Ok
    List<LoanApplicationItem> loanApplicationItems = loanApplicationItemService.getItemCollateralDistribute(
        loanId);

    List<String> selectedIncomes = new ArrayList<>();

    List<SalaryIncome> salaryIncomes = null;
    if (type == null || CMSTabEnum.SALARY_INCOME.getCode().equals(type)) {
      List<SalaryIncomeEntity> salaryIncomeEntities = salaryIncomeRepository.findByLoanApplicationIdOrderByCreatedAtAsc(
          loanId);
      if (!CollectionUtils.isEmpty(salaryIncomeEntities)) {
        selectedIncomes.add(CMSTabEnum.SALARY_INCOME.getCode());
        salaryIncomes = SalaryIncomeMapper.INSTANCE.toModels(salaryIncomeEntities);
      }
    }

    List<BusinessIncome> businessIncomesPerson = null;
    List<BusinessIncome> businessIncomesEnterprise = null;
    if (type == null || CMSTabEnum.PERSONAL_BUSINESS_INCOME.getCode().equals(type)
        || CMSTabEnum.BUSINESS_INCOME.getCode().equals(type)) {
      List<BusinessIncomeEntity> businessIncomeEntities = businessIncomeRepository.findByLoanApplicationIdOrderByCreatedAtAsc(
          loanId);

      if (type == null || CMSTabEnum.PERSONAL_BUSINESS_INCOME.getCode().equals(type)) {
        List<BusinessIncomeEntity> businessIncomeEntitiesPerson = businessIncomeEntities.stream()
            .filter(bi -> bi.getBusinessType().equals(BusinessTypeEnum.WITH_REGISTRATION)
                || bi.getBusinessType().equals(BusinessTypeEnum.WITHOUT_REGISTRATION))
            .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(businessIncomeEntitiesPerson)) {
          selectedIncomes.add(CMSTabEnum.PERSONAL_BUSINESS_INCOME.getCode());
          businessIncomesPerson = BusinessIncomeMapper.INSTANCE.toModels(
              businessIncomeEntitiesPerson);
        }
      }

      if (type == null || CMSTabEnum.BUSINESS_INCOME.getCode().equals(type)) {
        List<BusinessIncomeEntity> businessIncomeEntitiesEnterprise = businessIncomeEntities.stream()
            .filter(bi -> bi.getBusinessType().equals(BusinessTypeEnum.ENTERPRISE))
            .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(businessIncomeEntitiesEnterprise)) {
          selectedIncomes.add(CMSTabEnum.BUSINESS_INCOME.getCode());
          businessIncomesEnterprise = BusinessIncomeMapper.INSTANCE.toModels(
              businessIncomeEntitiesEnterprise);
        }
      }
    }

    List<OtherIncome> otherIncomes = null;
    if (type == null || CMSTabEnum.OTHERS_INCOME.getCode().equals(type)) {
      List<OtherIncomeEntity> otherIncomeEntities = otherIncomeRepository.findByLoanApplicationIdOrderByCreatedAtAsc(
          loanId);
      if (!CollectionUtils.isEmpty(otherIncomeEntities)) {
        selectedIncomes.add(CMSTabEnum.OTHERS_INCOME.getCode());
        otherIncomes = OtherIncomeMapper.INSTANCE.toModels(otherIncomeEntities);
        for (OtherIncome otherIncome : otherIncomes) {
          List<LandTransactionResponse> landTransactionResponses = landTransactionService.getByOtherIncomeId(
              otherIncome.getUuid());
          otherIncome.setLandTransactions(
              LandTransactionMapper.INSTANCE.responseToRequest(landTransactionResponses));
        }
        log.info("other income found: {}", otherIncomes);
      }
    }

    AssetEvaluate assetEvaluate = null;
    if (type == null || CMSTabEnum.ASSUMING_TOTAL_ASSETS_INCOME.getCode().equals(type)) {
      AssetEvaluateEntity assetEvaluateEntity = assetEvaluateRepository.findByLoanApplicationId(
          loanId);
      if (assetEvaluateEntity != null) {
        List<AssetEvaluateItemEntity> assetEvaluateItems = assetEvaluateItemRepository.findByAssetEvalIdOrderByCreatedAtAsc(
            assetEvaluateEntity.getUuid());
        List<AssetEvaluateItem> assetEvaluateItem = AssetEvaluateItemMapper.INSTANCE.toModels(
            assetEvaluateItems);
        assetEvaluate = AssetEvaluateMapper.INSTANCE.toModel(assetEvaluateEntity);
        assetEvaluate.setAssetEvaluateItems(assetEvaluateItem);
      }
    }

    List<CreditCardEntity> creditCardEntities = creditCardRepository.findByLoanIdOrderByCreatedAtAsc(
        loanId);
    List<CreditCard> creditCards = new ArrayList<>();
    if (!CollectionUtils.isEmpty(creditCardEntities)) {
      creditCards = creditCardEntities.stream().map(CreditCardMapper.INSTANCE::toModels)
          .collect(Collectors.toList());
    }

    List<OtherEvaluate> otherEvaluates = null;
    if (type == null || CMSTabEnum.ASSUMING_OTHERS_INCOME.getCode().equals(type)) {
      List<OtherEvaluateEntity> otherEvaluateEntities = otherEvaluateRepository.findByLoanApplicationId(
          loanId);
      otherEvaluates = OtherEvaluateMapper.INSTANCE.toModels(otherEvaluateEntities);
    }

    //hold on -> Ok
    IncomeInfo incomeInfo = new IncomeInfo(salaryIncomes, businessIncomesPerson,
        businessIncomesEnterprise, otherIncomes, loanApplication, assetEvaluate, otherEvaluates,
        commonIncome, loanApplicationItems, creditCards);
    return incomeInfo;
  }

  @Override
  public long totalIncome(String loanId) {
    return loanApplicationRepository.totalIncome(loanId);
  }

  @Override
  public long totalIncomeExchange(String loanId) {
    return loanApplicationRepository.totalIncomeExchange(loanId);
  }

  @Override
  public long totalIncomeActuallyReceived(String loanId) {
    return loanApplicationRepository.totalIncomeActuallyReceived(loanId);
  }

  private void validateWhenRmComplete(LoanApplicationEntity entity) {
    Objects.requireNonNull(entity);

    Map<String, String> detail = new LinkedHashMap<>();
    List<CmsTabActionEntity> cmsTabActionEntities = cmsTabActionRepository.findByLoanId(
        entity.getUuid());
    // check tab thông tin khách hàng
    if (!checkTabActionExist(cmsTabActionEntities, CMSTabEnum.CUSTOMER_INFO)) {
      detail.put(ErrorEnum.COMPLETE_TO_TRINH_ERROR_1.toString(),
          ErrorEnum.COMPLETE_TO_TRINH_ERROR_1.getMessage());
    }

    // check tab thông tin vợ chồng (conditional require)
    if (MaritalStatusEnum.MARRIED.equals(entity.getMaritalStatus())) {
      if (!checkTabActionExist(cmsTabActionEntities, CMSTabEnum.MARRIED_PERSON)) {
        detail.put(ErrorEnum.COMPLETE_TO_TRINH_ERROR_2.toString(),
            ErrorEnum.COMPLETE_TO_TRINH_ERROR_2.getMessage());
      }
    }

    // check tab thông tin người liên hệ
    if (!checkTabActionExist(cmsTabActionEntities, CMSTabEnum.CONTACT_PERSON)) {
      detail.put(ErrorEnum.COMPLETE_TO_TRINH_ERROR_3.toString(),
          ErrorEnum.COMPLETE_TO_TRINH_ERROR_3.getMessage());
    }

    // check tab thông tin nguồn thu

    //hold on --> ok
    CommonIncome commonIncome = commonIncomeService.findByLoanApplicationId(entity.getUuid());
    if (commonIncome == null) {
      detail.put(ErrorEnum.COMPLETE_TO_TRINH_ERROR_4.toString(),
          ErrorEnum.COMPLETE_TO_TRINH_ERROR_4.getMessage());
    } else {
      if (commonIncome.getRecognitionMethod1() == null) {
        detail.put(ErrorEnum.COMPLETE_TO_TRINH_ERROR_4.toString(),
            ErrorEnum.COMPLETE_TO_TRINH_ERROR_4.getMessage());
      } else if (RecognitionMethod1Enum.EXCHANGE.equals(commonIncome.getRecognitionMethod1())) {
        if (!checkTabActionExist(cmsTabActionEntities, CMSTabEnum.ASSUMING_TOTAL_ASSETS_INCOME)
            && !checkTabActionExist(cmsTabActionEntities, CMSTabEnum.ASSUMING_OTHERS_INCOME)) {
          detail.put(ErrorEnum.COMPLETE_TO_TRINH_ERROR_5.toString(),
              ErrorEnum.COMPLETE_TO_TRINH_ERROR_5.getMessage());
        }
      } else if (RecognitionMethod1Enum.DECLARATION.equals(commonIncome.getRecognitionMethod1())
          || RecognitionMethod1Enum.ACTUALLY_RECEIVED.equals(
          commonIncome.getRecognitionMethod1())) {
        if (!checkTabActionExist(cmsTabActionEntities, CMSTabEnum.SALARY_INCOME)
            && !checkTabActionExist(cmsTabActionEntities, CMSTabEnum.PERSONAL_BUSINESS_INCOME)
            && !checkTabActionExist(cmsTabActionEntities, CMSTabEnum.BUSINESS_INCOME)
            && !checkTabActionExist(cmsTabActionEntities, CMSTabEnum.OTHERS_INCOME)) {
          detail.put(ErrorEnum.COMPLETE_TO_TRINH_ERROR_6.toString(),
              ErrorEnum.COMPLETE_TO_TRINH_ERROR_6.getMessage());
        }
      }
    }

    // check tab thông tin tài sản bảo đảm
    if (!checkTabActionExist(cmsTabActionEntities, CMSTabEnum.COLLATERAL_INFO)) {
      detail.put(ErrorEnum.COMPLETE_TO_TRINH_ERROR_7.toString(),
          ErrorEnum.COMPLETE_TO_TRINH_ERROR_7.getMessage());
    }

    // Thông tin khoản vay
    if (!checkTabActionExist(cmsTabActionEntities, CMSTabEnum.LOAN_INFO)) {
      detail.put(ErrorEnum.COMPLETE_TO_TRINH_ERROR_8.toString(),
          ErrorEnum.COMPLETE_TO_TRINH_ERROR_8.getMessage());
    }

    // Ý kiến thẩm định của ĐVKD
    if (!checkTabActionExist(cmsTabActionEntities, CMSTabEnum.EXPERTISE)) {
      detail.put(ErrorEnum.COMPLETE_TO_TRINH_ERROR_10.toString(),
          ErrorEnum.COMPLETE_TO_TRINH_ERROR_10.getMessage());
    }

    // check tab hồ sơ vay
        /*if (!checkTabActionExist(cmsTabActionEntities, CMSTabEnum.FILE_UPLOAD)) {
            detail.put(ErrorEnum.COMPLETE_TO_TRINH_ERROR_9.toString(), ErrorEnum.COMPLETE_TO_TRINH_ERROR_9.getMessage());
        }*/

    // Nếu có lỗi
    if (!detail.isEmpty()) {
      throw new ApplicationException(ErrorEnum.CAN_NOT_COMPLETE_TO_TRINH, detail);
    }
  }

  private boolean checkTabActionExist(List<CmsTabActionEntity> cmsTabActionEntities,
      CMSTabEnum loanCMSCJ) {
    if (CollectionUtils.isEmpty(cmsTabActionEntities)) {
      return false;
    }
    List<CmsTabActionEntity> checkList = cmsTabActionEntities.stream()
        .filter(x -> loanCMSCJ.equals(x.getTabCode()))
        .collect(Collectors.toList());

    if (!checkList.isEmpty()) {
      return true;
    }
    return false;
  }

  private void deleteDataWhenRmSubmit(LoanApplicationEntity loanApplication) {
    //delete nguon thu

    //hold on --> ok
    CommonIncome commonIncome = commonIncomeService.findByLoanApplicationId(
        loanApplication.getUuid());
    Objects.requireNonNull(commonIncome);
    if (commonIncome != null) {
      if (!RecognitionMethod1Enum.EXCHANGE.equals(commonIncome.getRecognitionMethod1())) {
        try {
          otherEvaluateRepository.deleteByLoanApplicationId(loanApplication.getUuid());
          assetEvaluateItemRepository.deleteByLoanId(loanApplication.getUuid());
          assetEvaluateRepository.deleteByLoanApplicationId(loanApplication.getUuid());
        } catch (Exception ex) {
          log.error("[deleteDataWhenRmSubmit] error when delete incomes: {}", ex.getMessage());
        }
      }
    }

    //delete tai san dam bao
    List<CollateralEntity> collateralEntities = collateralRepository.findByLoanId(
        loanApplication.getUuid());
    if (!CollectionUtils.isEmpty(collateralEntities)) {
      List<CollateralEntity> check = collateralEntities.stream()
          .filter(x -> CollateralStatusEnum.THIRD_PARTY.equals(x.getStatus()))
          .collect(Collectors.toList());
      if (CollectionUtils.isEmpty(check)) {
        try {
          collateralOwnerRepository.deleteByLoanId(loanApplication.getUuid());
          collateralOwnerMapRepository.deleteByLoanId(loanApplication.getUuid());
        } catch (Exception ex) {
          log.error("[deleteDataWhenRmSubmit] error when delete collateral: {}", ex.getMessage());
        }
      } else {
        collateralOwnerRepository.refreshData(loanApplication.getUuid());
      }
    }
  }

  @Override
  public void deleteUnselectedIncomes(String loanId, String[] selectedIncomes) {
    if (ArrayUtils.isEmpty(selectedIncomes)) {
      return;
    }

    if (!Arrays.asList(selectedIncomes).contains(CMSTabEnum.SALARY_INCOME.getCode())) {
      // delete salary income
      List<SalaryIncomeEntity> salaryIncomeEntities = salaryIncomeRepository.findByLoanApplicationId(
          loanId);
      salaryIncomeRepository.deleteAll(salaryIncomeEntities);
      cmsTabActionService.delete(loanId, CMSTabEnum.SALARY_INCOME);
    }
    if (!Arrays.asList(selectedIncomes).contains(CMSTabEnum.PERSONAL_BUSINESS_INCOME.getCode())) {
      // delete business income without registration
      List<BusinessIncomeEntity> businessIncomeEntities = businessIncomeRepository.findByLoanApplicationIdAndBusinessTypeIn(
          loanId,
          Arrays.asList(BusinessTypeEnum.WITH_REGISTRATION, BusinessTypeEnum.WITHOUT_REGISTRATION));
      businessIncomeRepository.deleteAll(businessIncomeEntities);
      cmsTabActionService.delete(loanId, CMSTabEnum.PERSONAL_BUSINESS_INCOME);
    }
    if (!Arrays.asList(selectedIncomes).contains(CMSTabEnum.BUSINESS_INCOME.getCode())) {
      // delete business income with registration
      List<BusinessIncomeEntity> businessIncomeEntities = businessIncomeRepository.findByLoanApplicationIdAndBusinessTypeIn(
          loanId, Arrays.asList(BusinessTypeEnum.ENTERPRISE));
      businessIncomeRepository.deleteAll(businessIncomeEntities);
      cmsTabActionService.delete(loanId, CMSTabEnum.BUSINESS_INCOME);
    }
    if (!Arrays.asList(selectedIncomes).contains(CMSTabEnum.OTHERS_INCOME.getCode())) {
      // delete other income
      List<OtherIncomeEntity> otherIncomeEntities = otherIncomeRepository.findByLoanApplicationId(
          loanId);
      otherIncomeRepository.deleteAll(otherIncomeEntities);
      cmsTabActionService.delete(loanId, CMSTabEnum.OTHERS_INCOME);
    }
    if (!Arrays.asList(selectedIncomes)
        .contains(CMSTabEnum.ASSUMING_TOTAL_ASSETS_INCOME.getCode())) {
      // xóa giả định tổng tài sản
      AssetEvaluateEntity assetEvaluateEntity = assetEvaluateRepository.findByLoanApplicationId(
          loanId);
      if (assetEvaluateEntity != null) {
        assetEvaluateRepository.delete(assetEvaluateEntity);
        List<AssetEvaluateItemEntity> assetEvaluateItemEntities = assetEvaluateItemRepository.findByAssetEvalId(
            assetEvaluateEntity.getUuid());
        assetEvaluateItemRepository.deleteAll(assetEvaluateItemEntities);
        cmsTabActionService.delete(loanId, CMSTabEnum.ASSUMING_TOTAL_ASSETS_INCOME);
      }
    }
    if (!Arrays.asList(selectedIncomes).contains(CMSTabEnum.ASSUMING_OTHERS_INCOME.getCode())) {
      // tóa giả định khác
      List<OtherEvaluateEntity> otherEvaluateEntities = otherEvaluateRepository.findByLoanApplicationId(
          loanId);
      otherEvaluateRepository.deleteAll(otherEvaluateEntities);
      cmsTabActionService.delete(loanId, CMSTabEnum.ASSUMING_OTHERS_INCOME);
    }
  }

  @Override
  public String getMobioLink(String loanId) {
    String hash = SHA256EncryptUtil.createLinkMobio(environmentProperties.getSha256Secret(),
        loanId);
    return String.format("%s%s/%s", environmentProperties.getDealApplicationLink(), loanId, hash);
  }

  @Override
  public String checkPhone(String phone) {
    if (StringUtils.isEmpty(phone)
        || !StringUtils.isPhone(phone)) {
      throw new ApplicationException(ErrorEnum.OTP_PHONE_INVALID);
    }
    Optional<ProfileEntity> profile = profileRepository.findOneByPhone(phone);
    if (!profile.isPresent()) {
      return null;
    }

    List<LoanApplicationEntity> loanApplicationEntities = loanApplicationRepository.findByProfileIdOrderByUpdatedAtDesc(
        profile.get().getUuid());
    if (CollectionUtils.isEmpty(loanApplicationEntities)) {
      return null;
    }

    return loanApplicationEntities.get(0).getUuid();
  }

  @Override
  @Transactional
  public Map<String, String> loanInitialize(String phone, boolean isCopyLoan) {
    if (StringUtils.isEmpty(phone)
        || !StringUtils.isPhone(phone)) {
      throw new ApplicationException(ErrorEnum.OTP_PHONE_INVALID);
    }

    Map<String, String> mapReturn = new HashMap<>();
    Optional<ProfileEntity> profile = profileRepository.findOneByPhone(phone);
    if (!profile.isPresent()) {
      //Create profile
      ProfileEntity profileEntity = ProfileEntity.builder()
          .phone(phone)
          .build();
      profileEntity = profileRepository.save(profileEntity);
      mapReturn.put("profile_id", profileEntity.getUuid());
      return mapReturn;
    }

    mapReturn.put("profile_id", profile.get().getUuid());
    if (!isCopyLoan) {
      return mapReturn;
    }

    List<LoanApplicationEntity> loanApplicationEntities = loanApplicationRepository.findByProfileIdOrderByUpdatedAtDesc(
        profile.get().getUuid());
    if (CollectionUtils.isEmpty(loanApplicationEntities)) {
      return null;
    }
    LoanApplicationEntity entity = loanApplicationEntities.get(0);
    LoanApplicationEntity loanAppNew = copyLoan(profile.get(), entity);
    String loanIdNew = loanAppNew != null ? loanAppNew.getUuid() : null;
    if (!StringUtils.isEmpty(loanIdNew)) {
      mapReturn.put("loan_id", loanIdNew);
      //save his

      LoanStatusChangeEntity changeEntity = LoanStatusChangeEntity
          .builder()
          .loanApplicationId(loanIdNew)
          .statusTo(loanAppNew.getStatus())
          .cause("RM tao moi to trinh")
          .emplId(loanAppNew.getPicRm())
          .build();
      loanStatusChangeRepository.save(changeEntity);
      executorService.execute(() -> {
        List<LoanUploadFile> loanUploadFiles = loanUploadFileService.getCopyFiles(entity.getUuid());
        for (LoanUploadFile loanUploadFile : loanUploadFiles) {
          copyFileToApplication(loanUploadFile, LoanApplicationMapper.INSTANCE.toModel(loanAppNew));
        }
      });
    }
    return mapReturn;
  }

  @Override
  public void interestedInInsurance(String loanCode, InterestedEnum interestedEnum) {
    LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findByLoanCode(
        loanCode);
    if (loanApplicationEntity == null) {
      throw new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loan_applications:loan_code",
          loanCode);
    }
    loanApplicationEntity.setCj4Interested(interestedEnum);
    loanApplicationEntity.setCj4InterestedDate(new Date());
    loanApplicationRepository.save(loanApplicationEntity);
  }

  @Override
  public void interestedInInsurance2(String loanId, InterestedEnum interestedEnum) {
    LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findById(loanId)
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loan_applications:uuid",
                loanId));

    loanApplicationEntity.setCj4CmsInterested(interestedEnum);
    loanApplicationEntity.setCj4CmsInterestedDate(new Date());
    loanApplicationRepository.save(loanApplicationEntity);

    // send data update lead cj4
    executorService.execute(() -> {
      try {
        insuranceService.updateLeadCms(loanId,  interestedEnum);
      } catch (Exception ex) {
        log.error("update lead to CJ4 error: ", ex);
      }
    });
  }

  private LoanApplicationEntity copyLoan(ProfileEntity profile, LoanApplicationEntity entity) {
    //Copy customer
    LoanApplicationEntity entityCopy = copyCustomerInfo(
        LoanApplicationMapper.INSTANCE.toModel(entity), null);

    Optional<CmsUserEntity> cmsUser = cmsUserRepository.findByEmail(AuthorizationUtil.getEmail());

    entityCopy.setStatus(LoanInfoStatusEnum.PROCESSING);
    entityCopy.setMobioStatus(null);
    entityCopy.setPhone(profile.getPhone());
    entityCopy.setProfileId(profile.getUuid());
    entityCopy.setNationality(entity.getNationality());
    //entityCopy.setDownloadStatus(DownloadStatus.NEED_UPLOAD_ZIP.getValue());
    entityCopy.setLoanCode(getLoanCode(ClientTypeEnum.CMS.toString()));
    String email = AuthorizationUtil.getEmail();
    entityCopy.setRefCode(StringUtils.isEmpty(email) ? "" : email.replace("@msb.com.vn", "@msb"));
    entityCopy.setReceiveChannel(ReceiveChannelEnum.CMS);
    String userId = cmsUser.isPresent() ? cmsUser.get().getEmplId() : AuthorizationUtil.getUserId();
    entityCopy.setPicRm(userId);
    entityCopy.setReceiveDate((new Date()).toInstant());
    entityCopy = loanApplicationRepository.save(entityCopy);

    LoanApplicationEntity finalEntityCopy = entityCopy;

    //Copy Married Person
    List<MarriedPersonEntity> marriedPersonEntities = marriedPersonsRepository.findByLoanId(
        entity.getUuid());
    if (!CollectionUtils.isEmpty(marriedPersonEntities)) {
      List<MarriedPersonEntity> temps = BeanUtilsCustom.copyPropertiesArray(marriedPersonEntities,
          MarriedPersonEntity.class);
      temps.forEach(x -> {
        x.setLoanId(finalEntityCopy.getUuid());
        x.setUuid(null);
      });
      marriedPersonsRepository.saveAll(temps);
    }

    List<String> lstSelectedIncome = new ArrayList<>();
    //Copy salary incomes
    List<SalaryIncomeEntity> salaryIncomeEntities = salaryIncomeRepository.findByLoanApplicationId(
        entity.getUuid());
    if (!CollectionUtils.isEmpty(salaryIncomeEntities)) {
      List<SalaryIncomeEntity> filters = salaryIncomeEntities.stream()
          .filter(
              x -> OwnerTypeEnum.ME.equals(x.getOwnerType()) || OwnerTypeEnum.MARRIED_PERSON.equals(
                  x.getOwnerType()))
          .collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(filters)) {
        List<SalaryIncomeEntity> copies = BeanUtilsCustom.copyPropertiesArray(filters,
            SalaryIncomeEntity.class);
        copies.forEach(x -> {
          x.setLoanApplicationId(finalEntityCopy.getUuid());
          x.setUuid(null);
        });
        salaryIncomeRepository.saveAll(copies);
        lstSelectedIncome.add(CMSTabEnum.SALARY_INCOME.getCode());
      }
    }

    //Copy nguon thu tu kinh doanh
    List<BusinessIncomeEntity> businessIncomeEntities = businessIncomeRepository.findByLoanApplicationId(
        entity.getUuid());
    if (!CollectionUtils.isEmpty(businessIncomeEntities)) {
      List<BusinessIncomeEntity> filters = businessIncomeEntities.stream()
          .filter(
              x -> OwnerTypeEnum.ME.equals(x.getOwnerType()) || OwnerTypeEnum.MARRIED_PERSON.equals(
                  x.getOwnerType()))
          .collect(Collectors.toList());

      if (!CollectionUtils.isEmpty(filters)) {
        List<BusinessIncomeEntity> copies = BeanUtilsCustom.copyPropertiesArray(filters,
            BusinessIncomeEntity.class);
        copies.forEach(x -> {
          x.setLoanApplicationId(finalEntityCopy.getUuid());
          x.setUuid(null);
        });

        businessIncomeRepository.saveAll(copies);

        List<BusinessIncomeEntity> pbIncomes = copies.stream()
            .filter(x -> BusinessTypeEnum.WITH_REGISTRATION.equals(x.getBusinessType())
                || BusinessTypeEnum.WITHOUT_REGISTRATION.equals(x.getBusinessType()))
            .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(pbIncomes)) {
          lstSelectedIncome.add(CMSTabEnum.PERSONAL_BUSINESS_INCOME.getCode());
        }

        List<BusinessIncomeEntity> eIncomes = copies.stream()
            .filter(x -> BusinessTypeEnum.ENTERPRISE.equals(x.getBusinessType()))
            .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(eIncomes)) {
          lstSelectedIncome.add(CMSTabEnum.BUSINESS_INCOME.getCode());
        }
      }
    }

    //Copy nguon thu khac
    List<OtherIncomeEntity> otherIncomeEntities = otherIncomeRepository.findByLoanApplicationId(
        entity.getUuid());
    if (!CollectionUtils.isEmpty(otherIncomeEntities)) {
      List<OtherIncomeEntity> filters = otherIncomeEntities.stream()
          .filter(
              x -> OwnerTypeEnum.ME.equals(x.getOwnerType()) || OwnerTypeEnum.MARRIED_PERSON.equals(
                  x.getOwnerType()))
          .collect(Collectors.toList());

      if (!CollectionUtils.isEmpty(filters)) {
        List<OtherIncomeEntity> copies = BeanUtilsCustom.copyPropertiesArray(filters,
            OtherIncomeEntity.class);
        copies.forEach(x -> {
          x.setLoanApplicationId(finalEntityCopy.getUuid());
          x.setUuid(null);
        });

        otherIncomeRepository.saveAll(copies);

        lstSelectedIncome.add(CMSTabEnum.OTHERS_INCOME.getCode());
      }
    }

    //Copy nguon thu gia dinh
    AssetEvaluateEntity assetEvaluateEntity = assetEvaluateRepository.findByLoanApplicationId(
        entity.getUuid());
    if (assetEvaluateEntity != null && (OwnerTypeEnum.ME.equals(assetEvaluateEntity.getOwnerType())
        || OwnerTypeEnum.MARRIED_PERSON.equals(assetEvaluateEntity.getOwnerType()))) {
      String assetEvaluateCp = assetEvaluateEntity.getUuid();
      AssetEvaluateEntity assetEvaluateCopy = new AssetEvaluateEntity();
      BeanUtils.copyProperties(assetEvaluateEntity, assetEvaluateCopy);
      assetEvaluateCopy.setLoanApplicationId(entityCopy.getUuid());
      assetEvaluateCopy.setUuid(null);
      assetEvaluateCopy = assetEvaluateRepository.save(assetEvaluateCopy);

      List<AssetEvaluateItemEntity> assetEvaluateItemEntities = assetEvaluateItemRepository.findByAssetEvalId(
          assetEvaluateCp);
      if (!CollectionUtils.isEmpty(assetEvaluateItemEntities)) {
        List<AssetEvaluateItemEntity> copies = BeanUtilsCustom.copyPropertiesArray(
            assetEvaluateItemEntities, AssetEvaluateItemEntity.class);
        AssetEvaluateEntity finalAssetEvaluateCopy = assetEvaluateCopy;
        copies.forEach(x -> {
          x.setAssetEvalId(finalAssetEvaluateCopy.getUuid());
          x.setUuid(null);
        });
        assetEvaluateItemRepository.saveAll(copies);

        lstSelectedIncome.add(CMSTabEnum.ASSUMING_TOTAL_ASSETS_INCOME.getCode());
      }
    }

    //Copy nguon thu gia dinh khac
    List<OtherEvaluateEntity> otherEvaluateEntities = otherEvaluateRepository.findByLoanApplicationId(
        entity.getUuid());
    if (!CollectionUtils.isEmpty(otherEvaluateEntities)) {
      List<OtherEvaluateEntity> filters = otherEvaluateEntities.stream()
          .filter(
              x -> OwnerTypeEnum.ME.equals(x.getOwnerType()) || OwnerTypeEnum.MARRIED_PERSON.equals(
                  x.getOwnerType()))
          .collect(Collectors.toList());

      List<OtherEvaluateEntity> copies = BeanUtilsCustom.copyPropertiesArray(filters,
          OtherEvaluateEntity.class);

      if (!CollectionUtils.isEmpty(copies)) {
        copies.forEach(x -> {
          x.setLoanApplicationId(finalEntityCopy.getUuid());
          x.setUuid(null);
        });

        otherEvaluateRepository.saveAll(copies);
        lstSelectedIncome.add(CMSTabEnum.ASSUMING_OTHERS_INCOME.getCode());
      }
    }

    //Copy common income
    CommonIncome commonIncome = commonIncomeService.findByLoanApplicationId(entity.getUuid());
    if (commonIncome != null) {
      commonIncome.setUuid(null);
      commonIncome.setLoanApplicationId(finalEntityCopy.getUuid());
      commonIncome.setSelectedIncomes(lstSelectedIncome.toArray(new String[0]));
      commonIncomeService.save(commonIncome);
    } else {
      commonIncomeService.save(CommonIncome.builder().approvalFlow(ApprovalFlowEnum.NORMALLY)
          .loanApplicationId(finalEntityCopy.getUuid())
          .recognitionMethod1(RecognitionMethod1Enum.ACTUALLY_RECEIVED)
          .selectedIncomes(lstSelectedIncome.toArray(new String[0]))
          .build());
    }

    return entityCopy;
  }

  private void copyFileToApplication(LoanUploadFile loanUploadFile,
      LoanApplication loanApplication) {
    try {
      // download
      String fullPath = String.format("%s%s", loanUploadFile.getFolder(),
          loanUploadFile.getFileName());
      byte[] content = homeLoanUtil.downloadFileFromS3(fullPath);

      // upload
      boolean uploaded = false;

      String folder = String.format("%s/%s/%s/", Constants.SERVICE_NAME,
          DateUtils.convertToSimpleFormat(Date.from(loanApplication.getCreatedAt()), "yyyyMMdd"),
          loanApplication.getUuid());

      if (content != null) {
        UploadPresignedUrlRequest uploadPresignedUrlRequest = UploadPresignedUrlRequest.builder()
            .clientCode(environmentProperties.getClientCode())
            .scopes(environmentProperties.getClientScopes())
            .documentType("document")
            .priority(1)
            .filename(loanUploadFile.getFileName())
            .path(folder)
            .build();
        uploaded = fileService.upload(uploadPresignedUrlRequest, content, false);
      }

      // save loan upload file
      if (uploaded) {
        loanUploadFile.setLoanApplicationId(loanApplication.getUuid());
        loanUploadFile.setUuid(null);
        loanUploadFile.setFolder(folder);
        loanUploadFileService.save(loanUploadFile);
      }
    } catch (Exception ex) {
      log.error("Copy file error: {}", ex.getMessage());
    }
  }

  private LoanApplicationEntity copyCustomerInfo(LoanApplication loanApplication,
      LoanApplicationEntity entity) {
    if (entity == null) {
      entity = LoanApplicationEntity.builder().build();
    }
    entity.setFullName(loanApplication.getFullName());
    entity.setBirthday(loanApplication.getBirthday());
    if (entity.getBirthday() != null) {
      entity.setAge(DateUtils.calculateAge(entity.getBirthday()));
    }
    entity.setGender(loanApplication.getGender());
    entity.setIdNo(loanApplication.getIdNo());
    entity.setIssuedOn(loanApplication.getIssuedOn());
    entity.setPlaceOfIssue(loanApplication.getPlaceOfIssue());
    entity.setOldIdNo(loanApplication.getOldIdNo());
    entity.setOldIdNo2(loanApplication.getOldIdNo2());
    entity.setOldIdNo3(loanApplication.getOldIdNo3());
    entity.setEmail(loanApplication.getEmail());
    if (loanApplication.getNationality() != null) {
      entity.setNationality(loanApplication.getNationality());
    }
    entity.setMaritalStatus(loanApplication.getMaritalStatus());
    entity.setNumberOfDependents(loanApplication.getNumberOfDependents());
    entity.setProvince(loanApplication.getProvince());
    entity.setProvinceName(loanApplication.getProvinceName());
    entity.setDistrict(loanApplication.getDistrict());
    entity.setDistrictName(loanApplication.getDistrictName());
    entity.setWard(loanApplication.getWard());
    entity.setWardName(loanApplication.getWardName());
    entity.setAddress(loanApplication.getAddress());
    if (!StringUtils.isEmpty(loanApplication.getRefCode())) {
      entity.setRefCode(loanApplication.getRefCode());
    }
    entity.setResidenceProvince(loanApplication.getResidenceProvince());
    entity.setResidenceProvinceName(loanApplication.getResidenceProvinceName());
    entity.setResidenceDistrict(loanApplication.getResidenceDistrict());
    entity.setResidenceDistrictName(loanApplication.getResidenceDistrictName());
    entity.setResidenceWard(loanApplication.getResidenceWard());
    entity.setResidenceWardName(loanApplication.getResidenceWardName());
    entity.setResidenceAddress(loanApplication.getResidenceAddress());
    entity.setCifNo(loanApplication.getCifNo());
    entity.setEducation(loanApplication.getEducation());
    entity.setPartnerCode(loanApplication.getPartnerCode());
    entity.setCustomerSegment(loanApplication.getCustomerSegment());
    return entity;
  }
}
