package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.FileConfigCategoryEntity;
import vn.com.msb.homeloan.core.model.FileConfigCategory;

@Mapper
public interface FileConfigCategoryMapper {

  FileConfigCategoryMapper INSTANCE = Mappers.getMapper(FileConfigCategoryMapper.class);

  FileConfigCategory toModel(FileConfigCategoryEntity entity);

  FileConfigCategoryEntity toEntity(FileConfigCategory modal);

  List<FileConfigCategory> toModels(List<FileConfigCategoryEntity> entities);

  List<FileConfigCategoryEntity> toEntities(List<FileConfigCategory> models);
}
