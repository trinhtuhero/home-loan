package vn.com.msb.homeloan.core.constant.oprisk;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NonNull;

public enum OpRiskCollateralTypeEnum {

  BDS("BDS", "1", "Bất động sản"),
  PTVT("PTVT", "2", "Phương tiện vận tải"),
  GTCG("GTCG", "3", "Tiền/Chứng chỉ tiền gửi/Tiền gửi/Giấy tờ có giá");

  private final String code;
  private final String value;
  private final String name;

  OpRiskCollateralTypeEnum(String code, String value, String name) {
    this.name = name;
    this.value = value;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public String getCode() {
    return code;
  }

  public static Map<String, String> toMap = Stream.of(values())
      .collect(Collectors.toMap(k -> k.getCode(), v -> v.getCode()));

  public static OpRiskCollateralTypeEnum ofValue(@NonNull final String value) {
    return Stream.of(OpRiskCollateralTypeEnum.values())
        .filter(op -> op.value.equalsIgnoreCase(value))
        .findFirst().orElse(null);
  }
}

