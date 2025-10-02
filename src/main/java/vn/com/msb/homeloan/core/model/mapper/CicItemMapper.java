package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.CicItemEntity;
import vn.com.msb.homeloan.core.model.CMSCicItem;

@Mapper
public interface CicItemMapper {

  CicItemMapper INSTANCE = Mappers.getMapper(CicItemMapper.class);

  CMSCicItem toModel(CicItemEntity entity);

  List<CMSCicItem> toModels(List<CicItemEntity> entities);

  CicItemEntity toEntity(CMSCicItem model);

  List<CicItemEntity> toEntities(List<CMSCicItem> models);
}
