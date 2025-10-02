package vn.com.msb.homeloan.core.constant;

public enum LoanCurrentStepEnum {

  LOAN_CUSTOMER("LOAN_CUSTOMER", "Khách hàng"),
  LOAN_INFORMATION("LOAN_INFORMATION", "Thông tin khoản vay"),
  LOAN_INCOME("LOAN_INCOME", "Thông tin nguồn thu"),
  LOAN_COLLATERAL("LOAN_COLLATERAL", "Thông tin tài sản"),
  LOAN_FILE("LOAN_FILE", "Thông tin hồ sơ"),
  LOAN_SUBMIT("LOAN_SUBMIT", "Submit"),
  ;

  private String code;
  private String name;

  LoanCurrentStepEnum(String code, String name) {
    this.name = name;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }
}
