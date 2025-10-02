package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.LoanCurrentStepEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.entity.BusinessIncomeEntity;
import vn.com.msb.homeloan.core.entity.CicItemEntity;
import vn.com.msb.homeloan.core.entity.CreditworthinessItemEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.entity.OpRiskEntity;
import vn.com.msb.homeloan.core.entity.OtherEvaluateEntity;
import vn.com.msb.homeloan.core.entity.OtherIncomeEntity;
import vn.com.msb.homeloan.core.entity.SalaryIncomeEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.LoanPayer;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.model.mapper.LoanPayerMapper;
import vn.com.msb.homeloan.core.repository.BusinessIncomeRepository;
import vn.com.msb.homeloan.core.repository.CicItemRepository;
import vn.com.msb.homeloan.core.repository.CreditworthinessItemRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.repository.OpRiskRepository;
import vn.com.msb.homeloan.core.repository.OtherEvaluateRepository;
import vn.com.msb.homeloan.core.repository.OtherIncomeRepository;
import vn.com.msb.homeloan.core.repository.SalaryIncomeRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.LoanPayerService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;

@ExtendWith(MockitoExtension.class)
class LoanPayerServiceTest {

  private final String LOAN_PAYER_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String SALARY_INCOME_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String BUSINESS_INCOME_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String OTHER_INCOME_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String OTHER_EVALUATE_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String CREDITWORTHINESS_ITEM_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String OP_RISK_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String CIC_ITEM_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String ID_NO = "123456";
  private final String OLD_ID_NO = "654321";

  LoanPayerService loanPayerService;

  @Mock
  LoanPayerRepository loanPayerRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  LoanApplicationServiceImpl loanApplicationService;

  @Mock
  CMSTabActionService cmsTabActionService;

  @Mock
  SalaryIncomeRepository salaryIncomeRepository;

  @Mock
  BusinessIncomeRepository businessIncomeRepository;

  @Mock
  OtherIncomeRepository otherIncomeRepository;

  @Mock
  OtherEvaluateRepository otherEvaluateRepository;

  @Mock
  CicItemRepository cicItemRepository;

  @Mock
  OpRiskRepository opRiskRepository;

  @Mock
  CreditworthinessItemRepository creditworthinessItemRepository;

  @BeforeEach
  void setUp() {
    this.loanPayerService = new LoanPayerServiceImpl(
        loanPayerRepository,
        loanApplicationRepository,
        loanApplicationService,
        cmsTabActionService,
        salaryIncomeRepository,
        businessIncomeRepository,
        otherIncomeRepository,
        otherEvaluateRepository,
        cicItemRepository,
        opRiskRepository,
        creditworthinessItemRepository
    );
  }

  @Test
  void givenValidInput_ThenFindById_shouldReturnLoanPayerNotFound() {
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanPayerService.findById(LOAN_PAYER_ID);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_PAYER_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenFindById_shouldReturnSuccess() {
    LoanPayerEntity entity = LoanPayerEntity.builder()
        .uuid(LOAN_PAYER_ID)
        .build();
    doReturn(Optional.of(entity)).when(loanPayerRepository).findById(LOAN_PAYER_ID);

    LoanPayer result = loanPayerService.findById(LOAN_PAYER_ID);

    assertEquals(result.getUuid(), entity.getUuid());
  }

  @Test
  void givenValidInput_ThenFindLoanId_shouldReturnSuccess() {
    LoanPayerEntity entity = LoanPayerEntity.builder()
        .uuid(LOAN_PAYER_ID)
        .build();
    List<LoanPayerEntity> loanPayers = new ArrayList<>();
    loanPayers.add(entity);

    doReturn(loanPayers).when(loanPayerRepository).findByLoanId(LOAN_ID);

    List<LoanPayer> results = loanPayerService.findByLoanId(LOAN_ID);

    assertEquals(results.size(), 1);
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnLoanApplicationNotFound() {
    LoanPayerEntity entity = LoanPayerEntity.builder()
        .uuid(null)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .build();
    doReturn(Optional.empty()).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanPayerService.save(LoanPayerMapper.INSTANCE.toModel(entity), ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenInsertCountPayerGreaterThan5_shouldReturnItemLimit() {
    LoanPayerEntity entity = LoanPayerEntity.builder()
        .uuid(null)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(5L).when(loanPayerRepository).countByLoanId(LOAN_ID);
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanPayerService.save(LoanPayerMapper.INSTANCE.toModel(entity), ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.ITEM_LIMIT.getCode());
  }

  @Test
  void givenValidInput_ThenInsertAndUpdateStatusLoanApplication_shouldReturnItemLimit()
      throws IOException {
    LoanPayerEntity entity = LoanPayerEntity.builder()
        .uuid(null)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplication.setCurrentStep(LoanCurrentStepEnum.LOAN_CUSTOMER.getCode());
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(1L).when(loanPayerRepository).countByLoanId(LOAN_ID);
    LoanPayerEntity result = loanPayerService.save(LoanPayerMapper.INSTANCE.toModel(entity),
        ClientTypeEnum.LDP);
    assertEquals(result.getLoanId(), entity.getLoanId());
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnSuccess() throws ParseException, IOException {
    LoanPayerEntity entity = LoanPayerEntity.builder()
        .uuid(LOAN_PAYER_ID)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .birthday(new SimpleDateFormat("dd/MM/yyyy").parse("16/07/1996"))
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplication.setCurrentStep(LoanCurrentStepEnum.LOAN_CUSTOMER.getCode());
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(entity).when(loanPayerRepository).save(any());

    doReturn(Optional.of(entity)).when(loanPayerRepository).findById(LOAN_PAYER_ID);

    LoanPayerEntity result = loanPayerService.save(LoanPayerMapper.INSTANCE.toModel(entity),
        ClientTypeEnum.CMS);

    assertEquals(result.getLoanId(), entity.getLoanId());
  }

  @Test
  void givenValidInput_ThenUpdate_shouldReturnLoanPayerNotFound() {
    LoanPayerEntity entity = LoanPayerEntity.builder()
        .uuid(LOAN_PAYER_ID)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(Optional.empty()).when(loanPayerRepository).findById(LOAN_PAYER_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanPayerService.save(LoanPayerMapper.INSTANCE.toModel(entity), ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_PAYER_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenUpdate_shouldReturnSuccess() throws IOException {
    LoanPayerEntity entity = LoanPayerEntity.builder()
        .uuid(LOAN_PAYER_ID)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplication.setCurrentStep(LoanCurrentStepEnum.LOAN_CUSTOMER.getCode());
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(Optional.of(entity)).when(loanPayerRepository).findById(LOAN_PAYER_ID);
    doReturn(entity).when(loanPayerRepository).save(entity);
    LoanPayerEntity result = loanPayerService.save(LoanPayerMapper.INSTANCE.toModel(entity),
        ClientTypeEnum.LDP);

    assertEquals(result.getLoanId(), entity.getLoanId());
  }

  @Test
  void givenValidInput_ThenDeleteById_shouldReturnLoanPayerNotFound() {
    doReturn(Optional.empty()).when(loanPayerRepository).findById(LOAN_PAYER_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanPayerService.deleteById(LOAN_PAYER_ID, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenDeleteById_shouldLoanNotFound() {
    LoanPayerEntity entity = LoanPayerEntity.builder()
        .uuid(LOAN_PAYER_ID)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .build();

    doReturn(Optional.of(entity)).when(loanPayerRepository).findById(entity.getUuid());

    doReturn(Optional.empty()).when(loanApplicationRepository).findById(entity.getLoanId());

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanPayerService.deleteById(LOAN_PAYER_ID, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenDeleteById_shouldReturnSuccess() throws IOException {
    LoanPayerEntity entity = LoanPayerEntity.builder()
        .uuid(LOAN_PAYER_ID)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .build();
    doReturn(Optional.of(entity)).when(loanPayerRepository).findById(entity.getUuid());
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(entity.getLoanId());

    loanPayerService.deleteById(LOAN_PAYER_ID, ClientTypeEnum.LDP);
    verify(loanPayerRepository, times(1)).deleteById(LOAN_PAYER_ID);
  }

  @Test
  void givenValidInput_ThenDeleteByIdCMS_shouldReturnSuccess() throws IOException {
    LoanPayerEntity entity = LoanPayerEntity.builder()
        .uuid(LOAN_PAYER_ID)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .idNo(ID_NO)
        .oldIdNo(OLD_ID_NO)
        .build();
    doReturn(Optional.of(entity)).when(loanPayerRepository).findById(entity.getUuid());

    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .idNo(ID_NO)
        .oldIdNo(OLD_ID_NO).build();
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(entity.getLoanId());

    SalaryIncomeEntity salaryIncomeEntity = SalaryIncomeEntity.builder()
        .uuid(SALARY_INCOME_ID)
        .loanApplicationId(LOAN_ID).build();
    doReturn(Arrays.asList(salaryIncomeEntity)).when(salaryIncomeRepository)
        .findByLoanApplicationIdAndPayerId(LOAN_ID, LOAN_PAYER_ID);

    BusinessIncomeEntity businessIncomeEntity = BusinessIncomeEntity.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID).build();
    doReturn(Arrays.asList(businessIncomeEntity)).when(businessIncomeRepository)
        .findByLoanApplicationIdAndPayerId(LOAN_ID, BUSINESS_INCOME_ID);

    OtherIncomeEntity otherIncomeEntity = OtherIncomeEntity.builder()
        .uuid(OTHER_INCOME_ID)
        .loanApplicationId(LOAN_ID).build();
    doReturn(Arrays.asList(otherIncomeEntity)).when(otherIncomeRepository)
        .findByLoanApplicationIdAndPayerId(LOAN_ID, OTHER_INCOME_ID);

    OtherEvaluateEntity otherEvaluateEntity = OtherEvaluateEntity.builder()
        .uuid(OTHER_EVALUATE_ID)
        .loanApplicationId(LOAN_ID).build();
    doReturn(Arrays.asList(otherEvaluateEntity)).when(otherEvaluateRepository)
        .findByLoanApplicationIdAndPayerId(LOAN_ID, OTHER_EVALUATE_ID);

    CreditworthinessItemEntity creditworthinessItemEntity = CreditworthinessItemEntity.builder()
        .uuid(CREDITWORTHINESS_ITEM_ID)
        .loanApplicationId(LOAN_ID).build();
    doReturn(Arrays.asList(creditworthinessItemEntity)).when(creditworthinessItemRepository)
        .findByLoanApplicationIdAndTarget(LOAN_ID, CREDITWORTHINESS_ITEM_ID);

    OpRiskEntity opRiskEntity = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .identityCard(ID_NO)
        .loanApplicationId(LOAN_ID).build();
    doReturn(Arrays.asList(opRiskEntity)).when(opRiskRepository)
        .findByLoanApplicationIdAndIdentityCardIn(LOAN_ID,
            new HashSet<>(Arrays.asList(ID_NO, OLD_ID_NO)));

    CicItemEntity cicItemEntity = CicItemEntity.builder()
        .uuid(CIC_ITEM_ID).build();
    doReturn(Arrays.asList(cicItemEntity)).when(cicItemRepository)
        .findByLoanApplicationAndIdentityCardIn(LOAN_ID,
            new HashSet<>(Arrays.asList(ID_NO, OLD_ID_NO)));

    loanPayerService.deleteById(LOAN_PAYER_ID, ClientTypeEnum.CMS);
    verify(loanPayerRepository, times(1)).deleteById(LOAN_PAYER_ID);
  }

  @Test
  void givenValidInput_ThenSaves_shouldReturnSuccess() throws IOException {
    LoanPayerEntity entity = LoanPayerEntity.builder()
        .uuid(LOAN_PAYER_ID)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplication.setCurrentStep(LoanCurrentStepEnum.LOAN_CUSTOMER.getCode());
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(Optional.of(entity)).when(loanPayerRepository).findById(LOAN_PAYER_ID);
    doReturn(entity).when(loanPayerRepository).save(entity);

    List<LoanPayer> loanPayers = new ArrayList<>();
    loanPayers.add(LoanPayerMapper.INSTANCE.toModel(entity));

    List<LoanPayer> result = loanPayerService.save(loanPayers);

    assertEquals(result.get(0).getLoanId(), entity.getLoanId());
  }

  @Test
  void givenValidInput_ThenSaves_shouldReturnFailLoanPayerItemLimit() {
    LoanPayerEntity entity1 = LoanPayerEntity.builder()
        .uuid(LOAN_PAYER_ID)
        .loanId(LOAN_ID)
        .fullName("fullName1")
        .build();
    LoanPayerEntity entity2 = LoanPayerEntity.builder()
        .uuid(LOAN_PAYER_ID)
        .loanId(LOAN_ID)
        .fullName("fullName2")
        .build();
    LoanPayerEntity entity3 = LoanPayerEntity.builder()
        .uuid(LOAN_PAYER_ID)
        .loanId(LOAN_ID)
        .fullName("fullName3")
        .build();

    List<LoanPayer> loanPayers = new ArrayList<>();
    loanPayers.add(LoanPayerMapper.INSTANCE.toModel(entity1));
    loanPayers.add(LoanPayerMapper.INSTANCE.toModel(entity2));
    loanPayers.add(LoanPayerMapper.INSTANCE.toModel(entity3));

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanPayerService.save(loanPayers);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.ITEM_LIMIT.getCode());
  }
}