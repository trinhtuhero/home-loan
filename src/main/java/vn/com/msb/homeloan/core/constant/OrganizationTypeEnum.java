package vn.com.msb.homeloan.core.constant;

public enum OrganizationTypeEnum {

  DVKD("DVKD", "Đơn vị kinh doanh"),
  AREA("AREA", "Vùng");

  private String code;
  private String name;

  OrganizationTypeEnum(String code, String name) {
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
