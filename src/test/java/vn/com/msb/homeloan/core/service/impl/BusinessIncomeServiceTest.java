package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.BusinessLineEnum;
import vn.com.msb.homeloan.core.constant.BusinessTypeEnum;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.LoanCurrentStepEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.entity.BusinessIncomeEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.BusinessIncome;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.mapper.BusinessIncomeMapper;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.repository.BusinessIncomeRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.service.BusinessIncomeService;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CommonIncomeService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;

@ExtendWith(MockitoExtension.class)
class BusinessIncomeServiceTest {

  private final String BUSINESS_INCOME_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String BUSINESS_INCOME_ID_OTHER = "03f9e024-7ec4-4ed3-8f1f-330d232b6398";
  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID_OTHER = "03f9e024-7ec4-4ed3-8f1f-330d232b6388";
  private final String PAYER_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6388";

  BusinessIncomeService businessIncomeService;

  @Mock
  BusinessIncomeRepository businessIncomeRepository;

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
    this.businessIncomeService = new BusinessIncomeServiceImpl(
        businessIncomeRepository,
        loanApplicationRepository,
        loanApplicationService,
        cmsTabActionService,
        loanPayerRepository,
        commonIncomeService
    );
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnLoanApplicationNotFound() {
    BusinessIncomeEntity entity = BusinessIncomeEntity.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      businessIncomeService.save(BusinessIncomeMapper.INSTANCE.toModel(entity), ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenInsertNotExistLoanId_shouldReturnSuccess() throws IOException {
    BusinessIncomeEntity entity = BusinessIncomeEntity.builder()
        .uuid(null)
        .loanApplicationId(LOAN_ID)
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(entity).when(businessIncomeRepository).save(entity);
    BusinessIncome result = businessIncomeService.save(
        BusinessIncomeMapper.INSTANCE.toModel(entity), ClientTypeEnum.LDP);
    assertEquals(result.getLoanApplicationId(), entity.getLoanApplicationId());
  }

  @Test
  void givenValidInput_ThenInsertNotExistUuid_shouldReturnSuccess() throws IOException {
    BusinessIncomeEntity entity = BusinessIncomeEntity.builder()
        .uuid(null)
        .loanApplicationId(LOAN_ID)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplication.setCurrentStep(LoanCurrentStepEnum.LOAN_INFORMATION.getCode());

    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(entity).when(businessIncomeRepository).save(entity);

    BusinessIncome result = businessIncomeService.save(
        BusinessIncomeMapper.INSTANCE.toModel(entity), ClientTypeEnum.LDP);
    assertEquals(result.getLoanApplicationId(), entity.getLoanApplicationId());
  }

  @Test
  void givenValidInput_ThenFindByUuid_shouldReturnResourceNotFound() {
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      businessIncomeService.findByUuid(BUSINESS_INCOME_ID);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnSuccessBusinessTypeENTERPRISE()
      throws ParseException, IOException {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));

    BusinessIncomeEntity entity = BusinessIncomeEntity.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    List<BusinessIncome> businessIncomes = new ArrayList<>();
    BusinessIncome businessIncome = BusinessIncome.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.MARRIED_PERSON)
        .businessType(BusinessTypeEnum.ENTERPRISE)
        .businessLine(BusinessLineEnum.SERVICES)
        .businessCode("businessCode")
        .name("Cong ty A")
        .province("province")
        .provinceName("provinceName")
        .district("district")
        .districtName("districtName")
        .ward("ward")
        .wardName("wardName")
        .address("address")
        .phone("0988794797")
        .value(111111L)
        .businessActivity("businessActivity")
        .issuedDate(new SimpleDateFormat("dd/MM/yyyy").parse("16/07/1996"))
        .revenue(1222222L)
        .cost(222L)
        .profitMargin(BigDecimal.valueOf(111)).build();
    businessIncomes.add(businessIncome);

    doReturn(Optional.of(entity)).when(businessIncomeRepository).findById(BUSINESS_INCOME_ID);
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);

    CommonIncome commonIncome = CommonIncome.builder().build();

    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(LOAN_ID);

    loanApplicationService.deleteUnselectedIncomes(LOAN_ID, commonIncome.getSelectedIncomes());

    List<BusinessIncome> result = businessIncomeService.saves(businessIncomes);

    assertEquals(result.get(0).getUuid(), BUSINESS_INCOME_ID);
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnFailBusinessIncomeItemLimit()
      throws ParseException {
    BusinessIncome businessIncome = BusinessIncome.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.MARRIED_PERSON)
        .businessType(BusinessTypeEnum.ENTERPRISE)
        .businessLine(BusinessLineEnum.SERVICES)
        .businessCode("businessCode")
        .name("Cong ty A")
        .province("province")
        .provinceName("provinceName")
        .district("district")
        .districtName("districtName")
        .ward("ward")
        .wardName("wardName")
        .address("address")
        .phone("0988794797")
        .value(111111L)
        .businessActivity("businessActivity")
        .issuedDate(new SimpleDateFormat("dd/MM/yyyy").parse("16/07/1996"))
        .revenue(1222222L)
        .cost(222L)
        .profitMargin(BigDecimal.valueOf(111)).build();
    BusinessIncome businessIncome2 = BusinessIncome.builder()
        .uuid(BUSINESS_INCOME_ID).build();
    BusinessIncome businessIncome3 = BusinessIncome.builder()
        .uuid(BUSINESS_INCOME_ID).build();
    List<BusinessIncome> businessIncomes = Arrays.asList(businessIncome, businessIncome2,
        businessIncome3);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      businessIncomeService.saves(businessIncomes);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.ITEM_LIMIT.getCode());
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnSuccess2BusinessTypeOtherENTERPRISE()
      throws ParseException, IOException {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));

    BusinessIncomeEntity entity = BusinessIncomeEntity.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    List<BusinessIncome> businessIncomes = new ArrayList<>();
    BusinessIncome businessIncome = BusinessIncome.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.MARRIED_PERSON)
        .businessType(BusinessTypeEnum.WITH_REGISTRATION)
        .businessLine(BusinessLineEnum.SERVICES)
        .businessCode("businessCode")
        .name("Cong ty A")
        .province("province")
        .provinceName("provinceName")
        .district("district")
        .districtName("districtName")
        .ward("ward")
        .wardName("wardName")
        .address("address")
        .phone("0988794797")
        .value(111111L)
        .businessActivity("businessActivity")
        .issuedDate(new SimpleDateFormat("dd/MM/yyyy").parse("16/07/1996"))
        .revenue(1222222L)
        .cost(222L)
        .profitMargin(BigDecimal.valueOf(111))
        .placeOfIssue("placeOfIssue")
        .businessStartDate(new SimpleDateFormat("dd/MM/yyyy").parse("16/07/2020")).build();
    businessIncomes.add(businessIncome);

    doReturn(Optional.of(entity)).when(businessIncomeRepository).findById(BUSINESS_INCOME_ID);
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);

    CommonIncome commonIncome = CommonIncome.builder().build();

    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(LOAN_ID);

    List<BusinessIncome> result = businessIncomeService.saves(businessIncomes);

    assertEquals(result.get(0).getUuid(), BUSINESS_INCOME_ID);
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnFailLOAN_APPLICATION_ID_CAN_NOT_CHANGE()
      throws ParseException {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));

    BusinessIncomeEntity entity = BusinessIncomeEntity.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID_OTHER)
        .build();

    List<BusinessIncome> businessIncomes = new ArrayList<>();
    BusinessIncome businessIncome = BusinessIncome.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.MARRIED_PERSON)
        .businessType(BusinessTypeEnum.ENTERPRISE)
        .businessLine(BusinessLineEnum.SERVICES)
        .businessCode("businessCode")
        .name("Cong ty A")
        .province("province")
        .provinceName("provinceName")
        .district("district")
        .districtName("districtName")
        .ward("ward")
        .wardName("wardName")
        .address("address")
        .phone("0988794797")
        .value(111111L)
        .businessActivity("businessActivity")
        .issuedDate(new SimpleDateFormat("dd/MM/yyyy").parse("16/07/1996"))
        .revenue(1222222L)
        .cost(222L)
        .profitMargin(BigDecimal.valueOf(111)).build();
    businessIncomes.add(businessIncome);

    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);

    doReturn(Optional.of(entity)).when(businessIncomeRepository).findById(BUSINESS_INCOME_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      businessIncomeService.saves(businessIncomes);
    });

    assertEquals(exception.getCode().intValue(),
        ErrorEnum.LOAN_APPLICATION_ID_CAN_NOT_CHANGE.getCode());
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnFailNotBlankPlaceOfIssue() throws ParseException {
    List<BusinessIncome> businessIncomes = new ArrayList<>();
    BusinessIncome businessIncome = BusinessIncome.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.MARRIED_PERSON)
        .businessType(BusinessTypeEnum.WITH_REGISTRATION)
        .businessLine(BusinessLineEnum.SERVICES)
        .name("Cong ty A")
        .province("province")
        .provinceName("provinceName")
        .district("district")
        .districtName("districtName")
        .ward("ward")
        .wardName("wardName")
        .address("address")
        .phone("0988794797")
        .value(111111L)
        .businessActivity("businessActivity")
        .revenue(1222222L)
        .cost(222L)
        .profitMargin(BigDecimal.valueOf(111)).build();
    businessIncomes.add(businessIncome);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      businessIncomeService.saves(businessIncomes);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.INVALID_FORM.getCode());
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnFailInvalidForm() throws ParseException {
    List<BusinessIncome> businessIncomes = new ArrayList<>();
    BusinessIncome businessIncome = BusinessIncome.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.MARRIED_PERSON)
        .businessType(BusinessTypeEnum.WITHOUT_REGISTRATION)
        .businessLine(BusinessLineEnum.SERVICES)
        .name("Cong ty A")
        .province("province")
        .provinceName("provinceName")
        .district("district")
        .districtName("districtName")
        .ward("ward")
        .wardName("wardName")
        .address("address")
        .phone("0988794797")
        .value(111111L)
        .businessActivity("businessActivity")
        .revenue(1222222L)
        .cost(222L)
        .profitMargin(BigDecimal.valueOf(111)).build();
    businessIncomes.add(businessIncome);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      businessIncomeService.saves(businessIncomes);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.INVALID_FORM.getCode());
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnFailLOAN_PAYER_NOT_FOUND() throws ParseException {
    List<BusinessIncome> businessIncomes = new ArrayList<>();
    BusinessIncome businessIncome = BusinessIncome.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .businessType(BusinessTypeEnum.ENTERPRISE)
        .businessLine(BusinessLineEnum.SERVICES)
        .businessCode("businessCode")
        .name("Cong ty A")
        .province("province")
        .provinceName("provinceName")
        .district("district")
        .districtName("districtName")
        .ward("ward")
        .wardName("wardName")
        .address("address")
        .phone("0988794797")
        .value(111111L)
        .businessActivity("businessActivity")
        .issuedDate(new SimpleDateFormat("dd/MM/yyyy").parse("16/07/1996"))
        .revenue(1222222L)
        .cost(222L)
        .profitMargin(BigDecimal.valueOf(111))
        .payerId(PAYER_ID)
        .placeOfIssue("placeOfIssue").build();
    businessIncomes.add(businessIncome);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      businessIncomeService.saves(businessIncomes);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_PAYER_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenRMUpdate_shouldReturnFail() throws ParseException {
    List<BusinessIncome> businessIncomes = new ArrayList<>();
    BusinessIncome businessIncome = BusinessIncome.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .businessType(BusinessTypeEnum.ENTERPRISE)
        .businessLine(BusinessLineEnum.SERVICES)
        .name("Cong ty A")
        .province("province")
        .provinceName("provinceName")
        .district("district")
        .districtName("districtName")
        .ward("ward")
        .wardName("wardName")
        .address("address")
        .phone("0988794797")
        .value(111111L)
        .businessActivity("businessActivity")
        .revenue(1222222L)
        .cost(222L)
        .profitMargin(BigDecimal.valueOf(111)).build();
    businessIncomes.add(businessIncome);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      businessIncomeService.saves(businessIncomes);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.INVALID_FORM.getCode());
  }

  @Test
  void givenWITH_REGISTRATION_ThenRMUpdate_shouldReturnSuccess()
      throws ParseException, IOException {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));

    BusinessIncomeEntity entity = BusinessIncomeEntity.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    List<BusinessIncome> businessIncomes = new ArrayList<>();
    BusinessIncome businessIncome = BusinessIncome.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.MARRIED_PERSON)
        .businessType(BusinessTypeEnum.ENTERPRISE)
        .businessLine(BusinessLineEnum.SERVICES)
        .businessCode("businessCode")
        .name("Cong ty A")
        .province("province")
        .provinceName("provinceName")
        .district("district")
        .districtName("districtName")
        .ward("ward")
        .wardName("wardName")
        .address("address")
        .phone("0988794797")
        .value(111111L)
        .businessActivity("businessActivity")
        .issuedDate(new SimpleDateFormat("dd/MM/yyyy").parse("16/07/1996"))
        .revenue(1222222L)
        .cost(222L)
        .placeOfIssue("Ha Noi")
        .profitMargin(BigDecimal.valueOf(111)).build();
    businessIncomes.add(businessIncome);

    doReturn(Optional.of(entity)).when(businessIncomeRepository).findById(BUSINESS_INCOME_ID);
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);

    String[] strings = {"test"};
    loanApplicationService.deleteUnselectedIncomes(LOAN_ID, strings);

    List<BusinessIncome> result = businessIncomeService.saves(businessIncomes);

    assertEquals(result.get(0).getUuid(), BUSINESS_INCOME_ID);
  }

  @Test
  void givenValidInput_ThenUpdate_shouldReturnSuccess() throws IOException {
    BusinessIncomeEntity entity = BusinessIncomeEntity.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);

    doReturn(Optional.of(entity)).when(businessIncomeRepository).findById(BUSINESS_INCOME_ID);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    BusinessIncomeEntity entitySave = BusinessIncomeEntity.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    doReturn(entitySave).when(businessIncomeRepository).save(entity);

    BusinessIncome result = businessIncomeService.save(
        BusinessIncomeMapper.INSTANCE.toModel(entitySave), ClientTypeEnum.LDP);
    assertEquals(result.getLoanApplicationId(), entity.getLoanApplicationId());
    assertEquals(result.getUuid(), BUSINESS_INCOME_ID);
  }


  @Test
  void givenValidInput_ThenUpdate_shouldReturnLoanIdCannotChange() {
    BusinessIncomeEntity entity = BusinessIncomeEntity.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);

    BusinessIncomeEntity entityInDb = BusinessIncomeEntity.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId("LOAN_ID")
        .build();
    doReturn(Optional.of(entityInDb)).when(businessIncomeRepository).findById(BUSINESS_INCOME_ID);

    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      businessIncomeService.save(BusinessIncomeMapper.INSTANCE.toModel(entity), ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(),
        ErrorEnum.LOAN_APPLICATION_ID_CAN_NOT_CHANGE.getCode());

  }

  @Test
  void givenValidInput_ThenUpdate_shouldReturnBusinessIncomeItemLimit() {
    BusinessIncomeEntity entity = BusinessIncomeEntity.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .businessType(BusinessTypeEnum.ENTERPRISE)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);

    doReturn(2).when(businessIncomeRepository).countByLoanApplicationIdAndBusinessTypeIn(LOAN_ID,
        Arrays.asList(BusinessTypeEnum.ENTERPRISE));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    BusinessIncome businessIncome = BusinessIncomeMapper.INSTANCE.toModel(entity);
    businessIncome.setUuid(null);
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      businessIncomeService.save(businessIncome, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.ITEM_LIMIT.getCode());
  }

  @Test
  void givenValidInput_ThenDeleteByUuid_shouldReturnLoanApplicationNotFound() {
    BusinessIncomeEntity entity = BusinessIncomeEntity.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .build();

    doReturn(Optional.of(entity)).when(businessIncomeRepository).findByUuid(BUSINESS_INCOME_ID);
    doReturn(Optional.empty()).when(loanApplicationRepository).findById(BUSINESS_INCOME_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      businessIncomeService.deleteByUuid(BUSINESS_INCOME_ID, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenDeleteByUuid_shouldReturnSuccess() throws IOException {
    BusinessIncomeEntity entity = BusinessIncomeEntity.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .businessType(BusinessTypeEnum.ENTERPRISE)
        .build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);

    doReturn(Optional.of(entity)).when(businessIncomeRepository).findByUuid(BUSINESS_INCOME_ID);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(BUSINESS_INCOME_ID);

    businessIncomeService.deleteByUuid(BUSINESS_INCOME_ID, ClientTypeEnum.LDP);
    verify(businessIncomeRepository, times(1)).delete(entity);
  }

  @Test
  void givenValidInput_ThenDeleteByUuidCMSEnterprise_shouldReturnSuccess() throws IOException {
    BusinessIncomeEntity entity = BusinessIncomeEntity.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .businessType(BusinessTypeEnum.ENTERPRISE)
        .build();
    List<BusinessIncomeEntity> businessIncomeEntities = Arrays.asList(entity);

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);

    doReturn(Optional.of(entity)).when(businessIncomeRepository).findByUuid(BUSINESS_INCOME_ID);
    doReturn(new ArrayList<>()).when(businessIncomeRepository)
        .findByLoanApplicationIdAndBusinessTypeIn(LOAN_ID,
            Arrays.asList(BusinessTypeEnum.ENTERPRISE));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(BUSINESS_INCOME_ID);

    businessIncomeService.deleteByUuid(BUSINESS_INCOME_ID, ClientTypeEnum.CMS);
    verify(businessIncomeRepository, times(1)).delete(entity);
  }

  @Test
  void givenValidInput_ThenDeleteByUuidCMSOther_shouldReturnSuccess() throws IOException {
    BusinessIncomeEntity entity = BusinessIncomeEntity.builder()
        .uuid(BUSINESS_INCOME_ID)
        .loanApplicationId(LOAN_ID)
        .businessType(BusinessTypeEnum.WITH_REGISTRATION)
        .build();
    List<BusinessIncomeEntity> businessIncomeEntities = Arrays.asList(entity);

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);

    doReturn(Optional.of(entity)).when(businessIncomeRepository).findByUuid(BUSINESS_INCOME_ID);
    doReturn(new ArrayList<>()).when(businessIncomeRepository)
        .findByLoanApplicationIdAndBusinessTypeIn(LOAN_ID,
            Arrays.asList(BusinessTypeEnum.WITH_REGISTRATION,
                BusinessTypeEnum.WITHOUT_REGISTRATION));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(BUSINESS_INCOME_ID);

    businessIncomeService.deleteByUuid(BUSINESS_INCOME_ID, ClientTypeEnum.CMS);
    verify(businessIncomeRepository, times(1)).delete(entity);
  }

  @Test
  void givenValidInput_ThenFindByLoanApplicationId_shouldReturnSuccess() {
    businessIncomeService.findByLoanApplicationId(LOAN_ID);
    verify(businessIncomeRepository, times(1)).findByLoanApplicationId(LOAN_ID);
  }

}
