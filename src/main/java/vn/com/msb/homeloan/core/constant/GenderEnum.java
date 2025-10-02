package vn.com.msb.homeloan.core.constant;

public enum GenderEnum {

  MALE("MALE", "Nam"),

  FEMALE("FEMALE", "Nữ"),

  OTHERS("OTHERS", "Khác");

  private String code;
  private String name;

  GenderEnum(String code, String name) {
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
