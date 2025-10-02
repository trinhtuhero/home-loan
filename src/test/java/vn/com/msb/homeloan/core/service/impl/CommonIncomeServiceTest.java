package vn.com.msb.homeloan.core.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.BusinessTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.RecognitionMethod1Enum;
import vn.com.msb.homeloan.core.entity.CommonIncomeEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.repository.BusinessIncomeRepository;
import vn.com.msb.homeloan.core.repository.CommonIncomeRepository;
import vn.com.msb.homeloan.core.repository.OtherIncomeRepository;
import vn.com.msb.homeloan.core.repository.SalaryIncomeRepository;
import vn.com.msb.homeloan.core.service.CommonIncomeService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;

@ExtendWith(MockitoExtension.class)
public class CommonIncomeServiceTest {

  CommonIncomeService commonIncomeService;

  @Mock
  CommonIncomeRepository commonIncomeRepository;

  @Mock
  LoanApplicationService loanApplicationService;

  @Mock
  SalaryIncomeRepository salaryIncomeRepository;

  @Mock
  BusinessIncomeRepository businessIncomeRepository;

  @Mock
  OtherIncomeRepository otherIncomeRepository;

  @BeforeEach
  void setUp() {
    this.commonIncomeService = new CommonIncomeServiceImpl(
        commonIncomeRepository,
        loanApplicationService,
        salaryIncomeRepository,
        businessIncomeRepository,
        otherIncomeRepository
    );
  }

  @Test
  void test_Insert() {
    // insert
    CommonIncome commonIncome = CommonIncome.builder().loanApplicationId("loanId").build();

    LoanApplication loanApplication = LoanApplication.builder().build();

    doReturn(loanApplication).when(loanApplicationService)
        .findById(commonIncome.getLoanApplicationId());

    doReturn(null).when(commonIncomeRepository)
        .findByLoanApplicationId(commonIncome.getLoanApplicationId());

    CommonIncomeEntity entity2 = CommonIncomeEntity.builder().loanApplicationId("loanId").build();

    doReturn(entity2).when(commonIncomeRepository).save(any());

    CommonIncome result = commonIncomeService.save(commonIncome);

    assertEquals("loanId", result.getLoanApplicationId());
  }

  @Test
  void test_Update() {
    // update
    CommonIncome commonIncome = CommonIncome.builder().loanApplicationId("loanId").build();

    LoanApplication loanApplication = LoanApplication.builder().build();

    doReturn(loanApplication).when(loanApplicationService)
        .findById(commonIncome.getLoanApplicationId());

    CommonIncomeEntity entity = CommonIncomeEntity.builder().loanApplicationId("loanId").build();

    doReturn(entity).when(commonIncomeRepository)
        .findByLoanApplicationId(commonIncome.getLoanApplicationId());

    CommonIncomeEntity entity2 = CommonIncomeEntity.builder().loanApplicationId("loanId").build();

    doReturn(entity2).when(commonIncomeRepository).save(any());

    CommonIncome result = commonIncomeService.save(commonIncome);

    assertEquals("loanId", result.getLoanApplicationId());
  }

  @Test
  void test_findById() {
    //
    String uuid1 = "uuid1";

    doReturn(Optional.empty()).when(commonIncomeRepository).findById(uuid1);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      commonIncomeService.findById(uuid1);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_NOT_FOUND.getCode());

    //
    String uuid2 = "uuid2";

    CommonIncomeEntity commonIncomeEntity = CommonIncomeEntity.builder().uuid(uuid2).build();

    doReturn(Optional.of(commonIncomeEntity)).when(commonIncomeRepository).findById(uuid2);

    CommonIncome result = commonIncomeService.findById(uuid2);

    assertEquals(uuid2, result.getUuid());
  }

  @Test
  void givenValidInput_ThenGetSelectedIncomeSubmittedByCustomer_shouldReturnSelectedIncome() {
    //
    String loanId1 = "loanId1";

    doReturn(1).when(salaryIncomeRepository).countByLoanApplicationId(loanId1);
    doReturn(1).when(businessIncomeRepository).countByLoanApplicationIdAndBusinessTypeIn(loanId1,
        Arrays.asList(BusinessTypeEnum.WITH_REGISTRATION, BusinessTypeEnum.WITHOUT_REGISTRATION));
    doReturn(1).when(businessIncomeRepository).countByLoanApplicationIdAndBusinessTypeIn(loanId1,
        Arrays.asList(BusinessTypeEnum.ENTERPRISE));
    doReturn(1).when(otherIncomeRepository).countByLoanApplicationId(loanId1);

    String[] result = commonIncomeService.getSelectedIncomeSubmittedByCustomer(loanId1);

    assertEquals(4, result.length);
  }

  @Test
  void test_validateInput() {
    CommonIncome commonIncome = CommonIncome.builder()
        .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE).recognitionMethod2(null).build();

    try {
      Method privateMethod = CommonIncomeServiceImpl.class.getDeclaredMethod("validateInput",
          CommonIncome.class);
      privateMethod.setAccessible(true);
      privateMethod.invoke(commonIncomeService, commonIncome);
    } catch (Exception e) {
      assertThat(e.getCause(), instanceOf(ApplicationException.class));
    }

    // validate selected income
    commonIncome.setSelectedIncomes(
        new String[]{"SALARY_INCOME", "SALARY_INCOME", "PERSONAL_BUSINESS_INCOME"});
    try {
      Method privateMethod = CommonIncomeServiceImpl.class.getDeclaredMethod("validateInput",
          CommonIncome.class);
      privateMethod.setAccessible(true);
      privateMethod.invoke(commonIncomeService, commonIncome);
    } catch (Exception e) {
      assertThat(e.getCause(), instanceOf(ApplicationException.class));
    }

    commonIncome.setSelectedIncomes(new String[]{"SALARY_INCOME", "", "123"});
    try {
      Method privateMethod = CommonIncomeServiceImpl.class.getDeclaredMethod("validateInput",
          CommonIncome.class);
      privateMethod.setAccessible(true);
      privateMethod.invoke(commonIncomeService, commonIncome);
    } catch (Exception e) {
      assertThat(e.getCause(), instanceOf(ApplicationException.class));
    }

    commonIncome.setRecognitionMethod1(RecognitionMethod1Enum.ACTUALLY_RECEIVED);
    commonIncome.setSelectedIncomes(new String[]{"SALARY_INCOME", "ASSUMING_TOTAL_ASSETS_INCOME"});
    try {
      Method privateMethod = CommonIncomeServiceImpl.class.getDeclaredMethod("validateInput",
          CommonIncome.class);
      privateMethod.setAccessible(true);
      privateMethod.invoke(commonIncomeService, commonIncome);
    } catch (Exception e) {
      assertThat(e.getCause(), instanceOf(ApplicationException.class));
    }
  }
}
