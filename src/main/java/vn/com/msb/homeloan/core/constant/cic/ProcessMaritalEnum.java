package vn.com.msb.homeloan.core.constant.cic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessMaritalEnum {
  UNMARRIED("UNMARRIED"),
  MARRIED("MARRIED"),
  DIVORCE("DIVORCE"),
  WIDOW("WIDOW");

  private final String code;
}
