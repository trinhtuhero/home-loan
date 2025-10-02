package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSCicItemResponse;
import vn.com.msb.homeloan.core.model.CMSCicItem;

@Mapper
public interface CMSCicItemResponseMapper {

  CMSCicItemResponseMapper INSTANCE = Mappers.getMapper(CMSCicItemResponseMapper.class);

  @Mapping(source = "checkDate", target = "checkDate", dateFormat = "yyyyMMddHHmmss")
  CMSCicItemResponse toCmsResponse(CMSCicItem response);

  List<CMSCicItemResponse> toCmsResponses(List<CMSCicItem> responses);
}
