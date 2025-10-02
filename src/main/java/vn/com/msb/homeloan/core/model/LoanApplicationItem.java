package vn.com.msb.homeloan.core.model;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.DebtPaymentMethodEnum;
import vn.com.msb.homeloan.core.constant.DisbursementMethodEnum;
import vn.com.msb.homeloan.core.constant.LoanPurposeEnum;
import vn.com.msb.homeloan.core.constant.ProductTextCodeEnum;
import vn.com.msb.homeloan.core.constant.ProgramInterestRateEnum;
import vn.com.msb.homeloan.core.constant.RefinanceLoanEnum;
import vn.com.msb.homeloan.core.constant.collateral.LoanInvestmentFixedAssetEnum;
import vn.com.msb.homeloan.core.constant.collateral.LoanPurposeDetailEnum;
import vn.com.msb.homeloan.core.constant.collateral.LoanSupplementingBusinessCapitalEnum;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanApplicationItem {

  String uuid;

  String loanApplicationId;

  DebtPaymentMethodEnum debtPaymentMethod;

  Integer gracePeriod;

  ProgramInterestRateEnum interestRateProgram;

  ProductTextCodeEnum productTextCode;

  DisbursementMethodEnum disbursementMethod;

  // Phương thức giải ngân khác
  String disbursementMethodOther;

  String rmReview;

  String interestCode;

  String documentNumber2;

  LoanPurposeEnum loanPurpose;

  Integer loanTime;

  Long loanAssetValue;

  Long loanAmount;

  String beneficiaryBank;

  String beneficiaryBankName;

  String beneficiaryBankShortName;

  String beneficiaryAccount;

  String beneficiaryFullName;

  // Mục đích vay chi tiết
  LoanPurposeDetailEnum loanPurposeDetail;

  // Vay bổ sung vốn kinh doanh
  LoanSupplementingBusinessCapitalEnum loanSupplementingBusinessCapital;

  // Vay đầu tư tài sản cố định
  LoanInvestmentFixedAssetEnum loanInvestmentFixedAsset;

  // Thời gian khế ước nhận nợ
  Integer debtAcknowledgmentContractPeriod;

  // Là chủ doanh nghiệp tư nhân
  Boolean isPrivateBusinessOwner;

  // Khoản vay tái cấp
  RefinanceLoanEnum refinanceLoan;

  List<LoanItemCollateralDistribution> loanItemCollateralDistributions;

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    LoanApplicationItem that = (LoanApplicationItem) o;
    return (uuid != null && that.uuid != null && HomeLoanUtil.compare(uuid, that.uuid))
        && HomeLoanUtil.compare(loanPurpose, that.loanPurpose)
        && HomeLoanUtil.compare(loanAssetValue, that.loanAssetValue)
        && HomeLoanUtil.compare(loanAmount, that.loanAmount)
        && HomeLoanUtil.compare(loanTime, that.loanTime);
  }
}
