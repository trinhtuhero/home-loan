package vn.com.msb.homeloan.core.constant;

public enum SignatureLevelEnum {

  GĐKD("GĐKD", "GĐ kinh doanh"),

  GĐ_ĐVKD("GĐ_ĐVKD", "GĐ ĐVKD"),

  GĐ_HUB("GĐ_HUB", "GĐ HUB"),

  GĐ_VUNG("GĐ_VUNG", "GĐ Vùng"),

  TN_TD("TN_TD", "TN tín dụng");

  private String code;
  private String name;

  SignatureLevelEnum(String code, String name) {
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
