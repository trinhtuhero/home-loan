package vn.com.msb.homeloan.core.model;

import java.time.Instant;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.DebtPaymentMethodCWIEnum;
import vn.com.msb.homeloan.core.constant.FormOfCreditEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class CreditworthinessItem {

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

  Instant createdAt;

  Instant updatedAt;

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

  private double pmt() {
    double rate = 0;
    if (interestRate != null) {
      rate = interestRate / 12 / 100;
    }
    double pmt = 0;
    double tu = 0;
    double mau = 0;
    if (rate == 0) {
      if (currentBalance != null) {
        tu = -1 * (currentBalance);
      }
      if (remainingPeriod != null) {
        mau = remainingPeriod;
      }
    } else {
      double r1 = rate + 1;
      if (currentBalance != null && remainingPeriod != null) {
        tu = (currentBalance * Math.pow(r1, remainingPeriod)) * rate;
      }
      if (remainingPeriod != null) {
        mau = ((1 - Math.pow(r1, remainingPeriod)));
      }
    }
    if (mau != 0) {
      pmt = tu / mau;
    }
    return pmt;
  }

  public void setMonthlyDebtPaymentAuto() {
    double goc = 0;
    double lai = 0;

    if ((interestRate == null || interestRate < 0) && !Arrays.asList(FormOfCreditEnum.CREDIT_CARD,
            FormOfCreditEnum.SECURED_OVERDRAFT, FormOfCreditEnum.UNSECURED_OVERDRAFT)
        .contains(formOfCredit)) {
      monthlyDebtPaymentAuto = null;
    } else {
      if (debtPaymentMethod != null && remainingPeriod != null && currentBalance != null
          && remainingPeriod != 0 && debtPaymentMethod.equals(
          DebtPaymentMethodCWIEnum.DEBT_PAYMENT_1)) {
        goc = Double.valueOf(currentBalance) / remainingPeriod;
      } else if (debtPaymentMethod != null && debtPaymentMethod.equals(
          DebtPaymentMethodCWIEnum.DEBT_PAYMENT_4)) {
        goc = -pmt() / 2;
      }

      if (Arrays.asList(FormOfCreditEnum.CREDIT_CARD, FormOfCreditEnum.SECURED_OVERDRAFT,
          FormOfCreditEnum.UNSECURED_OVERDRAFT).contains(formOfCredit)) {
        lai = firstLimit * 0.05;
      } else {
        if (debtPaymentMethod != null) {
          if (currentBalance != null && interestRate != null && debtPaymentMethod.equals(
              DebtPaymentMethodCWIEnum.DEBT_PAYMENT_1) || debtPaymentMethod.equals(
              DebtPaymentMethodCWIEnum.DEBT_PAYMENT_3)) {
            lai = currentBalance * (interestRate / 100) / 12;
          } else if (debtPaymentMethod.equals(DebtPaymentMethodCWIEnum.DEBT_PAYMENT_2)) {
            lai = 0;
          } else if (debtPaymentMethod.equals(DebtPaymentMethodCWIEnum.DEBT_PAYMENT_4)) {
            lai = -pmt() / 2;
          }
        }
      }
      double result = goc + lai;
      this.g = goc;
      this.l = lai;
      monthlyDebtPaymentAuto = Math.round(result);
    }
  }

  Double g;
  Double l;
}
