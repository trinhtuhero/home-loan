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
import vn.com.msb.homeloan.core.constant.CardPriorityEnum;
import vn.com.msb.homeloan.core.constant.CreditCardObjectEnum;
import vn.com.msb.homeloan.core.constant.DebtDeductionRateEnum;

@Data
@Entity
@Table(name = "credit_card", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditCardEntity extends BaseEntity {

  @Column(name = "loan_id")
  String loanId;

  @Enumerated(EnumType.STRING)
  @Column(name = "card_priority")
  CardPriorityEnum cardPriority;

  @Column(name = "type")
  String type;

  @Column(name = "credit_limit")
  Long creditLimit;

  @Column(name = "object")
  String object;

  @Column(name = "card_policy_code")
  String cardPolicyCode;

  @Column(name = "time_limit")
  Integer timeLimit;

  @Column(name = "first_primary_school")
  String firstPrimarySchool;

  @Column(name = "auto_debit")
  Boolean autoDebit;

  @Enumerated(EnumType.STRING)
  @Column(name = "debt_deduction_rate")
  DebtDeductionRateEnum debtDeductionRate;

  @Column(name = "debit_account_number")
  String debitAccountNumber;

  @Column(name = "receiving_address")
  String receivingAddress;

  @Column(name = "name_in_card")
  String nameInCard;

  @Column(name = "parent_id")
  String parentId;
}
