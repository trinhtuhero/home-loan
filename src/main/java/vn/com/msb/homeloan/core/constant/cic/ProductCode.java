package vn.com.msb.homeloan.core.constant.cic;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * CIC template code
 */
public enum ProductCode {

  TONG_HOP("F5"), TAI_SAN_DAM_BAO("F6");

  private final String value;

  ProductCode(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

}
