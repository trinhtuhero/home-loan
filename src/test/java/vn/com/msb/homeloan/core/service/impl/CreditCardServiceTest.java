package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
import vn.com.msb.homeloan.core.constant.CardPriorityEnum;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.CreditCardObjectEnum;
import vn.com.msb.homeloan.core.constant.DebtDeductionRateEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.CardPolicyEntity;
import vn.com.msb.homeloan.core.entity.CardTypeEntity;
import vn.com.msb.homeloan.core.entity.CreditCardEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CreditCard;
import vn.com.msb.homeloan.core.model.mapper.CreditCardMapper;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.repository.CardPolicyRepository;
import vn.com.msb.homeloan.core.repository.CardTypeRepository;
import vn.com.msb.homeloan.core.repository.CreditCardRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationItemRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.service.CreditCardService;
import vn.com.msb.homeloan.core.service.CreditworthinessItemService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceTest {

  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID_OTHER = "03f9e024-7ec4-4ed3-8f1f-330d232b639a";
  private final String CREDIT_CARD_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b63aa";
  private final String CREDIT_CARD_ID_OTHER = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String CARD_POLICY_CODE = "CARD_POLICY_CODE";
  private final String CARD_TYPE_CODE = "CARD_TYPE_CODE";

  CreditCardService creditCardService;

  @Mock
  CreditCardRepository creditCardRepository;
  @Mock
  LoanApplicationService loanApplicationService;
  @Mock
  LoanApplicationRepository loanApplicationRepository;
  @Mock
  CardPolicyRepository cardPolicyRepository;
  @Mock
  CardTypeRepository cardTypeRepository;
  @Mock
  CreditworthinessItemService creditworthinessItemService;
  @Mock
  LoanApplicationItemRepository loanApplicationItemRepository;

  @BeforeEach
  void setUp() {
    this.creditCardService = new CreditCardServiceImpl(
        creditCardRepository,
        loanApplicationService,
        loanApplicationRepository,
        cardPolicyRepository,
        cardTypeRepository,
        creditworthinessItemService,
        loanApplicationItemRepository);
  }

  @Test
  void givenValidInput_ThenSaveLDP_shouldReturnSuccess() throws IOException {
    CreditCard creditCard = CreditCard.builder()
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type(CARD_TYPE_CODE)
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();

    doReturn(null).when(creditCardRepository).findByLoanId(LOAN_ID);

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    CardTypeEntity cardTypeEntity = CardTypeEntity.builder()
        .code(CARD_TYPE_CODE)
        .name("name").build();
    doReturn(Optional.of(cardTypeEntity)).when(cardTypeRepository).findById(CARD_TYPE_CODE);

    CreditCard result = creditCardService.save(creditCard, ClientTypeEnum.LDP);

    assertEquals(result.getLoanId(), LOAN_ID);
  }

  @Test
  void givenValidInput_ThenSaveLDP_shouldReturnSuccessParentId() throws IOException {
    CreditCardEntity creditCardEntity = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type(CARD_TYPE_CODE)
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();

    CreditCard creditCard = CreditCard.builder()
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.SECONDARY_CARD)
        .type(CARD_TYPE_CODE)
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(Arrays.asList(creditCardEntity)).when(creditCardRepository).findByLoanId(LOAN_ID);

    CreditCard result = creditCardService.save(creditCard, ClientTypeEnum.LDP);

    assertEquals(result.getLoanId(), LOAN_ID);
  }

  @Test
  void givenValidInput_ThenSaveLDP_shouldReturnFailMainCreditCardAlreadyExist() {
    CreditCard creditCard = CreditCard.builder()
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type(CARD_TYPE_CODE)
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();

    CreditCardEntity creditCardEntity = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();
    List<CreditCardEntity> creditCardEntities = new ArrayList<>();
    creditCardEntities.add(creditCardEntity);

    doReturn(creditCardEntities).when(creditCardRepository).findByLoanId(LOAN_ID);

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    CardTypeEntity cardTypeEntity = CardTypeEntity.builder()
        .code(CARD_TYPE_CODE)
        .name("name").build();
    doReturn(Optional.of(cardTypeEntity)).when(cardTypeRepository).findById(CARD_TYPE_CODE);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      creditCardService.save(creditCard, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(),
        ErrorEnum.MAIN_CREDIT_CARD_ALREADY_EXIST.getCode());
  }

  @Test
  void givenValidInput_ThenSaveLDP_shouldReturnFailExceedNumberCreditCard() {
    CreditCard creditCard = CreditCard.builder()
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.SECONDARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();

    CreditCardEntity creditCardEntity = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();
    CreditCardEntity creditCardEntity2 = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.SECONDARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();
    CreditCardEntity creditCardEntity3 =
        CreditCardEntity.builder()
            .uuid(CREDIT_CARD_ID)
            .loanId(LOAN_ID)
            .cardPriority(CardPriorityEnum.SECONDARY_CARD)
            .type("MASTER_BLUE")
            .creditLimit(1111L)
            .object(CreditCardObjectEnum.CUSTOMER.getCode())
            .cardPolicyCode("cardPolicyCode")
            .timeLimit(23)
            .firstPrimarySchool("đời")
            .autoDebit(true)
            .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
            .debitAccountNumber("123456789")
            .receivingAddress("receivingAddress")
            .build();
    CreditCardEntity creditCardEntity4 = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.SECONDARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();
    List<CreditCardEntity> creditCardEntities = new ArrayList<>();
    creditCardEntities.add(creditCardEntity);
    creditCardEntities.add(creditCardEntity2);
    creditCardEntities.add(creditCardEntity3);
    creditCardEntities.add(creditCardEntity4);

    doReturn(creditCardEntities).when(creditCardRepository).findByLoanId(LOAN_ID);

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      creditCardService.save(creditCard, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.EXCEED_NUMBER_CREDIT_CARD.getCode());
  }

  @Test
  void givenValidInput_ThenSaveLDP_shouldReturnFailCardLimitError() {
    CreditCard creditCard = CreditCard.builder()
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.SECONDARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(11111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();

    CreditCardEntity creditCardEntity = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();
    List<CreditCardEntity> creditCardEntities = new ArrayList<>();
    creditCardEntities.add(creditCardEntity);

    doReturn(creditCardEntities).when(creditCardRepository).findByLoanId(LOAN_ID);

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      creditCardService.save(creditCard, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.CARD_LIMIT_ERROR.getCode());
  }

  @Test
  void givenValidInput_ThenSaveLDP_shouldReturnFailCardLimitErrorWithParentId() {
    CreditCard creditCard = CreditCard.builder()
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.SECONDARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(11111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress")
        .parentId(CREDIT_CARD_ID).build();

    CreditCardEntity creditCardEntity = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();
    List<CreditCardEntity> creditCardEntities = new ArrayList<>();
    creditCardEntities.add(creditCardEntity);

    doReturn(creditCardEntities).when(creditCardRepository).findByLoanId(LOAN_ID);

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      creditCardService.save(creditCard, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.CARD_LIMIT_ERROR.getCode());
  }

  @Test
  void givenValidInput_ThenSaveLDP_shouldReturnFailPrimaryCardNotExist() {
    CreditCard creditCard = CreditCard.builder()
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.SECONDARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(11111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress")
        .parentId(CREDIT_CARD_ID).build();

    doReturn(null).when(creditCardRepository).findByLoanId(LOAN_ID);

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      creditCardService.save(creditCard, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.PRIMARY_CARD_NOT_EXIST.getCode());
  }

  @Test
  void givenValidInput_ThenSaveLDP_shouldReturnFailInvalidForm() {
    CreditCard creditCard = CreditCard.builder()
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type(null)
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool(null)
        .autoDebit(true)
        .receivingAddress(null).build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      creditCardService.save(creditCard, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.INVALID_FORM.getCode());
  }

  @Test
  void givenValidInput_ThenSaveLDP_shouldReturnFailInvalidForm2() {
    CreditCard creditCard = CreditCard.builder()
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type(CARD_TYPE_CODE)
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool(null)
        .autoDebit(null)
        .receivingAddress(null).build();

    CardTypeEntity cardTypeEntity = CardTypeEntity.builder()
        .code(CARD_TYPE_CODE)
        .name("name").build();
    doReturn(Optional.of(cardTypeEntity)).when(cardTypeRepository).findById(CARD_TYPE_CODE);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      creditCardService.save(creditCard, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.INVALID_FORM.getCode());
  }

  @Test
  void givenValidInput_ThenSaveCMS_shouldReturnSuccess() throws IOException {
    CreditCard creditCard = CreditCard.builder()
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type(CARD_TYPE_CODE)
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode(CARD_POLICY_CODE)
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(false)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();

    doReturn(null).when(creditCardRepository).findByLoanId(LOAN_ID);

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    CardPolicyEntity cardPolicyEntity = CardPolicyEntity.builder()
        .code(CARD_POLICY_CODE)
        .name("name").build();
    doReturn(Optional.of(cardPolicyEntity)).when(cardPolicyRepository).findByCode(CARD_POLICY_CODE);

    CardTypeEntity cardTypeEntity = CardTypeEntity.builder()
        .code(CARD_TYPE_CODE)
        .name("name").build();
    doReturn(Optional.of(cardTypeEntity)).when(cardTypeRepository).findById(CARD_TYPE_CODE);

    CreditCard result = creditCardService.save(creditCard, ClientTypeEnum.CMS);

    assertEquals(result.getLoanId(), LOAN_ID);
  }

  @Test
  void givenValidInput_ThenSaveCMS_shouldReturnFailInvalidForm() {
    CreditCard creditCard = CreditCard.builder()
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type(CARD_TYPE_CODE)
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode(null)
        .timeLimit(null)
        .firstPrimarySchool("đời")
        .autoDebit(false)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();

    CreditCardEntity creditCardEntity = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();
    List<CreditCardEntity> creditCardEntities = new ArrayList<>();
    creditCardEntities.add(creditCardEntity);

    CardTypeEntity cardTypeEntity = CardTypeEntity.builder()
        .code(CARD_TYPE_CODE)
        .name("name").build();
    doReturn(Optional.of(cardTypeEntity)).when(cardTypeRepository).findById(CARD_TYPE_CODE);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      creditCardService.save(creditCard, ClientTypeEnum.CMS);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.INVALID_FORM.getCode());
  }

  @Test
  void givenValidInput_ThenSaveCMS_shouldReturnFailCardPolicyCodeNotExist() {
    CreditCard creditCard = CreditCard.builder()
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(null)
        .firstPrimarySchool("đời")
        .autoDebit(false)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();

    CreditCardEntity creditCardEntity = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();
    List<CreditCardEntity> creditCardEntities = new ArrayList<>();
    creditCardEntities.add(creditCardEntity);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      creditCardService.save(creditCard, ClientTypeEnum.CMS);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.CARD_POLICY_CODE_NOT_EXIST.getCode());
  }

  @Test
  void givenValidInput_ThenSaveCMS_shouldReturnFailResourceNotFound() {
    CreditCard creditCard = CreditCard.builder()
        .uuid(CREDIT_CARD_ID_OTHER)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.SECONDARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode(CARD_POLICY_CODE)
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();

    CreditCardEntity creditCardEntity = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();
    List<CreditCardEntity> creditCardEntities = new ArrayList<>();
    creditCardEntities.add(creditCardEntity);

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      creditCardService.save(creditCard, ClientTypeEnum.CMS);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenSaveCMS_shouldReturnFailLoanApplicationIdCanNotChange() {
    CreditCardEntity creditCardEntity = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type(CARD_TYPE_CODE)
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode(CARD_POLICY_CODE)
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();
    List<CreditCardEntity> creditCardEntities = new ArrayList<>();
    creditCardEntities.add(creditCardEntity);

    doReturn(Optional.of(creditCardEntity)).when(creditCardRepository).findByUuid(CREDIT_CARD_ID);

    CreditCard creditCard = CreditCardMapper.INSTANCE.toModels(creditCardEntity);
    creditCard.setLoanId(LOAN_ID_OTHER);

    CardPolicyEntity cardPolicyEntity = CardPolicyEntity.builder()
        .code(CARD_POLICY_CODE)
        .name("name").build();
    doReturn(Optional.of(cardPolicyEntity)).when(cardPolicyRepository).findByCode(CARD_POLICY_CODE);

    CardTypeEntity cardTypeEntity = CardTypeEntity.builder()
        .code(CARD_TYPE_CODE)
        .name("name").build();
    doReturn(Optional.of(cardTypeEntity)).when(cardTypeRepository).findById(CARD_TYPE_CODE);

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID_OTHER));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID_OTHER);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      creditCardService.save(creditCard, ClientTypeEnum.CMS);
    });

    assertEquals(exception.getCode().intValue(),
        ErrorEnum.LOAN_APPLICATION_ID_CAN_NOT_CHANGE.getCode());
  }

  @Test
  void givenValidInput_ThenDelete_shouldReturnSuccess() throws IOException {
    CreditCardEntity creditCardEntity = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();
    CreditCardEntity creditCardEntity2 = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID_OTHER)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.SECONDARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();

    List<CreditCardEntity> creditCardEntities = new ArrayList<>();
    creditCardEntities.add(creditCardEntity);
    creditCardEntities.add(creditCardEntity2);

    doReturn(Arrays.asList(creditCardEntity2)).when(creditCardRepository)
        .findByLoanIdAndCardPriority(LOAN_ID, CardPriorityEnum.SECONDARY_CARD);
    doReturn(Optional.of(creditCardEntity)).when(creditCardRepository).findByUuid(CREDIT_CARD_ID);

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    doNothing().when(creditworthinessItemService).deleteByConditions(any());

    creditCardService.deleteByUuid(CREDIT_CARD_ID, ClientTypeEnum.LDP);
    verify(creditCardRepository, times(1)).deleteAll(creditCardEntities);
  }

  @Test
  void givenValidInput_ThenDelete_shouldReturnSuccessParentId() throws IOException {
    CreditCardEntity creditCardEntity = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress").build();
    CreditCardEntity creditCardEntity2 = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID_OTHER)
        .loanId(LOAN_ID)
        .cardPriority(CardPriorityEnum.SECONDARY_CARD)
        .type("MASTER_BLUE")
        .creditLimit(1111L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .cardPolicyCode("cardPolicyCode")
        .timeLimit(23)
        .firstPrimarySchool("đời")
        .autoDebit(true)
        .debtDeductionRate(DebtDeductionRateEnum.STATEMENT_BALANCE)
        .debitAccountNumber("123456789")
        .receivingAddress("receivingAddress")
        .parentId(CREDIT_CARD_ID).build();

    List<CreditCardEntity> creditCardEntities = new ArrayList<>();
    creditCardEntities.add(creditCardEntity);
    creditCardEntities.add(creditCardEntity2);

    doReturn(Arrays.asList(creditCardEntity2)).when(creditCardRepository)
        .findByLoanIdAndCardPriorityAndParentId(LOAN_ID, CardPriorityEnum.SECONDARY_CARD,
            CREDIT_CARD_ID);
    doReturn(Optional.of(creditCardEntity)).when(creditCardRepository).findByUuid(CREDIT_CARD_ID);

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    doNothing().when(creditworthinessItemService).deleteByConditions(any());

    creditCardService.deleteByUuid(CREDIT_CARD_ID, ClientTypeEnum.LDP);
    verify(creditCardRepository, times(1)).deleteAll(creditCardEntities);
  }
}
