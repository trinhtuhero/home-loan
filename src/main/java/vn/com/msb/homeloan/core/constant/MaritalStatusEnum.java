package vn.com.msb.homeloan.core.constant;

public enum MaritalStatusEnum {
  MARRIED("MARRIED", "Đã kết hôn"),
  SINGLE("SINGLE", "Độc thân"),
  DIVORCE("DIVORCE", "Ly hôn"),
  WIDOW("WIDOW", "Góa vợ/Góa chồng");

  private String code;
  private String name;

  MaritalStatusEnum(String code, String name) {
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
