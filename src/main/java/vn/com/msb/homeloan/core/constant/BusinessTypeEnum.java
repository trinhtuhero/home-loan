package vn.com.msb.homeloan.core.constant;

public enum BusinessTypeEnum {

  WITH_REGISTRATION("WITH_REGISTRATION", "Hộ kinh doanh có ĐKKD"),

  ENTERPRISE("ENTERPRISE", "Doanh nghiệp"),

  WITHOUT_REGISTRATION("WITHOUT_REGISTRATION", "Hộ kinh doanh không có ĐKKD");

  private String code;
  private String name;

  BusinessTypeEnum(String code, String name) {
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
