package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.creditAppraisal.ExceptionItemResponse;
import vn.com.msb.homeloan.core.model.ExceptionItem;

@Mapper
public interface ExceptionItemResponseMapper {

  ExceptionItemResponseMapper INSTANCE = Mappers.getMapper(ExceptionItemResponseMapper.class);

  ExceptionItemResponse toResponse(ExceptionItem model);

  List<ExceptionItemResponse> toResponses(List<ExceptionItem> models);
}
