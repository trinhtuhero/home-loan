package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.entity.CreditCardEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationItemEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CreditCard;
import vn.com.msb.homeloan.core.model.CreditworthinessItem;
import vn.com.msb.homeloan.core.model.mapper.CreditCardMapper;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.CreditCardService;
import vn.com.msb.homeloan.core.service.CreditworthinessItemService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
class CreditCardServiceImpl implements CreditCardService {

  private final CreditCardRepository creditCardRepository;
  private final LoanApplicationService loanApplicationService;
  private final LoanApplicationRepository loanApplicationRepository;
  private final CardPolicyRepository cardPolicyRepository;
  private final CardTypeRepository cardTypeRepository;
  private final CreditworthinessItemService creditworthinessItemService;
  private final LoanApplicationItemRepository loanApplicationItemRepository;


  @Override
  public CreditCard save(CreditCard creditCard, ClientTypeEnum clientType) {
    validateInput(creditCard, clientType);
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            creditCard.getLoanId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    CreditCardEntity creditCardEntity = new CreditCardEntity();
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);

    if (!StringUtils.isEmpty(creditCard.getUuid())) {
      creditCardEntity = creditCardRepository.findByUuid(creditCard.getUuid())
          .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid",
              creditCard.getUuid()));
      //ko được thay đổi loan_application_id
      if (!creditCard.getLoanId().equals(creditCardEntity.getLoanId())) {
        throw new ApplicationException(ErrorEnum.LOAN_APPLICATION_ID_CAN_NOT_CHANGE);
      }
    } else {
      List<CreditCardEntity> creditCardEntities = creditCardRepository.findByLoanId(
          creditCard.getLoanId());
      if (CardPriorityEnum.PRIMARY_CARD.equals(creditCard.getCardPriority())) {
        if (!CollectionUtils.isEmpty(creditCardEntities)) {
          throw new ApplicationException(ErrorEnum.MAIN_CREDIT_CARD_ALREADY_EXIST);
        }
      } else {
        if (CollectionUtils.isEmpty(creditCardEntities)) {
          //phải có thẻ chính trc khi tạo thẻ phụ
          throw new ApplicationException(ErrorEnum.PRIMARY_CARD_NOT_EXIST);
        }

        //chỉ được 1 thẻ chính, 3 thẻ phụ
        if (creditCardEntities.size() == 4) {
          throw new ApplicationException(ErrorEnum.EXCEED_NUMBER_CREDIT_CARD);
        }

        List<CreditCardEntity> mainCard = creditCardEntities.stream()
            .filter(cc -> cc.getCardPriority().equals(CardPriorityEnum.PRIMARY_CARD))
            .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(mainCard)) {
          if (!StringUtils.isEmpty(creditCard.getParentId())) {
            creditCardEntity.setParentId(creditCard.getParentId());
            List<CreditCardEntity> main = mainCard.stream()
                .filter(mc -> mc.getUuid().equals(creditCard.getParentId()))
                .collect(Collectors.toList());

            if (creditCard.getCreditLimit() > main.get(0).getCreditLimit()) {
              throw new ApplicationException(ErrorEnum.CARD_LIMIT_ERROR);
            }
          } else {
            if (creditCard.getCreditLimit() > mainCard.get(0).getCreditLimit()) {
              throw new ApplicationException(ErrorEnum.CARD_LIMIT_ERROR);
            }
            creditCardEntity.setParentId(mainCard.get(0).getUuid());
          }
        }
      }
      creditCardEntity.setCardPriority(creditCard.getCardPriority());
    }

    creditCardEntity.setNameInCard(creditCard.getNameInCard());
    creditCardEntity.setLoanId(creditCard.getLoanId());
    creditCardEntity.setCreditLimit(creditCard.getCreditLimit());
    if (CardPriorityEnum.PRIMARY_CARD.equals(creditCard.getCardPriority())) {
      creditCardEntity.setObject(CreditCardObjectEnum.CUSTOMER.getCode());
      creditCardEntity.setType(creditCard.getType());
      creditCardEntity.setFirstPrimarySchool(creditCard.getFirstPrimarySchool());
      creditCardEntity.setAutoDebit(creditCard.getAutoDebit());
      if (Boolean.TRUE.equals(creditCard.getAutoDebit())) {
        creditCardEntity.setDebtDeductionRate(creditCard.getDebtDeductionRate());
        creditCardEntity.setDebitAccountNumber(creditCard.getDebitAccountNumber());
      } else {
        creditCardEntity.setDebtDeductionRate(null);
        creditCardEntity.setDebitAccountNumber(null);
      }
      creditCardEntity.setReceivingAddress(creditCard.getReceivingAddress());
    } else {
      creditCardEntity.setObject(creditCard.getObject());
    }

    if (ClientTypeEnum.CMS == clientType && CardPriorityEnum.PRIMARY_CARD.equals(
        creditCard.getCardPriority())) {
      creditCardEntity.setCardPolicyCode(creditCard.getCardPolicyCode());
      creditCardEntity.setTimeLimit(creditCard.getTimeLimit());
    }
    creditCardRepository.save(creditCardEntity);

    //Insert or update to loan_application_item
    saveLoanItem(creditCard);

    return CreditCardMapper.INSTANCE.toModels(creditCardEntity);
  }

  private void saveLoanItem(CreditCard creditCard) {
    if (CardPriorityEnum.SECONDARY_CARD.equals(creditCard.getCardPriority())) {
      return;
    }

    LoanApplicationItemEntity itemEntity = getCreditCardFromLoanItem(creditCard.getLoanId());
    itemEntity = itemEntity == null ? LoanApplicationItemEntity.builder().build() : itemEntity;

    itemEntity.setLoanApplicationId(creditCard.getLoanId());
    itemEntity.setProductTextCode(ProductTextCodeEnum.CREDIT_CARD);
    itemEntity.setLoanPurpose(LoanPurposeEnum.CREDIT_CARD);
    itemEntity.setLoanAmount(creditCard.getCreditLimit());

    loanApplicationItemRepository.save(itemEntity);
  }

  private LoanApplicationItemEntity getCreditCardFromLoanItem(String loanId) {
    List<LoanApplicationItemEntity> loanApplicationItemEntities = loanApplicationItemRepository.findByLoanApplicationId(
        loanId);
    if (!CollectionUtils.isEmpty(loanApplicationItemEntities)) {
      List<LoanApplicationItemEntity> temps = loanApplicationItemEntities.stream()
          .filter(x -> ProductTextCodeEnum.CREDIT_CARD.equals(x.getProductTextCode()))
          .collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(temps)) {
        return temps.get(0);
      }
    }
    return null;
  }

  @Override
  public void deleteByUuid(String uuid, ClientTypeEnum clientType) {
    List<CreditCardEntity> lstDelete = new ArrayList<>();
    CreditCardEntity creditCardEntity = creditCardRepository.findByUuid(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid", uuid));
    lstDelete.add(creditCardEntity);
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            creditCardEntity.getLoanId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);

    //nếu xóa thẻ chính thì phải xóa tất cả thẻ phụ
    if (CardPriorityEnum.PRIMARY_CARD.equals(creditCardEntity.getCardPriority())) {
      // xóa thông tin bản ghi tương ứng trong creditworthinessItem
      creditworthinessItemService.deleteByConditions(CreditworthinessItem.builder()
          .loanApplicationId(creditCardEntity.getLoanId())
          .creditCardId(creditCardEntity.getUuid())
          .build());
      List<CreditCardEntity> creditCardEntities = creditCardRepository.findByLoanIdAndCardPriorityAndParentId(
          creditCardEntity.getLoanId(), CardPriorityEnum.SECONDARY_CARD,
          creditCardEntity.getUuid());
      //nếu SECONDARY_CARD có parent_id thì xóa theo parent_id
      if (!CollectionUtils.isEmpty(creditCardEntities)) {
        lstDelete.addAll(creditCardEntities);
      } else {
        List<CreditCardEntity> creditCards = creditCardRepository.findByLoanIdAndCardPriority(
            creditCardEntity.getLoanId(), CardPriorityEnum.SECONDARY_CARD);
        if (!CollectionUtils.isEmpty(creditCards)) {
          lstDelete.addAll(creditCards);
        }
      }

      //Xoa du lieu trong bang loan application item
      LoanApplicationItemEntity itemEntity = getCreditCardFromLoanItem(
          creditCardEntity.getLoanId());
      if (itemEntity != null) {
        loanApplicationItemRepository.delete(itemEntity);
      }
    }

    if (!CollectionUtils.isEmpty(lstDelete)) {
      creditCardRepository.deleteAll(lstDelete);
    }
  }

  private void validateInput(CreditCard creditCard, ClientTypeEnum clientType) {
    Map<String, String> errorDetail = new HashMap<>();
    if (ClientTypeEnum.CMS == clientType) {
      if (CardPriorityEnum.PRIMARY_CARD.equals(creditCard.getCardPriority())) {
        if (StringUtils.isEmpty(creditCard.getCardPolicyCode())) {
          errorDetail.put("not_blank.card_policy_code", "must not be blank");
        } else {
          cardPolicyRepository.findByCode(creditCard.getCardPolicyCode())
              .orElseThrow(() -> new ApplicationException(ErrorEnum.CARD_POLICY_CODE_NOT_EXIST));
        }
        if (creditCard.getTimeLimit() == null) {
          errorDetail.put("not_null.time_limit", "must not be null");
        }
      }
    }

    if (CardPriorityEnum.PRIMARY_CARD.equals(creditCard.getCardPriority())) {
      if (StringUtils.isEmpty(creditCard.getFirstPrimarySchool())) {
        errorDetail.put("not_blank.first_primary_school", "must not be blank");
      }
      if (StringUtils.isEmpty(creditCard.getType())) {
        errorDetail.put("not_blank.type", "must not be blank");
      } else {
        cardTypeRepository.findById(creditCard.getType())
            .orElseThrow(() -> new ApplicationException(ErrorEnum.CARD_TYPE_CODE_NOT_EXIST));
      }
      if (creditCard.getAutoDebit() == null) {
        errorDetail.put("not_null.auto_debit", "must not be null");
      } else if (creditCard.getAutoDebit()) {
        if (creditCard.getDebtDeductionRate() == null) {
          errorDetail.put("not_blank.debt_deduction_rate", "must not be blank");
        }
        if (StringUtils.isEmpty(creditCard.getDebitAccountNumber())) {
          errorDetail.put("not_blank.debit_account_number", "must not be blank");
        }
      }
      if (StringUtils.isEmpty(creditCard.getReceivingAddress())) {
        errorDetail.put("not_blank.receiving_address", "must not be blank");
      }
    }
    if (!errorDetail.isEmpty()) {
      throw new ApplicationException(ErrorEnum.INVALID_FORM, errorDetail);
    }
  }

  public String validateCreditCard(String loanId) {
    boolean check = true;
    List<CreditCardEntity> creditCardEntityList = creditCardRepository.findByLoanId(loanId);
    for (CreditCardEntity creditCardEntity : creditCardEntityList) {
      try {
        CreditCard card = CreditCardMapper.INSTANCE.toModels(creditCardEntity);
        validateInput(card, ClientTypeEnum.CMS);
      } catch (Exception e) {
        check = false;
      }
    }
    if (check) {
      return "success";
    } else {
      throw new ApplicationException(HttpStatus.BAD_REQUEST.value(),
          "Cần khai báo đầy đủ thông tin thẻ tín dụng để lưu Tab");
    }
  }
}
