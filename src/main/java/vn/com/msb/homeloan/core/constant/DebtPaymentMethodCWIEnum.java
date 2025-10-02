package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum DebtPaymentMethodCWIEnum {

  DEBT_PAYMENT_1("DEBT_PAYMENT_1", "Gốc đều lãi theo dư nợ thực tế giảm dần"),
  DEBT_PAYMENT_2("DEBT_PAYMENT_2", "Gốc lãi cuối kỳ"),
  DEBT_PAYMENT_3("DEBT_PAYMENT_3", "Gốc cuối kỳ, lãi hàng tháng"),
  DEBT_PAYMENT_4("DEBT_PAYMENT_4", "Gốc lãi đều hàng tháng");

  private String code;
  private String name;

  DebtPaymentMethodCWIEnum(String code, String name) {
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

