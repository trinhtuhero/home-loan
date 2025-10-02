package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.AssetEvaluateItemEntity;
import vn.com.msb.homeloan.core.model.AssetEvaluateItem;

@Mapper
public interface AssetEvaluateItemMapper {

  AssetEvaluateItemMapper INSTANCE = Mappers.getMapper(AssetEvaluateItemMapper.class);

  AssetEvaluateItem toModel(AssetEvaluateItemEntity entity);

  List<AssetEvaluateItem> toModels(List<AssetEvaluateItemEntity> entities);

  AssetEvaluateItemEntity toEntity(AssetEvaluateItem model);

  List<AssetEvaluateItemEntity> toEntities(List<AssetEvaluateItem> models);
}
