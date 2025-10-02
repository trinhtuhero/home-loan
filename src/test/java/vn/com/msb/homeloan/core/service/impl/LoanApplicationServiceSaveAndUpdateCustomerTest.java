package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.LoanCurrentStepEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.constant.ProfileStatusEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;
import vn.com.msb.homeloan.core.util.StringUtils;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceSaveAndUpdateCustomerTest extends LoanApplicationServiceBaseTest {

  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String PROFILE_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  @Test
  @DisplayName("LoanApplicationService Test SAVE(insert) function success")
  void givenValidInput_ThenInsertData_shouldReturnSuccess() {
    LoanApplication loanApplication = BuildData.buildLoanApplication(PROFILE_ID);
    loanApplication.setRefCode("refCode");
    ProfileEntity profile = BuildData.buildProfile(PROFILE_ID);
    doReturn(Optional.of(profile)).when(profileRepository).findById(loanApplication.getProfileId());

    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        loanApplication);
    doReturn(loanApplicationEntity).when(loanApplicationRepository).save(any());
    loanApplicationEntity.setUuid(LOAN_ID);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_CUSTOMER.getCode());
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.DRAFT);

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getUserId())
          .thenReturn(PROFILE_ID);

      LoanApplication result = loanApplicationService.save(loanApplication, ClientTypeEnum.LDP);

      assertTrue(!StringUtils.isEmpty(result.getUuid()), String.valueOf(true));
      assertEquals(result.getUuid(), loanApplicationEntity.getUuid());
      assertEquals(result.getFullName(), loanApplication.getFullName());
      assertEquals(result.getPhone(), loanApplication.getPhone());
    }
  }

  @Test
  @DisplayName("LoanApplicationService Test SAVE(insert) function exception PROFILE NOT FOUND")
  void givenValidateInput_ThenInsertData_shouldReturnProfileNotFound() {
    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getUserId())
          .thenReturn(PROFILE_ID);

      LoanApplication loanApplication = BuildData.buildLoanApplication(PROFILE_ID);
      doReturn(Optional.empty()).when(profileRepository).findById(loanApplication.getProfileId());

      ApplicationException exception = assertThrows(ApplicationException.class, () -> {
        loanApplicationService.save(loanApplication, ClientTypeEnum.LDP);
      });

      assertEquals(exception.getCode().intValue(), ErrorEnum.PROFILE_NOT_FOUND.getCode());
    }
  }

  @Test
  @DisplayName("LoanApplicationService Test SAVE(update) function success")
  void givenValidInput_ThenUpdateData_shouldReturnSuccess() throws IOException {
    LoanApplication loanApplication = BuildData.buildLoanApplication(PROFILE_ID);
    loanApplication.setUuid(LOAN_ID);

    ProfileEntity profile = BuildData.buildProfile(PROFILE_ID);

    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        loanApplication);
    loanApplicationEntity.setUuid(LOAN_ID);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_FILE.getCode());
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());
    doReturn(loanApplicationEntity).when(loanApplicationRepository).save(any());
    doReturn(Optional.of(new ProfileEntity())).when(profileRepository).findById(any());
    LoanApplication result = loanApplicationService.save(loanApplication, ClientTypeEnum.LDP);

    assertTrue(!StringUtils.isEmpty(result.getUuid()), String.valueOf(true));
    assertEquals(result.getUuid(), loanApplication.getUuid());
    assertEquals(result.getFullName(), loanApplication.getFullName());
    assertEquals(result.getPhone(), loanApplication.getPhone());
    assertEquals(result.getStatus(), loanApplicationEntity.getStatus().getCode());
    assertEquals(result.getCurrentStep(), loanApplicationEntity.getCurrentStep());
  }

  @Test
  @DisplayName("LoanApplicationService Test SAVE(update) function exception LOAN APPLICATION NOT FOUND")
  void givenValidateInput_ThenInsertData_shouldReturnLoanApplicationNotFound() {
    LoanApplication loanApplication = BuildData.buildLoanApplication(PROFILE_ID);
    loanApplication.setUuid(LOAN_ID);
    doReturn(Optional.empty()).when(loanApplicationRepository).findById(loanApplication.getUuid());
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.save(loanApplication, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  @DisplayName("LoanApplicationService Test SAVE(update) function exception LOAN_APPLICATION_EDIT_NOT_ALLOW")
  void givenValidateInput_ThenInsertData_shouldReturnEditNotAllow() {
    LoanApplication loanApplication = BuildData.buildLoanApplication(PROFILE_ID);
    loanApplication.setUuid(LOAN_ID);
    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        loanApplication);
    loanApplicationEntity.setUuid(LOAN_ID);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_FILE.getCode());
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.ACCEPT_LOAN_APPLICATION);
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.save(loanApplication, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(),
        ErrorEnum.LOAN_APPLICATION_EDIT_NOT_ALLOW.getCode());
  }

  @Test
  @DisplayName("LoanApplicationService Test UPDATE CUSTOMER function success")
  void givenValidInput_ThenUpdateCustomer_shouldReturnSuccess() throws IOException {
    LoanApplication loanApplication = BuildData.buildLoanApplication(PROFILE_ID);
    loanApplication.setUuid(LOAN_ID);
    loanApplication.setRefCode("refCode@msb.com.vn");

    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        loanApplication);
    loanApplicationEntity.setUuid(LOAN_ID);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_FILE.getCode());
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());
    doReturn(loanApplicationEntity).when(loanApplicationRepository).save(loanApplicationEntity);

    ProfileEntity profile = ProfileEntity.builder()
        .uuid(PROFILE_ID)
        .phone("098764325")
        .status(ProfileStatusEnum.ACTIVE)
        .build();
    doReturn(Optional.of(profile)).when(profileRepository).findById(loanApplication.getProfileId());
    LoanApplication result = loanApplicationService.updateCustomer(loanApplication);

    assertEquals(result.getUuid(), loanApplication.getUuid());
    assertEquals(result.getFullName(), loanApplication.getFullName());
    assertEquals(result.getPhone(), loanApplication.getPhone());
    assertEquals(result.getStatus(), loanApplicationEntity.getStatus().getCode());
    assertEquals(result.getCurrentStep(), loanApplicationEntity.getCurrentStep());
  }

  @Test
  @DisplayName("LoanApplicationService Test UPDATE CUSTOMER LOAN_APPLICATION_NOT_FOUND")
  void givenValidInput_ThenUpdateCustomer_shouldReturnLoanNotFound() {
    LoanApplication loanApplication = BuildData.buildLoanApplication(PROFILE_ID);
    loanApplication.setUuid(LOAN_ID);
    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        loanApplication);
    loanApplicationEntity.setUuid(LOAN_ID);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_FILE.getCode());
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.ACCEPT_LOAN_APPLICATION);
    doReturn(Optional.empty()).when(loanApplicationRepository).findById(loanApplication.getUuid());
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.save(loanApplication, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  @DisplayName("LoanApplicationService Test UPDATE CUSTOMER LOAN_APPLICATION_EDIT_NOT_ALLOW")
  void givenValidInput_ThenUpdateCustomer_shouldReturnLoanEditNotAllow() {
    LoanApplication loanApplication = BuildData.buildLoanApplication(PROFILE_ID);
    loanApplication.setUuid(LOAN_ID);
    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        loanApplication);
    loanApplicationEntity.setUuid(LOAN_ID);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_FILE.getCode());
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.ACCEPT_LOAN_APPLICATION);
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.save(loanApplication, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(),
        ErrorEnum.LOAN_APPLICATION_EDIT_NOT_ALLOW.getCode());
  }


}