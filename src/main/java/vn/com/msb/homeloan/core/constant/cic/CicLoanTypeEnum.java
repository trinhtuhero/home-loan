package vn.com.msb.homeloan.core.constant.cic;

import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CicLoanTypeEnum {
  NGAN_HAN_VND(1, "01", "Vay ngắn hạn vnd"),
  TRUNG_HAN_VND(2, "02", "Vắn trung hạn vnd"),
  DAI_HAN_VND(3, "03", "Vay dài hạn vnd"),
  KHAC_VND(13, "13", "Khoản vay khác vnd"),
  BO_SUNG_VND(99, "99", "Khoản vay bổ sung vnd"),

  NGAN_HAN_USD(6, "06", "Vay ngắn hạn usd"),
  TRUNG_HAN_USD(7, "07", "Vắn trung hạn usd"),
  DAI_HAN_USD(8, "08", "Vay dài hạn usd"),
  KHAC_USD(9, "09", "Khoản vay khác usd"),
  BO_SUNG_USD(10, "10", "Khoản vay bổ sung usd");

  private final int type;
  private final String code;
  private final String description;

  public static CicLoanTypeEnum of(final int type) {
    return Stream.of(CicLoanTypeEnum.values())
        .filter(var -> var.type == type)
        .findFirst().orElse(null);
  }

  public static CicLoanTypeEnum ofCode(final String code) {
    return Stream.of(CicLoanTypeEnum.values())
        .filter(var -> var.code.equalsIgnoreCase(code))
        .findFirst().orElse(null);
  }

  @javax.persistence.Converter(autoApply = true)
  public static class Converter implements AttributeConverter<CicLoanTypeEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CicLoanTypeEnum type) {
      return (type == null) ? null : type.getType();
    }

    @Override
    public CicLoanTypeEnum convertToEntityAttribute(Integer integer) {
      return (integer == null) ? null : CicLoanTypeEnum.of(integer);
    }
  }

}
