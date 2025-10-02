package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.entity.*;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.*;
import vn.com.msb.homeloan.core.model.mapper.*;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.*;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;
import vn.com.msb.homeloan.core.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CreditAppraisalServiceImpl implements CreditAppraisalService {

  private final CreditAppraisalRepository creditAppraisalRepository;

  private final FieldSurveyItemRepository fieldSurveyItemRepository;

  private final CreditworthinessItemRepository creditworthinessItemRepository;

  private final ExceptionItemRepository exceptionItemRepository;

  private final LoanPayerRepository loanPayerRepository;

  private final CreditInstitutionRepository creditInstitutionRepository;

  private final HomeLoanUtil homeLoanUtil;

  private final LoanApplicationService loanApplicationService;

  private final CollateralService collateralService;

  private final CMSTabActionService cmsTabActionService;
  private final CmsUserService cmsUserService;

  private final OrganizationRepository organizationRepository;

  private final CommonIncomeService commonIncomeService;

  private final LoanApplicationItemService loanApplicationItemService;

  private final CreditworthinessItemService creditworthinessItemService;

  private final CssRepository cssRepository;

  private final CmsUserRepository cmsUserRepository;

  @Override
  @Transactional
  public CreditAppraisal save(CreditAppraisal creditAppraisal) {

    LoanApplication loanApplication = loanApplicationService.findById(
        creditAppraisal.getLoanApplicationId());

    loanApplicationService.checkEditLoanApp(
        LoanApplicationMapper.INSTANCE.toEntity(loanApplication), ClientTypeEnum.CMS);

    // For insert
    CreditAppraisalEntity creditAppraisalEntity = creditAppraisalRepository.findByLoanApplicationId(
            creditAppraisal.getLoanApplicationId())
        .orElse(null);

    // may be null
    CommonIncome commonIncome = commonIncomeService.findByLoanApplicationId(
        creditAppraisal.getLoanApplicationId());

    // Thông tin thực địa có ít nhất một bản ghi
    List<FieldSurveyItem> fieldSurveyItems = FieldSurveyItemMapper.INSTANCE.toModels(
        fieldSurveyItemRepository.findByLoanApplicationId(creditAppraisal.getLoanApplicationId()));
    if (CollectionUtils.isEmpty(fieldSurveyItems)) {
      throw new ApplicationException(ErrorEnum.FIELD_SURVEY_ITEM_NOT_EMPTY);
    }

    List<ExceptionItem> exceptionItems = ExceptionItemMapper.INSTANCE.toModels(
        exceptionItemRepository.findByLoanApplicationId(creditAppraisal.getLoanApplicationId()));

    List<CreditworthinessItem> creditworthinessItems = CreditworthinessItemMapper.INSTANCE.toModels(
        creditworthinessItemRepository.findByLoanApplicationId(
            creditAppraisal.getLoanApplicationId()));
    autoComplete(loanApplication, commonIncome, creditAppraisal, creditworthinessItems);

    CreditAppraisal creditAppraisalModel;

    if (creditAppraisalEntity == null) {
      creditAppraisalEntity = creditAppraisalRepository.save(
          CreditAppraisalMapper.INSTANCE.toEntity(creditAppraisal));
      creditAppraisalModel = CreditAppraisalMapper.INSTANCE.toModel(creditAppraisalEntity);
    } else {
      creditAppraisal.setLoanApplicationId(creditAppraisalEntity.getLoanApplicationId());
      creditAppraisal.setUuid(creditAppraisalEntity.getUuid());
      // For update
      creditAppraisalEntity = creditAppraisalRepository.save(
          CreditAppraisalMapper.INSTANCE.toEntity(creditAppraisal));
      creditAppraisalModel = CreditAppraisalMapper.INSTANCE.toModel(creditAppraisalEntity);
    }
    creditAppraisalModel.setFieldSurveyItems(fieldSurveyItems);
    creditAppraisalModel.setCreditworthinessItems(creditworthinessItems);
    creditAppraisalModel.setExceptionItems(exceptionItems);
    cmsTabActionService.save(creditAppraisalModel.getLoanApplicationId(), CMSTabEnum.EXPERTISE);
    return creditAppraisalModel;
  }

  // Kiểm tra đúng là người đồng trả nợ của hồ sơ này
  private void checkLoanPayer(CreditAppraisal creditAppraisal,
      CreditworthinessItem creditworthinessItem) {
    if (creditworthinessItem.getOwnerType().equals(OwnerTypeEnum.PAYER_PERSON)) {
      LoanPayerEntity loanPayerEntity = loanPayerRepository.findByUuid(
          creditworthinessItem.getTarget()).orElse(null);
      if (loanPayerEntity == null || (loanPayerEntity != null
          && !creditAppraisal.getLoanApplicationId().equals(loanPayerEntity.getLoanId()))) {
        throw new ApplicationException(ErrorEnum.DATA_INVALID, "target",
            creditworthinessItem.getTarget());
      }
    } else {
      creditworthinessItem.setTarget(null);
    }
  }

  // Set thông tin tổ chức tín dụng
  private void setCreditInstitutionName(CreditworthinessItem creditworthinessItem) {
    CreditInstitutionEntity creditInstitutionEntity = creditInstitutionRepository.findByCode(
        creditworthinessItem.getCreditInstitution()).orElseThrow(
        () -> new ApplicationException(ErrorEnum.DATA_INVALID, "credit_institution",
            creditworthinessItem.getCreditInstitution()));
    creditworthinessItem.setCreditInstitutionName(creditInstitutionEntity.getName());
  }

  private void autoComplete(LoanApplication loanApplication, CommonIncome commonIncome,
      CreditAppraisal creditAppraisal, List<CreditworthinessItem> creditworthinessItems) {
    // Tổng nghĩa vụ trả nợ
    creditworthinessItems = creditworthinessItemService.getByLoanId(loanApplication.getUuid());

    for (CreditworthinessItem creditworthinessItem : creditworthinessItems) {
      creditworthinessItem.setMonthlyDebtPaymentAuto();
    }

    Long totalOfDebits = HomeLoanUtil.computeTotalOfDebits(creditworthinessItems);

    long totalIncomes = 0l;

    // hold on -> Ok

    if (RecognitionMethod1Enum.ACTUALLY_RECEIVED.equals(
        commonIncome != null ? commonIncome.getRecognitionMethod1() : null)
        || RecognitionMethod1Enum.DECLARATION.equals(
        commonIncome != null ? commonIncome.getRecognitionMethod1() : null)) {
      totalIncomes = loanApplicationService.totalIncomeActuallyReceived(loanApplication.getUuid());
    } else if (RecognitionMethod1Enum.EXCHANGE.equals(
        commonIncome != null ? commonIncome.getRecognitionMethod1() : null)) {
      totalIncomes = loanApplicationService.totalIncomeExchange(loanApplication.getUuid());
    }

    creditAppraisal.setTotalOfDebits(totalOfDebits);
    creditAppraisal.setTotalIncome(totalIncomes);

    // DTI
    Double dti = HomeLoanUtil.computeDTI(totalOfDebits, totalIncomes);

    if (dti == null) {
      creditAppraisal.setDti(null);
    } else {
      BigDecimal dtiBD = BigDecimal.valueOf(dti).setScale(2, RoundingMode.HALF_UP);
      double roundDti = dtiBD.doubleValue();
      creditAppraisal.setDti(roundDti);
    }

    creditAppraisal.setLtv(homeLoanUtil.computeLTV(loanApplication.getUuid()));
  }

  // Set thông tin tỉnh, quận, huyện
  private void setMasterData(FieldSurveyItem fieldSurveyItem) {
    String provinceName = homeLoanUtil.getProvinceNameByCode(fieldSurveyItem.getProvince());
    if (StringUtils.isEmpty(provinceName)) {
      throw new ApplicationException(ErrorEnum.DATA_INVALID, "province",
          fieldSurveyItem.getProvince());
    }

    String districtName = homeLoanUtil.getDistrictNameByCode(fieldSurveyItem.getDistrict());
    if (StringUtils.isEmpty(districtName)) {
      throw new ApplicationException(ErrorEnum.DATA_INVALID, "district",
          fieldSurveyItem.getDistrict());
    }

    String wardName = homeLoanUtil.getWardNameByCode(fieldSurveyItem.getWard());
    if (StringUtils.isEmpty(wardName)) {
      throw new ApplicationException(ErrorEnum.DATA_INVALID, "ward", fieldSurveyItem.getWard());
    }
  }

  @Override
  public CreditAppraisal getByLoanId(String loanId) {

    LoanApplication loanApplication = loanApplicationService.findById(loanId);

    CommonIncome commonIncome = commonIncomeService.findByLoanApplicationId(loanId);

    CmsUserEntity picRmEntity = null;
    if (loanApplication.getPicRm() != null) {
      picRmEntity = cmsUserRepository.findByEmplId(loanApplication.getPicRm()).orElse(null);
    }
    CmsUser cmsUser = cmsUserService.findByEmail(AuthorizationUtil.getEmail());

    OrganizationEntity organization;

    if (picRmEntity != null && picRmEntity.getBranchCode() != null) {
      organization = organizationRepository.findByCode(picRmEntity.getBranchCode());
    } else {
      organization = cmsUser.getBranchCode() != null ? organizationRepository.findByCode(
          cmsUser.getBranchCode()) : null;
    }

    CreditAppraisalEntity creditAppraisalEntity = creditAppraisalRepository.findByLoanApplicationId(
        loanId).orElse(null);

    OrganizationEntity businessAreaEntity = getBusinessArea(organization);
    CreditAppraisal creditAppraisal;
    if (creditAppraisalEntity == null) {
      creditAppraisal = CreditAppraisal.builder()
          .businessArea(businessAreaEntity != null ? businessAreaEntity.getCode() : null)
          .businessAreaName(businessAreaEntity != null ? businessAreaEntity.getName() : null)
          .businessName(organization != null ? organization.getName() : null)
          .businessCode(organization != null ? organization.getCode() : null)
          .saleFullName(picRmEntity == null ? cmsUser.getFullName() : picRmEntity.getFullName())
          .salePhone(picRmEntity == null ? cmsUser.getPhone() : picRmEntity.getPhone())
          .fieldSurveyItems(new ArrayList<>())
          .creditworthinessItems(new ArrayList<>())
          .exceptionItems(new ArrayList<>()).build();
      try {

        String leaderEmail;
        if (picRmEntity != null) {
          leaderEmail = picRmEntity.getLeaderEmail();
        } else {
          leaderEmail = cmsUser != null ? cmsUser.getLeaderEmail() : null;
        }

        if (leaderEmail != null) {
          CmsUserEntity leaderEntity = cmsUserRepository.findByEmail(leaderEmail).orElse(null);
          creditAppraisal.setManagerFullName(
              leaderEntity != null ? leaderEntity.getFullName() : null);
          creditAppraisal.setManagerPhone(leaderEntity != null ? leaderEntity.getPhone() : null);
        }
      } catch (Exception ex) {
        log.error("findByEmail: ", ex);
      }
    } else {
      creditAppraisal = CreditAppraisalMapper.INSTANCE.toModel(creditAppraisalEntity);
    }
    creditAppraisal.setFieldSurveyItems(FieldSurveyItemMapper.INSTANCE.toModels(
        fieldSurveyItemRepository.findByLoanApplicationIdOrderByCreatedAt(loanId)));

    List<CreditworthinessItem> creditworthinessItems = CreditworthinessItemMapper.INSTANCE.toModels(
        creditworthinessItemRepository.findByLoanApplicationId(loanId));

    creditAppraisal.setCreditworthinessItems(creditworthinessItems);
    creditAppraisal.setExceptionItems(ExceptionItemMapper.INSTANCE.toModels(
        exceptionItemRepository.findByLoanApplicationId(loanId)));

    CssEntity cssEntity = cssRepository.findByLoanApplicationId(loanId);
    if (cssEntity != null) {
      creditAppraisal.setCssProfileId(
          cssEntity.getProfileId() == null ? null : cssEntity.getProfileId().toString());
      creditAppraisal.setCssScore(
          StringUtils.isEmpty(cssEntity.getScore()) ? null : Double.valueOf(cssEntity.getScore()));
      creditAppraisal.setCssGrade(cssEntity.getGrade());
      creditAppraisal.setScoringDate(cssEntity.getScoringDate());
    }

    // auto tính Tổng nghĩa vụ trả nợ, Tổng thu nhập, DTI, LTV
    autoComplete(loanApplication, commonIncome, creditAppraisal, creditworthinessItems);

    return creditAppraisal;
  }

  private OrganizationEntity getBusinessArea(OrganizationEntity org) {
    if (org == null || "AREA".equals(org.getType())) {
      return org;
    }
    OrganizationEntity organization = organizationRepository.findByCode(org.getAreaCode());
    if (organization == null) {
      return null;
    }

    if (!"AREA".equals(organization.getType())) {
      return getBusinessArea(organization);
    }
    return organization;
  }

    /*private void validateInputCreditworthinessItemRequest(List<CreditworthinessItem> creditworthinessItems) {
        Map<String, String> errorDetail = new HashMap<>();
        for (CreditworthinessItem creditworthinessItem : creditworthinessItems) {
            // Lãi suất (bắt buộc trong trường hợp hình thức cấp tín dụng là vay không TSBĐ/ vay có TSBĐ/ hạn mức tín dụng)
            if (Arrays.asList(FormOfCreditEnum.UNSECURED_MORTGAGE, FormOfCreditEnum.SECURED_MORTGAGE, FormOfCreditEnum.LINE_OF_CREDIT).contains(creditworthinessItem.getFormOfCredit())
                    && creditworthinessItem.getInterestRate() == null) {
                errorDetail.put("not_blank.interest_rate", "must not be null");
            }
            // trong trường hợp hình thức cấp tín dụng là vay không TSBĐ/ vay có TSBĐ
            if (Arrays.asList(FormOfCreditEnum.UNSECURED_MORTGAGE, FormOfCreditEnum.SECURED_MORTGAGE).contains(creditworthinessItem.getFormOfCredit())) {
                //Kì hạn vay ban đầu bắt buộc
                if (creditworthinessItem.getFirstPeriod() == null) {
                    errorDetail.put("not_blank.first_period", "must not be null");
                }
                // Kì hạn vay còn lại bắt buộc
                if (creditworthinessItem.getRemainingPeriod() == null) {
                    errorDetail.put("not_blank.remaining_period", "must not be null");
                }
                //Phương thức trả nợ bắt buộc
                if (creditworthinessItem.getDebtPaymentMethod() == null) {
                    errorDetail.put("not_blank.debt_payment_method", "must not be null");
                }
            }
        }

        if (!errorDetail.isEmpty()) {
            throw new ApplicationException(ErrorEnum.INVALID_FORM, errorDetail);
        }
    }*/
}
