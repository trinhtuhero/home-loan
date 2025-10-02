package vn.com.msb.homeloan.core.constant.oprisk;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum OpRiskStatusEnum {

  SUCCESS("SUCCESS", "Gọi API thành công, có thông tin blacklist"),
  FAIL("FAIL", "Gọi API lỗi"),
  NO_DATA("NO_DATA", "Gọi API thành công nhưng không có thông tin blacklist");

  private String code;
  private String name;

  OpRiskStatusEnum(String code, String name) {
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

