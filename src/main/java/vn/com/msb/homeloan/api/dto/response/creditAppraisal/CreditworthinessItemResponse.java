package vn.com.msb.homeloan.api.dto.response.creditAppraisal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.DebtPaymentMethodCWIEnum;
import vn.com.msb.homeloan.core.constant.FormOfCreditEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditworthinessItemResponse {

  String uuid;

  // loan_application_id
  String loanApplicationId;

  // credit_appraisal_id
  String creditAppraisalId;

  String loanApplicationItemId;

  String creditCardId;

  String overdraftId;

  // owner_type
  OwnerTypeEnum ownerType;

  // target
  String target;

  // credit_institution
  String creditInstitution;

  // credit_institution_name
  String creditInstitutionName;

  // form_of_credit
  FormOfCreditEnum formOfCredit;

  // current_balance
  Long currentBalance;

  // monthly_debt_payment
  Long monthlyDebtPayment;

  // interest_rate (Lãi suất)
  Double interestRate;

  // first_period (Kì hạn vay ban đầu)
  Integer firstPeriod;

  // remaining_period (Kì hạn vay còn lại)
  Integer remainingPeriod;

  // first_limit ( Hạn mức ban đầu)
  Long firstLimit;

  // debt_payment_method ( Phương thức trả nợ)
  DebtPaymentMethodCWIEnum debtPaymentMethod;

  //Nghĩa vụ trả nợ tự động tính toán
  Long monthlyDebtPaymentAuto;
}
