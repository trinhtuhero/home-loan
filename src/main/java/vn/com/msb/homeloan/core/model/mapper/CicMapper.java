package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.CicEntity;
import vn.com.msb.homeloan.core.model.CMSCic;

@Mapper
public interface CicMapper {

  CicMapper INSTANCE = Mappers.getMapper(CicMapper.class);

  CMSCic toModel(CicEntity entity);

  List<CMSCic> toModels(List<CicEntity> entities);

  CicEntity toEntity(CMSCic model);

  List<CicEntity> toEntities(List<CMSCic> models);
}
