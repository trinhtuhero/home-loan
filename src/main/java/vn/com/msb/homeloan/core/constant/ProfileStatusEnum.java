package vn.com.msb.homeloan.core.constant;

public enum ProfileStatusEnum {

  ACTIVE("ACTIVE", "Active"),
  INACTIVE("INACTIVE", "Inactive");

  private String code;
  private String name;

  ProfileStatusEnum(String code, String name) {
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
