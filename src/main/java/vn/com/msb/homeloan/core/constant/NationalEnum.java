package vn.com.msb.homeloan.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NationalEnum {

  VIETNAM("VIETNAM", "Việt Nam"),
  OTHER("OTHER", "Khác");

  private final String code;
  private final String name;
}
