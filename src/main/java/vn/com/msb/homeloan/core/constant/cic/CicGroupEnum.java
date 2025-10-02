package vn.com.msb.homeloan.core.constant.cic;

import java.util.Comparator;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CicGroupEnum {
  NHOM_1(1, "Nhóm 1", "NHOM_1"),
  NHOM_2(2, "Nhóm 2", "NHOM_2"),
  NHOM_3(3, "Nhóm 3", "NHOM_3"),
  NHOM_4(4, "Nhóm 4", "NHOM_4"),
  NHOM_5(5, "Nhóm 5", "NHOM_5");

  private final int value;
  private final String description;
  private final String code;

  public static CicGroupEnum defaultGroup() {
    return NHOM_1;
  }

  public static CicGroupEnum of(final int value) {
    return Stream.of(CicGroupEnum.values())
        .filter(group -> group.value == value)
        .findFirst().orElse(null);
  }

  public static CicGroupEnum ofDescription(@NonNull final String description) {
    return Stream.of(CicGroupEnum.values())
        .filter(group -> group.description.equalsIgnoreCase(description))
        .findFirst().orElse(null);
  }

  public static CicGroupEnum max(@NonNull final CicGroupEnum... list) {
    return Stream.of(list).max(Comparator.comparingInt(CicGroupEnum::getValue))
        .orElse(CicGroupEnum.NHOM_1);
  }

  @javax.persistence.Converter(autoApply = true)
  public static class Converter implements AttributeConverter<CicGroupEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CicGroupEnum cicGroup) {
      return (cicGroup == null) ? null : cicGroup.getValue();
    }

    @Override
    public CicGroupEnum convertToEntityAttribute(Integer integer) {
      return (integer == null) ? null : CicGroupEnum.of(integer);
    }
  }

}
