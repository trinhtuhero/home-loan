package vn.com.msb.homeloan.core.constant.collateral;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LoanPurposeDetailEnum {

  VAY_BO_SUNG_VON_KINH_DOANH("VAY_BO_SUNG_VON_KINH_DOANH", "Vay bổ sung vốn kinh doanh"),
  VAY_DAU_TU_TAI_SAN_CO_DINH("VAY_DAU_TU_TAI_SAN_CO_DINH", "Vay đầu tư tài sản cố định");

  private String code;
  private String name;

  LoanPurposeDetailEnum(String code, String name) {
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
