package vn.com.msb.homeloan.core.constant.collateral;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum StatusUsingEnum {

  NEW_CAR("NEW_CAR", "Ô tô mới"),
  OLD_CAR("OLD_CAR", "Ô tô cũ");

  private String code;
  private String name;

  StatusUsingEnum(String code, String name) {
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
