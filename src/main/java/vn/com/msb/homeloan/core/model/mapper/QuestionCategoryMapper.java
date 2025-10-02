package vn.com.msb.homeloan.core.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.QuestionCategoryEntity;
import vn.com.msb.homeloan.core.model.QuestionCategory;

@Mapper
public interface QuestionCategoryMapper extends
    BaseMapper<QuestionCategory, QuestionCategoryEntity> {

  QuestionCategoryMapper INSTANCE = Mappers.getMapper(QuestionCategoryMapper.class);
}
