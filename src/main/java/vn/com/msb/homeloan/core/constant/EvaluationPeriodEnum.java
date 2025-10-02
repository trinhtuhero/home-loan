package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EvaluationPeriodEnum {

  THREE_MONTHS("THREE_MONTHS", "3 Tháng"),
  SIX_MONTHS("SIX_MONTHS", "6 Tháng"),
  NINE_MONTHS("NINE_MONTHS", "9 Tháng"),
  YEAR("YEAR", "1 năm");

  private String code;
  private String name;

  EvaluationPeriodEnum(String code, String name) {
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

