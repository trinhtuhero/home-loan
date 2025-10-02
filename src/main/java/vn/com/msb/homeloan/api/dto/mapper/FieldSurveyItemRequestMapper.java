package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.creditappraisal.FieldSurveyItemRequest;
import vn.com.msb.homeloan.core.model.FieldSurveyItem;

@Mapper
public interface FieldSurveyItemRequestMapper {

  FieldSurveyItemRequestMapper INSTANCE = Mappers.getMapper(FieldSurveyItemRequestMapper.class);

  @Mapping(source = "time", target = "time", dateFormat = "yyyyMMdd")
  FieldSurveyItem toModel(FieldSurveyItemRequest request);

  List<FieldSurveyItem> toModels(List<FieldSurveyItemRequest> requests);
}
