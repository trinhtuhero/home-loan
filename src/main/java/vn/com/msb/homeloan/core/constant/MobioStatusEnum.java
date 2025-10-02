package vn.com.msb.homeloan.core.constant;

public enum MobioStatusEnum {

  SUCCESS("SUCCESS", "Thành công"),

  FAILED("FAILED", "Thất bại"),

  WAITING("WAITING", "Đợi");

  private String code;
  private String name;

  MobioStatusEnum(String code, String name) {
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
