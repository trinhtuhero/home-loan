package vn.com.msb.homeloan.core.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.AnswerEntity;
import vn.com.msb.homeloan.core.model.Answer;

@Mapper
public interface AnswerMapper extends BaseMapper<Answer, AnswerEntity> {

  AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);
}
