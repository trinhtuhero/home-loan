package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.BusinessTypeEnum;
import vn.com.msb.homeloan.core.entity.AssetEvaluateEntity;
import vn.com.msb.homeloan.core.entity.BusinessIncomeEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.OtherEvaluateEntity;
import vn.com.msb.homeloan.core.entity.OtherIncomeEntity;
import vn.com.msb.homeloan.core.entity.SalaryIncomeEntity;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.IncomeInfo;

@ExtendWith(MockitoExtension.class)
class LoanApplicationGetIncomeInfoServiceTest extends LoanApplicationServiceBaseTest {

  private final String UUID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String EMAIL = "abc@gmail.com";
  private final String CODE = "123abc";

  @Test
  void givenValidInput_ThenGetIncomeInfo_shouldReturnSucess() {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(UUID)
        .build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(UUID);

    SalaryIncomeEntity salaryIncomeEntity = SalaryIncomeEntity.builder()
        .uuid(UUID)
        .loanApplicationId(LOAN_ID)
        .value(1111L)
        .build();
    List<SalaryIncomeEntity> salaryIncomeEntities = new ArrayList<>();
    salaryIncomeEntities.add(salaryIncomeEntity);
    doReturn(salaryIncomeEntities).when(salaryIncomeRepository)
        .findByLoanApplicationIdOrderByCreatedAtAsc(LOAN_ID);

    BusinessIncomeEntity businessIncomeEntity = BusinessIncomeEntity.builder()
        .uuid(UUID)
        .loanApplicationId(LOAN_ID)
        .businessType(BusinessTypeEnum.ENTERPRISE)
        .value(1111L)
        .build();
    List<BusinessIncomeEntity> businessIncomeEntities = new ArrayList<>();
    businessIncomeEntities.add(businessIncomeEntity);
    doReturn(businessIncomeEntities).when(businessIncomeRepository)
        .findByLoanApplicationIdOrderByCreatedAtAsc(LOAN_ID);

    OtherIncomeEntity otherIncomeEntity = OtherIncomeEntity.builder()
        .uuid(UUID)
        .loanApplicationId(LOAN_ID)
        .value(1111L)
        .build();
    List<OtherIncomeEntity> otherIncomeEntities = new ArrayList<>();
    otherIncomeEntities.add(otherIncomeEntity);
    doReturn(otherIncomeEntities).when(otherIncomeRepository)
        .findByLoanApplicationIdOrderByCreatedAtAsc(LOAN_ID);

    AssetEvaluateEntity assetEvaluateEntity = AssetEvaluateEntity.builder()
        .uuid(UUID)
        .loanApplicationId(LOAN_ID)
        .rmInputValue(1111L)
        .build();
    doReturn(assetEvaluateEntity).when(assetEvaluateRepository).findByLoanApplicationId(LOAN_ID);

    OtherEvaluateEntity otherEvaluateEntity = OtherEvaluateEntity.builder()
        .uuid(UUID)
        .loanApplicationId(LOAN_ID)
        .rmInputValue(1111L)
        .build();
    List<OtherEvaluateEntity> otherEvaluateEntities = new ArrayList<>();
    otherEvaluateEntities.add(otherEvaluateEntity);
    doReturn(otherEvaluateEntities).when(otherEvaluateRepository).findByLoanApplicationId(LOAN_ID);

    CommonIncome commonIncome = CommonIncome.builder().build();
    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(LOAN_ID);

//        doReturn(new ArrayList<>()).when(loanApplicationItemService).findByLoanApplicationId(LOAN_ID);

    IncomeInfo result = loanApplicationService.getIncomeInfo(LOAN_ID, null);
    assertEquals(result.getAssetEvaluate().getLoanApplicationId(), LOAN_ID);
  }
}
