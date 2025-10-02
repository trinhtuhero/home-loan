package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.ExceptionItemEntity;
import vn.com.msb.homeloan.core.model.ExceptionItem;

@Mapper
public interface ExceptionItemMapper {

  ExceptionItemMapper INSTANCE = Mappers.getMapper(ExceptionItemMapper.class);

  ExceptionItem toModel(ExceptionItemEntity entity);

  ExceptionItemEntity toEntity(ExceptionItem model);

  List<ExceptionItem> toModels(List<ExceptionItemEntity> entities);

  List<ExceptionItemEntity> toEntities(List<ExceptionItem> models);
}
