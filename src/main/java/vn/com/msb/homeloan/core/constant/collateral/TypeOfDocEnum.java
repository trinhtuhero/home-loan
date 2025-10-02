package vn.com.msb.homeloan.core.constant.collateral;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TypeOfDocEnum {

  ĐKX("ĐKX", "Đăng ký xe"),
  HĐMB("HĐMB", "Hợp đồng mua bán"),
  ;

  private String code;
  private String name;

  TypeOfDocEnum(String code, String name) {
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
