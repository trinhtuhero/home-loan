package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum KpiRateEnum {

  E("E", "Xếp loại E", "E"),
  S("S", "Xếp loại S", "S"),
  A_PLUS("A_PLUS", "Xếp loại A+", "A+"),
  A("A", "Xếp loại A", "A"),
  B("B", "Xếp loại B", "B");

  private String code;
  private String name;
  private String tt;

  KpiRateEnum(String code, String name, String tt) {
    this.name = name;
    this.code = code;
    this.tt = tt;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public String getTt() {
    return tt;
  }

  public static Map<String, String> toMap = Stream.of(values())
      .collect(Collectors.toMap(k -> k.getCode(), v -> v.getCode()));
}

