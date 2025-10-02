package vn.com.msb.homeloan.core.constant;

public enum CompanyEnum {

  MSB("MSB", "MSB"),

  OTHERS("OTHERS", "Khác MSB");

  private String code;
  private String name;

  CompanyEnum(String code, String name) {
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

