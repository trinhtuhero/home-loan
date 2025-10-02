package vn.com.msb.homeloan.api.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.CardPriorityEnum;
import vn.com.msb.homeloan.core.constant.DebtDeductionRateEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CRUCreditCardResponse {

  String uuid;
  String loanId;
  String type;
  CardPriorityEnum cardPriority;
  Long creditLimit;
  String object;
  String firstPrimarySchool;
  Boolean autoDebit;
  DebtDeductionRateEnum debtDeductionRate;
  String debitAccountNumber;
  String receivingAddress;
  String nameInCard;
}
