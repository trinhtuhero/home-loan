package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.LoginProfileInfoResponse;
import vn.com.msb.homeloan.core.model.LoginProfileInfo;

@Mapper
public interface LoginProfileInfoResponseMapper {

  LoginProfileInfoResponseMapper INSTANCE = Mappers.getMapper(LoginProfileInfoResponseMapper.class);

  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  LoginProfileInfoResponse toDTO(LoginProfileInfo model);
}
