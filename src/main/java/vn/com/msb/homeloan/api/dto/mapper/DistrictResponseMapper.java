package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.DistrictResponse;
import vn.com.msb.homeloan.core.model.District;

@Mapper
public interface DistrictResponseMapper {

  DistrictResponseMapper INSTANCE = Mappers.getMapper(DistrictResponseMapper.class);

  @Mapping(source = "code", target = "id")
  DistrictResponse toResponse(District model);

  List<DistrictResponse> toResponses(List<District> model);
}
