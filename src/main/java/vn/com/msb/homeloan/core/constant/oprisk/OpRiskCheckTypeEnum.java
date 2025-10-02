package vn.com.msb.homeloan.core.constant.oprisk;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum OpRiskCheckTypeEnum {

  CHECK_P("CHECK_P", "Gọi API checkP"),
  CHECK_C("CHECK_C", "Gọi API checkC");

  private String code;
  private String name;

  OpRiskCheckTypeEnum(String code, String name) {
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

