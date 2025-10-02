package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AssetTypeEnum {

  REAL_ESTATE("REAL_ESTATE", "Bất động sản"),
  CAR("CAR", "Ô tô"),
  VALUABLE_PAPERS("VALUABLE_PAPERS", "Giấy tờ có giá trị"),
  OTHER("OTHER", "Khác");

  private String code;
  private String name;

  AssetTypeEnum(String code, String name) {
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

