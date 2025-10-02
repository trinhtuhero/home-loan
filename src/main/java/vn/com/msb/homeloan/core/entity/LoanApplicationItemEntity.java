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
import vn.com.msb.homeloan.core.constant.DisbursementMethodEnum;
import vn.com.msb.homeloan.core.constant.LoanPurposeEnum;
import vn.com.msb.homeloan.core.constant.ProductTextCodeEnum;
import vn.com.msb.homeloan.core.constant.ProgramInterestRateEnum;
import vn.com.msb.homeloan.core.constant.RefinanceLoanEnum;
import vn.com.msb.homeloan.core.constant.collateral.LoanInvestmentFixedAssetEnum;
import vn.com.msb.homeloan.core.constant.collateral.LoanPurposeDetailEnum;
import vn.com.msb.homeloan.core.constant.collateral.LoanSupplementingBusinessCapitalEnum;

@Data
@Entity
@Table(name = "loan_application_item", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanApplicationItemEntity extends BaseEntity {

  // loan_application_id
  @Column(name = "loan_application_id")
  String loanApplicationId;

  // phương thức trả nợ
  @Column(name = "debt_payment_method")
  @Enumerated(EnumType.STRING)
  DebtPaymentMethodEnum debtPaymentMethod;

  // thời gian ân hạn gốc
  @Column(name = "grace_period")
  Integer gracePeriod;

  // Chương trình áp dụng lãi suất
  @Column(name = "interest_rate_program")
  @Enumerated(EnumType.STRING)
  ProgramInterestRateEnum interestRateProgram;

  // Tên sản phẩm
  @Column(name = "product_text_code")
  @Enumerated(EnumType.STRING)
  ProductTextCodeEnum productTextCode;

  // Phương thức giải ngân
  @Column(name = "disbursement_method")
  @Enumerated(EnumType.STRING)
  DisbursementMethodEnum disbursementMethod;

  // Phương thức giải ngân khác
  @Column(name = "disbursement_method_other")
  String disbursementMethodOther;

  // Mô tả mục đích vay vốn
  @Column(name = "rm_review")
  String rmReview;

  // interest_code(Mã lãi suất)
  @Column(name = "interest_code")
  String interestCode;

  // document_number_2(Mã văn bản sản phẩm 2)
  @Column(name = "document_number_2")
  String documentNumber2;

  // Mục đích vay vốn
  @Column(name = "loan_purpose")
  @Enumerated(EnumType.STRING)
  LoanPurposeEnum loanPurpose;

  // Thời gian vay
  @Column(name = "loan_time")
  Integer loanTime;

  // Tổng nhu cầu vốn, giá trị tài sản cần mua
  @Column(name = "loan_asset_value")
  Long loanAssetValue;

  // Số tiền vay vốn
  @Column(name = "loan_amount")
  Long loanAmount;

  // Ngân hàng
  @Column(name = "beneficiary_bank")
  String beneficiaryBank;

  // Số tài khoản
  @Column(name = "beneficiary_account")
  String beneficiaryAccount;

  // Người thụ hưởng
  @Column(name = "beneficiary_full_name")
  String beneficiaryFullName;

  // Mục đích vay chi tiết
  // loan_purpose_detail
  @Column(name = "loan_purpose_detail")
  @Enumerated(EnumType.STRING)
  LoanPurposeDetailEnum loanPurposeDetail;

  // Vay bổ sung vốn kinh doanh
  // loan_supplementing_business_capital
  @Column(name = "loan_supplementing_business_capital")
  @Enumerated(EnumType.STRING)
  LoanSupplementingBusinessCapitalEnum loanSupplementingBusinessCapital;

  // Vay đầu tư tài sản cố định
  // loan_investment_fixed_asset
  @Column(name = "loan_investment_fixed_asset")
  @Enumerated(EnumType.STRING)
  LoanInvestmentFixedAssetEnum loanInvestmentFixedAsset;

  // Thời gian khế ước nhận nợ
  // debt_acknowledgment_contract_period
  @Column(name = "debt_acknowledgment_contract_period")
  Integer debtAcknowledgmentContractPeriod;

  // Là chủ doanh nghiệp tư nhân
  // is_private_business_owner
  @Column(name = "is_private_business_owner")
  Boolean isPrivateBusinessOwner;

  // Khoản vay tái cấp
  // refinance_loan
  @Column(name = "refinance_loan")
  @Enumerated(EnumType.STRING)
  RefinanceLoanEnum refinanceLoan;
}
