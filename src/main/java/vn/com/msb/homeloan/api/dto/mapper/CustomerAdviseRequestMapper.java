package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CustomerRegistryRequest;
import vn.com.msb.homeloan.core.model.AdviseCustomer;

@Mapper
public interface CustomerAdviseRequestMapper {

  CustomerAdviseRequestMapper INSTANCE = Mappers.getMapper(CustomerAdviseRequestMapper.class);

  @Mapping(source = "provinceCode", target = "province")
  AdviseCustomer toModel(CustomerRegistryRequest request);
}
