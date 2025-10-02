package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import vn.com.msb.homeloan.api.dto.request.CloseLoanApplicationRequest;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.entity.CmsUserEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;

class LoanApplicationServiceCloseLoanApplicationTest extends LoanApplicationServiceBaseTest {

  @Test
  void givenValidInput_ThenCloseLoan_shouldReturnLoanNotFound() {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .fullName("Nguyen van A")
        .status(LoanInfoStatusEnum.PROCESSING)
        .build();

    CloseLoanApplicationRequest closeApplicationRequest = CloseLoanApplicationRequest
        .builder()
        .uuid(LOAN_ID)
        .cause("KH không có nhu cầu vay")
        .build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.closeLoanApplication(closeApplicationRequest.getUuid(),
          closeApplicationRequest.getCause());
    });

    assertEquals(ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode(), exception.getCode().intValue());
  }

  @Test
  void givenValidInput_ThenCloseLoan_shouldReturnNotAllowClose() {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .fullName("Nguyen van A")
        .status(LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION)
        .build();

    CloseLoanApplicationRequest closeApplicationRequest = CloseLoanApplicationRequest
        .builder()
        .uuid(LOAN_ID)
        .cause("KH không có nhu cầu vay")
        .build();

    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.closeLoanApplication(closeApplicationRequest.getUuid(),
          closeApplicationRequest.getCause());
    });

    assertEquals(ErrorEnum.LOAN_APPLICATIONS_CLOSE_NOT_ALLOW.getCode(),
        exception.getCode().intValue());
  }

  @Test
  void givenValidInput_ThenCloseLoan_shouldCMSNotFound() {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    String EMAIL = "xxx@gmail.com";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .fullName("Nguyen van A")
        .status(LoanInfoStatusEnum.PROCESSING)
        .build();

    CloseLoanApplicationRequest closeApplicationRequest = CloseLoanApplicationRequest
        .builder()
        .uuid(LOAN_ID)
        .cause("KH không có nhu cầu vay")
        .build();

    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository).findById(LOAN_ID);

    doReturn(Optional.empty()).when(cmsUserRepository).findByEmail(EMAIL);

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn(EMAIL);

      ApplicationException exception = assertThrows(ApplicationException.class, () -> {
        loanApplicationService.closeLoanApplication(closeApplicationRequest.getUuid(),
            closeApplicationRequest.getCause());
      });

      assertEquals(ErrorEnum.CMS_USER_NOT_EXIST.getCode(), exception.getCode().intValue());
    }

  }

  @Test
  void givenValidInput_ThenCloseLoan_shouldReturnSuccess() {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    String EMAIL = "xxx@gmail.com";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .fullName("Nguyen van A")
        .status(LoanInfoStatusEnum.PROCESSING)
        .build();

    CloseLoanApplicationRequest closeApplicationRequest = CloseLoanApplicationRequest
        .builder()
        .uuid(LOAN_ID)
        .cause("KH không có nhu cầu vay")
        .build();

    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository).findById(LOAN_ID);

    CmsUserEntity cmsUsersEntity = CmsUserEntity.builder().build();

    doReturn(Optional.of(cmsUsersEntity)).when(cmsUserRepository).findByEmail(EMAIL);

    doReturn(1).when(loanApplicationRepository)
        .updateStatusByUuid(LoanInfoStatusEnum.CLOSED.getCode(), LOAN_ID);

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn(EMAIL);
      int result = loanApplicationService.closeLoanApplication(closeApplicationRequest.getUuid(),
          closeApplicationRequest.getCause());
      assertEquals(result, 1);
    }
  }
}
