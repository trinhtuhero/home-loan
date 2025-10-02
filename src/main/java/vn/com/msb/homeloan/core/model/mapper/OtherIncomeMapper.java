package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.OtherIncomeEntity;
import vn.com.msb.homeloan.core.model.OtherIncome;

@Mapper
public interface OtherIncomeMapper {

  OtherIncomeMapper INSTANCE = Mappers.getMapper(OtherIncomeMapper.class);

  OtherIncome toModel(OtherIncomeEntity entity);

  OtherIncomeEntity toEntity(OtherIncome modal);

  List<OtherIncome> toModels(List<OtherIncomeEntity> entities);

  List<OtherIncomeEntity> toEntities(List<OtherIncome> models);
}
