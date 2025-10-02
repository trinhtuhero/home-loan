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
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;

@Data
@Entity
@Table(name = "asset_evaluate", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssetEvaluateEntity extends BaseEntity {

  @Column(name = "loan_application_id")
  String loanApplicationId;

  @Column(name = "total_value")
  Long totalValue;

  @Column(name = "debit_balance")
  Long debitBalance;

  @Column(name = "income_value")
  Long incomeValue;

  @Column(name = "rm_input_value")
  Long rmInputValue;

  @Column(name = "rm_review")
  String rmReview;

  @Column(name = "owner_type")
  @Enumerated(EnumType.STRING)
  OwnerTypeEnum ownerType;

  @Column(name = "payer_id")
  String payerId;
}
