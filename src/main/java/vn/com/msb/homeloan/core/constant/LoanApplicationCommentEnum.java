package vn.com.msb.homeloan.core.constant;

public enum LoanApplicationCommentEnum {

  // Thông tin khách hàng
  CUSTOMER_INFO("CUSTOMER_INFO", "Thông tin khách hàng"),

  // Thông tin vợ chồng
  MARRIED_INFO("MARRIED_INFO", "Thông tin vợ chồng"),

  // Thông tin người liên hệ
  CONTACT_INFO("CONTACT_INFO", "Thông tin người liên hệ"),

  // Người đồng trả nợ
  PAYER_INFO("PAYER_INFO", "Người đồng trả nợ"),

  // Thông tin thu nhập từ lương
  SALARY_INCOME_INFO("SALARY_INCOME_INFO", "Thông tin thu nhập từ lương"),

  // Thông tin thu nhập từ Cá nhân/Hộ kinh doanh
  BUSINESS_INCOME_INFO("BUSINESS_INCOME_INFO", "Thông tin thu nhập từ Cá nhân/Hộ kinh doanh"),

  // Thông tin thu nhập khác
  OTHER_INCOME_INFO("OTHER_INCOME_INFO", "Thông tin thu nhập khác"),

  // Thông tin tài sản bảo đảm
  COLLATERAL_INFO("COLLATERAL_INFO", "Thông tin tài sản bảo đảm"),

  // Thông tin khoản vay
  LOAN_INFO("LOAN_INFO", "Thông tin khoản vay");

  private String code;
  private String name;

  LoanApplicationCommentEnum(String code, String name) {
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
