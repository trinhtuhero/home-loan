package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum InterestCodeEnum {

  INTEREST_CODE_6003("INTEREST_CODE_6003", "Band 1"),
  INTEREST_CODE_6004("INTEREST_CODE_6004", "Band 2"),
  INTEREST_CODE_6005("INTEREST_CODE_6005", "Band 3");

  private String code;
  private String name;

  InterestCodeEnum(String code, String name) {
    this.name = name;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public static Map<String, String> toMap = Stream.of(values())
      .collect(Collectors.toMap(k -> k.getCode(), v -> v.getCode()));
}

