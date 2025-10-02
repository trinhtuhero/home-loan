package vn.com.msb.homeloan.core.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.AssetEvaluateEntity;
import vn.com.msb.homeloan.core.model.AssetEvaluate;

@Mapper
public interface AssetEvaluateMapper {

  AssetEvaluateMapper INSTANCE = Mappers.getMapper(AssetEvaluateMapper.class);

  AssetEvaluate toModel(AssetEvaluateEntity entity);

  AssetEvaluateEntity toEntity(AssetEvaluate model);
}
