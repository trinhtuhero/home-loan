package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.CollateralStatusEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.GenderEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.constant.MaritalStatusEnum;
import vn.com.msb.homeloan.core.constant.ReceiveChannelEnum;
import vn.com.msb.homeloan.core.constant.RecognitionMethod1Enum;
import vn.com.msb.homeloan.core.entity.CmsTabActionEntity;
import vn.com.msb.homeloan.core.entity.CollateralEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanPreApprovalEntity;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CmsUser;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;

@ExtendWith(MockitoExtension.class)
public class LoanApplicationServiceUpdateStatusTest extends LoanApplicationServiceBaseTest {

  @Test
  void givenValidInput_ThenUpdateStatus_shouldReturnLoanNotFound() {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    doReturn(Optional.empty()).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.updateStatus(LOAN_ID, LoanInfoStatusEnum.CLOSED.getCode(), "",
          ClientTypeEnum.CMS);
    });

    assertEquals(ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode(), exception.getCode().intValue());
  }

  @Test
  void givenValidInput_ThenUpdateStatus_shouldReturnUpdateStatusNotAllow() {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .fullName("Nguyen van A")
        .status(LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION)
        .build();

    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.updateStatus(LOAN_ID, LoanInfoStatusEnum.PROCESSING.getCode(), "",
          ClientTypeEnum.CMS);
    });
    assertEquals(ErrorEnum.LOAN_APPLICATION_UPDATE_STATUS_NOT_ALLOW.getCode(),
        exception.getCode().intValue());
  }

  @Test
  void givenValidInput_ThenUpdateStatus_shouldReturnValidateError() {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .fullName("Nguyen van A")
        .maritalStatus(MaritalStatusEnum.MARRIED)
        .status(LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION)
        .build();
    doReturn(new ArrayList<>()).when(cmsTabActionRepository).findByLoanId(LOAN_ID);

    CommonIncome commonIncome = new CommonIncome();
    commonIncome.setRecognitionMethod1(RecognitionMethod1Enum.ACTUALLY_RECEIVED);
    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      ReflectionTestUtils.invokeMethod(
          loanApplicationService, "validateWhenRmComplete", loanApplicationBefore);
    });
    assertEquals(ErrorEnum.CAN_NOT_COMPLETE_TO_TRINH.getCode(), exception.getCode().intValue());
  }

  @Test
  void givenRMComplete_ThenUpdateStatus_shouldReturnSuccess() throws JsonProcessingException {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
        .fullName("Nguyen van A")
        .picRm("emplId")
        .status(LoanInfoStatusEnum.ACCEPT_LOAN_APPLICATION)
        .build();
    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository).findById(LOAN_ID);

    LoanPreApprovalEntity loanPreApprovalEntity = LoanPreApprovalEntity.builder()
        .loanId(LOAN_ID)
        .metaData(
            "{\"loan_application\":{\"uuid\":\"025635c4-f82e-459b-ae4b-9bb713d47749\",\"profile_id\":\"8ccb8204-956b-434b-9d61-dadb9198af8e\",\"phone\":\"0981231233\",\"full_name\":\"nguyen pham tran'n12..?!~, @#\",\"gender\":\"MALE\",\"nationality\":\"VIETNAM\",\"email\":\"email123@gmail.com\",\"id_no\":\"123123123\", \"place_of_issue\":\"3\",\"place_of_issue_name\":\"Cục Quản lý xuất nhập cảnh\",\"province\":\"01\",\"province_name\":\"Thành phố Hà Nội\",\"district\":\"001\",\"district_name\":\"Quận Ba Đình\",\"ward\":\"00006\",\"ward_name\":\"Phường Vĩnh Phúc\",\"address\":\"234\",\"marital_status\":\"SINGLE\",\"number_of_dependents\":1,\"ref_code\":\"\",\"status\":\"PROCESSING\",\"current_step\":\"LOAN_SUBMIT\",\"download_status\":1,\"receive_channel\":\"LDP\",\"loan_code\":\"LDP80920220002\",\"total_income\":732441}}\n")
        .build();
    doReturn(Optional.of(loanPreApprovalEntity)).when(loanPreApprovalRepository).findById(any());

    doReturn(1).when(loanApplicationRepository)
        .updateStatus(any(), any(), any(), any(), any(), any(), any());

    List<CmsTabActionEntity> cmsTabActionEntities = new ArrayList<>();
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.CUSTOMER_INFO).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.MARRIED_PERSON).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.CONTACT_PERSON).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.ASSUMING_TOTAL_ASSETS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.ASSUMING_OTHERS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.SALARY_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.PERSONAL_BUSINESS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.BUSINESS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.OTHERS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.COLLATERAL_INFO).build());
    cmsTabActionEntities.add(CmsTabActionEntity.builder().tabCode(CMSTabEnum.LOAN_INFO).build());
    cmsTabActionEntities.add(CmsTabActionEntity.builder().tabCode(CMSTabEnum.FILE_UPLOAD).build());
    cmsTabActionEntities.add(CmsTabActionEntity.builder().tabCode(CMSTabEnum.EXPERTISE).build());
    doReturn(cmsTabActionEntities).when(cmsTabActionRepository).findByLoanId(any());

    CommonIncome commonIncome = new CommonIncome();
    commonIncome.setRecognitionMethod1(RecognitionMethod1Enum.ACTUALLY_RECEIVED);
    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(LOAN_ID);

    ProfileEntity profileEntity = new ProfileEntity();
    doReturn(Optional.of(profileEntity)).when(profileRepository).findById(any());

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn("abc@msb.com.vn");

      CmsUser cmsUserEntity = CmsUser.builder().emplId("emplId").build();
      doReturn(cmsUserEntity).when(cmsUserService).findByEmail("abc@msb.com.vn");
      String result = loanApplicationService.updateStatus(LOAN_ID,
          LoanInfoStatusEnum.RM_COMPLETED.getCode(), "", ClientTypeEnum.CMS);

      assertEquals("WAITING_FOR_CONFIRM", result);
    }
  }

  @Test
  void givenProcessing_ThenUpdateStatus_shouldReturnSuccess() throws JsonProcessingException {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
        .fullName("Nguyen van A")
        .picRm("emplId")
        .status(LoanInfoStatusEnum.ACCEPT_LOAN_APPLICATION)
        .build();
    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository).findById(LOAN_ID);

    LoanPreApprovalEntity loanPreApprovalEntity = LoanPreApprovalEntity.builder()
        .loanId(LOAN_ID)
        .metaData(
            "{\"loan_application\":{\"uuid\":\"025635c4-f82e-459b-ae4b-9bb713d47749\",\"profile_id\":\"8ccb8204-956b-434b-9d61-dadb9198af8e\",\"phone\":\"0981231233\",\"full_name\":\"nguyen pham tran'n12..?!~, @#\",\"gender\":\"MALE\",\"nationality\":\"VIETNAM\",\"email\":\"email123@gmail.com\",\"id_no\":\"123123123\", \"place_of_issue\":\"3\",\"place_of_issue_name\":\"Cục Quản lý xuất nhập cảnh\",\"province\":\"01\",\"province_name\":\"Thành phố Hà Nội\",\"district\":\"001\",\"district_name\":\"Quận Ba Đình\",\"ward\":\"00006\",\"ward_name\":\"Phường Vĩnh Phúc\",\"address\":\"234\",\"marital_status\":\"SINGLE\",\"number_of_dependents\":1,\"ref_code\":\"\",\"status\":\"PROCESSING\",\"current_step\":\"LOAN_SUBMIT\",\"download_status\":1,\"receive_channel\":\"LDP\",\"loan_code\":\"LDP80920220002\",\"total_income\":732441}}\n")
        .build();

    doReturn(1).when(loanApplicationRepository)
        .updateStatus(any(), any(), any(), any(), any(), any(), any());

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn("abc@msb.com.vn");

      CmsUser cmsUserEntity = CmsUser.builder().emplId("emplId").build();
      doReturn(cmsUserEntity).when(cmsUserService).findByEmail("abc@msb.com.vn");

      String result = loanApplicationService.updateStatus(LOAN_ID,
          LoanInfoStatusEnum.PROCESSING.getCode(), "", ClientTypeEnum.CMS);
      assertEquals("PROCESSING", result);
    }
  }

  @Test
  void givenRmComplete_ThenUpdateStatus_shouldReturnSuccess() throws JsonProcessingException {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
        .fullName("Nguyen van A")
        .status(LoanInfoStatusEnum.PROCESSING)
        .maritalStatus(MaritalStatusEnum.MARRIED)
        .picRm("emplId")
        .build();
    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository).findById(LOAN_ID);

    LoanPreApprovalEntity loanPreApprovalEntity = LoanPreApprovalEntity.builder()
        .loanId(LOAN_ID)
        .metaData(
            "{\"loan_application\":{\"uuid\":\"025635c4-f82e-459b-ae4b-9bb713d47749\",\"profile_id\":\"8ccb8204-956b-434b-9d61-dadb9198af8e\",\"phone\":\"0981231233\",\"full_name\":\"nguyen pham tran'n12..?!~, @#\",\"gender\":\"MALE\",\"nationality\":\"VIETNAM\",\"email\":\"email123@gmail.com\",\"id_no\":\"123123123\", \"place_of_issue\":\"3\",\"place_of_issue_name\":\"Cục Quản lý xuất nhập cảnh\",\"province\":\"01\",\"province_name\":\"Thành phố Hà Nội\",\"district\":\"001\",\"district_name\":\"Quận Ba Đình\",\"ward\":\"00006\",\"ward_name\":\"Phường Vĩnh Phúc\",\"address\":\"234\",\"marital_status\":\"SINGLE\",\"number_of_dependents\":1,\"ref_code\":\"\",\"status\":\"PROCESSING\",\"current_step\":\"LOAN_SUBMIT\",\"download_status\":1,\"receive_channel\":\"LDP\",\"loan_code\":\"LDP80920220002\",\"total_income\":732441}}\n")
        .build();
    doReturn(Optional.of(loanPreApprovalEntity)).when(loanPreApprovalRepository).findById(any());

    doReturn(1).when(loanApplicationRepository)
        .updateStatus(any(), any(), any(), any(), any(), any(), any());

    List<CmsTabActionEntity> cmsTabActionEntities = new ArrayList<>();
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.CUSTOMER_INFO).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.MARRIED_PERSON).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.CONTACT_PERSON).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.ASSUMING_TOTAL_ASSETS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.ASSUMING_OTHERS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.SALARY_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.PERSONAL_BUSINESS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.BUSINESS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.OTHERS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.COLLATERAL_INFO).build());
    cmsTabActionEntities.add(CmsTabActionEntity.builder().tabCode(CMSTabEnum.LOAN_INFO).build());
    cmsTabActionEntities.add(CmsTabActionEntity.builder().tabCode(CMSTabEnum.FILE_UPLOAD).build());
    cmsTabActionEntities.add(CmsTabActionEntity.builder().tabCode(CMSTabEnum.EXPERTISE).build());
    doReturn(cmsTabActionEntities).when(cmsTabActionRepository).findByLoanId(any());

    CommonIncome commonIncome = new CommonIncome();
    commonIncome.setRecognitionMethod1(RecognitionMethod1Enum.ACTUALLY_RECEIVED);
    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(LOAN_ID);

    ProfileEntity profileEntity = new ProfileEntity();
    doReturn(Optional.of(profileEntity)).when(profileRepository).findById(any());

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn("abc@msb.com.vn");

      CmsUser cmsUserEntity = CmsUser.builder().emplId("emplId").build();
      doReturn(cmsUserEntity).when(cmsUserService).findByEmail("abc@msb.com.vn");

      String result = loanApplicationService.updateStatus(LOAN_ID,
          LoanInfoStatusEnum.RM_COMPLETED.getCode(), "", ClientTypeEnum.CMS);

      assertEquals("WAITING_FOR_CONFIRM", result);
    }

  }


  @Test
  void givenWaitingConfirm_ThenUpdateStatus_shouldReturnSuccess() throws JsonProcessingException {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
        .fullName("Nguyen van A")
        .picRm("emplId")
        .gender(GenderEnum.MALE)
        .status(LoanInfoStatusEnum.PROCESSING)
        .build();
    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository).findById(LOAN_ID);

    LoanPreApprovalEntity loanPreApprovalEntity = LoanPreApprovalEntity.builder()
        .loanId(LOAN_ID)
        .metaData(
            "{\"loan_application\":{\"uuid\":\"928fa504-d390-4b70-9cdd-6ddfd7acdde9\",\"profile_id\":\"8ccb8204-956b-434b-9d61-dadb9198af8e\",\"phone\":\"0981231233\",\"full_name\":\"Nguyen van A\",\"gender\":\"MALE\", \"status\":\"PROCESSING\"}}\n")
        .build();
    doReturn(Optional.of(loanPreApprovalEntity)).when(loanPreApprovalRepository).findById(any());

    doReturn(1).when(loanApplicationRepository)
        .updateStatus(any(), any(), any(), any(), any(), any(), any());

    List<CmsTabActionEntity> cmsTabActionEntities = new ArrayList<>();
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.CUSTOMER_INFO).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.MARRIED_PERSON).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.CONTACT_PERSON).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.ASSUMING_TOTAL_ASSETS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.ASSUMING_OTHERS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.SALARY_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.PERSONAL_BUSINESS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.BUSINESS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.OTHERS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.COLLATERAL_INFO).build());
    cmsTabActionEntities.add(CmsTabActionEntity.builder().tabCode(CMSTabEnum.LOAN_INFO).build());
    cmsTabActionEntities.add(CmsTabActionEntity.builder().tabCode(CMSTabEnum.FILE_UPLOAD).build());
    cmsTabActionEntities.add(CmsTabActionEntity.builder().tabCode(CMSTabEnum.EXPERTISE).build());
    doReturn(cmsTabActionEntities).when(cmsTabActionRepository).findByLoanId(any());

    CommonIncome commonIncome = new CommonIncome();
    commonIncome.setRecognitionMethod1(RecognitionMethod1Enum.ACTUALLY_RECEIVED);
    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(LOAN_ID);

    ProfileEntity profileEntity = new ProfileEntity();
    doReturn(Optional.of(profileEntity)).when(profileRepository).findById(any());

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn("abc@msb.com.vn");

      CmsUser cmsUserEntity = CmsUser.builder().emplId("emplId").build();
      doReturn(cmsUserEntity).when(cmsUserService).findByEmail("abc@msb.com.vn");

      String result = loanApplicationService.updateStatus(LOAN_ID,
          LoanInfoStatusEnum.RM_COMPLETED.getCode(), "", ClientTypeEnum.CMS);

      assertEquals("WAITING_FOR_CONFIRM", result);
    }
  }

  @Test
  void givenCreateFromCMS_ThenUpdateStatus_shouldReturnSuccess() throws JsonProcessingException {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
        .fullName("Nguyen van A")
        .gender(GenderEnum.MALE)
        .picRm("emplId")
        .status(LoanInfoStatusEnum.PROCESSING)
        .receiveChannel(ReceiveChannelEnum.CMS)
        .build();
    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(1).when(loanApplicationRepository)
        .updateStatus(any(), any(), any(), any(), any(), any(), any());
    List<CmsTabActionEntity> cmsTabActionEntities = new ArrayList<>();
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.CUSTOMER_INFO).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.MARRIED_PERSON).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.CONTACT_PERSON).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.ASSUMING_TOTAL_ASSETS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.ASSUMING_OTHERS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.SALARY_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.PERSONAL_BUSINESS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.BUSINESS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.OTHERS_INCOME).build());
    cmsTabActionEntities.add(
        CmsTabActionEntity.builder().tabCode(CMSTabEnum.COLLATERAL_INFO).build());
    cmsTabActionEntities.add(CmsTabActionEntity.builder().tabCode(CMSTabEnum.LOAN_INFO).build());
    cmsTabActionEntities.add(CmsTabActionEntity.builder().tabCode(CMSTabEnum.FILE_UPLOAD).build());
    cmsTabActionEntities.add(CmsTabActionEntity.builder().tabCode(CMSTabEnum.EXPERTISE).build());
    doReturn(cmsTabActionEntities).when(cmsTabActionRepository).findByLoanId(any());

    CommonIncome commonIncome = new CommonIncome();
    commonIncome.setRecognitionMethod1(RecognitionMethod1Enum.ACTUALLY_RECEIVED);
    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(LOAN_ID);

    ProfileEntity profileEntity = new ProfileEntity();
    doReturn(Optional.of(profileEntity)).when(profileRepository).findById(any());
    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn("abc@msb.com.vn");

      CmsUser cmsUserEntity = CmsUser.builder().emplId("emplId").build();
      doReturn(cmsUserEntity).when(cmsUserService).findByEmail("abc@msb.com.vn");
      String result = loanApplicationService.updateStatus(LOAN_ID,
          LoanInfoStatusEnum.RM_COMPLETED.getCode(), "", ClientTypeEnum.CMS);

      assertEquals("WAITING_FOR_CONFIRM", result);
    }

  }


  @Test
  void givenThirdPartyNotExist_ThenDeleteDataWhenRmSubmit_shouldReturnValidateError() {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .fullName("Nguyen van A")
//                .recognitionMethod1(RecognitionMethod1Enum.ACTUALLY_RECEIVED)
        .maritalStatus(MaritalStatusEnum.MARRIED)
        .status(LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION)
        .build();

    List<CollateralEntity> collateralEntities = new ArrayList<>();
    collateralEntities.add(CollateralEntity.builder().status(CollateralStatusEnum.OTHERS).build());
    doReturn(collateralEntities).when(collateralRepository).findByLoanId(any());

    CommonIncome commonIncome = new CommonIncome();
    commonIncome.setRecognitionMethod1(RecognitionMethod1Enum.ACTUALLY_RECEIVED);
    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(LOAN_ID);

    ReflectionTestUtils.invokeMethod(
        loanApplicationService, "deleteDataWhenRmSubmit", loanApplicationBefore);

    //hold on

//        verify(otherEvaluateRepository,times(1)).deleteByLoanApplicationId(LOAN_ID);
//        verify(assetEvaluateItemRepository,times(1)).deleteByLoanId(LOAN_ID);
//        verify(assetEvaluateRepository,times(1)).deleteByLoanApplicationId(LOAN_ID);
//        verify(collateralOwnerRepository,times(1)).deleteByLoanId(LOAN_ID);
//        verify(collateralOwnerMapRepository,times(1)).deleteByLoanId(LOAN_ID);
  }

  @Test
  void givenThirdPartyExist_ThenDeleteDataWhenRmSubmit_shouldReturnValidateError() {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .fullName("Nguyen van A")
//                .recognitionMethod1(RecognitionMethod1Enum.ACTUALLY_RECEIVED)
        .maritalStatus(MaritalStatusEnum.MARRIED)
        .status(LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION)
        .build();

    List<CollateralEntity> collateralEntities = new ArrayList<>();
    collateralEntities.add(
        CollateralEntity.builder().status(CollateralStatusEnum.THIRD_PARTY).build());
    doReturn(collateralEntities).when(collateralRepository).findByLoanId(any());

    CommonIncome commonIncome = new CommonIncome();
    commonIncome.setRecognitionMethod1(RecognitionMethod1Enum.ACTUALLY_RECEIVED);
    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(LOAN_ID);

    ReflectionTestUtils.invokeMethod(
        loanApplicationService, "deleteDataWhenRmSubmit", loanApplicationBefore);

    verify(collateralOwnerRepository, times(1)).refreshData(LOAN_ID);
  }

}
