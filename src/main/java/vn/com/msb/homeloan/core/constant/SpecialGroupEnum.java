package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SpecialGroupEnum {

  TALENT_POOL("TALENT_POOL", "Talent pool", "Talentpool"),
  KEY_PERSON("KEY_PERSON", "Key person", "Keyperson"),
  OTHERS("OTHERS", "Khác", "");

  private String code;
  private String name;
  private String tt;

  SpecialGroupEnum(String code, String name, String tt) {
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

