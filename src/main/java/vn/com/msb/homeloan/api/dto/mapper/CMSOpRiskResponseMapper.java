package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSOpRiskResponse;
import vn.com.msb.homeloan.core.model.OpRisk;

@Mapper
public interface CMSOpRiskResponseMapper {

  CMSOpRiskResponseMapper INSTANCE = Mappers.getMapper(CMSOpRiskResponseMapper.class);

  @Mapping(source = "checkDate", target = "checkDate", dateFormat = "yyyyMMddHHmmss")
  CMSOpRiskResponse toCmsResponse(OpRisk response);

  List<CMSOpRiskResponse> toCmsResponses(List<OpRisk> responses);
}
