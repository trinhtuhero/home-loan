package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.NationalityResponse;
import vn.com.msb.homeloan.core.model.Nationality;

@Mapper
public interface NationalityResponseMapper {

  NationalityResponseMapper INSTANCE = Mappers.getMapper(NationalityResponseMapper.class);

  @Mapping(source = "code", target = "id")
  NationalityResponse toResponse(Nationality model);

  List<NationalityResponse> toResponses(List<Nationality> model);
}
