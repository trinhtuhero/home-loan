package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.ProfileResponse;
import vn.com.msb.homeloan.core.model.Profile;

@Mapper
public interface ProfileResponseMapper {

  ProfileResponseMapper INSTANCE = Mappers.getMapper(ProfileResponseMapper.class);

  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  ProfileResponse toDto(Profile profile);
}
