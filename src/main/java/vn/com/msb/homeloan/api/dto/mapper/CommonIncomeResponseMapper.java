package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.loanApplication.CommonIncomeResponse;
import vn.com.msb.homeloan.core.model.CommonIncome;

@Mapper
public interface CommonIncomeResponseMapper {

  CommonIncomeResponseMapper INSTANCE = Mappers.getMapper(CommonIncomeResponseMapper.class);

  CommonIncomeResponse toResponse(CommonIncome model);
}
