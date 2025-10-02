package vn.com.msb.homeloan.core.constant;

public enum CollateralOwnerMapTypeEnum {

  ME("ME", "TS sở hữu của riêng KH"),
  COUPLE("COUPLE", "TS sở hữu của cả 2 vợ chồng KH"),
  THIRD_PARTY("THIRD_PARTY", "TS sở hữu của bên thứ 3"),
  LOAN_PAYER("LOAN_PAYER", "Người đồng trả nợ");

  private String code;
  private String name;

  CollateralOwnerMapTypeEnum(String code, String name) {
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
