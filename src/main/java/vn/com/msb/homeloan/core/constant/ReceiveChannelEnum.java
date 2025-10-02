package vn.com.msb.homeloan.core.constant;

public enum ReceiveChannelEnum {

  LDP("LDP", "Landing page"),
  CMS("CMS", "CMS"),
  CTV("CTV", "Sàn/CTV"),
  OTHER("OTHER", "Khác");

  private String code;
  private String name;

  ReceiveChannelEnum(String code, String name) {
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
