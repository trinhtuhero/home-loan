package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.WardResponse;
import vn.com.msb.homeloan.core.model.Ward;

@Mapper
public interface WardResponseMapper {

  WardResponseMapper INSTANCE = Mappers.getMapper(WardResponseMapper.class);

  @Mapping(source = "code", target = "id")
  WardResponse toResponse(Ward model);

  List<WardResponse> toResponses(List<Ward> model);
}
