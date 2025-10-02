package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.ContactPersonRequest;
import vn.com.msb.homeloan.core.model.ContactPerson;

@Mapper
public interface ContactPersonRequestMapper {

  ContactPersonRequestMapper INSTANCE = Mappers.getMapper(ContactPersonRequestMapper.class);

  ContactPerson toModel(ContactPersonRequest request);
}
