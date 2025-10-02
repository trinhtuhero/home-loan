package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSMarriedPersonResponse;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;

@Mapper
public interface CMSMarriedPersonResponseMapper {

  CMSMarriedPersonResponseMapper INSTANCE = Mappers.getMapper(CMSMarriedPersonResponseMapper.class);

  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  CMSMarriedPersonResponse toDto(MarriedPersonEntity request);
}
