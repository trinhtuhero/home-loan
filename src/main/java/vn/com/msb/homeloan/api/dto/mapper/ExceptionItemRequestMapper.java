package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.creditappraisal.ExceptionItemRequest;
import vn.com.msb.homeloan.core.model.ExceptionItem;

@Mapper
public interface ExceptionItemRequestMapper {

  ExceptionItemRequestMapper INSTANCE = Mappers.getMapper(ExceptionItemRequestMapper.class);

  ExceptionItem toModel(ExceptionItemRequest request);

  List<ExceptionItem> toModels(List<ExceptionItemRequest> requests);
}
