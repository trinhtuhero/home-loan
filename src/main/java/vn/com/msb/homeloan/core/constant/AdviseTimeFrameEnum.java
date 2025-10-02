package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AdviseTimeFrameEnum {

  EARLY_POSSIBLE("EARLY_POSSIBLE", "Sớm nhất có thể"),
  FROM_8_TO_12("FROM_8_TO_12", "08:00-12:00"),
  FROM_13_TO_16("FROM_13_TO_16", "13:00-16:00"),
  FROM_16_TO_20("FROM_16_TO_20", "16:00-20:00"),

  ;

  private String code;
  private String name;

  AdviseTimeFrameEnum(String code, String name) {
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
