package vn.com.msb.homeloan.core.model.mapper;

import io.jsonwebtoken.lang.Objects;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.CommonIncomeEntity;
import vn.com.msb.homeloan.core.model.CommonIncome;

@Mapper
public abstract class CommonIncomeMapper {

  public static final CommonIncomeMapper INSTANCE = Mappers.getMapper(CommonIncomeMapper.class);

  @Mapping(source = "selectedIncomes", target = "selectedIncomes", qualifiedByName = "selectedIncomesFromString2Array")
  public abstract CommonIncome toModel(CommonIncomeEntity entity);

  public abstract List<CommonIncome> toModels(List<CommonIncomeEntity> entities);

  @Mapping(source = "selectedIncomes", target = "selectedIncomes", qualifiedByName = "selectedIncomesArray2String")
  public abstract CommonIncomeEntity toEntity(CommonIncome model);

  public abstract List<CommonIncomeEntity> toEntities(List<CommonIncome> models);

  // entity to model
  @Named("selectedIncomesFromString2Array")
  public static String[] selectedIncomesFromString2Array(String selectedIncomes) {
    if (StringUtils.isEmpty(selectedIncomes)) {
      return new String[0];
    }
    return selectedIncomes.split(", ");
  }

  // model to entity
  @Named("selectedIncomesArray2String")
  public static String selectedIncomesArray2String(String[] selectedIncomes) {
    if (Objects.isEmpty(selectedIncomes)) {
      return null;
    }
    return String.join(", ", selectedIncomes);
  }
}
