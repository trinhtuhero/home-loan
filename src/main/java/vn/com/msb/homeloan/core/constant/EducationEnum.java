package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EducationEnum {

  POSTGRADUATE("POSTGRADUATE", "Sau đại học"),
  UNIVERSITY("UNIVERSITY", "Đại học"),
  BACHELOR("BACHELOR", "Cao đẳng"),
  INTERMEDIATE("INTERMEDIATE", "Trung cấp"),
  FINISH_SECONDARY("FINISH_SECONDARY", "THPT");

  private String code;
  private String name;

  EducationEnum(String code, String name) {
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
