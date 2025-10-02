package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BandEnum {

  BAND_1("BAND_1", "Band 1"),
  BAND_2("BAND_2", "Band 2"),
  BAND_3("BAND_3", "Band 3"),
  BAND_4("BAND_4", "Band 4"),
  BAND_5("BAND_5", "Band 5"),
  BAND_6("BAND_6", "Band 6"),
  BAND_7("BAND_7", "Band 7"),
  BAND_8("BAND_8", "Band 8"),
  BAND_9("BAND_9", "Band 9");

  private String code;
  private String name;

  BandEnum(String code, String name) {
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

