package vn.com.msb.homeloan.api.dto.request.creditappraisal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditworthinessItemRequest {

  String uuid;

  @NotBlank
  String loanApplicationId;

  String creditAppraisalId;

  String loanApplicationItemId;

  String creditCardId;

  String overdraftId;

  // owner_type
  // OwnerTypeEnum
  @NotBlank
  @Pattern(regexp = "^(ME|MARRIED_PERSON|PAYER_PERSON)$")
  String ownerType;

  // target
  String target;

  // credit_institution
  @NotBlank
  String creditInstitution;

  // form_of_credit
  // FormOfCreditEnum
  @NotBlank
  @Pattern(regexp = "^(SECURED_MORTGAGE|UNSECURED_MORTGAGE|SECURED_OVERDRAFT|UNSECURED_OVERDRAFT|CREDIT_CARD|LINE_OF_CREDIT)$")
  String formOfCredit;

  // current_balance
  @NotNull
  Long currentBalance;

  // monthly_debt_payment
//    @NotNull
  Long monthlyDebtPayment;

  // interest_rate (Lãi suất)
  Double interestRate;

  // first_period (Kì hạn vay ban đầu)
  Integer firstPeriod;

  // remaining_period (Kì hạn vay còn lại)
  Integer remainingPeriod;

  // first_limit ( Hạn mức ban đầu)
  @NotNull
  Long firstLimit;

  // debt_payment_method ( Phương thức trả nợ)
  @Pattern(regexp = "^(DEBT_PAYMENT_1|DEBT_PAYMENT_2|DEBT_PAYMENT_3|DEBT_PAYMENT_4)$")
  String debtPaymentMethod;
}
