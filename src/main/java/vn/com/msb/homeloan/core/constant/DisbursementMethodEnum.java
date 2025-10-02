package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum DisbursementMethodEnum {
  CASH("CASH", "Tiền mặt"),
  TRANSFER("TRANSFER", "Chuyển khoản"),
  BLOCKAGE("BLOCKAGE", "Phong tỏa"),
  TRANSFER_OR_CASH("TRANSFER_OR_CASH", "Chuyển khoản/ Tiền mặt"),
  OTHER("OTHER", "Khác"),
  ;

  private String code;
  private String name;

  DisbursementMethodEnum(String code, String name) {
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

  public static Map<String, DisbursementMethodEnum> toMapExcludeOther = Stream.of(values())
      .filter(val -> !val.equals(OTHER)).collect(Collectors.toMap(k -> k.getCode(), v -> v));
}
