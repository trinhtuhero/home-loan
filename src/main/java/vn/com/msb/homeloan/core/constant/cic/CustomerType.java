package vn.com.msb.homeloan.core.constant.cic;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * Customer type
 */
@Getter
public enum CustomerType {

  BUSINESS("1"), INDIVIDUAL("2");

  private final String value;

  CustomerType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }


}
