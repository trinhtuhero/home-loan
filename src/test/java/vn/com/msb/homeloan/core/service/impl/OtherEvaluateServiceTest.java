package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.LoanCurrentStepEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.OtherEvaluateEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.OtherEvaluate;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.model.mapper.OtherEvaluateMapper;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.repository.OtherEvaluateRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CommonIncomeService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.OtherEvaluateService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;

@ExtendWith(MockitoExtension.class)
class OtherEvaluateServiceTest {

  private final String OTHER_EVALUATE_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String OTHER_EVALUATE_ID_OTHER = "03f9e024-7ec4-4ed3-8f1f-330d232b63aa";
  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID_OTHER = "03f9e024-7ec4-4ed3-8f1f-330d232b63aa";
  private final String PAYER_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b63aa";

  OtherEvaluateService otherEvaluateService;

  @Mock
  OtherEvaluateRepository otherEvaluateRepository;

  @Mock
  LoanApplicationService loanApplicationService;

  @Mock
  CMSTabActionService cmsTabActionService;

  @Mock
  LoanPayerRepository loanPayerRepository;

  @Mock
  CommonIncomeService commonIncomeService;

  @BeforeEach
  void setUp() {
    this.otherEvaluateService = new OtherEvaluateServiceImpl(
        otherEvaluateRepository,
        loanApplicationService,
        cmsTabActionService,
        loanPayerRepository,
        commonIncomeService);
  }

  @Test
  void givenValidInput_ThenSaves_shouldReturnSuccessUpdate() {
    OtherEvaluate otherEvaluateInsert = OtherEvaluate.builder()
        .uuid(OTHER_EVALUATE_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.MARRIED_PERSON)
        .legalRecord("legalRecord")
        .rmInputValue(12312L)
        .rmReview("ok").build();
    List<OtherEvaluate> otherEvaluateInserts = Arrays.asList(otherEvaluateInsert);
    OtherEvaluateEntity entityInsert = OtherEvaluateMapper.INSTANCE.toEntity(otherEvaluateInsert);

    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_INFORMATION.getCode());
    doReturn(Optional.of(entityInsert)).when(otherEvaluateRepository).findById(OTHER_EVALUATE_ID);

    CommonIncome commonIncome = CommonIncome.builder().build();
    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(LOAN_ID);

    loanApplicationService.deleteUnselectedIncomes(LOAN_ID, commonIncome.getSelectedIncomes());

    List<OtherEvaluate> result = otherEvaluateService.saves(otherEvaluateInserts);

    assertEquals(result.get(0).getLoanApplicationId(), LOAN_ID);
  }

  @Test
  void givenValidInput_ThenSaves_shouldReturnFailOtherEvaluateItemLimit() {
    OtherEvaluate otherEvaluateInsert = OtherEvaluate.builder()
        .uuid(null)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.MARRIED_PERSON)
        .legalRecord("legalRecord")
        .rmInputValue(12312L)
        .rmReview("ok").build();
    OtherEvaluate otherEvaluateInsert2 = OtherEvaluate.builder()
        .uuid(null)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.MARRIED_PERSON)
        .legalRecord("legalRecord")
        .rmInputValue(12312L)
        .rmReview("ok").build();
    List<OtherEvaluate> otherEvaluateInserts = Arrays.asList(otherEvaluateInsert,
        otherEvaluateInsert2);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      otherEvaluateService.saves(otherEvaluateInserts);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.ITEM_LIMIT.getCode());
  }

  @Test
  void givenValidInput_ThenSaves_shouldReturnFailResourceNotFound() {
    OtherEvaluate otherEvaluateInsert = OtherEvaluate.builder()
        .uuid(OTHER_EVALUATE_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.MARRIED_PERSON)
        .legalRecord("legalRecord")
        .rmInputValue(12312L)
        .rmReview("ok").build();
    List<OtherEvaluate> otherEvaluateInserts = Arrays.asList(otherEvaluateInsert);

    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_INFORMATION.getCode());

    CommonIncome commonIncome = CommonIncome.builder().build();

    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(LOAN_ID);

    loanApplicationService.deleteUnselectedIncomes(LOAN_ID, commonIncome.getSelectedIncomes());

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      otherEvaluateService.saves(otherEvaluateInserts);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenSaves_shouldReturnInvalidForm() {
    OtherEvaluate otherEvaluateInsert = OtherEvaluate.builder()
        .uuid(null)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .legalRecord("legalRecord")
        .rmInputValue(12312L)
        .rmReview("ok").build();
    List<OtherEvaluate> otherEvaluateInserts = Arrays.asList(otherEvaluateInsert);

    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_INFORMATION.getCode());

    String[] strings = {"test"};
    loanApplicationService.deleteUnselectedIncomes(LOAN_ID, strings);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      otherEvaluateService.saves(otherEvaluateInserts);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.INVALID_FORM.getCode());
  }

  @Test
  void givenValidInput_ThenSaves_shouldReturnLoanPayerNotFound() {
    OtherEvaluate otherEvaluateInsert = OtherEvaluate.builder()
        .uuid(null)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .legalRecord("legalRecord")
        .rmInputValue(12312L)
        .rmReview("ok")
        .payerId(PAYER_ID).build();
    List<OtherEvaluate> otherEvaluateInserts = Arrays.asList(otherEvaluateInsert);

    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_INFORMATION.getCode());

    String[] strings = {"test"};
    loanApplicationService.deleteUnselectedIncomes(LOAN_ID, strings);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      otherEvaluateService.saves(otherEvaluateInserts);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_PAYER_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenDeleteByUuid_shouldReturnSuccess() {
    OtherEvaluateEntity entity = OtherEvaluateEntity.builder()
        .uuid(OTHER_EVALUATE_ID)
        .loanApplicationId(LOAN_ID)
        .build();
    doReturn(Optional.of(entity)).when(otherEvaluateRepository).findByUuid(OTHER_EVALUATE_ID);

    otherEvaluateService.deleteByUuid(OTHER_EVALUATE_ID);
    verify(otherEvaluateRepository, times(1)).delete(entity);
  }
}
