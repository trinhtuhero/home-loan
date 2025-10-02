package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.CmsUserEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.PicRmHistoryEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CmsUser;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceUpdateRMForLoanTest extends LoanApplicationServiceBaseTest {

  @Test
  @DisplayName("LoanApplicationService Test updateRmForLoan Success")
  void givenValidInput_ThenUpdateRmForLoan_shouldReturnSuccess() throws NoSuchAlgorithmException {
    String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
    String EMPL_ID = "035564";
    String EMAIL = "trinhvantu93@gmail.com";
    LoanApplicationEntity mockLoanApplicationEntityEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .fullName("Nguyen van A")
        .build();

    CmsUserEntity mockCmsUserEntity = CmsUserEntity.builder()
        .emplId(EMPL_ID)
        .fullName("Nguyen van A")
        .email(EMAIL)
        .build();

    CmsUser mockCmsUser = CmsUser.builder()
        .emplId(EMPL_ID)
        .fullName("Nguyen van A")
        .email(EMAIL)
        .build();

    CmsUser cmsUser = new CmsUser();
    cmsUser.setEmplId(EMPL_ID);
    cmsUser.setPhone("0906130895");

    doReturn(Optional.of(mockLoanApplicationEntityEntity)).when(loanApplicationRepository)
        .findById(LOAN_ID);
    doReturn(mockCmsUser).when(cmsUserService).findByEmail(mockCmsUserEntity.getEmail());

    PicRmHistoryEntity picRmHistoryEntity = PicRmHistoryEntity.builder().build();
    doReturn(picRmHistoryEntity).when(picRmHistoryRepository).save(any());

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn(EMAIL);
      LoanApplication loanApplication = loanApplicationService.updateRmForLoan(LOAN_ID,
          "3256b70fd39cfa577c857aaac33d8214d74afe19d7bcad0d39893f20c8fdef04");
      assertTrue(loanApplication.getPicRm().equalsIgnoreCase(EMPL_ID), String.valueOf(true));
    }
  }

  @Test
  @DisplayName("LoanApplicationService Test updateRmForLoan fail")
  void givenValidInput_ThenUpdateRmForLoan_shouldReturnFail() throws NoSuchAlgorithmException {
    String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
    String EMPL_ID = "035564";
    String EMAIL = "trinhvantu93@gmail.com";

    CmsUser cmsUser = new CmsUser();
    cmsUser.setEmplId(EMPL_ID);
    cmsUser.setPhone("0906130895");

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn(EMAIL);

      ApplicationException exception = assertThrows(ApplicationException.class, () -> {
        loanApplicationService.updateRmForLoan(LOAN_ID,
            "3256b70fd39cfa577c857aaac33d8214d74afe19d7bcad0d39893f2");
      });
      assertEquals(exception.getCode().intValue(), ErrorEnum.SERVER_ERROR.getCode());
    }
  }
}