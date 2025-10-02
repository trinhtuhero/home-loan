package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSContactPersonRequest;
import vn.com.msb.homeloan.core.model.ContactPerson;

@Mapper
public interface CMSContactPersonRequestMapper {

  CMSContactPersonRequestMapper INSTANCE = Mappers.getMapper(CMSContactPersonRequestMapper.class);

  ContactPerson toModel(CMSContactPersonRequest request);
}
