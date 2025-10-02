package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.SalaryIncomeEntity;
import vn.com.msb.homeloan.core.model.SalaryIncome;

@Mapper
public interface SalaryIncomeMapper {

  SalaryIncomeMapper INSTANCE = Mappers.getMapper(SalaryIncomeMapper.class);

  SalaryIncome toModel(SalaryIncomeEntity entity);

  SalaryIncomeEntity toEntity(SalaryIncome modal);

  List<SalaryIncome> toModels(List<SalaryIncomeEntity> entities);

  List<SalaryIncomeEntity> toEntities(List<SalaryIncome> models);
}
