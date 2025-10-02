package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum OwnershipTypeEnum {

  PART_OWNER("PART_OWNER", "Chủ sở hữu một phần"),
  ENTIRE_OWNER("ENTIRE_OWNER", "Chủ sở hữu toàn bộ"),
  RENTAL("RENTAL", "Đi thuê"),
  BORROWING("BORROWING", "Đi mượn");

  private String code;
  private String name;

  OwnershipTypeEnum(String code, String name) {
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

