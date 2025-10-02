package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSMvalueResponse;
import vn.com.msb.homeloan.core.model.Mvalue;

@Mapper
public interface CMSMvalueResponseMapper {

  CMSMvalueResponseMapper INSTANCE = Mappers.getMapper(CMSMvalueResponseMapper.class);

  @Mapping(source = "checkDate", target = "checkDate", dateFormat = "yyyyMMddHHmmss")
  CMSMvalueResponse toCmsResponse(Mvalue response);

  List<CMSMvalueResponse> toCmsResponses(List<Mvalue> responses);
}
