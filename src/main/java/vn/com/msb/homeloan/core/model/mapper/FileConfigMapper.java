package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.FileConfigEntity;
import vn.com.msb.homeloan.core.model.FileConfig;

@Mapper
public interface FileConfigMapper {

  FileConfigMapper INSTANCE = Mappers.getMapper(FileConfigMapper.class);

  FileConfig toModel(FileConfigEntity entity);

  FileConfigEntity toEntity(FileConfig modal);

  List<FileConfig> toModels(List<FileConfigEntity> entities);

  List<FileConfigEntity> toEntities(List<FileConfig> models);
}
