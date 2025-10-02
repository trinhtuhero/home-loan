package vn.com.msb.homeloan.core.constant;

public enum ProductCodeEnum {

  BV_HEALTH_CARE("BV_HEALTH_CARE", "Bảo hiểm sức khỏe Bảo Việt An Gia"),
  BM_CAR_CARE("BM_CAR_CARE", "Bảo hiểm ô tô Bảo Minh");

  private final String code;
  private final String value;

  ProductCodeEnum(String code, String value) {
    this.code = code;
    this.value = value;
  }

  public String code() {
    return this.code;
  }

  public String value() {
    return this.value;
  }


}
