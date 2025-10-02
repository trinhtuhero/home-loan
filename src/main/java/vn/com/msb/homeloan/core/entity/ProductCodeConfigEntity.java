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
@Table(name = "product_code_config", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCodeConfigEntity extends BaseEntity {

  @Column(name = "product_code")
  String productCode;

  @Column(name = "loan_time_from")
  Integer from;

  @Column(name = "loan_time_to")
  Integer to;

  @Column(name = "approval_flow")
  String approvalFlow;

  @Column(name = "income_method")
  String incomeMethod;

  @Column(name = "product_type")
  String productType;

  @Column(name = "expression")
  String expression;
}
