package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.SuggestionCommentEntity;
import vn.com.msb.homeloan.core.model.feedback.SuggestionComment;

@Mapper
public interface SuggestionCommentMapper {

  SuggestionCommentMapper INSTANCE = Mappers.getMapper(SuggestionCommentMapper.class);

  SuggestionComment toModel(SuggestionCommentEntity entity);

  SuggestionCommentEntity toEntity(SuggestionComment modal);

  List<SuggestionComment> toModels(List<SuggestionCommentEntity> entities);

  List<SuggestionCommentEntity> toEntities(List<SuggestionComment> models);
}
