package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import vn.com.msb.homeloan.core.constant.CardPriorityEnum;
import vn.com.msb.homeloan.core.constant.DebtPaymentMethodEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.FormOfCreditEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.entity.CreditAppraisalEntity;
import vn.com.msb.homeloan.core.entity.CreditCardEntity;
import vn.com.msb.homeloan.core.entity.CreditworthinessItemEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationItemEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CreditworthinessItem;
import vn.com.msb.homeloan.core.model.mapper.CreditworthinessItemMapper;
import vn.com.msb.homeloan.core.repository.CreditAppraisalRepository;
import vn.com.msb.homeloan.core.repository.CreditCardRepository;
import vn.com.msb.homeloan.core.repository.CreditworthinessItemRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationItemRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.repository.OverdraftRepository;
import vn.com.msb.homeloan.core.service.CreditworthinessItemService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;

@ExtendWith(MockitoExtension.class)
class CreditworthinessItemServiceTest {

  private final String CREDITWORTHINESS_ITEM_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String CREDITWORTHINESS_ITEM_ID_2 = "03f9e024-7ec4-4ed3-8f1f-330d232b639q";
  private final String CREDITWORTHINESS_ITEM_ID_3 = "03f9e024-7ec4-4ed3-8f1f-330d232b639w";
  private final String CREDITWORTHINESS_ITEM_ID_4 = "03f9e024-7ec4-4ed3-8f1f-330d232b639e";
  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b63aa";
  private final String LOAN_APPLICATION_ITEM_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b63aa";
  private final String CREDIT_APPRAISAL_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String CREDIT_CARD_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b63aa";

  CreditworthinessItemService creditworthinessItemService;

  @Mock
  CreditworthinessItemRepository creditworthinessItemRepository;
  @Mock
  LoanApplicationItemRepository loanApplicationItemRepository;
  @Mock
  CreditCardRepository creditCardRepository;
  @Mock
  LoanApplicationRepository loanApplicationRepository;
  @Mock
  CreditAppraisalRepository creditAppraisalRepository;
  @Mock
  LoanPayerRepository loanPayerRepository;

  @Mock
  LoanApplicationService loanApplicationService;

  @Mock
  OverdraftRepository overdraftRepository;

  @BeforeEach
  void setUp() {
    this.creditworthinessItemService = new CreditworthinessItemServiceImpl(
        creditworthinessItemRepository,
        loanApplicationItemRepository,
        creditCardRepository,
        loanApplicationRepository,
        creditAppraisalRepository,
        loanPayerRepository,
        loanApplicationService,
        overdraftRepository
    );
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnSuccess() {
    CreditworthinessItem creditworthinessItem = CreditworthinessItem.builder()
        .uuid(CREDITWORTHINESS_ITEM_ID)
        .loanApplicationId(LOAN_ID)
        .creditAppraisalId(CREDIT_APPRAISAL_ID)
        .formOfCredit(FormOfCreditEnum.CREDIT_CARD)
        .firstLimit(10000L).build();
    doReturn(CreditworthinessItemMapper.INSTANCE.toEntity(creditworthinessItem)).when(
        creditworthinessItemRepository).save(any());

    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);

    CreditworthinessItem result = creditworthinessItemService.save(creditworthinessItem);
    assertEquals(result.getUuid(), CREDITWORTHINESS_ITEM_ID);
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnSuccess1() {
    CreditworthinessItem creditworthinessItem = CreditworthinessItem.builder()
        .uuid(CREDITWORTHINESS_ITEM_ID)
        .loanApplicationId(LOAN_ID)
        .creditAppraisalId(CREDIT_APPRAISAL_ID)
        .formOfCredit(FormOfCreditEnum.CREDIT_CARD)
        .loanApplicationItemId(LOAN_APPLICATION_ITEM_ID)
        .firstLimit(10000L).build();
    doReturn(Optional.of(
        LoanApplicationItemEntity.builder().uuid(LOAN_APPLICATION_ITEM_ID).build())).when(
        loanApplicationItemRepository).findById(creditworthinessItem.getLoanApplicationItemId());

    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);

    CreditworthinessItem result = creditworthinessItemService.save(creditworthinessItem);
    assertEquals(result.getUuid(), CREDITWORTHINESS_ITEM_ID);
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnSuccess2() {
    CreditworthinessItem creditworthinessItem = CreditworthinessItem.builder()
        .uuid(CREDITWORTHINESS_ITEM_ID)
        .loanApplicationId(LOAN_ID)
        .creditAppraisalId(CREDIT_APPRAISAL_ID)
        .formOfCredit(FormOfCreditEnum.CREDIT_CARD)
        .creditCardId(CREDIT_CARD_ID)
        .firstLimit(10000L).build();
    doReturn(Optional.of(CreditCardEntity.builder().uuid(CREDIT_CARD_ID).build())).when(
        creditCardRepository).findById(creditworthinessItem.getCreditCardId());

    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);

    CreditworthinessItem result = creditworthinessItemService.save(creditworthinessItem);
    assertEquals(result.getUuid(), CREDITWORTHINESS_ITEM_ID);
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnFailItemLimit() {
    CreditworthinessItem creditworthinessItem = CreditworthinessItem.builder()
        .loanApplicationId(LOAN_ID)
        .formOfCredit(FormOfCreditEnum.CREDIT_CARD)
        .firstLimit(10000L).build();
    doReturn(14).when(creditworthinessItemRepository)
        .countByLoanApplicationIdAndLoanApplicationItemIdAndCreditCardId(LOAN_ID, null, null);

    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      creditworthinessItemService.save(creditworthinessItem);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.ITEM_LIMIT.getCode());
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnFail() {
    CreditworthinessItem creditworthinessItem = CreditworthinessItem.builder()
        .uuid(CREDITWORTHINESS_ITEM_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .creditAppraisalId(CREDIT_APPRAISAL_ID)
        .formOfCredit(FormOfCreditEnum.CREDIT_CARD).build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      creditworthinessItemService.save(creditworthinessItem);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_PAYER_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnFailInvalidForm() {
    CreditworthinessItem creditworthinessItem = CreditworthinessItem.builder()
        .uuid(CREDITWORTHINESS_ITEM_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .creditAppraisalId(CREDIT_APPRAISAL_ID)
        .formOfCredit(FormOfCreditEnum.SECURED_MORTGAGE).build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      creditworthinessItemService.save(creditworthinessItem);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.INVALID_FORM.getCode());
  }

  @Test
  void givenValidInput_ThenGetByLoanId_shouldReturnSuccess() {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);

    CreditAppraisalEntity creditAppraisalEntity = CreditAppraisalEntity.builder()
        .uuid(CREDIT_APPRAISAL_ID).build();
    doReturn(Optional.of(creditAppraisalEntity)).when(creditAppraisalRepository)
        .findByLoanApplicationId(LOAN_ID);

    CreditworthinessItemEntity creditworthinessItemEntity = CreditworthinessItemEntity.builder()
        .uuid(CREDITWORTHINESS_ITEM_ID)
        .loanApplicationId(LOAN_ID)
        .creditAppraisalId(CREDIT_APPRAISAL_ID)
        .loanApplicationItemId(LOAN_APPLICATION_ITEM_ID).build();
    CreditworthinessItemEntity creditworthinessItemEntity2 = CreditworthinessItemEntity.builder()
        .uuid(CREDITWORTHINESS_ITEM_ID_2)
        .loanApplicationId(LOAN_ID)
        .creditAppraisalId(CREDIT_APPRAISAL_ID)
        .creditCardId(CREDIT_CARD_ID).build();
    CreditworthinessItemEntity creditworthinessItemEntity3 = CreditworthinessItemEntity.builder()
        .uuid(CREDITWORTHINESS_ITEM_ID_3)
        .loanApplicationId(LOAN_ID)
        .creditAppraisalId(CREDIT_APPRAISAL_ID).build();
    List<CreditworthinessItemEntity> creditworthinessItemEntities = Arrays.asList(
        creditworthinessItemEntity, creditworthinessItemEntity2, creditworthinessItemEntity3);
    doReturn(creditworthinessItemEntities).when(creditworthinessItemRepository)
        .findByLoanApplicationId(LOAN_ID);

    LoanApplicationItemEntity loanApplicationItemEntity = LoanApplicationItemEntity.builder()
        .uuid(LOAN_APPLICATION_ITEM_ID)
        .loanApplicationId(LOAN_ID)
        .debtPaymentMethod(DebtPaymentMethodEnum.DEBT_PAYMENT_1).build();
    doReturn(Arrays.asList(loanApplicationItemEntity)).when(loanApplicationItemRepository)
        .findByLoanApplicationIdOrderByCreatedAtAsc(LOAN_ID);

    CreditCardEntity creditCardEntity = CreditCardEntity.builder()
        .uuid(CREDIT_CARD_ID)
        .loanId(LOAN_ID)
        .creditLimit(1000l)
        .build();
    doReturn(Arrays.asList(creditCardEntity)).when(creditCardRepository)
        .findByLoanIdAndCardPriority(LOAN_ID, CardPriorityEnum.PRIMARY_CARD);

    List<CreditworthinessItem> result = creditworthinessItemService.getByLoanId(LOAN_ID);
    assertEquals(result.size(), 3);
  }

  @Test
  void givenValidInput_ThenDeleteById_shouldReturnSuccess() {
    CreditworthinessItemEntity mockEntity = CreditworthinessItemEntity.builder()
        .uuid(CREDITWORTHINESS_ITEM_ID)
        .loanApplicationId(LOAN_ID)
        .build();
    doReturn(Optional.of(mockEntity)).when(creditworthinessItemRepository)
        .findById(mockEntity.getUuid());

    creditworthinessItemService.deleteByUuid(CREDITWORTHINESS_ITEM_ID);
    verify(creditworthinessItemRepository, times(1)).delete(mockEntity);
  }
}
