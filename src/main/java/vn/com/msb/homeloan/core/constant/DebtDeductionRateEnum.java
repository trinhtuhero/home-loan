package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum DebtDeductionRateEnum {

  MINIMUM_PAYMENT("MINIMUM_PAYMENT", "Giá trị thanh toán tối thiếu - 5% Tổng dự nợ sao kê"),
  STATEMENT_BALANCE("STATEMENT_BALANCE", "Tổng dư nợ sao kê - 100% Tổng dư nợ sao kê");

  private String code;
  private String name;

  DebtDeductionRateEnum(String code, String name) {
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
