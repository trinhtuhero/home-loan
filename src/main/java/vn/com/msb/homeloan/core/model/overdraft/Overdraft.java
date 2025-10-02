package vn.com.msb.homeloan.core.model.overdraft;

import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.core.constant.DebtPaymentMethodEnum;
import vn.com.msb.homeloan.core.constant.FormOfCreditEnum;
import vn.com.msb.homeloan.core.constant.OverdraftPurposeEnum;
import vn.com.msb.homeloan.core.constant.OverdraftSubjectEnum;
import vn.com.msb.homeloan.core.constant.ProductTextCodeEnum;
import vn.com.msb.homeloan.core.model.LoanItemCollateralDistribution;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Overdraft {

  String uuid;

  // loan_application_id
  String loanApplicationId;

  // overdraft_purpose (Mục đích vay thấu chi)
  OverdraftPurposeEnum overdraftPurpose;

  // loan_amount (Hạn mức vay thấu chi)
  Long loanAmount;

  // overdraft_subject (Đối tượng cấp thấu chi)
  OverdraftSubjectEnum overdraftSubject;

  // form_of_credit (Hình thức cấp tín dụng)
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
  DebtPaymentMethodEnum debtPaymentMethod;

  // loan_asset_value (Tổng nhu cầu vốn)
  Long loanAssetValue;

  // product_text_code (Tên sản phẩm)
  ProductTextCodeEnum productTextCode;

  // document_number_2 (Mã văn bản sản phẩm 2)
  String documentNumber2;

  List<LoanItemCollateralDistribution> loanItemCollateralDistributions;

  Float lTDPercent;

  Long equityCapital;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Overdraft)) {
      return false;
    }
    Overdraft overdraft = (Overdraft) o;
    return (uuid != null && overdraft.uuid != null
        && HomeLoanUtil.compare(loanApplicationId, overdraft.loanApplicationId)
        && HomeLoanUtil.compare(overdraftPurpose, overdraft.overdraftPurpose)
        && HomeLoanUtil.compare(loanAmount, overdraft.loanAmount)
        && HomeLoanUtil.compare(overdraftSubject, overdraft.overdraftSubject)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }
}
