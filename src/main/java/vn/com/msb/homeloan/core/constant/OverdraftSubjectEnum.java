package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum OverdraftSubjectEnum {

  CUSTOMER("CUSTOMER", "Khách hàng"),
  // WIFE_HUSBAND("WIFE_HUSBAND", "Vợ/chồng Khách hàng"),

  ;

  private String code;
  private String name;

  OverdraftSubjectEnum(String code, String name) {
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

