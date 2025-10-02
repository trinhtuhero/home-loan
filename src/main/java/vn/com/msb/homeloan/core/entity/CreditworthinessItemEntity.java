package vn.com.msb.homeloan.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.DebtPaymentMethodCWIEnum;
import vn.com.msb.homeloan.core.constant.FormOfCreditEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;

@Data
@Entity
@Table(name = "creditworthiness_items", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditworthinessItemEntity extends BaseEntity {

  // loan_application_id
  @Column(name = "loan_application_id")
  String loanApplicationId;

  @Column(name = "loan_application_item_id")
  String loanApplicationItemId;

  @Column(name = "credit_card_id")
  String creditCardId;

  @Column(name = "overdraft_id")
  String overdraftId;

  // credit_appraisal_id
  @Column(name = "credit_appraisal_id")
  String creditAppraisalId;

  //owner_type
  @Column(name = "owner_type")
  @Enumerated(EnumType.STRING)
  OwnerTypeEnum ownerType;

  // target
  @Column(name = "target")
  String target;

  // credit_institution
  @Column(name = "credit_institution")
  String creditInstitution;

  // credit_institution_name
  @Column(name = "credit_institution_name")
  String creditInstitutionName;

  // form_of_credit
  @Column(name = "form_of_credit")
  @Enumerated(EnumType.STRING)
  FormOfCreditEnum formOfCredit;

  // current_balance
  @Column(name = "current_balance")
  Long currentBalance;

  // monthly_debt_payment
  @Column(name = "monthly_debt_payment")
  Long monthlyDebtPayment;

  // interest_rate (Lãi suất)
  @Column(name = "interest_rate")
  Double interestRate;

  // first_period (Kì hạn vay ban đầu)
  @Column(name = "first_period")
  Integer firstPeriod;

  // remaining_period (Kì hạn vay còn lại)
  @Column(name = "remaining_period")
  Integer remainingPeriod;

  // first_limit ( Hạn mức ban đầu)
  @Column(name = "first_limit")
  Long firstLimit;

  // debt_payment_method ( Phương thức trả nợ)
  @Column(name = "debt_payment_method")
  @Enumerated(EnumType.STRING)
  DebtPaymentMethodCWIEnum debtPaymentMethod;
}
