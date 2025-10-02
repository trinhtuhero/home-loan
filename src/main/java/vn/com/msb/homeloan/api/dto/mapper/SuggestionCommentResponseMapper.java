package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.feedback.SuggestionCommentResponse;
import vn.com.msb.homeloan.core.model.feedback.SuggestionComment;

@Mapper
public interface SuggestionCommentResponseMapper {

  SuggestionCommentResponseMapper INSTANCE = Mappers.getMapper(
      SuggestionCommentResponseMapper.class);

  SuggestionCommentResponse toResponse(SuggestionComment model);

  List<SuggestionCommentResponse> toResponses(List<SuggestionComment> models);
}
