package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CollateralRequest;
import vn.com.msb.homeloan.core.model.Collateral;

@Mapper
public interface CollateralRequestMapper {

  CollateralRequestMapper INSTANCE = Mappers.getMapper(CollateralRequestMapper.class);

  Collateral toModel(CollateralRequest dto);
}
