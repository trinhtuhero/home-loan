package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.CollateralEntity;
import vn.com.msb.homeloan.core.model.Collateral;

@Mapper
public interface CollateralMapper {

  CollateralMapper INSTANCE = Mappers.getMapper(CollateralMapper.class);

  Collateral toModel(CollateralEntity entity);

  CollateralEntity toEntity(Collateral model);

  List<Collateral> toModels(List<CollateralEntity> entities);

  List<CollateralEntity> toEntities(List<Collateral> models);
}
