package vn.com.msb.homeloan.core.constant;

public enum LoanProductEnum {

  HOME("HOME", "Home");

  private String code;
  private String name;

  LoanProductEnum(String code, String name) {
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
