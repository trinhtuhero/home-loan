package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RefinanceLoanEnum {

  B_SCORE("B_SCORE", "Tái cấp hạn mức B-Score"),
  NON_B_SCORE("NON_B_SCORE", "Tái cấp hạn mức Non B-Score");

  private String code;
  private String name;

  RefinanceLoanEnum(String code, String name) {
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
      .collect(Collectors.toMap(k -> k.getCode(), v -> v.getName()));
}
