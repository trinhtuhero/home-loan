package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSGetCicInfoResponse;
import vn.com.msb.homeloan.core.model.CMSGetCicInfo;

@Mapper(uses = CMSCicItemResponseMapper.class)
public interface CMSGetCicInfoResponseMapper {

  CMSGetCicInfoResponseMapper INSTANCE = Mappers.getMapper(CMSGetCicInfoResponseMapper.class);

  CMSGetCicInfoResponse toCmsResponse(CMSGetCicInfo response);

  List<CMSGetCicInfoResponse> toCmsResponses(List<CMSGetCicInfo> responses);
}
