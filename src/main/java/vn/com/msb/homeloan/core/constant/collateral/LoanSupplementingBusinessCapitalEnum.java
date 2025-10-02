package vn.com.msb.homeloan.core.constant.collateral;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LoanSupplementingBusinessCapitalEnum {

  VAY_NGAN_HAN("VAY_NGAN_HAN", "Vay ngắn hạn"),
  VAY_HAN_MUC("VAY_HAN_MUC", "Vay hạn mức"),
  VAY_TRUNG_DAI_HAN("VAY_TRUNG_DAI_HAN", "Vay trung dài hạn");

  private String code;
  private String name;

  LoanSupplementingBusinessCapitalEnum(String code, String name) {
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
