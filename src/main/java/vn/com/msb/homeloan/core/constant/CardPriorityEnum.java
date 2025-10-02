package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CardPriorityEnum {

  PRIMARY_CARD("PRIMARY_CARD", "Thẻ chính"),
  SECONDARY_CARD("SECONDARY_CARD", "Thẻ phụ");

  private String code;
  private String name;

  CardPriorityEnum(String code, String name) {
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
