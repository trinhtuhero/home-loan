package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CreditCardObjectEnum {

  CUSTOMER("CUSTOMER", "Khách hàng"),
  WIFE_HUSBAND("WIFE_HUSBAND", "Vợ/chồng Khách hàng");

  private String code;
  private String name;

  CreditCardObjectEnum(String code, String name) {
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

  public static CreditCardObjectEnum asEnum(String str) {
    for (CreditCardObjectEnum me : CreditCardObjectEnum.values()) {
      if (me.getCode().equalsIgnoreCase(str))
        return me;
    }
    return null;
  }
}

