package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CustomerInfoRequest;
import vn.com.msb.homeloan.core.model.LoanApplication;

@Mapper
public interface CustomerInfoRequestMapper {

  CustomerInfoRequestMapper INSTANCE = Mappers.getMapper(CustomerInfoRequestMapper.class);

  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  LoanApplication toLoanModel(CustomerInfoRequest request);
}
