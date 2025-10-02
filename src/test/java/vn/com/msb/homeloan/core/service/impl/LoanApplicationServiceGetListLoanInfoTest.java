package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import vn.com.msb.homeloan.core.constant.LoanCJEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.entity.LoanStatusChangeEntity;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;

class LoanApplicationServiceGetListLoanInfoTest extends LoanApplicationServiceBaseTest {

  @Test
  @DisplayName("LoanApplicationService Test getListLoanInfo Success")
  void givenValidInput_ThenUpdateRmForLoan_shouldReturnSuccess() throws NoSuchAlgorithmException {
    String LOAN_CODE = "loanCode123";
    String USER_ID = "035564";
    String LOAN_ID = "loanId035564";

    List<LoanApplication> loanApplications = new ArrayList<>();
    LoanApplication loanApplication = LoanApplication.builder()
        .status("DRAFT")
        .loanCode(LOAN_CODE)
        .profileId(USER_ID)
        .uuid(LOAN_ID)
        .build();
    loanApplications.add(loanApplication);

    List<LoanStatusChangeEntity> loanStatusChanges = new ArrayList<>();
    LoanStatusChangeEntity loanStatusChange = LoanStatusChangeEntity.builder()
        .loanApplicationId(LOAN_ID)
        .statusTo(LoanInfoStatusEnum.ACCEPT_LOAN_APPLICATION)
        .createdAt(Instant.now())
        .build();
    loanStatusChanges.add(loanStatusChange);

    doReturn(loanStatusChanges).when(loanStatusChangeRepository).findByLoanApplicationId(LOAN_ID);
    doReturn(loanApplications).when(loanApplicationRepository)
        .getLoansByProfileId(LOAN_CODE, null, null, null, null, null, USER_ID);

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getUserId())
          .thenReturn(USER_ID);
      List<LoanApplication> listResult = loanApplicationService.getLoansByProfileId(LOAN_CODE, null,
          null, null, null, null);
      assertTrue(LoanCJEnum.RECEIVE.getCode()
              .equalsIgnoreCase(listResult.get(0).getLoanJourneyStatuses().get(0).getStatus()),
          String.valueOf(true));
    }
  }
}