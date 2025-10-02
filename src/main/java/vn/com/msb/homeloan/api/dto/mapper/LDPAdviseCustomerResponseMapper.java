package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.advisecustomer.LDPAdviseCustomerResponse;
import vn.com.msb.homeloan.core.model.LoanAdviseCustomer;

@Mapper
public interface LDPAdviseCustomerResponseMapper {

  LDPAdviseCustomerResponseMapper INSTANCE = Mappers.getMapper(
      LDPAdviseCustomerResponseMapper.class);

  @Mapping(source = "adviseDate", target = "adviseDate", dateFormat = "yyyyMMdd")
  LDPAdviseCustomerResponse toResponse(LoanAdviseCustomer model);
}
