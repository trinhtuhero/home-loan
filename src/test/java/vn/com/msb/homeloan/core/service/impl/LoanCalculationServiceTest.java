package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.util.List;
import javax.script.ScriptException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.model.LoanCalculate;
import vn.com.msb.homeloan.core.model.LoanCalculation;
import vn.com.msb.homeloan.core.repository.DebtPaymentMethodRepository;
import vn.com.msb.homeloan.core.repository.InterestRateRepository;
import vn.com.msb.homeloan.core.service.LoanCalculationService;
import vn.com.msb.homeloan.core.service.mockdata.LoanCalculationDataBuild;

@ExtendWith(MockitoExtension.class)
public class LoanCalculationServiceTest {

  LoanCalculationService loanCalculationService;

  @Mock
  private InterestRateRepository interestRateRepository;

  @Mock
  private DebtPaymentMethodRepository debtPaymentMethodRepository;

  @BeforeEach
  public void setup() {
    loanCalculationService = new LoanCalculationServiceImpl(interestRateRepository,
        debtPaymentMethodRepository);
  }

  @Test
  public void test_calculate_thenReturnSuccess() throws ScriptException {
    LoanCalculate loanCalculate = LoanCalculationDataBuild.buildLoanCalculate();

    doReturn(LoanCalculationDataBuild.buildListInterestRateEntity()).when(interestRateRepository)
        .findAllByKey(anyString());
    doReturn(LoanCalculationDataBuild.buildListDebtPaymentMethodEntity()).when(
        debtPaymentMethodRepository).findAllByKey(anyString());

    List<LoanCalculation> loanCalculations = loanCalculationService.calculateLoan(loanCalculate);
    LoanCalculation result = loanCalculations.get(0);
    assertEquals(3, loanCalculations.size());
    assertEquals(result.getInterestMonthly(), new BigDecimal("410137").toPlainString());
    assertEquals(result.getPrincipalAmountMonthly(), new BigDecimal("33333333").toPlainString());
    assertEquals(result.getTotal(), new BigDecimal("33743470").toPlainString());
  }
}
