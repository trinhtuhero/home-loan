package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.creditAppraisal.FieldSurveyItemResponse;
import vn.com.msb.homeloan.core.model.FieldSurveyItem;

@Mapper
public interface FieldSurveyItemResponseMapper {

  FieldSurveyItemResponseMapper INSTANCE = Mappers.getMapper(FieldSurveyItemResponseMapper.class);

  @Mapping(source = "time", target = "time", dateFormat = "yyyyMMdd")
  FieldSurveyItemResponse toResponse(FieldSurveyItem model);

  List<FieldSurveyItemResponse> toResponses(List<FieldSurveyItem> model);
}
