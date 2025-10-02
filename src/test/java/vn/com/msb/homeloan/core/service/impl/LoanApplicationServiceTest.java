package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.BusinessTypeEnum;
import vn.com.msb.homeloan.core.constant.CustomerApprovalEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.GenderEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.entity.AssetEvaluateEntity;
import vn.com.msb.homeloan.core.entity.AssetEvaluateItemEntity;
import vn.com.msb.homeloan.core.entity.BusinessIncomeEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanPreApprovalEntity;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;
import vn.com.msb.homeloan.core.entity.OtherEvaluateEntity;
import vn.com.msb.homeloan.core.entity.OtherIncomeEntity;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.entity.SalaryIncomeEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.model.LoanApplicationReview;
import vn.com.msb.homeloan.core.model.LoanUploadFile;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.model.request.UploadPresignedUrlRequest;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;
import vn.com.msb.homeloan.core.util.StringUtils;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceTest extends LoanApplicationServiceBaseTest {

  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  @Test
  void givenValidInput_ThenGetMobioLink_shouldReturnSuccess() {
    String mobioLink = loanApplicationService.getMobioLink(LOAN_ID);
    assertEquals(StringUtils.isEmpty(mobioLink), false);
  }

  @Test
  void givenValidInput_ThenCustomerReview_shouldReturnLoanNotFound() {
    doReturn(Optional.empty()).when(loanApplicationRepository).findById(any());
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.ldpReview(LOAN_ID);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenCustomerReview_shouldReturnSuccess() {
    LoanApplication loanApplication = BuildData.buildLoanApplication(PROFILE_ID);
    loanApplication.setStatus(LoanInfoStatusEnum.PROCESSING.getCode());
    loanApplication.setUuid(LOAN_ID);
    doReturn(Optional.of(LoanApplicationMapper.INSTANCE.toEntity(loanApplication))).when(
        loanApplicationRepository).findById(any());

    LoanPreApprovalEntity loanPreApprovalEntity = LoanPreApprovalEntity.builder()
        .loanId(LOAN_ID)
        .metaData(
            "{\"loan_application\":{\"uuid\":\"025635c4-f82e-459b-ae4b-9bb713d47749\",\"profile_id\":\"8ccb8204-956b-434b-9d61-dadb9198af8e\",\"phone\":\"0981231233\",\"full_name\":\"nguyen pham tran'n12..?!~, @#\",\"gender\":\"MALE\",\"nationality\":\"VIETNAM\",\"email\":\"email123@gmail.com\",\"id_no\":\"123123123\",\"issued_on\":\"2013-02-06T00:00:00.000+00:00\",\"birthday\":\"1990-05-09T00:00:00.000+00:00\",\"place_of_issue\":\"3\",\"place_of_issue_name\":\"Cục Quản lý xuất nhập cảnh\",\"province\":\"01\",\"province_name\":\"Thành phố Hà Nội\",\"district\":\"001\",\"district_name\":\"Quận Ba Đình\",\"ward\":\"00006\",\"ward_name\":\"Phường Vĩnh Phúc\",\"address\":\"234\",\"marital_status\":\"SINGLE\",\"number_of_dependents\":1,\"ref_code\":\"\",\"loan_purpose\":\"LAND\",\"loan_asset_value\":1000000000,\"loan_amount\":700000000,\"loan_time\":35,\"status\":\"PROCESSING\",\"current_step\":\"LOAN_SUBMIT\",\"download_status\":1,\"receive_channel\":\"LDP\",\"loan_code\":\"LDP80920220002\",\"total_income\":732441,\"approval_flow\":\"FAST\",\"recognition_method1\":\"ACTUALLY_RECEIVED\",\"created_at\":\"2022-09-08T09:44:14Z\",\"updated_at\":\"2022-09-09T07:58:50Z\"},\"contact_person\":{\"uuid\":\"d20344ea-e220-44ee-96c9-394f166de74c\",\"loan_id\":\"025635c4-f82e-459b-ae4b-9bb713d47749\",\"type\":\"FATHER\",\"full_name\":\"Nguyễn B'de\",\"phone\":\"0987654321\"},\"salary_incomes\":[{\"uuid\":\"1d1795bc-9c02-468e-ab69-315d73908fda\",\"loan_application_id\":\"025635c4-f82e-459b-ae4b-9bb713d47749\",\"owner_type\":\"ME\",\"payment_method\":\"CASH\",\"office_name\":\"4564hhh\",\"office_phone\":\"09756456432\",\"office_province\":\"79\",\"office_province_name\":\"Thành phố Hồ Chí Minh\",\"office_district\":\"768\",\"office_district_name\":\"Quận Phú Nhuận\",\"office_ward\":\"27061\",\"office_ward_name\":\"Phường 02\",\"office_address\":\"34\",\"office_title\":\"44vfrtrt6\",\"value\":54664,\"created_at\":\"2022-09-08T09:45:23Z\",\"updated_at\":\"2022-09-08T10:01:41Z\",\"start_work_date\":\"2022-08-30T00:00:00.000+00:00\",\"tax_code\":null,\"insurance_no\":null,\"kind_of_contract\":\"KHONG_THOI_HAN\",\"company\":\"OTHERS\",\"band\":null,\"kpi_rate\":null,\"empl_code\":null,\"special_group\":null,\"rm_review\":\"4645\",\"work_grouping\":\"GE1\"},{\"uuid\":\"fda1511a-23f5-49c5-8c68-34435e7e7b7b\",\"loan_application_id\":\"025635c4-f82e-459b-ae4b-9bb713d47749\",\"owner_type\":\"ME\",\"payment_method\":\"OTHERS\",\"office_name\":\"tydf\",\"office_phone\":\"0975645643\",\"office_province\":\"04\",\"office_province_name\":\"Tỉnh Cao Bằng\",\"office_district\":\"049\",\"office_district_name\":\"Huyện Quảng Uyên\",\"office_ward\":\"01597\",\"office_ward_name\":\"Xã Cai Bộ\",\"office_address\":\"5464\",\"office_title\":\"chức vụ\",\"value\":677777,\"created_at\":\"2022-09-08T09:44:53Z\",\"updated_at\":\"2022-09-08T09:44:53Z\",\"start_work_date\":null,\"tax_code\":null,\"insurance_no\":null,\"kind_of_contract\":null,\"company\":null,\"band\":null,\"kpi_rate\":null,\"empl_code\":null,\"special_group\":null,\"rm_review\":null,\"work_grouping\":null}],\"collaterals\":[{\"uuid\":\"34065330-ad7f-412d-9475-da2b8f11c082\",\"loan_id\":\"025635c4-f82e-459b-ae4b-9bb713d47749\",\"type\":\"ND\",\"status\":\"COUPLE\",\"full_name\":null,\"relationship\":null,\"province\":\"10\",\"province_name\":\"Tỉnh Lào Cai\",\"district\":\"087\",\"district_name\":\"Huyện Bảo Yên\",\"ward\":\"02968\",\"ward_name\":\"Xã Thượng Hà\",\"address\":\"3456545\",\"value\":null,\"location\":null,\"description\":null,\"mvalue_id\":null,\"core_id\":null,\"valuation_cert\":null,\"asset_category\":null,\"legal_doc\":null,\"doc_issued_on\":null,\"doc_place_of_issue\":null,\"guaranteed_value\":null,\"ltv_rate\":null}]}\n")
        .build();
    doReturn(Optional.of(loanPreApprovalEntity)).when(loanPreApprovalRepository).findById(any());

    LoanApplicationReview result = loanApplicationService.ldpReview(LOAN_ID);
    assertEquals(result.getLoanApplication().getUuid(), LOAN_ID);
  }

    /*@Test
    void givenApproval_ThenCustomerApprovalChange_shouldReturnSuccess() throws JsonProcessingException {
        String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
        LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
                .uuid(LOAN_ID)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
                .fullName("Nguyen van A")
                .gender(GenderEnum.MALE)
                .status(LoanInfoStatusEnum.PROCESSING)
                .createdAt((new Date()).toInstant())
                .build();
        doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository).findById(LOAN_ID);

        FileConfigEntity fileConfigEntity = FileConfigEntity.builder()
                .code(Constants.FILE_CONFIG_CODE_DNVV)
                .name(Constants.FILE_CONFIG_CODE_DNVV)
                .uuid(LOAN_ID)
                .build();
        doReturn(Optional.of(fileConfigEntity)).when(fileConfigRepository).findByCode(any());

        try (MockedStatic<HomeLoanUtil> theMock = Mockito.mockStatic(HomeLoanUtil.class)) {
            theMock.when(() -> HomeLoanUtil.exportFilePdf(any(), any()))
                    .thenReturn("Any String you want".getBytes());

            boolean result = loanApplicationService.customerApprovalChange(LOAN_ID, CustomerApprovalEnum.APPROVAL.getCode(), "");
            assertTrue(result);
        }
    }*/

  @Test
  void givenReject_ThenCustomerApprovalChange_shouldReturnSuccess() throws JsonProcessingException {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
        .fullName("Nguyen van A")
        .gender(GenderEnum.MALE)
        .status(LoanInfoStatusEnum.PROCESSING)
        .build();
    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository).findById(LOAN_ID);

    boolean result = loanApplicationService.customerApprovalChange(LOAN_ID,
        CustomerApprovalEnum.REJECT.getCode(), "");
    assertTrue(result);
  }

  @Test
  void givenApproval_ThenCustomerApprovalChange_shouldReturnLoanNotFound() {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    doReturn(Optional.empty()).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.customerApprovalChange(LOAN_ID,
          CustomerApprovalEnum.APPROVAL.getCode(), "");
    });
    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenTotalIncome_shouldReturnSuccess() {
    doReturn(100000L).when(loanApplicationRepository).totalIncome(LOAN_ID);

    long result = loanApplicationService.totalIncome(LOAN_ID);
    assertEquals(100000L, result);
  }

  @Test
  void givenValidInput_ThenTotalIncomeExchange_shouldReturnSuccess() {
    doReturn(100000L).when(loanApplicationRepository).totalIncomeExchange(LOAN_ID);

    long result = loanApplicationService.totalIncomeExchange(LOAN_ID);
    assertEquals(100000L, result);
  }

  @Test
  void givenValidInput_ThenTotalIncomeActuallyReceived_shouldReturnSuccess() {
    doReturn(100000L).when(loanApplicationRepository).totalIncomeActuallyReceived(LOAN_ID);

    long result = loanApplicationService.totalIncomeActuallyReceived(LOAN_ID);
    assertEquals(100000L, result);
  }

  @Test
  void givenValidInput_ThenDeleteUnselectedIncomes_shouldReturnSuccess() {
    String[] strings = {"test"};
    List<SalaryIncomeEntity> salaryIncomeEntities = new ArrayList<>();
    salaryIncomeEntities.add(SalaryIncomeEntity.builder().build());
    doReturn(salaryIncomeEntities).when(salaryIncomeRepository).findByLoanApplicationId(LOAN_ID);

    List<BusinessIncomeEntity> businessIncomeEntities = new ArrayList<>();
    businessIncomeEntities.add(BusinessIncomeEntity.builder().build());
    doReturn(businessIncomeEntities).when(businessIncomeRepository)
        .findByLoanApplicationIdAndBusinessTypeIn(LOAN_ID,
            Arrays.asList(BusinessTypeEnum.WITH_REGISTRATION,
                BusinessTypeEnum.WITHOUT_REGISTRATION));
    doReturn(businessIncomeEntities).when(businessIncomeRepository)
        .findByLoanApplicationIdAndBusinessTypeIn(LOAN_ID,
            Arrays.asList(BusinessTypeEnum.ENTERPRISE));

    List<OtherIncomeEntity> otherIncomeEntities = new ArrayList<>();
    otherIncomeEntities.add(OtherIncomeEntity.builder().build());
    doReturn(otherIncomeEntities).when(otherIncomeRepository).findByLoanApplicationId(LOAN_ID);

    doReturn(AssetEvaluateEntity.builder().build()).when(assetEvaluateRepository)
        .findByLoanApplicationId(LOAN_ID);

    List<OtherEvaluateEntity> otherEvaluateEntities = new ArrayList<>();
    otherEvaluateEntities.add(OtherEvaluateEntity.builder().build());
    doReturn(otherEvaluateEntities).when(otherEvaluateRepository).findByLoanApplicationId(LOAN_ID);

    loanApplicationService.deleteUnselectedIncomes(LOAN_ID, strings);

    verify(salaryIncomeRepository, times(1)).deleteAll(any());
    verify(otherIncomeRepository, times(1)).deleteAll(any());
    verify(businessIncomeRepository, times(2)).deleteAll(any());
    verify(assetEvaluateRepository, times(1)).delete(any());
    verify(otherEvaluateRepository, times(1)).deleteAll(any());
  }

  @Test
  void givenValidInput_ThenCheckPhone_shouldReturnPhoneInvalid() {
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.checkPhone("123456789");
    });
    assertEquals(exception.getCode().intValue(), ErrorEnum.OTP_PHONE_INVALID.getCode());
  }

  @Test
  void givenProfileNotExist_ThenCheckPhone_shouldReturnSuccess() {
    doReturn(Optional.empty()).when(profileRepository).findOneByPhone("0973431352");
    String result = loanApplicationService.checkPhone("0973431352");
    assertEquals(result, null);
  }

  @Test
  void givenLoanNotExist_ThenCheckPhone_shouldReturnSuccess() {
    ProfileEntity profile = ProfileEntity.builder()
        .phone("0973431352")
        .uuid("profileId")
        .build();
    doReturn(Optional.of(profile)).when(profileRepository).findOneByPhone("0973431352");

    doReturn(new ArrayList<>()).when(loanApplicationRepository)
        .findByProfileIdOrderByUpdatedAtDesc(profile.getUuid());

    String result = loanApplicationService.checkPhone("0973431352");
    assertEquals(result, null);
  }

  @Test
  void givenValidInput_ThenCheckPhone_shouldReturnSuccess() {
    ProfileEntity profile = ProfileEntity.builder()
        .phone("0973431352")
        .uuid("profileId")
        .build();
    doReturn(Optional.of(profile)).when(profileRepository).findOneByPhone("0973431352");

    List<LoanApplicationEntity> loanApplicationEntities = new ArrayList<>();
    loanApplicationEntities.add(LoanApplicationEntity.builder().uuid("loanId").build());
    doReturn(loanApplicationEntities).when(loanApplicationRepository)
        .findByProfileIdOrderByUpdatedAtDesc(profile.getUuid());

    String result = loanApplicationService.checkPhone("0973431352");
    assertEquals(result, "loanId");
  }

  @Test
  void givenValidInput_ThenLoanInitialize_shouldReturnPhoneInvalid() {
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.loanInitialize("123456789", false);
    });
    assertEquals(exception.getCode().intValue(), ErrorEnum.OTP_PHONE_INVALID.getCode());
  }

  @Test
  void givenProfileNotExist_ThenLoanInitialize_shouldReturnSuccess() {
    doReturn(Optional.empty()).when(profileRepository).findOneByPhone("0973431352");
    doReturn(ProfileEntity.builder().uuid("profileId").build()).when(profileRepository).save(any());
    Map<String, String> result = loanApplicationService.loanInitialize("0973431352", false);
    assertEquals(result.get("profile_id"), "profileId");
  }

  @Test
  void givenProfileExisted_ThenLoanInitialize_shouldReturnSuccess() {
    doReturn(Optional.of(ProfileEntity.builder().uuid("profileId").build())).when(profileRepository)
        .findOneByPhone("0973431352");
    Map<String, String> result = loanApplicationService.loanInitialize("0973431352", false);
    assertEquals(result.get("profile_id"), "profileId");
  }

  @Test
  void givenCopyLoan_ThenLoanInitialize_shouldReturnSuccess() {
    ProfileEntity profile = ProfileEntity.builder()
        .phone("0973431352")
        .uuid("profileId")
        .build();

    doReturn(Optional.of(profile)).when(profileRepository).findOneByPhone("0973431352");

    List<LoanApplicationEntity> loanApplicationEntities = new ArrayList<>();
    LoanApplication loanApplication = BuildData.buildLoanApplication(profile.getUuid());
    loanApplication.setUuid("loanIdInDb");
    loanApplicationEntities.add(LoanApplicationMapper.INSTANCE.toEntity(loanApplication));
    doReturn(loanApplicationEntities).when(loanApplicationRepository)
        .findByProfileIdOrderByUpdatedAtDesc(profile.getUuid());

    LoanApplicationEntity loanApplicationEntity = loanApplicationEntities.get(0);
    loanApplicationEntity.setUuid("loanIdNew");
    doReturn(loanApplicationEntity).when(loanApplicationRepository).save(any());

    List<MarriedPersonEntity> marriedPersonEntities = new ArrayList<>();
    MarriedPersonEntity marriedPersonEntity = BuildData.buildMarriedPersonEntity(
        loanApplication.getUuid());
    marriedPersonEntities.add(marriedPersonEntity);
    doReturn(marriedPersonEntities).when(marriedPersonsRepository).findByLoanId(any());

    List<SalaryIncomeEntity> salaryIncomeEntities = new ArrayList<>();
    SalaryIncomeEntity salaryIncomeEntity = BuildData.buildSalaryIncome(loanApplication.getUuid());
    salaryIncomeEntity.setOwnerType(OwnerTypeEnum.MARRIED_PERSON);
    salaryIncomeEntities.add(salaryIncomeEntity);
    doReturn(salaryIncomeEntities).when(salaryIncomeRepository).findByLoanApplicationId(any());

    List<BusinessIncomeEntity> businessIncomeEntities = new ArrayList<>();
    BusinessIncomeEntity businessIncomeEntity = BuildData.buildBusinessIncome(
        loanApplication.getUuid(), BusinessTypeEnum.ENTERPRISE);
    businessIncomeEntity.setOwnerType(OwnerTypeEnum.MARRIED_PERSON);
    businessIncomeEntities.add(businessIncomeEntity);
    doReturn(businessIncomeEntities).when(businessIncomeRepository).findByLoanApplicationId(any());

    List<OtherIncomeEntity> otherIncomeEntities = new ArrayList<>();
    OtherIncomeEntity otherIncomeEntity = BuildData.buiOtherIncome(loanApplication.getUuid());
    otherIncomeEntity.setOwnerType(OwnerTypeEnum.MARRIED_PERSON);
    otherIncomeEntities.add(otherIncomeEntity);
    doReturn(otherIncomeEntities).when(otherIncomeRepository).findByLoanApplicationId(any());

    AssetEvaluateEntity assetEvaluateEntity = BuildData.buildAssetEvaluate("assetEvaluateId");
    assetEvaluateEntity.setOwnerType(OwnerTypeEnum.ME);
    doReturn(assetEvaluateEntity).when(assetEvaluateRepository).findByLoanApplicationId(any());
    doReturn(assetEvaluateEntity).when(assetEvaluateRepository).save(any());

    List<AssetEvaluateItemEntity> assetEvaluateItemEntities = new ArrayList<>();
    assetEvaluateItemEntities.add(BuildData.buildAssetEvaluateItem(assetEvaluateEntity.getUuid()));
    doReturn(assetEvaluateItemEntities).when(assetEvaluateItemRepository).findByAssetEvalId(any());

    List<OtherEvaluateEntity> otherEvaluateEntities = new ArrayList<>();
    otherEvaluateEntities.add(
        OtherEvaluateEntity.builder().ownerType(OwnerTypeEnum.MARRIED_PERSON).build());
    doReturn(otherEvaluateEntities).when(otherEvaluateRepository).findByLoanApplicationId(any());
    String email = "hienpv5@msb.com.vn";
    String userId = "123456";
    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn(email);

      Map<String, String> result = loanApplicationService.loanInitialize("0973431352", true);
      assertEquals(result.get("profile_id"), "profileId");
    }

  }

  @Test
  void test_copyFileToApplication()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    LoanUploadFile loanUploadFile = LoanUploadFile.builder().folder("homeloan_service/")
        .fileName("test.png").build();
    LoanApplication loanApplication = LoanApplication.builder().uuid("uuid1")
        .createdAt(Instant.now()).build();

    String fullPath = String.format("%s%s", loanUploadFile.getFolder(),
        loanUploadFile.getFileName());

    assertEquals("homeloan_service/test.png", fullPath);

    byte[] conntent = new byte[0];

    doReturn(conntent).when(homeLoanUtil).downloadFileFromS3(eq(fullPath));

    doReturn(true).when(fileService)
        .upload(any(UploadPresignedUrlRequest.class), any(byte[].class), anyBoolean());

    doReturn(LoanUploadFile.builder().build()).when(loanUploadFileService).save(loanUploadFile);

    Method privateMethod = LoanApplicationServiceImpl.class.getDeclaredMethod(
        "copyFileToApplication", LoanUploadFile.class, LoanApplication.class);
    privateMethod.setAccessible(true);
    privateMethod.invoke(loanApplicationService, loanUploadFile, loanApplication);

    System.out.println("End");
  }
}
