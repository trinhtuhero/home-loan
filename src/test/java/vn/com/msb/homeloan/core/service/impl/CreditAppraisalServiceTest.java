package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.DebtPaymentMethodCWIEnum;
import vn.com.msb.homeloan.core.constant.FormOfCreditEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.entity.CreditAppraisalEntity;
import vn.com.msb.homeloan.core.entity.CssEntity;
import vn.com.msb.homeloan.core.entity.ExceptionItemEntity;
import vn.com.msb.homeloan.core.entity.FieldSurveyItemEntity;
import vn.com.msb.homeloan.core.entity.OrganizationEntity;
import vn.com.msb.homeloan.core.model.CmsUser;
import vn.com.msb.homeloan.core.model.CreditAppraisal;
import vn.com.msb.homeloan.core.model.CreditworthinessItem;
import vn.com.msb.homeloan.core.model.ExceptionItem;
import vn.com.msb.homeloan.core.model.FieldSurveyItem;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.repository.CmsUserRepository;
import vn.com.msb.homeloan.core.repository.CreditAppraisalRepository;
import vn.com.msb.homeloan.core.repository.CreditInstitutionRepository;
import vn.com.msb.homeloan.core.repository.CreditworthinessItemRepository;
import vn.com.msb.homeloan.core.repository.CssRepository;
import vn.com.msb.homeloan.core.repository.ExceptionItemRepository;
import vn.com.msb.homeloan.core.repository.FieldSurveyItemRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.repository.OrganizationRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CmsUserService;
import vn.com.msb.homeloan.core.service.CollateralService;
import vn.com.msb.homeloan.core.service.CommonIncomeService;
import vn.com.msb.homeloan.core.service.CreditAppraisalService;
import vn.com.msb.homeloan.core.service.CreditworthinessItemService;
import vn.com.msb.homeloan.core.service.LoanApplicationItemService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

@ExtendWith(MockitoExtension.class)
public class CreditAppraisalServiceTest {

  CreditAppraisalService creditAppraisalService;

  @Mock
  CreditAppraisalRepository creditAppraisalRepository;

  @Mock
  FieldSurveyItemRepository fieldSurveyItemRepository;

  @Mock
  CreditworthinessItemRepository creditworthinessItemRepository;

  @Mock
  ExceptionItemRepository exceptionItemRepository;

  @Mock
  LoanApplicationService loanApplicationService;

  @Mock
  CollateralService collateralService;

  @Mock
  HomeLoanUtil homeLoanUtil;

  @Mock
  LoanPayerRepository loanPayerRepository;

  @Mock
  CreditInstitutionRepository creditInstitutionRepository;

  @Mock
  CMSTabActionService cmsTabActionService;
  @Mock
  CmsUserService cmsUserService;

  @Mock
  OrganizationRepository organizationRepository;

  @Mock
  CommonIncomeService commonIncomeService;

  @Mock
  LoanApplicationItemService loanApplicationItemService;

  @Mock
  CreditworthinessItemService creditworthinessItemService;

  @Mock
  CssRepository cssRepository;

  @Mock
  CmsUserRepository cmsUserRepository;

  @BeforeEach
  void setUp() {
    this.creditAppraisalService = new CreditAppraisalServiceImpl(
        creditAppraisalRepository,
        fieldSurveyItemRepository,
        creditworthinessItemRepository,
        exceptionItemRepository,
        loanPayerRepository,
        creditInstitutionRepository,
        homeLoanUtil,
        loanApplicationService,
        collateralService,
        cmsTabActionService,
        cmsUserService,
        organizationRepository,
        commonIncomeService,
        loanApplicationItemService,
        creditworthinessItemService,
        cssRepository,
        cmsUserRepository);
  }

  private final String LOAN_ID = "LOAN_ID";
  private final String FIELD_SURVEY_ITEM_ID = "FIELD_SURVEY_ITEM_ID";
  private final String CSS_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  @Test
  void givenValidInput_ThenSaveInsert_shouldReturnSuccess() {
    CreditAppraisalEntity creditAppraisalEntity = new CreditAppraisalEntity();
    creditAppraisalEntity.setLoanApplicationId(LOAN_ID);
    LoanApplication loanApplication = new LoanApplication();

    CreditAppraisal creditAppraisal = new CreditAppraisal();
    creditAppraisal.setLoanApplicationId(LOAN_ID);

    doReturn(Optional.empty()).when(creditAppraisalRepository).findByLoanApplicationId(any());

    doReturn(Arrays.asList(new FieldSurveyItemEntity())).when(fieldSurveyItemRepository)
        .findByLoanApplicationId(LOAN_ID);

    doReturn(Arrays.asList(new ExceptionItemEntity())).when(exceptionItemRepository)
        .findByLoanApplicationId(LOAN_ID);

    doReturn(creditAppraisalEntity).when(creditAppraisalRepository).save(any());

    doReturn(loanApplication).when(loanApplicationService).findById(any());

    //doReturn(0L).when(collateralService).totalValues(any());

    //doReturn(0L).when(collateralService).totalGuaranteedValues(any());

    CreditAppraisal creditAppraisal1 = creditAppraisalService.save(creditAppraisal);

    assertTrue(creditAppraisal1.getFieldSurveyItems().size() == 1);
  }

  @Test
  void givenValidInput_ThenSaveUpdate_shouldReturnSuccess() {
    CreditAppraisalEntity creditAppraisalEntity = new CreditAppraisalEntity();
    creditAppraisalEntity.setLoanApplicationId(LOAN_ID);
    LoanApplication loanApplication = new LoanApplication();

    CreditAppraisal creditAppraisal = new CreditAppraisal();
    creditAppraisal.setLoanApplicationId(LOAN_ID);

    doReturn(Optional.of(CreditAppraisalEntity.builder()
        .uuid(LOAN_ID).loanApplicationId(LOAN_ID).build())).when(creditAppraisalRepository)
        .findByLoanApplicationId(any());

    doReturn(Arrays.asList(new FieldSurveyItemEntity())).when(fieldSurveyItemRepository)
        .findByLoanApplicationId(LOAN_ID);

    doReturn(Arrays.asList(new ExceptionItemEntity())).when(exceptionItemRepository)
        .findByLoanApplicationId(LOAN_ID);

    doReturn(creditAppraisalEntity).when(creditAppraisalRepository).save(any());

    doReturn(loanApplication).when(loanApplicationService).findById(any());

    //doReturn(0L).when(collateralService).totalValues(any());

    //doReturn(0L).when(collateralService).totalGuaranteedValues(any());

    CreditAppraisal creditAppraisal1 = creditAppraisalService.save(creditAppraisal);

    assertTrue(creditAppraisal1.getFieldSurveyItems().size() == 1);
  }

  @Test
  void givenValidInput_ThenSaveUpdate_shouldReturnFailProvinceInvalid() {

    FieldSurveyItem fieldSurveyItem = new FieldSurveyItem();

    doReturn(null).when(homeLoanUtil).getProvinceNameByCode(any());

    Exception exception = assertThrows(Exception.class, () -> {
      Method privateMethod = CreditAppraisalServiceImpl.class.getDeclaredMethod("setMasterData",
          FieldSurveyItem.class);
      privateMethod.setAccessible(true);
      privateMethod.invoke(creditAppraisalService, fieldSurveyItem);

    });

    assertTrue(exception != null);
  }

  @Test
  void givenValidInput_ThenSaveUpdate_shouldReturnSuccessMonthlyDebtPaymentAutoEquals0() {
    CreditAppraisalEntity creditAppraisalEntity = new CreditAppraisalEntity();
    creditAppraisalEntity.setLoanApplicationId(LOAN_ID);
    LoanApplication loanApplication = new LoanApplication();

    CreditAppraisal creditAppraisal = new CreditAppraisal();
    creditAppraisal.setLoanApplicationId(LOAN_ID);

    doReturn(Optional.empty()).when(creditAppraisalRepository).findByLoanApplicationId(any());

    doReturn(Arrays.asList(new FieldSurveyItemEntity())).when(fieldSurveyItemRepository)
        .findByLoanApplicationId(LOAN_ID);

    doReturn(Arrays.asList(new ExceptionItemEntity())).when(exceptionItemRepository)
        .findByLoanApplicationId(LOAN_ID);

    doReturn(creditAppraisalEntity).when(creditAppraisalRepository).save(any());

    doReturn(loanApplication).when(loanApplicationService).findById(any());

    //doReturn(0L).when(collateralService).totalValues(any());

    //doReturn(0L).when(collateralService).totalGuaranteedValues(any());

    CreditAppraisal creditAppraisal1 = creditAppraisalService.save(creditAppraisal);

    assertTrue(creditAppraisal1.getFieldSurveyItems().size() == 1);
  }

  @Test
  void givenValidInput_ThenSaveUpdate_shouldReturnFailDistrictInvalid() {
    FieldSurveyItem fieldSurveyItem = new FieldSurveyItem();

    doReturn("Test").when(homeLoanUtil).getProvinceNameByCode(any());

    doReturn(null).when(homeLoanUtil).getDistrictNameByCode(any());

    Exception exception = assertThrows(Exception.class, () -> {
      Method privateMethod = CreditAppraisalServiceImpl.class.getDeclaredMethod("setMasterData",
          FieldSurveyItem.class);
      privateMethod.setAccessible(true);
      privateMethod.invoke(creditAppraisalService, fieldSurveyItem);

    });

    assertTrue(exception != null);
  }

  @Test
  void givenValidInput_ThenSaveUpdate_shouldReturnFailWardInvalid() {
    FieldSurveyItem fieldSurveyItem = new FieldSurveyItem();

    doReturn("Test").when(homeLoanUtil).getProvinceNameByCode(any());
    doReturn("Test").when(homeLoanUtil).getDistrictNameByCode(any());
    doReturn(null).when(homeLoanUtil).getWardNameByCode(any());

    Exception exception = assertThrows(Exception.class, () -> {
      Method privateMethod = CreditAppraisalServiceImpl.class.getDeclaredMethod("setMasterData",
          FieldSurveyItem.class);
      privateMethod.setAccessible(true);
      privateMethod.invoke(creditAppraisalService, fieldSurveyItem);

    });

    assertTrue(exception != null);
  }

  @Test
  void givenValidInput_ThenGetByLoanId_shouldReturnSuccess() {
    try (MockedStatic<AuthorizationUtil> mockedStatic = Mockito.mockStatic(
        AuthorizationUtil.class)) {
      LoanApplication loanApplication = new LoanApplication();

      doReturn(loanApplication).when(loanApplicationService).findById(LOAN_ID);

      mockedStatic.when(AuthorizationUtil::getEmail).thenReturn("a");

      CmsUser cmsUser = new CmsUser();
      cmsUser.setBranchCode("11000");
      cmsUser.setLeaderEmail("emailLeader");

      doReturn(cmsUser).when(cmsUserService).findByEmail("a");
      CreditAppraisalEntity creditAppraisalEntity = new CreditAppraisalEntity();
      creditAppraisalEntity.setLoanApplicationId(LOAN_ID);

      doReturn(OrganizationEntity.builder().type("AREA").build()).when(organizationRepository)
          .findByCode(any());

      doReturn(Optional.of(creditAppraisalEntity)).when(creditAppraisalRepository)
          .findByLoanApplicationId(any());

      CssEntity cssEntity = CssEntity.builder()
          .uuid(CSS_ID)
          .loanApplicationId(LOAN_ID).build();
      doReturn(cssEntity).when(cssRepository).findByLoanApplicationId(LOAN_ID);

      CreditAppraisal creditAppraisal = creditAppraisalService.getByLoanId(LOAN_ID);

      assertTrue(creditAppraisal.getFieldSurveyItems().size() == 0);
    }
  }

  @Test
  void givenInputChildrentHaveParent_thenGetParent_shouldReturnParent() {
    String rs = "MSB AREA";
    try (MockedStatic<AuthorizationUtil> mockedStatic = Mockito.mockStatic(
        AuthorizationUtil.class)) {
      LoanApplication loanApplication = new LoanApplication();
      doReturn(loanApplication).when(loanApplicationService).findById(LOAN_ID);

      mockedStatic.when(AuthorizationUtil::getEmail).thenReturn("a");

      CmsUser cmsUser = new CmsUser();
      cmsUser.setBranchCode("11000");
      cmsUser.setLeaderEmail("emailLeader");

      doReturn(cmsUser).when(cmsUserService).findByEmail("a");
      doReturn(Optional.ofNullable(null)).when(creditAppraisalRepository)
          .findByLoanApplicationId(any());
      OrganizationEntity childrent = OrganizationEntity.builder().code("00").areaCode("01").type("")
          .build();
      OrganizationEntity parentV1 = OrganizationEntity.builder().code("01").areaCode("02").type("")
          .build();
      OrganizationEntity parentV2 = OrganizationEntity.builder().code("02").areaCode("03").name(rs)
          .type("AREA").build();
      doReturn(childrent).when(organizationRepository).findByCode(any());
      doReturn(parentV1).when(organizationRepository).findByCode(childrent.getAreaCode());
      doReturn(parentV2).when(organizationRepository).findByCode(parentV1.getAreaCode());

      CssEntity cssEntity = CssEntity.builder()
          .uuid(CSS_ID)
          .loanApplicationId(LOAN_ID).build();
      doReturn(cssEntity).when(cssRepository).findByLoanApplicationId(LOAN_ID);
      assertEquals(parentV2.getCode(),
          creditAppraisalService.getByLoanId(LOAN_ID).getBusinessArea());
    }
  }

  @Test
  void givenInputChildrentNotHaveParent_thenGetParent_shouldReturnNull() {
    try (MockedStatic<AuthorizationUtil> mockedStatic = Mockito.mockStatic(
        AuthorizationUtil.class)) {
      LoanApplication loanApplication = new LoanApplication();
      doReturn(loanApplication).when(loanApplicationService).findById(LOAN_ID);

      mockedStatic.when(AuthorizationUtil::getEmail).thenReturn("a");

      CmsUser cmsUser = new CmsUser();
      cmsUser.setBranchCode("11000");
      cmsUser.setLeaderEmail("emailLeader");

      doReturn(cmsUser).when(cmsUserService).findByEmail("a");
      doReturn(Optional.ofNullable(null)).when(creditAppraisalRepository)
          .findByLoanApplicationId(any());
      OrganizationEntity childrent = OrganizationEntity.builder().code("00").areaCode("01").type("")
          .build();
      doReturn(childrent).when(organizationRepository).findByCode(any());
      doReturn(null).when(organizationRepository).findByCode(childrent.getAreaCode());

      CssEntity cssEntity = CssEntity.builder()
          .uuid(CSS_ID)
          .loanApplicationId(LOAN_ID).build();
      doReturn(cssEntity).when(cssRepository).findByLoanApplicationId(LOAN_ID);
      String rs = creditAppraisalService.getByLoanId(LOAN_ID).getBusinessArea();
      assertNull(rs);
    }
  }

  private List<ExceptionItem> buildExceptionItems() {
    List<ExceptionItem> exceptionItems = new ArrayList<>();
    ExceptionItem item1 = ExceptionItem.builder()
        .loanApplicationId(LOAN_ID)
        .build();
    exceptionItems.add(item1);
    return exceptionItems;
  }

  private List<CreditworthinessItem> buildCreditworthinessItems() {
    List<CreditworthinessItem> creditworthinessItems = new ArrayList<>();
    CreditworthinessItem item1 = CreditworthinessItem.builder()
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .loanApplicationId(LOAN_ID)
        .interestRate(32.0)
        .remainingPeriod(55)
        .currentBalance(7999999L)
        .firstLimit(86566556L)
        .build();
    creditworthinessItems.add(item1);
    return creditworthinessItems;
  }

  private List<CreditworthinessItem> buildCreditworthinessItems2() {
    List<CreditworthinessItem> creditworthinessItems = new ArrayList<>();
    CreditworthinessItem item1 = CreditworthinessItem.builder()
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .loanApplicationId(LOAN_ID)
        .formOfCredit(FormOfCreditEnum.UNSECURED_MORTGAGE)
        .build();
    creditworthinessItems.add(item1);
    return creditworthinessItems;
  }

  private List<CreditworthinessItem> buildCreditworthinessItems3() {
    List<CreditworthinessItem> creditworthinessItems = new ArrayList<>();
    CreditworthinessItem item1 = CreditworthinessItem.builder()
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .loanApplicationId(LOAN_ID)
        .currentBalance(111L)
        .firstLimit(4444L)
        .formOfCredit(FormOfCreditEnum.UNSECURED_MORTGAGE)
        .interestRate(3333.0)
        .firstPeriod(1111)
        .remainingPeriod(22222)
        .debtPaymentMethod(DebtPaymentMethodCWIEnum.DEBT_PAYMENT_1)
        .build();
    creditworthinessItems.add(item1);
    return creditworthinessItems;
  }

  private List<CreditworthinessItem> buildCreditworthinessItems4() {
    List<CreditworthinessItem> creditworthinessItems = new ArrayList<>();
    CreditworthinessItem item1 = CreditworthinessItem.builder()
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .loanApplicationId(LOAN_ID)
        .debtPaymentMethod(DebtPaymentMethodCWIEnum.DEBT_PAYMENT_1)
        .monthlyDebtPaymentAuto(0L)
        .build();
    creditworthinessItems.add(item1);
    return creditworthinessItems;
  }

  private List<FieldSurveyItem> buildFieldSurveyItems() {
    List<FieldSurveyItem> fieldSurveyItems = new ArrayList<>();
    FieldSurveyItem item1 = FieldSurveyItem.builder()
        .loanApplicationId(LOAN_ID)
        .build();
    fieldSurveyItems.add(item1);
    return fieldSurveyItems;
  }
}
