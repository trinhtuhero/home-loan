package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.QuestionResponse;
import vn.com.msb.homeloan.core.model.QuestionCategory;

@Mapper
public interface QuestionCategoryMapper {

  QuestionCategoryMapper INSTANCE = Mappers.getMapper(QuestionCategoryMapper.class);

  List<QuestionResponse> toDtos(List<QuestionCategory> categories);
}
