package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.OpRiskEntity;
import vn.com.msb.homeloan.core.model.OpRisk;

@Mapper
public interface OpRiskMapper {

  OpRiskMapper INSTANCE = Mappers.getMapper(OpRiskMapper.class);

  OpRisk toModel(OpRiskEntity entity);

  OpRiskEntity toEntity(OpRisk model);

  List<OpRisk> toModels(List<OpRiskEntity> entities);

  List<OpRiskEntity> toEntities(List<OpRisk> models);
}
