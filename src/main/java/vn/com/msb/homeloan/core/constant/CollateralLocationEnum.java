package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CollateralLocationEnum {

  MD("MD", "Mặt đường"),
  MN35("MN35", "Mặt ngõ lớn hơn hoặc bằng 3.5m"),
  MN5("MN5", "Mặt ngõ lớn hơn hoặc bằng 5m"),
  OTHERS("OTHERS", "Khác");

  private String code;
  private String name;

  CollateralLocationEnum(String code, String name) {
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
