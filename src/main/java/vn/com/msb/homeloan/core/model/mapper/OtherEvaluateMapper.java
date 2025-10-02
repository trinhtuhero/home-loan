package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.OtherEvaluateEntity;
import vn.com.msb.homeloan.core.model.OtherEvaluate;

@Mapper
public interface OtherEvaluateMapper {

  OtherEvaluateMapper INSTANCE = Mappers.getMapper(OtherEvaluateMapper.class);

  OtherEvaluate toModel(OtherEvaluateEntity entity);

  OtherEvaluateEntity toEntity(OtherEvaluate modal);

  List<OtherEvaluate> toModels(List<OtherEvaluateEntity> entities);

  List<OtherEvaluateEntity> toEntities(List<OtherEvaluate> models);
}
