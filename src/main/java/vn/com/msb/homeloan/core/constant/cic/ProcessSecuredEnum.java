package vn.com.msb.homeloan.core.constant.cic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessSecuredEnum {
  SECURED("S"),
  UNSECURED("U");

  private final String code;

  public static boolean isSecured(String property) {
    return SECURED.getCode().equalsIgnoreCase(property);
  }
}
