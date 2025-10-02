package vn.com.msb.homeloan.core.constant;

public enum CollateralTypeEnum {

  ND("ND", "Nhà đất"),
  CC("CC", "Chung cư"),
  PTVT("PTVT", "Phương tiện vận tải"),
  GTCG("GTCG", "Giấy tờ có giá"),
  OTHERS("OTHERS", "Khác");

  private String code;
  private String name;

  CollateralTypeEnum(String code, String name) {
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
