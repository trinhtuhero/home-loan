package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.FileRuleEntity;
import vn.com.msb.homeloan.core.model.FileRule;

@Mapper
public interface FileRuleMapper {

  FileRuleMapper INSTANCE = Mappers.getMapper(FileRuleMapper.class);

  FileRule toModel(FileRuleEntity entity);

  FileRuleEntity toEntity(FileRule modal);

  List<FileRule> toModels(List<FileRuleEntity> entities);

  List<FileRuleEntity> toEntities(List<FileRule> models);
}
