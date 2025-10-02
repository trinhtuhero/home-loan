package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSAmlResponse;
import vn.com.msb.homeloan.core.model.Aml;

@Mapper
public interface CMSAmlResponseMapper {

  CMSAmlResponseMapper INSTANCE = Mappers.getMapper(CMSAmlResponseMapper.class);

  @Mapping(source = "checkDate", target = "checkDate", dateFormat = "yyyyMMddHHmmss")
  CMSAmlResponse toCmsResponse(Aml model);

  List<CMSAmlResponse> toCmsResponses(List<Aml> models);
}
