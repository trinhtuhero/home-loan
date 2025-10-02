package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CustomerRegistryAdviseResponse;
import vn.com.msb.homeloan.core.model.AdviseCustomer;

@Mapper
public interface CustomerAdviseResponseMapper {

  CustomerAdviseResponseMapper INSTANCE = Mappers.getMapper(CustomerAdviseResponseMapper.class);

  CustomerRegistryAdviseResponse toResponse(AdviseCustomer advise);
}
