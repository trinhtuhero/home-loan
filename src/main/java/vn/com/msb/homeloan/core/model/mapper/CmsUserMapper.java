package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.CmsUserEntity;
import vn.com.msb.homeloan.core.model.CmsUser;

@Mapper
public interface CmsUserMapper {

  CmsUserMapper INSTANCE = Mappers.getMapper(CmsUserMapper.class);

  CmsUser toModel(CmsUserEntity entity);

  CmsUserEntity toEntity(CmsUser model);

  List<CmsUser> toModels(List<CmsUserEntity> entities);

  List<CmsUserEntity> toEntities(List<CmsUser> models);
}
