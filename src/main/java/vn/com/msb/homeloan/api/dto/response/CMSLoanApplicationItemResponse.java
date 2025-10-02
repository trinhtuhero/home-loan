package vn.com.msb.homeloan.api.dto.response;

import lombok.*;
import vn.com.msb.homeloan.core.constant.DebtPaymentMethodEnum;
import vn.com.msb.homeloan.core.constant.LoanPurposeEnum;
import vn.com.msb.homeloan.core.constant.ProductTextCodeEnum;
import vn.com.msb.homeloan.core.constant.ProgramInterestRateEnum;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CMSLoanApplicationItemResponse {

  String uuid;

  String loanApplicationId;

  DebtPaymentMethodEnum debtPaymentMethod;

  Integer gracePeriod;

  ProgramInterestRateEnum interestRateProgram;

  ProductTextCodeEnum productTextCode;

  String disbursementMethod;

  String disbursementMethodOther;

  String rmReview;

  String interestCode;

  String documentNumber2;

  LoanPurposeEnum loanPurpose;

  Integer loanTime;

  Long loanAssetValue;

  Long loanAmount;

  String beneficiaryBank;

  String beneficiaryAccount;

  String beneficiaryFullName;

  // Mục đích vay chi tiết
  String loanPurposeDetail;

  // Vay bổ sung vốn kinh doanh
  String loanSupplementingBusinessCapital;

  // Vay đầu tư tài sản cố định
  String loanInvestmentFixedAsset;

  // Thời gian khế ước nhận nợ
  Integer debtAcknowledgmentContractPeriod;

  // Là chủ doanh nghiệp tư nhân
  Boolean isPrivateBusinessOwner;

  // Khoản vay tái cấp
  String refinanceLoan;

  List<LoanItemCollateralDistributeResponse> loanItemCollateralDistributions;
}
