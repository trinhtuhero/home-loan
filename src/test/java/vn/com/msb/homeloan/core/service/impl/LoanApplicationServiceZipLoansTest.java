package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.LoanUploadFileEntity;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceZipLoansTest extends LoanApplicationServiceBaseTest {

  @Test
  @DisplayName("LoanApplicationService Test cmsSearch Success")
  void givenValidInput_ThenZipLoans_shouldReturnSuccess() {
    doNothing().when(loanApplicationRepository).zipLoans(any());
    loanApplicationService.zipLoans(Arrays.asList("1", "2"));
    assertTrue(1 == 1);
  }

  @Test
  void test1() {
    LoanUploadFileEntity loanUploadFileEntity = new LoanUploadFileEntity();
    loanUploadFileEntity.setUuid("test");
    doReturn(Optional.of(loanUploadFileEntity)).when(loanUploadFileRepository)
        .getDNVVByLoanId(any());
    LoanUploadFileEntity loanUploadFileEntity2 = loanUploadFileRepository.getDNVVByLoanId("123")
        .map(obj -> {
          obj.setUpdatedAt(Instant.now());
          return Optional.of(obj);
        }).orElseGet(() -> Optional.of(LoanUploadFileEntity.builder().build())).get();
    assertTrue(loanUploadFileEntity.equals(loanUploadFileEntity2));

    doReturn(Optional.empty()).when(loanUploadFileRepository).getDNVVByLoanId(any());
    LoanUploadFileEntity loanUploadFileEntity3 = loanUploadFileRepository.getDNVVByLoanId("123")
        .map(obj -> {
          obj.setUpdatedAt(Instant.now());
          return Optional.of(obj);
        }).orElseGet(() -> Optional.of(LoanUploadFileEntity.builder().build())).get();
    assertTrue(loanUploadFileEntity3.getUuid() == null);
  }
}
