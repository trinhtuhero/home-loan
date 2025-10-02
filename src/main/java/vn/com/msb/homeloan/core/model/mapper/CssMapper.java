package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.CssEntity;
import vn.com.msb.homeloan.core.model.Css;

@Mapper
public interface CssMapper {

  CssMapper INSTANCE = Mappers.getMapper(CssMapper.class);

  Css toModel(CssEntity entity);

  CssEntity toEntity(Css modal);

  List<Css> toModels(List<CssEntity> entities);

  List<CssEntity> toEntities(List<Css> models);
}
