package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.CmsTabActionEntity;
import vn.com.msb.homeloan.core.model.CMSTabAction;

@Mapper
public interface CMSTabActionMapper {

  CMSTabActionMapper INSTANCE = Mappers.getMapper(CMSTabActionMapper.class);

  CMSTabAction toModel(CmsTabActionEntity entity);

  List<CMSTabAction> toModels(List<CmsTabActionEntity> entitys);

  CmsTabActionEntity toEntity(CMSTabAction model);
}
