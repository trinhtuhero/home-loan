package vn.com.msb.homeloan.core.constant;

public enum MerchantEnum {

  BAOVIET("BAOVIET"),
  BAOMINH("BAOMINH");

  private final String value;

  MerchantEnum(String value) {
    this.value = value;
  }

  public String value() {
    return this.value;
  }

}
