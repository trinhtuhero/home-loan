package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.AdviseTimeFrameEnum;
import vn.com.msb.homeloan.core.model.LoanAdviseCustomer;
import vn.com.msb.homeloan.core.repository.LoanAdviseCustomerRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.service.mockdata.AdviseDataBuild;

@ExtendWith(MockitoExtension.class)
class LoanAdviseCustomerServiceImplTest {

  @Mock
  private LoanAdviseCustomerRepository loanAdviseCustomerRepository;
  @Mock
  private LoanApplicationRepository loanApplicationRepository;
  @InjectMocks
  private LoanAdviseCustomerServiceImpl underTest;

  private final String LOAN_ID = "2a82b70d-4b67-4495-8649-84a6decfcce0";

  @Test
  public void test_getLoanAdviseCustomer_ThenReturnSuccess() {
    when(loanAdviseCustomerRepository.findByLoanApplicationId(anyString()))
        .thenReturn(AdviseDataBuild.buildLoanAdviseCustomerEntity());
    LoanAdviseCustomer customer = underTest.get(LOAN_ID);
    assertEquals(LOAN_ID, customer.getLoanApplicationId());
    assertEquals(AdviseTimeFrameEnum.FROM_8_TO_12, customer.getAdviseTimeFrame());
  }

  @Test
  public void test_getLoanAdviseCustomer_ThenReturnNull() {
    when(loanAdviseCustomerRepository.findByLoanApplicationId(anyString()))
        .thenReturn(null);
    LoanAdviseCustomer customer = underTest.get(LOAN_ID);
    assertNull(customer);
  }
}