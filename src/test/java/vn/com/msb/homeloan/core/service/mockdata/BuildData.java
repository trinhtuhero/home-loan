package vn.com.msb.homeloan.core.service.mockdata;

import java.util.Date;
import vn.com.msb.homeloan.core.constant.AssetTypeEnum;
import vn.com.msb.homeloan.core.constant.BusinessLineEnum;
import vn.com.msb.homeloan.core.constant.BusinessTypeEnum;
import vn.com.msb.homeloan.core.constant.CollateralLocationEnum;
import vn.com.msb.homeloan.core.constant.CollateralStatusEnum;
import vn.com.msb.homeloan.core.constant.CollateralTypeEnum;
import vn.com.msb.homeloan.core.constant.ContactPersonTypeEnum;
import vn.com.msb.homeloan.core.constant.CreditEvaluationResultEnum;
import vn.com.msb.homeloan.core.constant.DebtPaymentMethodCWIEnum;
import vn.com.msb.homeloan.core.constant.DownloadStatus;
import vn.com.msb.homeloan.core.constant.EducationEnum;
import vn.com.msb.homeloan.core.constant.FieldSurveyEvaluationResultEnum;
import vn.com.msb.homeloan.core.constant.FieldSurveyRelationshipTypeEnum;
import vn.com.msb.homeloan.core.constant.FormOfCreditEnum;
import vn.com.msb.homeloan.core.constant.GenderEnum;
import vn.com.msb.homeloan.core.constant.IncomeFromEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.constant.LocationTypeEnum;
import vn.com.msb.homeloan.core.constant.MaritalStatusEnum;
import vn.com.msb.homeloan.core.constant.NationalEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.constant.PaymentMethodEnum;
import vn.com.msb.homeloan.core.constant.SignatureLevelEnum;
import vn.com.msb.homeloan.core.entity.AssetEvaluateEntity;
import vn.com.msb.homeloan.core.entity.AssetEvaluateItemEntity;
import vn.com.msb.homeloan.core.entity.BusinessIncomeEntity;
import vn.com.msb.homeloan.core.entity.CollateralEntity;
import vn.com.msb.homeloan.core.entity.CollateralOwnerEntity;
import vn.com.msb.homeloan.core.entity.CreditAppraisalEntity;
import vn.com.msb.homeloan.core.entity.CreditworthinessItemEntity;
import vn.com.msb.homeloan.core.entity.FieldSurveyItemEntity;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;
import vn.com.msb.homeloan.core.entity.OtherIncomeEntity;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.entity.SalaryIncomeEntity;
import vn.com.msb.homeloan.core.model.LoanApplication;

public class BuildData {

  public static LoanApplication buildLoanApplication(String profileId) {
    return LoanApplication.builder()
        .profileId(profileId)
        .phone("0987654321")
        .fullName("Nguyen van a")
        .status(LoanInfoStatusEnum.DRAFT.getCode())
        .gender(GenderEnum.FEMALE)
        .nationality(NationalEnum.VIETNAM)
        .email("abc@gmail.com")
        .idNo("123456779800")
        .issuedOn(new Date())
        .birthday(new Date())
        .placeOfIssue("Ha Noi")
        .oldIdNo("172323529")
        .province("01")
        .provinceName("Ha Noi")
        .district("001")
        .districtName("ha dong")
        .ward("0001")
        .wardName("Yen nghia")
        .address("Yen nghia ha dong ha noi")
        .maritalStatus(MaritalStatusEnum.MARRIED)
        .numberOfDependents(0)
        .downloadStatus(DownloadStatus.NEED_UPLOAD_ZIP.getValue())
        .build();
  }

  public static MarriedPersonEntity buildMarriedPersonEntity(String loanId) {
    return MarriedPersonEntity.builder()
        .loanId(loanId)
        .phone("0987654321")
        .fullName("Nguyen van a")
        .gender(GenderEnum.FEMALE)
        .nationality(NationalEnum.VIETNAM)
        .email("abc@gmail.com")
        .idNo("123456779800")
        .issuedOn(new Date())
        .birthday(new Date())
        .placeOfIssue("Ha Noi")
        .oldIdNo("172323529")
        .province("01")
        .provinceName("Ha Noi")
        .district("001")
        .districtName("ha dong")
        .ward("0001")
        .wardName("Yen nghia")
        .address("Yen nghia ha dong ha noi")
        .build();
  }

  public static ProfileEntity buildProfile(String profileId) {
    return ProfileEntity.builder()
        .uuid(profileId)
        .phone("0987654321")
        .fullName("Nguyen van a")
        .gender(GenderEnum.FEMALE)
        .nationality(NationalEnum.VIETNAM)
        .email("abc@gmail.com")
        .idNo("123456779800")
        .issuedOn(new Date())
        .birthday(new Date())
        .placeOfIssue("Ha Noi")
        .oldIdNo("172323529")
        .build();
  }

  public static CollateralEntity buildCollateral(String collateralId, String loanId) {
    return CollateralEntity.builder()
        .relationship(ContactPersonTypeEnum.HUSBAND)
        .location(CollateralLocationEnum.MD)
        .uuid(collateralId)
        .loanId(loanId)
        .type(CollateralTypeEnum.ND)
        .status(CollateralStatusEnum.THIRD_PARTY)
        .fullName("Nguyen van A")
        .province("01")
        .provinceName("Ha noi")
        .district("001")
        .districtName("Ha dong")
        .ward("0001")
        .wardName("Yen nghia")
        .address("Address")
        .value(10000000L)
        .guaranteedValue(5000000L)
        .description("Description")
        .build();
  }

  public static LoanPayerEntity buildLoanPayer(String loanId) {
    return LoanPayerEntity.builder()
        .loanId(loanId)
        .phone("0987654321")
        .fullName("Nguyen van a")
        .gender(GenderEnum.FEMALE)
        .nationality(NationalEnum.VIETNAM)
        .email("abc@gmail.com")
        .idNo("123456779800")
        .issuedOn(new Date())
        .birthday(new Date())
        .placeOfIssue("Ha Noi")
        .oldIdNo("172323529")
        .province("01")
        .provinceName("Ha Noi")
        .district("001")
        .districtName("ha dong")
        .ward("0001")
        .wardName("Yen nghia")
        .address("Yen nghia ha dong ha noi")
        .build();
  }

  public static BusinessIncomeEntity buildBusinessIncome(String loanId,
      BusinessTypeEnum businessTypeEnum) {
    return BusinessIncomeEntity.builder()
        .loanApplicationId(loanId)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .businessType(businessTypeEnum)
        .businessLine(BusinessLineEnum.ADMINISTRATION_AND_CAREER)
        .businessCode("businessCode")
        .name("Name")
        .province("01")
        .provinceName("Hà Nội")
        .district("001")
        .districtName("Hoàn kiếm")
        .ward("0001")
        .wardName("Đào tấn")
        .address("address")
        .phone("Phone")
        .value(1212121L)
        .build();
  }

  public static SalaryIncomeEntity buildSalaryIncome(String loanId) {
    return SalaryIncomeEntity.builder()
        .loanApplicationId(loanId)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .paymentMethod(PaymentMethodEnum.MSB)
        .officeName("officeName")
        .officePhone("officePhone")
        .officeProvince("01")
        .officeProvinceName("Hà Nội")
        .officeDistrict("001")
        .officeDistrictName("Hà Đông")
        .officeWard("0001")
        .officeWardName("Yên nghĩa")
        .officeAddress("address")
        .officeTitle("officeTitle")
        .value(1000L)
        .build();
  }

  public static OtherIncomeEntity buiOtherIncome(String loanId) {
    return OtherIncomeEntity.builder()
        .loanApplicationId(loanId)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .incomeFrom(IncomeFromEnum.OTHERS)
        .value(10000L)
        .build();
  }

  public static CollateralOwnerEntity buildCollateralOwner(String id) {
    return CollateralOwnerEntity.builder()
        .uuid(id)
        .phone("0987654321")
        .fullName("Nguyen van a")
        .gender(GenderEnum.FEMALE)
        //.nationality(NationalityEnum.VIETNAM.getCode())
        .email("abc@gmail.com")
        .idNo("123456779800")
        .issuedOn(new Date())
        .birthday(new Date())
        .placeOfIssue("Ha Noi")
        .oldIdNo("172323529")
        .province("01")
        .provinceName("Ha Noi")
        .district("001")
        .districtName("ha dong")
        .ward("0001")
        .wardName("Yen nghia")
        .address("Yen nghia ha dong ha noi")
        .maritalStatus(MaritalStatusEnum.MARRIED)
        //.downloadStatus(DownloadStatus.NEED_UPLOAD_ZIP.getValue())
        .build();
  }

  public static CreditAppraisalEntity buiOtherCreditAppraisalEntity(String loanId) {
    return CreditAppraisalEntity.builder()
        .uuid(loanId)
        .loanApplicationId(loanId)
        .totalOfDebits(100L)
        .totalIncome(100L)
        .dti(100.0)
        .ltv(100.0)
        .creditEvaluationResult(CreditEvaluationResultEnum.NOT_PASS)
        .beforeOpenLimitCondition("beforeOpenLimitCondition")
        .beforeDisbursementCondition("beforeDisbursementCondition")
        .afterDisbursementCondition("afterDisbursementCondition")
        .businessReview("businessReview")
        .cssProfileId("cssProfileId")
        .cssScore(9.0)
        .cssGrade("cssGrade")
        .scoringDate(new Date())
        .businessArea("business_area")
        .businessName("businessName")
        .saleFullName("saleFullName")
        .salePhone("098765432")
        .managerFullName("managerFullName")
        .managerPhone("managerPhone")
        .signatureLevel(SignatureLevelEnum.GĐ_HUB)
        .build();
  }

  public static AssetEvaluateEntity buildAssetEvaluate(String id) {
    return AssetEvaluateEntity.builder()
        .uuid(id)
        .loanApplicationId(id)
        .totalValue(10L)
        .debitBalance(10L)
        .incomeValue(10L)
        .rmInputValue(10L)
        .rmReview("rmReview")
        .build();
  }

  public static AssetEvaluateItemEntity buildAssetEvaluateItem(String id) {
    return AssetEvaluateItemEntity.builder()
        .assetEvalId(id)
        .assetType(AssetTypeEnum.REAL_ESTATE)
        .legalRecord("legalRecord")
        .value(10L)
        .assetDescription("assetDescription")
        .build();
  }

  public static CollateralOwnerEntity buildCollateralOwner2(String loanId) {
    return CollateralOwnerEntity.builder()
        .loanId(loanId)
        .phone("0987654321")
        .fullName("Nguyen van a")
        .gender(GenderEnum.FEMALE)
        .nationality(NationalEnum.VIETNAM)
        .relationship(ContactPersonTypeEnum.HUSBAND)
        .educationLevel(EducationEnum.BACHELOR)
        .email("abc@gmail.com")
        .idNo("123456779800")
        .issuedOn(new Date())
        .birthday(new Date())
        .placeOfIssue("Ha Noi")
        .oldIdNo("172323529")
        .province("01")
        .provinceName("Ha Noi")
        .district("001")
        .districtName("ha dong")
        .ward("0001")
        .wardName("Yen nghia")
        .address("Yen nghia ha dong ha noi")
        .maritalStatus(MaritalStatusEnum.MARRIED)
        .build();
  }

  public static FieldSurveyItemEntity buildFieldSurveyItem(String loanId) {
    return FieldSurveyItemEntity.builder()
        .uuid(loanId)
        .loanApplicationId(loanId)
        .creditAppraisalId("creditAppraisalId")
        .relationshipType(FieldSurveyRelationshipTypeEnum.PAYER_PERSON)
        .time(new Date())
        .locationType(LocationTypeEnum.NOI_CU_TRU)
        .fieldGuidePerson("fieldGuidePerson")
        .province("001")
        .provinceName("Ha noi")
        .district("0001")
        .districtName("Hoan kiem")
        .ward("00001")
        .wardName("Dao tan")
        .address("address")
        .evaluationResult(FieldSurveyEvaluationResultEnum.NOT_PASS)
        .build();
  }

  public static CreditworthinessItemEntity buildCreditworthinessItem(String loanId) {
    return CreditworthinessItemEntity.builder()
        .loanApplicationId(loanId)
        .creditAppraisalId("creditAppraisalId")
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .target("target")
        .creditInstitution("creditInstitution")
        .creditInstitutionName("creditInstitutionName")
        .formOfCredit(FormOfCreditEnum.LINE_OF_CREDIT)
        .currentBalance(1000000L)
        .monthlyDebtPayment(100000000L)
        .interestRate(50.0)
        .firstPeriod(10)
        .remainingPeriod(15)
        .firstLimit(10L)
        .debtPaymentMethod(DebtPaymentMethodCWIEnum.DEBT_PAYMENT_1)
        .build();
  }
}
