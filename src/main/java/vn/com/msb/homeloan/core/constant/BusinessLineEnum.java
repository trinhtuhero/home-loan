package vn.com.msb.homeloan.core.constant;

public enum BusinessLineEnum {

  SERVICES("SERVICES", "Dịch vụ"),
  TRADE("TRADE", "Thương mại"),
  MINING("MINING", "Khai khoáng"),
  CONSTRUCTION("CONSTRUCTION", "Xây dựng"),
  PRODUCTION("PRODUCTION", "Sản xuất"),
  TRANSPORT("TRANSPORT", "Vận tải"),
  EDUCATION("EDUCATION", "Giáo dục"),
  REAL_ESTATE("REAL_ESTATE", "Bất động sản"),
  ADMINISTRATION_AND_CAREER("ADMINISTRATION_AND_CAREER", "Hành chính sự nghiệp"),
  BANKING_AND_FINANCE("BANKING_AND_FINANCE", "Tài chính ngân hàng"),
  INSURANCE("INSURANCE", "Bảo hiểm"),
  OTHERS("OTHERS", "Khác");

  private String code;
  private String name;

  BusinessLineEnum(String code, String name) {
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
