package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CRUSalaryIncomeRequest;
import vn.com.msb.homeloan.api.dto.response.CRUSalaryIncomeResponse;
import vn.com.msb.homeloan.core.entity.SalaryIncomeEntity;
import vn.com.msb.homeloan.core.model.SalaryIncome;

@Mapper
public interface SalaryIncomeMapper {

  SalaryIncomeMapper INSTANCE = Mappers.getMapper(SalaryIncomeMapper.class);

  //request to model
  SalaryIncome toModel(CRUSalaryIncomeRequest request);

  //model to response
  CRUSalaryIncomeResponse toDTO(SalaryIncome model);

  CRUSalaryIncomeResponse toDTO(SalaryIncomeEntity entity);

  List<CRUSalaryIncomeResponse> toDTOs(List<SalaryIncome> models);
}
