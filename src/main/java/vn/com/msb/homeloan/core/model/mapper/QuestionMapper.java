package vn.com.msb.homeloan.core.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.QuestionEntity;
import vn.com.msb.homeloan.core.model.Question;

@Mapper
public interface QuestionMapper extends BaseMapper<Question, QuestionEntity> {

  QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);
}
