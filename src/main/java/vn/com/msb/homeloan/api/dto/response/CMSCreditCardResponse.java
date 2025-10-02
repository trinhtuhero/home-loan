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
public class CMSCreditCardResponse {

  String uuid;
  String loanId;
  String type;
  String typeName;
  CardPriorityEnum cardPriority;
  Long creditLimit;
  String object;
  String cardPolicyCode;
  String cardPolicyName;
  Integer timeLimit;
  String firstPrimarySchool;
  Boolean autoDebit;
  DebtDeductionRateEnum debtDeductionRate;
  String debitAccountNumber;
  String receivingAddress;
  String nameInCard;
}
