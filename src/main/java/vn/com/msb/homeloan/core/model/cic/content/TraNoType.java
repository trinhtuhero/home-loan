package vn.com.msb.homeloan.core.model.cic.content;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * CIC query result status
 */
public enum TraNoType {
  MSB_SECURE("MsbTsbd"),
  MSB_UNSECURE("MsbKhongTsbd"),
  NON_MSB_SECURE("OtherTsbd"),
  NON_MSB_UNSECURE("OtherKhongTsbd");

  private final String value;

  TraNoType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
