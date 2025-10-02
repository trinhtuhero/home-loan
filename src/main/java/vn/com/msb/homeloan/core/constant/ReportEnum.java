package vn.com.msb.homeloan.core.constant;

import lombok.Getter;

@Getter
public enum ReportEnum {

  TAT("TAT"),

  FEEDBACK("FEEDBACK");

  private final String apiCode;

  ReportEnum(String apiCode) {
    this.apiCode = apiCode;
  }

}
