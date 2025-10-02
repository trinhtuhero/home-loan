package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.MarriedPersonResponse;
import vn.com.msb.homeloan.core.model.MarriedPerson;

@Mapper
public interface MarriedPersonResponseMapper {

  MarriedPersonResponseMapper INSTANCE = Mappers.getMapper(MarriedPersonResponseMapper.class);

  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  MarriedPersonResponse toDto(MarriedPerson request);
}
