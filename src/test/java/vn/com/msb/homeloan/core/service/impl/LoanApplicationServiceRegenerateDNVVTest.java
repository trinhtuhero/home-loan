package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.LoanStatusChangeEntity;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceRegenerateDNVVTest extends LoanApplicationServiceBaseTest {

  @Test
  void givenValidInput_ThenRegenerateDNVV_shouldReturnSuccess() throws ParseException {
    LoanStatusChangeEntity loanStatusChangeEntity = new LoanStatusChangeEntity();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String dateInString = "21/09/2022 10:20:30";
    Date date = sdf.parse(dateInString);
    Instant inst = date.toInstant();
    loanStatusChangeEntity.setCreatedAt(inst);
    loanStatusChangeEntity.setUuid("test@test.com");

    String loanId = "123";

    doReturn(loanStatusChangeEntity).when(loanStatusChangeRepository)
        .getLastestSubmitByCustomer(any());
    doReturn("htmlContent").when(sendMailService).genHtmlContentOfAttachFile(loanId, date);

    environmentProperties.setCustomerSupportEmail(null);

    Exception exception = assertThrows(NullPointerException.class, () -> {
      loanApplicationService.regenerateDNVV(loanId);
    });
  }
}
