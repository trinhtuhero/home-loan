package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSContactPersonResponse;
import vn.com.msb.homeloan.core.model.ContactPerson;

@Mapper
public interface CMSContactPersonResponseMapper {

  CMSContactPersonResponseMapper INSTANCE = Mappers.getMapper(CMSContactPersonResponseMapper.class);

  CMSContactPersonResponse toResponse(ContactPerson response);

  List<CMSContactPersonResponse> toResponses(List<ContactPerson> responses);
}
