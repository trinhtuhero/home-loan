package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.LoanCalculationRequest;
import vn.com.msb.homeloan.core.model.LoanCalculate;

@Mapper
public interface LoanCalculationRequestMapper {

  LoanCalculationRequestMapper INSTANCE = Mappers.getMapper(LoanCalculationRequestMapper.class);

  LoanCalculate toModel(LoanCalculationRequest request);
}
