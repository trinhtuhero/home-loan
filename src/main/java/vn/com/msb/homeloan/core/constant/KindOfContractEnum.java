package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum KindOfContractEnum {

  KHONG_THOI_HAN("KHONG_THOI_HAN", "Không thời hạn"),
  BA_NAM("BA_NAM", "3 năm"),
  MOT_DEN_BA_NAM("MOT_DEN_BA_NAM", "1- <3 năm"),
  SAU_THANG_DEN_MOT_NAM("SAU_THANG_DEN_MOT_NAM", "6 tháng - <1 năm"),
  DUOI_6_THANG("DUOI_6_THANG", "< 6 tháng"),
  KHONG_CO("KHONG_CO", "Không có HĐLĐ");

  private String code;
  private String name;

  KindOfContractEnum(String code, String name) {
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

