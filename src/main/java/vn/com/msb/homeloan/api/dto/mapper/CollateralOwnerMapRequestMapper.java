package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CollateralOwnerMapRequest;
import vn.com.msb.homeloan.core.model.CollateralOwnerMap;

@Mapper
public interface CollateralOwnerMapRequestMapper {

  CollateralOwnerMapRequestMapper INSTANCE = Mappers.getMapper(
      CollateralOwnerMapRequestMapper.class);

  CollateralOwnerMap toModel(CollateralOwnerMapRequest request);

  List<CollateralOwnerMap> toDTOs(List<CollateralOwnerMapRequest> models);
}
