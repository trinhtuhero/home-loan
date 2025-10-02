package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum DebtPaymentMethodEnum {

  DEBT_PAYMENT_1("DEBT_PAYMENT_1",
      "Nợ gốc trả đều hàng tháng lãi hàng tháng tính trên dư nợ thực tế giảm dần"),
  DEBT_PAYMENT_2("DEBT_PAYMENT_2", "Nợ gốc và lãi trả đều hàng tháng"),
  DEBT_PAYMENT_3("DEBT_PAYMENT_3", "Gốc trả cuối kỳ lãi trả hàng tháng");

  private String code;
  private String name;

  DebtPaymentMethodEnum(String code, String name) {
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
