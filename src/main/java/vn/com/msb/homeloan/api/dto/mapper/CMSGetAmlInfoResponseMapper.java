package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSGetAmlInfoResponse;
import vn.com.msb.homeloan.core.model.CMSGetAmlInfo;

@Mapper(uses = CMSOpRiskResponseMapper.class)
public interface CMSGetAmlInfoResponseMapper {

  CMSGetAmlInfoResponseMapper INSTANCE = Mappers.getMapper(CMSGetAmlInfoResponseMapper.class);

  CMSGetAmlInfoResponse toCmsResponse(CMSGetAmlInfo model);

  List<CMSGetAmlInfoResponse> toCmsResponses(List<CMSGetAmlInfo> models);
}
