package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.LoanTermResponse;
import vn.com.msb.homeloan.core.model.LoanTermCalculation;

@Mapper
public interface LoanTermResponseMapper {

  LoanTermResponseMapper INSTANCE = Mappers.getMapper(LoanTermResponseMapper.class);

  LoanTermResponse toResponse(LoanTermCalculation termCalculation);
}
