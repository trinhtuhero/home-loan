package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.entity.*;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CreditworthinessItem;
import vn.com.msb.homeloan.core.model.mapper.CreditworthinessItemMapper;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.CreditworthinessItemService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;
import vn.com.msb.homeloan.core.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CreditworthinessItemServiceImpl implements CreditworthinessItemService {

  private final CreditworthinessItemRepository creditworthinessItemRepository;
  private final LoanApplicationItemRepository loanApplicationItemRepository;
  private final CreditCardRepository creditCardRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final CreditAppraisalRepository creditAppraisalRepository;
  private final LoanPayerRepository loanPayerRepository;
  private final LoanApplicationService loanApplicationService;

  private final OverdraftRepository overdraftRepository;

  @Override
  public CreditworthinessItem save(CreditworthinessItem creditworthinessItem) {
    validateInput(creditworthinessItem);
    if (OwnerTypeEnum.PAYER_PERSON.equals(creditworthinessItem.getOwnerType())) {
      loanPayerRepository.findByUuid(creditworthinessItem.getTarget())
          .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_PAYER_NOT_FOUND));
    }
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            creditworthinessItem.getLoanApplicationId())
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loan_applications:uuid",
                creditworthinessItem.getLoanApplicationId()));
    loanApplicationService.checkEditLoanApp(loanApplication, ClientTypeEnum.CMS);
    creditworthinessItem.setMonthlyDebtPaymentAuto();
    CreditworthinessItemEntity creditworthinessItemEntity = new CreditworthinessItemEntity();

    if (!StringUtils.isEmpty(creditworthinessItem.getLoanApplicationItemId())
        || !StringUtils.isEmpty(creditworthinessItem.getCreditCardId())
        || !StringUtils.isEmpty(creditworthinessItem.getOverdraftId())) {
      creditworthinessItemEntity.setMonthlyDebtPayment(
          creditworthinessItem.getMonthlyDebtPayment());
      creditworthinessItemEntity.setInterestRate(creditworthinessItem.getInterestRate());
      creditworthinessItemEntity.setUuid(creditworthinessItem.getUuid());
      creditworthinessItemEntity.setLoanApplicationId(creditworthinessItem.getLoanApplicationId());
      if (!StringUtils.isEmpty(creditworthinessItem.getLoanApplicationItemId())) {
        loanApplicationItemRepository.findById(creditworthinessItem.getLoanApplicationItemId())
            .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND,
                "loan_application_item:uuid", creditworthinessItem.getLoanApplicationItemId()));
        creditworthinessItemEntity.setLoanApplicationItemId(
            creditworthinessItem.getLoanApplicationItemId());
      }
      if (!StringUtils.isEmpty(creditworthinessItem.getCreditCardId())) {
        creditCardRepository.findById(creditworthinessItem.getCreditCardId())
            .orElseThrow(
                () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "credit_card:uuid",
                    creditworthinessItem.getCreditCardId()));
        creditworthinessItemEntity.setCreditCardId(creditworthinessItem.getCreditCardId());
      }
      if (!StringUtils.isEmpty(creditworthinessItem.getOverdraftId())) {
        overdraftRepository.findById(creditworthinessItem.getOverdraftId())
            .orElseThrow(
                () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "overdraft:uuid",
                    creditworthinessItem.getOverdraftId()));
        creditworthinessItemEntity.setOverdraftId(creditworthinessItem.getOverdraftId());
      }
    } else {
      //valiate số lượng bản ghi
      if (StringUtils.isEmpty(creditworthinessItem.getUuid())) {
        int countCreditworthinessItem = creditworthinessItemRepository.countByLoanApplicationIdAndLoanApplicationItemIdAndCreditCardId(
            creditworthinessItem.getLoanApplicationId(), null, null);
        if (countCreditworthinessItem >= 14) {
          throw new ApplicationException(ErrorEnum.ITEM_LIMIT, "creditworthiness item",
              String.valueOf(14));
        }
      }
      creditworthinessItemEntity = CreditworthinessItemMapper.INSTANCE.toEntity(
          creditworthinessItem);
    }
    creditworthinessItemRepository.save(creditworthinessItemEntity);
    return CreditworthinessItemMapper.INSTANCE.toModel(creditworthinessItemEntity);
  }

  @Override
  public List<CreditworthinessItem> getByLoanId(String loanId) {
    loanApplicationRepository.findById(loanId)
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loan_applications:uuid",
                loanId));
    String creditAppraisalId = null;
    Optional<CreditAppraisalEntity> opCreditAppraisalEntity = creditAppraisalRepository.findByLoanApplicationId(
        loanId);
    if (opCreditAppraisalEntity.isPresent()) {
      creditAppraisalId = opCreditAppraisalEntity.get().getUuid();
    }
    List<CreditworthinessItem> creditworthinessItems = new ArrayList<>();
    List<CreditworthinessItemEntity> creditworthinessItemEntities = creditworthinessItemRepository.findByLoanApplicationId(
        loanId);
    List<LoanApplicationItemEntity> loanApplicationItemEntities = loanApplicationItemRepository.findByLoanApplicationIdOrderByCreatedAtAsc(
        loanId);
    for (LoanApplicationItemEntity loanApplicationItemEntity : loanApplicationItemEntities) {
      CreditworthinessItem creditworthinessItem = new CreditworthinessItem();
      if (!CollectionUtils.isEmpty(creditworthinessItemEntities)) {
        List<CreditworthinessItemEntity> entities = creditworthinessItemEntities.stream().filter(
            cw -> cw.getLoanApplicationItemId() != null && cw.getLoanApplicationItemId()
                .equals(loanApplicationItemEntity.getUuid())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(entities)) {
          creditworthinessItem = CreditworthinessItemMapper.INSTANCE.toModel(entities.get(0));
        }
      }
      creditworthinessItem.setOwnerType(OwnerTypeEnum.ME);
      creditworthinessItem.setCreditInstitution(Constants.CREDIT_INSTITUTION_MSB_CODE);
      creditworthinessItem.setFormOfCredit(FormOfCreditEnum.SECURED_MORTGAGE);
      creditworthinessItem.setLoanApplicationId(loanId);
      creditworthinessItem.setCreditAppraisalId(creditAppraisalId);
      creditworthinessItem.setLoanApplicationItemId(loanApplicationItemEntity.getUuid());
      creditworthinessItem.setFirstPeriod(loanApplicationItemEntity.getLoanTime());
      creditworthinessItem.setRemainingPeriod(loanApplicationItemEntity.getLoanTime());
      creditworthinessItem.setFirstLimit(loanApplicationItemEntity.getLoanAmount());
      creditworthinessItem.setCurrentBalance(loanApplicationItemEntity.getLoanAmount());
      creditworthinessItem.setDebtPaymentMethod(
          loanApplicationItemEntity.getDebtPaymentMethod() != null ? HomeLoanUtil.convertEnum(
              DebtPaymentMethodCWIEnum.class, loanApplicationItemEntity.getDebtPaymentMethod())
              : null);
      creditworthinessItem.setMonthlyDebtPaymentAuto();
      creditworthinessItems.add(creditworthinessItem);
    }

    List<OverdraftEntity> overdraftEntities = overdraftRepository.findByLoanApplicationIdOrderByCreatedAtAsc(
        loanId);
    for (OverdraftEntity overdraftEntity : overdraftEntities) {
      CreditworthinessItem creditworthinessItem = new CreditworthinessItem();
      if (!CollectionUtils.isEmpty(creditworthinessItemEntities)) {
        List<CreditworthinessItemEntity> entities = creditworthinessItemEntities.stream().filter(
            cw -> cw.getOverdraftId() != null && cw.getOverdraftId()
                .equals(overdraftEntity.getUuid())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(entities)) {
          creditworthinessItem = CreditworthinessItemMapper.INSTANCE.toModel(entities.get(0));

        }
      }
      creditworthinessItem.setOwnerType(OwnerTypeEnum.ME);
      creditworthinessItem.setCreditInstitution(Constants.CREDIT_INSTITUTION_MSB_CODE);
      creditworthinessItem.setFormOfCredit(overdraftEntity.getFormOfCredit());
      creditworthinessItem.setLoanApplicationId(loanId);
      creditworthinessItem.setCreditAppraisalId(creditAppraisalId);
      creditworthinessItem.setOverdraftId(overdraftEntity.getUuid());
      creditworthinessItem.setFirstPeriod(overdraftEntity.getLoanTime());
      creditworthinessItem.setRemainingPeriod(overdraftEntity.getLoanTime());
      creditworthinessItem.setFirstLimit(overdraftEntity.getLoanAmount());
      creditworthinessItem.setCurrentBalance(0l);
      creditworthinessItem.setFormOfCredit(overdraftEntity.getFormOfCredit());
      creditworthinessItem.setDebtPaymentMethod(DebtPaymentMethodCWIEnum.DEBT_PAYMENT_3);
      creditworthinessItem.setMonthlyDebtPaymentAuto();
      creditworthinessItems.add(creditworthinessItem);
    }

    List<CreditCardEntity> creditCardEntities = creditCardRepository.findByLoanIdAndCardPriority(
        loanId, CardPriorityEnum.PRIMARY_CARD);
    for (CreditCardEntity creditCardEntity : creditCardEntities) {
      CreditworthinessItem creditworthinessItem = new CreditworthinessItem();
      if (!CollectionUtils.isEmpty(creditworthinessItemEntities)) {
        List<CreditworthinessItemEntity> entities = creditworthinessItemEntities.stream().filter(
            cw -> cw.getCreditCardId() != null && cw.getCreditCardId()
                .equals(creditCardEntity.getUuid())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(entities)) {
          creditworthinessItem = CreditworthinessItemMapper.INSTANCE.toModel(entities.get(0));

        }
      }
      creditworthinessItem.setOwnerType(OwnerTypeEnum.ME);
      creditworthinessItem.setCreditInstitution(Constants.CREDIT_INSTITUTION_MSB_CODE);
      creditworthinessItem.setFormOfCredit(FormOfCreditEnum.CREDIT_CARD);
      creditworthinessItem.setLoanApplicationId(loanId);
      creditworthinessItem.setCreditAppraisalId(creditAppraisalId);
      creditworthinessItem.setCreditCardId(creditCardEntity.getUuid());
      creditworthinessItem.setFirstPeriod(creditCardEntity.getTimeLimit());
      creditworthinessItem.setRemainingPeriod(creditCardEntity.getTimeLimit());
      creditworthinessItem.setFirstLimit(creditCardEntity.getCreditLimit());
      creditworthinessItem.setCurrentBalance(creditCardEntity.getCreditLimit());
      creditworthinessItem.setFormOfCredit(FormOfCreditEnum.CREDIT_CARD);
      creditworthinessItem.setMonthlyDebtPaymentAuto();
      creditworthinessItems.add(creditworthinessItem);
    }
    if (!CollectionUtils.isEmpty(creditworthinessItemEntities)) {
      List<CreditworthinessItemEntity> oldCreditworthinessItemEntities = creditworthinessItemEntities.stream()
          .filter(cw -> cw.getLoanApplicationItemId() == null && cw.getCreditCardId() == null
              && cw.getOverdraftId() == null).collect(Collectors.toList());
      List<CreditworthinessItem> oldCreditworthinessItems = CreditworthinessItemMapper.INSTANCE.toModels(
          oldCreditworthinessItemEntities);
      for (CreditworthinessItem creditworthinessItem : oldCreditworthinessItems) {
        creditworthinessItem.setMonthlyDebtPaymentAuto();
      }
      creditworthinessItems.addAll(oldCreditworthinessItems);
    }
    return creditworthinessItems;
  }

  @Override
  public void deleteByUuid(String uuid) {
    creditworthinessItemRepository.delete(creditworthinessItemRepository.findById(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid", uuid)));
  }

  private void validateInput(CreditworthinessItem creditworthinessItem) {
    Map<String, String> errorDetail = new HashMap<>();
    if (!FormOfCreditEnum.CREDIT_CARD.equals(creditworthinessItem.getFormOfCredit())) {
      if (creditworthinessItem.getDebtPaymentMethod() == null) {
        errorDetail.put("not_blank.debt_payment_method", "must not be blank");
      }
    }
    if (!errorDetail.isEmpty()) {
      throw new ApplicationException(ErrorEnum.INVALID_FORM, errorDetail);
    }
  }

  @Override
  public void deleteByConditions(CreditworthinessItem creditworthinessItem) {
    creditworthinessItemRepository.deleteByConditions(creditworthinessItem.getLoanApplicationId(),
        creditworthinessItem.getLoanApplicationItemId(), creditworthinessItem.getCreditCardId());
  }
}
