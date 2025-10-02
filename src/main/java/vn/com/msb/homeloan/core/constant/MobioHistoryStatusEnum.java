package vn.com.msb.homeloan.core.constant;

public enum MobioHistoryStatusEnum {

  SUCCESS("SUCCESS", "Thành công"),

  FAILED("FAILED", "Thất bại");

  private String code;
  private String name;

  MobioHistoryStatusEnum(String code, String name) {
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
