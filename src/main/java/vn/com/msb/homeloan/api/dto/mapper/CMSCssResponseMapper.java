package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSCssResponse;
import vn.com.msb.homeloan.core.model.Css;

@Mapper
public interface CMSCssResponseMapper {

  CMSCssResponseMapper INSTANCE = Mappers.getMapper(CMSCssResponseMapper.class);

  @Mapping(source = "scoringDate", target = "scoringDate", dateFormat = "yyyyMMdd")
  CMSCssResponse toCmsResponse(Css response);

  List<CMSCssResponse> toCmsResponses(List<Css> responses);
}
