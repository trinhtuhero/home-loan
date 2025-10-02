package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RecognitionMethod1Enum {

  ACTUALLY_RECEIVED("ACTUALLY_RECEIVED", "Thực nhận"),
  EXCHANGE("EXCHANGE", "Quy đổi"),
  DECLARATION("DECLARATION", "Kê khai");

  private String code;
  private String name;

  RecognitionMethod1Enum(String code, String name) {
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

