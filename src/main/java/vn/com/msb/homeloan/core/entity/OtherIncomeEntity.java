package vn.com.msb.homeloan.core.entity;

import java.math.BigDecimal;
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
import vn.com.msb.homeloan.core.constant.IncomeFromEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;

@Data
@Entity
@Table(name = "other_incomes", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OtherIncomeEntity extends BaseEntity {

  // loan_application_id
  @Column(name = "loan_application_id")
  String loanApplicationId;

  // owner_type
  // enum [Tôi, Vợ/Chồng, Người đồng trả nợ]
  @Enumerated(EnumType.STRING)
  @Column(name = "owner_type")
  OwnerTypeEnum ownerType;

  // income_from
  @Enumerated(EnumType.STRING)
  @Column(name = "income_from")
  IncomeFromEnum incomeFrom;

  // value
  @Column(name = "value")
  Long value;

  @Column(name = "rm_review")
  String rmReview;

  @Column(name = "payer_id")
  String payerId;

  //Địa chỉ tài sản/Loại xe
  @Column(name = "rent_property")
  String rentProperty;

  //Bên thuê
  @Column(name = "tenant")
  String tenant;

  //Số điện thoại Bên thuê
  @Column(name = "tenant_phone")
  String tenantPhone;

  //Mục đích thuê
  @Column(name = "tenant_purpose")
  String tenantPurpose;

  //Giá cho thuê
  @Column(name = "tenant_price")
  Long tenantPrice;

  @Column
  BigDecimal incomePerMonth;

  @Column
  Integer quantity;
}
