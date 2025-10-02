package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import vn.com.msb.homeloan.core.model.response.GetPriceBracketLDPResponse;
import vn.com.msb.homeloan.core.model.response.mvalue.GetPriceBracketResponse;

@Mapper
public interface GetPriceBracketMapper {
  GetPriceBracketMapper INSTANCE = Mappers.getMapper(GetPriceBracketMapper.class);

  GetPriceBracketLDPResponse toResponse(GetPriceBracketResponse model);
}
