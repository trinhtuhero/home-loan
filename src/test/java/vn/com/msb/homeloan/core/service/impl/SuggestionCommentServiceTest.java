package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.SuggestionCommentEntity;
import vn.com.msb.homeloan.core.model.feedback.SuggestionComment;
import vn.com.msb.homeloan.core.repository.SuggestionCommentRepository;
import vn.com.msb.homeloan.core.service.SuggestionCommentService;

@ExtendWith(MockitoExtension.class)
class SuggestionCommentServiceTest {

  @Mock
  SuggestionCommentRepository suggestionCommentRepository;

  SuggestionCommentService suggestionCommentService;

  @BeforeEach
  void setUp() throws IOException {
    this.suggestionCommentService = new SuggestionCommentServiceImpl(
        suggestionCommentRepository
    );
  }

  @Test
  void givenValidInput_ThenFindSuggestionComments_shouldReturnListEmpty() {
    int rate = 0;
    List<SuggestionComment> result = suggestionCommentService.findSuggestionComments(rate);
    assertTrue(result.isEmpty());
  }

  @Test
  void givenValidInput_ThenFindSuggestionComments_shouldReturnListNotEmpty() {
    int rate = 3;

    List<SuggestionCommentEntity> suggestionCommentEntities = new ArrayList<>();
    SuggestionCommentEntity suggestionCommentEntity = SuggestionCommentEntity
        .builder()
        .uuid("0f7f4e01-a295-11ed-a5de-00ff474fd3ea")
        .comment("Thông tin chưa rõ ràng/khó hiểu")
        .build();
    suggestionCommentEntities.add(suggestionCommentEntity);

    doReturn(suggestionCommentEntities).when(suggestionCommentRepository)
        .findSuggestionComments(rate);
    List<SuggestionComment> result = suggestionCommentService.findSuggestionComments(rate);
    assertTrue(result.size() == 1);
  }
}
