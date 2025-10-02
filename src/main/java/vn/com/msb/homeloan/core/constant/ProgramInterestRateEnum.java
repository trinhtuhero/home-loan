package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ProgramInterestRateEnum {

  PROGRAM_INTEREST_RATE_1("PROGRAM_INTEREST_RATE_1", "Theo lãi suất KH rủi ro đặc biệt"),
  PROGRAM_INTEREST_RATE_2("PROGRAM_INTEREST_RATE_2", "Theo quy định MSB"),
  PROGRAM_INTEREST_RATE_3("PROGRAM_INTEREST_RATE_3",
      "Theo chương trình ưu đãi dành cho đối tác BĐS");

  private String code;
  private String name;

  ProgramInterestRateEnum(String code, String name) {
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

