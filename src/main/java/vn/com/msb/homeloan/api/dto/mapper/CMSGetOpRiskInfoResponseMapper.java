package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSGetOpRiskInfoResponse;
import vn.com.msb.homeloan.core.model.CMSGetOpRiskInfo;

@Mapper(uses = CMSOpRiskResponseMapper.class)
public interface CMSGetOpRiskInfoResponseMapper {

  CMSGetOpRiskInfoResponseMapper INSTANCE = Mappers.getMapper(CMSGetOpRiskInfoResponseMapper.class);

  @Mapping(source = "birthday", target = "birthday", dateFormat = "dd/MM/yyyy")
  CMSGetOpRiskInfoResponse toCmsResponse(CMSGetOpRiskInfo response);

  List<CMSGetOpRiskInfoResponse> toCmsResponses(List<CMSGetOpRiskInfo> responses);
}
