package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CustomerSegmentEnum {

  YP("YP", "thu nhập từ lương 8-40 triệu", "YP"),
  HP("HP", "thu nhập từ lương > 40 triệu", "HP"),
  SBO("SBO", "thu nhập từ kinh doanh 8-40 triệu", "SBO"),
  BO("BO", "thu nhập từ kinh doanh > 40 triệu", "BO"),
  M_FIRST("M_FIRST", "khách hàng ưu tiên", "Mfirst"),
  OTHER("OTHER", "chưa được định danh", "Khác");

  private String code;
  private String name;
  private String tt;

  CustomerSegmentEnum(String code, String name, String tt) {
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
