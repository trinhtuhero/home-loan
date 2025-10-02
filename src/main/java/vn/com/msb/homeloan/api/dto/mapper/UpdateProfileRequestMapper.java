package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.UpdateProfileRequest;
import vn.com.msb.homeloan.core.model.Profile;

@Mapper
public interface UpdateProfileRequestMapper {

  UpdateProfileRequestMapper INSTANCE = Mappers.getMapper(UpdateProfileRequestMapper.class);

  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  Profile toProfileModel(UpdateProfileRequest request);
}
