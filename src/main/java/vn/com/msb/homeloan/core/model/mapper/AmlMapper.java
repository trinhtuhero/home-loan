package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.AmlEntity;
import vn.com.msb.homeloan.core.model.Aml;

@Mapper
public interface AmlMapper {

  AmlMapper INSTANCE = Mappers.getMapper(AmlMapper.class);

  Aml toModel(AmlEntity entity);

  AmlEntity toEntity(Aml model);

  List<Aml> toModels(List<AmlEntity> entities);

  List<AmlEntity> toEntities(List<Aml> models);
}
