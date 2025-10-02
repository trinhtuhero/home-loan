package vn.com.msb.homeloan.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "" +
    "loan_item_collateral_distribution", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanItemCollateralDistributionEntity extends BaseEntity {

  // loan_item_id
  @Column(name = "loan_item_id")
  String loanItemId;

  // collateral_id
  @Column(name = "collateral_id")
  String collateralId;

  // percent
  @Column(name = "percent")
  Float percent;

  String type;
}
