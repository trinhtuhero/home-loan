package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSGetOpRiskCollateralInfoResponse;
import vn.com.msb.homeloan.core.model.CMSGetOpRiskCollateralInfo;

@Mapper(uses = CMSOpRiskResponseMapper.class)
public interface CMSGetOpRiskCollateralInfoResponseMapper {

  CMSGetOpRiskCollateralInfoResponseMapper INSTANCE = Mappers.getMapper(
      CMSGetOpRiskCollateralInfoResponseMapper.class);

  CMSGetOpRiskCollateralInfoResponse toCmsResponse(CMSGetOpRiskCollateralInfo response);

  List<CMSGetOpRiskCollateralInfoResponse> toCmsResponses(
      List<CMSGetOpRiskCollateralInfo> responses);
}
