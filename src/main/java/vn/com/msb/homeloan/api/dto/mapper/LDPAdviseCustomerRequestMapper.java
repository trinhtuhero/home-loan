package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.advisecustomer.LDPAdviseCustomerRequest;
import vn.com.msb.homeloan.core.model.LoanAdviseCustomer;

@Mapper
public interface LDPAdviseCustomerRequestMapper {

  LDPAdviseCustomerRequestMapper INSTANCE = Mappers.getMapper(LDPAdviseCustomerRequestMapper.class);

  @Mapping(source = "adviseDate", target = "adviseDate", dateFormat = "yyyyMMdd")
  LoanAdviseCustomer toModel(LDPAdviseCustomerRequest request);
}
