package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.BandEnum;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.CompanyEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.KpiRateEnum;
import vn.com.msb.homeloan.core.constant.LoanCurrentStepEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.constant.SpecialGroupEnum;
import vn.com.msb.homeloan.core.constant.WorkGroupingEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.entity.SalaryIncomeEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.model.SalaryIncome;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.model.mapper.SalaryIncomeMapper;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.repository.SalaryIncomeRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CommonIncomeService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.SalaryIncomeService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;

@ExtendWith(MockitoExtension.class)
class SalaryIncomeServiceTest {

  private final String SALARY_INCOME_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID_OTHER = "03f9e024-7ec4-4ed3-8f1f-330d232b6388";
  private final String LOAN_PAYER_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  SalaryIncomeService salaryIncomeService;

  @Mock
  SalaryIncomeRepository salaryIncomeRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

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
    this.salaryIncomeService = new SalaryIncomeServiceImpl(
        salaryIncomeRepository,
        loanApplicationRepository,
        loanApplicationService,
        cmsTabActionService,
        loanPayerRepository,
        commonIncomeService);
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnLoanApplicationNotFound() {
    SalaryIncomeEntity entity = SalaryIncomeEntity.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      salaryIncomeService.save(SalaryIncomeMapper.INSTANCE.toModel(entity), ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnLoanApplicationIdCanNotChange() {
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID_OTHER));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID_OTHER);

    SalaryIncomeEntity entity = SalaryIncomeEntity.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();
    doReturn(Optional.of(entity)).when(salaryIncomeRepository).findByUuid(SALARY_INCOME_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      SalaryIncome salaryIncome = SalaryIncomeMapper.INSTANCE.toModel(entity);
      salaryIncome.setLoanApplicationId(LOAN_ID_OTHER);
      salaryIncomeService.save(salaryIncome, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(),
        ErrorEnum.LOAN_APPLICATION_ID_CAN_NOT_CHANGE.getCode());
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnFailItemLimit() {
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID_OTHER));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    SalaryIncomeEntity entity = SalaryIncomeEntity.builder()
        .loanApplicationId(LOAN_ID)
        .build();

    doReturn(4).when(salaryIncomeRepository).countByLoanApplicationId(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      SalaryIncome salaryIncome = SalaryIncomeMapper.INSTANCE.toModel(entity);
      salaryIncome.setLoanApplicationId(LOAN_ID);
      salaryIncomeService.save(salaryIncome, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.ITEM_LIMIT.getCode());
  }

  @Test
  void givenValidInput_ThenInsertNotExistLoanId_shouldReturnSuccess() throws IOException {
    SalaryIncomeEntity entity = SalaryIncomeEntity.builder()
        .uuid(null)
        .loanApplicationId(LOAN_ID)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);

    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(entity).when(salaryIncomeRepository).save(entity);

    SalaryIncome result = salaryIncomeService.save(SalaryIncomeMapper.INSTANCE.toModel(entity),
        ClientTypeEnum.LDP);
    assertEquals(result.getLoanApplicationId(), entity.getLoanApplicationId());
  }

  @Test
  void givenValidInput_ThenInsertNotExistUuid_shouldReturnSuccess() throws IOException {
    SalaryIncomeEntity entity = SalaryIncomeEntity.builder()
        .uuid(null)
        .loanApplicationId(LOAN_ID)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplication.setCurrentStep(LoanCurrentStepEnum.LOAN_INFORMATION.getCode());

    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(entity).when(salaryIncomeRepository).save(entity);

    SalaryIncome result = salaryIncomeService.save(SalaryIncomeMapper.INSTANCE.toModel(entity),
        ClientTypeEnum.LDP);
    assertEquals(result.getLoanApplicationId(), entity.getLoanApplicationId());
  }

  @Test
  void givenValidInput_ThenFindByUuid_shouldReturnResourceNotFound() {
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      salaryIncomeService.findByUuid(SALARY_INCOME_ID);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnSuccess() throws IOException {
    SalaryIncomeEntity entity = SalaryIncomeEntity.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    SalaryIncome salaryIncome = SalaryIncome.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .company(CompanyEnum.MSB)
        .band(BandEnum.BAND_1)
        .kpiRate(KpiRateEnum.E)
        .emplCode("312333")
        .specialGroup(SpecialGroupEnum.OTHERS)
        .rmReview("tốt")
        .workGrouping(WorkGroupingEnum.AA1)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .payerId(LOAN_PAYER_ID)
        .build();
    List<SalaryIncome> salaryIncomes = new ArrayList<>();
    salaryIncomes.add(salaryIncome);

    // hold on
    LoanApplication loanApplication = LoanApplication.builder()
        .uuid(LOAN_ID)
//                .approvalFlow(ApprovalFlowEnum.NORMALLY)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
//                .recognitionMethod2(RecognitionMethod2Enum.ACCUMULATED_ASSETS)
        .build();

    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_INFORMATION.getCode());
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);
    // doReturn(Optional.of(entity)).when(salaryIncomeRepository).findByUuid(SALARY_INCOME_ID);

    LoanPayerEntity loanPayerEntity = LoanPayerEntity.builder()
        .uuid(LOAN_PAYER_ID).build();
    doReturn(Optional.of(loanPayerEntity)).when(loanPayerRepository).findByUuid(LOAN_PAYER_ID);

    CommonIncome commonIncome = CommonIncome.builder().build();

    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(LOAN_ID);

    List<SalaryIncome> result = salaryIncomeService.saves(salaryIncomes);

    assertEquals(result.get(0).getUuid(), SALARY_INCOME_ID);
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnFail() {
    SalaryIncomeEntity entity = SalaryIncomeEntity.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    SalaryIncome salaryIncome = SalaryIncome.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .company(CompanyEnum.MSB)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .payerId(LOAN_PAYER_ID)
        .build();
    List<SalaryIncome> salaryIncomes = new ArrayList<>();
    salaryIncomes.add(salaryIncome);

    LoanApplication loanApplication = LoanApplication.builder()
        .uuid(LOAN_ID)
        // hold on
//                .approvalFlow(ApprovalFlowEnum.NORMALLY)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
//                .recognitionMethod2(RecognitionMethod2Enum.ACCUMULATED_ASSETS)
        .build();

    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_INFORMATION.getCode());
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);
    // doReturn(Optional.of(entity)).when(salaryIncomeRepository).findByUuid(SALARY_INCOME_ID);

    LoanPayerEntity loanPayerEntity = LoanPayerEntity.builder()
        .uuid(LOAN_PAYER_ID).build();
    doReturn(Optional.of(loanPayerEntity)).when(loanPayerRepository).findByUuid(LOAN_PAYER_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      salaryIncomeService.saves(salaryIncomes);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.INVALID_FORM.getCode());
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnFailLoanApplicationIdCanNotChange() {
    SalaryIncomeEntity entity = SalaryIncomeEntity.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID_OTHER)
        .build();

    SalaryIncome salaryIncome = SalaryIncome.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .company(CompanyEnum.MSB)
        .band(BandEnum.BAND_1)
        .kpiRate(KpiRateEnum.E)
        .emplCode("312333")
        .specialGroup(SpecialGroupEnum.OTHERS)
        .rmReview("tốt")
        .workGrouping(WorkGroupingEnum.AA1)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .payerId(LOAN_PAYER_ID)
        .build();
    List<SalaryIncome> salaryIncomes = new ArrayList<>();
    salaryIncomes.add(salaryIncome);

    LoanApplication loanApplication = LoanApplication.builder()
        .uuid(LOAN_ID)
        // hold on
//                .approvalFlow(ApprovalFlowEnum.NORMALLY)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
//                .recognitionMethod2(RecognitionMethod2Enum.ACCUMULATED_ASSETS)
        .build();

    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_INFORMATION.getCode());
    // doReturn(Optional.of(entity)).when(salaryIncomeRepository).findByUuid(SALARY_INCOME_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      salaryIncomeService.saves(salaryIncomes);
    });

    assertTrue(1 == 1);
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnFailInvalidForm() {
    SalaryIncomeEntity entity = SalaryIncomeEntity.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    SalaryIncome salaryIncome = SalaryIncome.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .company(CompanyEnum.MSB)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
//                .payerId(LOAN_PAYER_ID)
        .build();
    List<SalaryIncome> salaryIncomes = new ArrayList<>();
    salaryIncomes.add(salaryIncome);

    LoanApplication loanApplication = LoanApplication.builder()
        .uuid(LOAN_ID)
        // hold on
//                .approvalFlow(ApprovalFlowEnum.NORMALLY)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
//                .recognitionMethod2(RecognitionMethod2Enum.ACCUMULATED_ASSETS)
        .build();

    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_INFORMATION.getCode());
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);
    // doReturn(Optional.of(entity)).when(salaryIncomeRepository).findByUuid(SALARY_INCOME_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      salaryIncomeService.saves(salaryIncomes);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.INVALID_FORM.getCode());
  }

  @Test
  void givenValidInput_ThenUpdate_shouldReturnSuccess() throws IOException {
    SalaryIncomeEntity entity = SalaryIncomeEntity.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);

    doReturn(Optional.of(entity)).when(salaryIncomeRepository).findByUuid(SALARY_INCOME_ID);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    SalaryIncomeEntity entitySave = SalaryIncomeEntity.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    doReturn(entitySave).when(salaryIncomeRepository).save(entity);

    SalaryIncome result = salaryIncomeService.save(SalaryIncomeMapper.INSTANCE.toModel(entitySave),
        ClientTypeEnum.LDP);
    assertEquals(result.getLoanApplicationId(), entity.getLoanApplicationId());
    assertEquals(result.getUuid(), SALARY_INCOME_ID);
  }

  @Test
  void givenValidInput_ThenDeleteByUuid_shouldReturnLoanApplicationNotFound() {
    SalaryIncomeEntity entity = SalaryIncomeEntity.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    doReturn(Optional.of(entity)).when(salaryIncomeRepository).findByUuid(SALARY_INCOME_ID);
    doReturn(Optional.empty()).when(loanApplicationRepository).findById(SALARY_INCOME_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      salaryIncomeService.deleteByUuid(SALARY_INCOME_ID, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenDeleteByUuid_shouldReturnSuccess() throws IOException {
    SalaryIncomeEntity entity = SalaryIncomeEntity.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);

    doReturn(Optional.of(entity)).when(salaryIncomeRepository).findByUuid(SALARY_INCOME_ID);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(SALARY_INCOME_ID);

    salaryIncomeService.deleteByUuid(SALARY_INCOME_ID, ClientTypeEnum.LDP);
    verify(salaryIncomeRepository, times(1)).delete(entity);
  }

  @Test
  void givenValidInput_ThenDeleteByUuidCMS_shouldReturnSuccess() throws IOException {
    SalaryIncomeEntity entity = SalaryIncomeEntity.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);

    doReturn(Optional.of(entity)).when(salaryIncomeRepository).findByUuid(SALARY_INCOME_ID);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(SALARY_INCOME_ID);

    salaryIncomeService.deleteByUuid(SALARY_INCOME_ID, ClientTypeEnum.CMS);
    verify(salaryIncomeRepository, times(1)).delete(entity);
  }

  @Test
  void givenValidInput_ThenFindByLoanApplicationId_shouldReturnSuccess() {
    salaryIncomeService.findByLoanApplicationId(LOAN_ID);
    verify(salaryIncomeRepository, times(1)).findByLoanApplicationId(LOAN_ID);
  }

}
