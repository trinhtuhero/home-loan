package vn.com.msb.homeloan.api.dto.response.overdraft;


import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.core.constant.DebtPaymentMethodEnum;
import vn.com.msb.homeloan.core.constant.FormOfCreditEnum;
import vn.com.msb.homeloan.core.constant.ProductTextCodeEnum;
import vn.com.msb.homeloan.core.model.LoanItemCollateralDistribution;

@Getter
@Setter
@ToString
public class CMSOverdraftResponse {

  private String uuid;

  private String loanApplicationId;

  private String overdraftPurpose;

  private Long loanAmount;

  private String overdraftSubject;

  // form_of_credit (Hình thức cấp tín dụng)
  private FormOfCreditEnum formOfCredit;

  // loan_time (Thời gian duy trì hạn mức/ thời gian vay)
  private Integer loanTime;

  // interest_code (Mã lãi suất thấu chi)
  private String interestCode;

  // margin (Biên độ)
  private Float margin;

  // payment_account_number (Số tài khoản thanh toán)
  private String paymentAccountNumber;

  // debt_payment_method (phương thức trả nợ)
  private DebtPaymentMethodEnum debtPaymentMethod;

  // loan_asset_value (Tổng nhu cầu vốn)
  private Long loanAssetValue;

  // product_text_code (Tên sản phẩm)
  private ProductTextCodeEnum productTextCode;

  // document_number_2 (Mã văn bản sản phẩm 2)
  private String documentNumber2;

  private Float lTDPercent;

  private Long equityCapital;

  List<LoanItemCollateralDistribution> loanItemCollateralDistributions;
}
