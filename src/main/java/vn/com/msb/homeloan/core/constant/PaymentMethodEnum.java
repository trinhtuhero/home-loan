package vn.com.msb.homeloan.core.constant;

public enum PaymentMethodEnum {

  MSB("MSB", "Chuyển khoản qua MSB"),

  OTHERS("OTHERS", "Chuyển khoản qua TCTD khác"),

  CASH("CASH", "Tiền mặt");

  private String code;
  private String name;

  PaymentMethodEnum(String code, String name) {
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

