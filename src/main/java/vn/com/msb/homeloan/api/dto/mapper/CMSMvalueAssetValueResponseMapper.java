package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSMvalueAssetInfoResponse;
import vn.com.msb.homeloan.core.model.MvalueAssetInfo;

@Mapper
public interface CMSMvalueAssetValueResponseMapper {

  CMSMvalueAssetValueResponseMapper INSTANCE = Mappers.getMapper(
      CMSMvalueAssetValueResponseMapper.class);

  CMSMvalueAssetInfoResponse toCmsResponse(MvalueAssetInfo response);

  List<CMSMvalueAssetInfoResponse> toCmsResponses(List<MvalueAssetInfo> responses);
}
