package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSSalaryIncomeRequest;
import vn.com.msb.homeloan.core.model.SalaryIncome;

@Mapper
public interface CMSSalaryIncomeRequestMapper {

  CMSSalaryIncomeRequestMapper INSTANCE = Mappers.getMapper(CMSSalaryIncomeRequestMapper.class);

  @Mapping(source = "startWorkDate", target = "startWorkDate", dateFormat = "yyyyMMdd")
  SalaryIncome toModel(CMSSalaryIncomeRequest request);

  List<SalaryIncome> toModels(List<CMSSalaryIncomeRequest> request);
}
