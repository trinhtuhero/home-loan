package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.BusinessIncomeEntity;
import vn.com.msb.homeloan.core.model.BusinessIncome;

@Mapper
public interface BusinessIncomeMapper {

  BusinessIncomeMapper INSTANCE = Mappers.getMapper(BusinessIncomeMapper.class);

  BusinessIncome toModel(BusinessIncomeEntity entity);

  BusinessIncomeEntity toEntity(BusinessIncome modal);

  List<BusinessIncome> toModels(List<BusinessIncomeEntity> entities);

  List<BusinessIncomeEntity> toEntities(List<BusinessIncome> models);
}
