package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.IncomeFromEnum;
import vn.com.msb.homeloan.core.constant.LoanCurrentStepEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.OtherIncomeEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.OtherIncome;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.model.mapper.OtherIncomeMapper;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.repository.OtherIncomeRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CommonIncomeService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.OtherIncomeService;
import vn.com.msb.homeloan.core.service.LandTransactionService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;

@ExtendWith(MockitoExtension.class)
class OtherIncomeServiceTest {

  private final String OTHER_INCOME_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID_OTHER = "03f9e024-7ec4-4ed3-8f1f-330d232b6388";
  private final String LOAN_PAYER_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6388";

  OtherIncomeService otherIncomeService;

  @Mock
  OtherIncomeRepository otherIncomeRepository;

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

  @Mock
  LandTransactionService landTransactionService;

  @BeforeEach
  void setUp() {
    this.otherIncomeService = new OtherIncomeServiceImpl(
        otherIncomeRepository,
        loanApplicationRepository,
        loanApplicationService,
        cmsTabActionService,
        loanPayerRepository,
        commonIncomeService, landTransactionService);
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnLoanApplicationNotFound() {
    OtherIncomeEntity entity = OtherIncomeEntity.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      otherIncomeService.save(OtherIncomeMapper.INSTANCE.toModel(entity), ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenInsertNotExistLoanId_shouldReturnSuccess() throws IOException {
    OtherIncomeEntity entity = OtherIncomeEntity.builder()
        .uuid(null)
        .loanApplicationId(LOAN_ID)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);

    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(entity).when(otherIncomeRepository).save(entity);

    OtherIncome result = otherIncomeService.save(OtherIncomeMapper.INSTANCE.toModel(entity),
        ClientTypeEnum.LDP);
    assertEquals(result.getLoanApplicationId(), entity.getLoanApplicationId());
  }

  @Test
  void givenValidInput_ThenInsertNotExistUuid_shouldReturnSuccess() throws IOException {
    OtherIncomeEntity entity = OtherIncomeEntity.builder()
        .uuid(null)
        .loanApplicationId(LOAN_ID)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplication.setCurrentStep(LoanCurrentStepEnum.LOAN_INFORMATION.getCode());

    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(entity).when(otherIncomeRepository).save(entity);

    OtherIncome result = otherIncomeService.save(OtherIncomeMapper.INSTANCE.toModel(entity),
        ClientTypeEnum.LDP);
    assertEquals(result.getLoanApplicationId(), entity.getLoanApplicationId());
  }

  @Test
  void givenValidInput_ThenFindByUuid_shouldReturnResourceNotFound() {
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      otherIncomeService.findByUuid(OTHER_INCOME_ID);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnSuccess() throws IOException {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));

    List<OtherIncome> otherIncomes = new ArrayList<>();
    OtherIncome otherIncome = OtherIncome.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.MARRIED_PERSON)
        .incomeFrom(IncomeFromEnum.OTHERS)
        .value(10000L)
        .rmReview("review").build();
    otherIncomes.add(otherIncome);

    OtherIncomeEntity otherEntity = OtherIncomeEntity.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    doReturn(Optional.of(otherEntity)).when(otherIncomeRepository).findById(OTHER_INCOME_ID);
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);

    CommonIncome commonIncome = CommonIncome.builder().build();

    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(LOAN_ID);

    loanApplicationService.deleteUnselectedIncomes(LOAN_ID, commonIncome.getSelectedIncomes());

    List<OtherIncome> result = otherIncomeService.saves(otherIncomes);

    assertEquals(result.get(0).getUuid(), OTHER_INCOME_ID);
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnFailInvalidForm() {
    List<OtherIncome> otherIncomes = new ArrayList<>();
    OtherIncome otherIncome = OtherIncome.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .incomeFrom(IncomeFromEnum.OTHERS)
        .value(10000L)
        .rmReview("review")
        .incomeFrom(IncomeFromEnum.RENTAL_PROPERTIES).build();
    otherIncomes.add(otherIncome);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      otherIncomeService.saves(otherIncomes);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.INVALID_FORM.getCode());
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnFailLoanPayerNotFound() {
    List<OtherIncome> otherIncomes = new ArrayList<>();
    OtherIncome otherIncome = OtherIncome.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .incomeFrom(IncomeFromEnum.OTHERS)
        .value(10000L)
        .rmReview("review")
        .payerId(LOAN_PAYER_ID).build();
    otherIncomes.add(otherIncome);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      otherIncomeService.saves(otherIncomes);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_PAYER_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenUpdate_shouldReturnSuccess() throws IOException {
    OtherIncomeEntity entity = OtherIncomeEntity.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);

    doReturn(Optional.of(entity)).when(otherIncomeRepository).findById(OTHER_INCOME_ID);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    OtherIncomeEntity entitySave = OtherIncomeEntity.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    doReturn(entitySave).when(otherIncomeRepository).save(entity);

    OtherIncome result = otherIncomeService.save(OtherIncomeMapper.INSTANCE.toModel(entitySave),
        ClientTypeEnum.LDP);
    assertEquals(result.getLoanApplicationId(), entity.getLoanApplicationId());
    assertEquals(result.getUuid(), OTHER_INCOME_ID);
  }

  @Test
  void givenValidInput_ThenDeleteByUuid_shouldReturnLoanApplicationNotFound() {
    OtherIncomeEntity entity = OtherIncomeEntity.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    doReturn(Optional.of(entity)).when(otherIncomeRepository).findByUuid(OTHER_INCOME_ID);
    doReturn(Optional.empty()).when(loanApplicationRepository).findById(OTHER_INCOME_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      otherIncomeService.deleteByUuid(OTHER_INCOME_ID, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenDeleteByUuid_shouldReturnSuccess() throws IOException {
    OtherIncomeEntity entity = OtherIncomeEntity.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);

    doReturn(Optional.of(entity)).when(otherIncomeRepository).findByUuid(OTHER_INCOME_ID);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(OTHER_INCOME_ID);

    otherIncomeService.deleteByUuid(OTHER_INCOME_ID, ClientTypeEnum.LDP);
    verify(otherIncomeRepository, times(1)).delete(entity);
  }

  @Test
  void givenValidInput_ThenDeleteByUuidCMS_shouldReturnSuccess() throws IOException {
    OtherIncomeEntity entity = OtherIncomeEntity.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);

    doReturn(Optional.of(entity)).when(otherIncomeRepository).findByUuid(OTHER_INCOME_ID);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(OTHER_INCOME_ID);

    doReturn(null).when(otherIncomeRepository).findByLoanApplicationId(LOAN_ID);

    otherIncomeService.deleteByUuid(OTHER_INCOME_ID, ClientTypeEnum.CMS);
    verify(otherIncomeRepository, times(1)).delete(entity);
  }

  @Test
  void givenValidInput_ThenFindByLoanApplicationId_shouldReturnSuccess() {
    otherIncomeService.findByLoanApplicationId(LOAN_ID);
    verify(otherIncomeRepository, times(1)).findByLoanApplicationId(LOAN_ID);
  }

  @Test
  void givenValidInput_ThenUpdateLDP_shouldReturnOtherIncomeItemLimit() {
    OtherIncomeEntity entity = OtherIncomeEntity.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    List<String> incomeFromIds = Arrays.asList(IncomeFromEnum.OTHERS.getCode(),
        IncomeFromEnum.SAVING_ACCOUNTS.getCode());
    doReturn(4).when(otherIncomeRepository).countByLoanIdEndIncomeFromIds(LOAN_ID, incomeFromIds);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    OtherIncome otherIncome = OtherIncomeMapper.INSTANCE.toModel(entity);
    otherIncome.setUuid(null);
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      otherIncomeService.save(otherIncome, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.ITEM_LIMIT.getCode());
  }

  @Test
  void givenValidInput_ThenUpdateCMS_shouldReturnOtherIncomeItemLimit() {
    OtherIncome otherIncome1 = OtherIncome.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();
    OtherIncome otherIncome2 = OtherIncome.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();
    OtherIncome otherIncome3 = OtherIncome.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();
    OtherIncome otherIncome4 = OtherIncome.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();
    OtherIncome otherIncome5 = OtherIncome.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();
    OtherIncome otherIncome6 = OtherIncome.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();
    OtherIncome otherIncome7 = OtherIncome.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();
    List<OtherIncome> otherIncomes = Arrays.asList(otherIncome1, otherIncome2, otherIncome3,
        otherIncome4, otherIncome5, otherIncome6, otherIncome7);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      otherIncomeService.saves(otherIncomes);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.ITEM_LIMIT.getCode());
  }
}
