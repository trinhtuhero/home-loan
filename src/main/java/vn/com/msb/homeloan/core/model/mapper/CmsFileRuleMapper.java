package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.CmsFileRuleEntity;
import vn.com.msb.homeloan.core.model.CmsFileRule;

@Mapper
public interface CmsFileRuleMapper {

  CmsFileRuleMapper INSTANCE = Mappers.getMapper(CmsFileRuleMapper.class);

  CmsFileRule toModel(CmsFileRuleEntity entity);

  CmsFileRuleEntity toEntity(CmsFileRule modal);

  List<CmsFileRule> toModels(List<CmsFileRuleEntity> entities);

  List<CmsFileRuleEntity> toEntities(List<CmsFileRule> models);
}
