package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.FieldSurveyItemEntity;
import vn.com.msb.homeloan.core.model.FieldSurveyItem;

@Mapper
public interface FieldSurveyItemMapper {

  FieldSurveyItemMapper INSTANCE = Mappers.getMapper(FieldSurveyItemMapper.class);

  FieldSurveyItem toModel(FieldSurveyItemEntity entity);

  FieldSurveyItemEntity toEntity(FieldSurveyItem model);

  List<FieldSurveyItem> toModels(List<FieldSurveyItemEntity> entities);

  List<FieldSurveyItemEntity> toEntities(List<FieldSurveyItem> models);
}
