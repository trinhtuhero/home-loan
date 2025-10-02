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
import vn.com.msb.homeloan.core.constant.DebtPaymentMethodEnum;
import vn.com.msb.homeloan.core.constant.FormOfCreditEnum;
import vn.com.msb.homeloan.core.constant.OverdraftPurposeEnum;
import vn.com.msb.homeloan.core.constant.OverdraftSubjectEnum;
import vn.com.msb.homeloan.core.constant.ProductTextCodeEnum;

@Data
@Entity
@Table(name = "overdraft", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OverdraftEntity extends BaseEntity {

  // loan_application_id
  String loanApplicationId;

  // overdraft_purpose (Mục đích vay thấu chi)
  @Enumerated(EnumType.STRING)
  OverdraftPurposeEnum overdraftPurpose;

  // loan_amount (Hạn mức vay thấu chi)
  Long loanAmount;

  // overdraft_subject (Đối tượng cấp thấu chi)
  @Enumerated(EnumType.STRING)
  OverdraftSubjectEnum overdraftSubject;

  // form_of_credit (Hình thức cấp tín dụng)
  @Enumerated(EnumType.STRING)
  FormOfCreditEnum formOfCredit;

  // loan_time (Thời gian duy trì hạn mức/ thời gian vay)
  Integer loanTime;

  // interest_code (Mã lãi suất thấu chi)
  String interestCode;

  // margin (Biên độ)
  Float margin;

  // payment_account_number (Số tài khoản thanh toán)
  String paymentAccountNumber;

  // debt_payment_method (phương thức trả nợ)
  @Enumerated(EnumType.STRING)
  DebtPaymentMethodEnum debtPaymentMethod;

  // loan_asset_value (Tổng nhu cầu vốn)
  Long loanAssetValue;

  // product_text_code (Tên sản phẩm)
  @Enumerated(EnumType.STRING)
  ProductTextCodeEnum productTextCode;

  // document_number_2 (Mã văn bản sản phẩm 2)
  @Column(name = "document_number_2")
  String documentNumber2;
}
