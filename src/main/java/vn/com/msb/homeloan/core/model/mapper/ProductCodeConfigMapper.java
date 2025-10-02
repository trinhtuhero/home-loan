package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.ProductCodeConfigEntity;
import vn.com.msb.homeloan.core.model.ProductCodeConfig;

@Mapper
public abstract class ProductCodeConfigMapper {

  public static final ProductCodeConfigMapper INSTANCE = Mappers.getMapper(
      ProductCodeConfigMapper.class);

  public abstract ProductCodeConfig toModel(ProductCodeConfigEntity entity);

  public abstract List<ProductCodeConfig> toModels(List<ProductCodeConfigEntity> entities);

  public abstract ProductCodeConfigEntity toEntity(ProductCodeConfig model);

  public abstract List<ProductCodeConfigEntity> toEntities(List<ProductCodeConfig> models);
}
