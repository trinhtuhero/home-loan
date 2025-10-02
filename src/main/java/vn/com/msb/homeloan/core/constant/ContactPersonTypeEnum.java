package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ContactPersonTypeEnum {
  WIFE("WIFE", "Vợ"),
  HUSBAND("HUSBAND", "Chồng"),
  FATHER("FATHER", "Bố ruột / bố nuôi hợp pháp"),
  FATHER_IN_LAW("FATHER_IN_LAW", "Bố vợ/ chồng"),
  MOTHER("MOTHER", "Mẹ ruột/ mẹ nuôi hợp pháp"),
  MOTHER_IN_LAW("MOTHER_IN_LAW", "Mẹ vợ/ chồng"),
  SIBLINGS("SIBLINGS", "Anh/chị/em ruột"),
  SIBLINGS_IN_LAW("SIBLINGS_IN_LAW", "Anh/chị/em của vợ/chồng"),
  SON("SON", "Con ruột/ con nuôi hợp pháp"),
  STEP_CHILD("STEP_CHILD", "Con riêng"),
  HOUSEHOLD_MEM("HOUSEHOLD_MEM", "Thành viên Hộ gia đình"),
  CAPITAL_CONTRIBUTING_MEM("CAPITAL_CONTRIBUTING_MEM", "Thành viên góp vốn HKD/DNTN"),
  NEIGHTBOR("NEIGHTBOR", "Hàng xóm"),
  FRIEND("FRIEND", "Bạn bè"),
  FELLOW_SHIP("FELLOW_SHIP", "Đồng nghiệp"),
  OTHERS("OTHERS", "Khác");

  private String code;
  private String name;

  ContactPersonTypeEnum(String code, String name) {
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
