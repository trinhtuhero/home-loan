package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CollateralResponse;
import vn.com.msb.homeloan.core.model.Collateral;

@Mapper
public interface CollateralResponseMapper {

  CollateralResponseMapper INSTANCE = Mappers.getMapper(CollateralResponseMapper.class);

  CollateralResponse toResponse(Collateral model);

  List<CollateralResponse> toResponses(List<Collateral> model);
}
