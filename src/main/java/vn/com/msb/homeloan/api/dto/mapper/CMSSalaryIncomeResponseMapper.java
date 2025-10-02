package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSSalaryIncomeResponse;
import vn.com.msb.homeloan.core.entity.SalaryIncomeEntity;
import vn.com.msb.homeloan.core.model.SalaryIncome;

@Mapper
public interface CMSSalaryIncomeResponseMapper {

  CMSSalaryIncomeResponseMapper INSTANCE = Mappers.getMapper(CMSSalaryIncomeResponseMapper.class);

  @Mapping(source = "startWorkDate", target = "startWorkDate", dateFormat = "yyyyMMdd")
  CMSSalaryIncomeResponse toModel(SalaryIncomeEntity request);

  List<CMSSalaryIncomeResponse> toModels(List<SalaryIncomeEntity> requests);

  @Mapping(source = "startWorkDate", target = "startWorkDate", dateFormat = "yyyyMMdd")
  CMSSalaryIncomeResponse toDTO(SalaryIncome response);

  List<CMSSalaryIncomeResponse> toDTOs(List<SalaryIncome> responses);
}
