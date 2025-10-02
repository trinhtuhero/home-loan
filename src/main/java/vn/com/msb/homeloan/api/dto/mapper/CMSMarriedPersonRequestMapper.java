package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSMarriedPersonRequest;
import vn.com.msb.homeloan.core.model.MarriedPerson;

@Mapper
public interface CMSMarriedPersonRequestMapper {

  CMSMarriedPersonRequestMapper INSTANCE = Mappers.getMapper(CMSMarriedPersonRequestMapper.class);

  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  MarriedPerson toModel(CMSMarriedPersonRequest request);
}
