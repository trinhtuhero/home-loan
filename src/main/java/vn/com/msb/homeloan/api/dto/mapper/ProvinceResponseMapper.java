package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.ProvinceResponse;
import vn.com.msb.homeloan.core.model.Province;

@Mapper
public interface ProvinceResponseMapper {

  ProvinceResponseMapper INSTANCE = Mappers.getMapper(ProvinceResponseMapper.class);

  @Mapping(source = "code", target = "id")
  ProvinceResponse toResponse(Province model);

  List<ProvinceResponse> toResponses(List<Province> model);
}
