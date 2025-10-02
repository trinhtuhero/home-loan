package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSCicResponse;
import vn.com.msb.homeloan.core.model.CMSCic;

@Mapper
public interface CMSCicResponseMapper {

  CMSCicResponseMapper INSTANCE = Mappers.getMapper(CMSCicResponseMapper.class);

  CMSCicResponse toCmsResponse(CMSCic response);

  List<CMSCicResponse> toCmsResponses(List<CMSCic> responses);
}
