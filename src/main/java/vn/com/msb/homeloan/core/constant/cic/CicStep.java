package vn.com.msb.homeloan.core.constant.cic;

import lombok.Getter;

/**
 * CIC query error code
 */
@Getter
public enum CicStep {

  SEARCH("search"), SEARCH_CODE("searchCode");

  private final String code;

  CicStep(String code) {
    this.code = code;
  }
}
