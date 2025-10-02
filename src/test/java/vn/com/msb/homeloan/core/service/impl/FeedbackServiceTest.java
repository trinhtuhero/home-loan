package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.FeedbackEntity;
import vn.com.msb.homeloan.core.entity.SuggestionCommentEntity;
import vn.com.msb.homeloan.core.model.feedback.SuggestionComment;
import vn.com.msb.homeloan.core.repository.FeedbackRepository;
import vn.com.msb.homeloan.core.repository.FeedbackSuggestionMapRepository;
import vn.com.msb.homeloan.core.repository.SuggestionCommentRepository;
import vn.com.msb.homeloan.core.service.FeedbackService;
import vn.com.msb.homeloan.core.service.SuggestionCommentService;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

  @Mock
  FeedbackRepository feedbackRepository;

  @Mock
  FeedbackSuggestionMapRepository feedbackSuggestionMapRepository;

  @Mock
  SuggestionCommentRepository suggestionCommentRepository;

  @Mock
  SuggestionCommentService suggestionCommentService;

  FeedbackService feedbackService;

  @BeforeEach
  void setUp() throws IOException {
    this.feedbackService = new FeedbackServiceImpl(
        feedbackRepository
        , feedbackSuggestionMapRepository
        , suggestionCommentRepository
        , suggestionCommentService
    );
  }

  @Test
  void givenValidInput_ThenSubmitFeedback_shouldReturnResourceNotFound() {

  }

  @Test
  void givenValidInput_ThenSubmitFeedback_shouldReturnDataInvalid() {

  }

  @Test
  void givenValidInput_ThenSubmitFeedback_shouldReturnSucess() {
    String loandId = "loanId";
    Integer rate = 3;
    String[] comments = {"0f7f4e01-a295-11ed-a5de-00ff474fd3ea"};
    String additionalComment = "No comment";

    FeedbackEntity feedbackEntity = FeedbackEntity
        .builder()
        .loanApplicationId(loandId)
        .rate(rate)
        .additionalComment(additionalComment)
        .build();
    doReturn(feedbackEntity).when(feedbackRepository).save(feedbackEntity);

    SuggestionCommentEntity suggestionCommentEntity = SuggestionCommentEntity
        .builder()
        .uuid("0f7f4e01-a295-11ed-a5de-00ff474fd3ea")
        .comment("Thông tin chưa rõ ràng/khó hiểu")
        .build();
    doReturn(Optional.of(suggestionCommentEntity)).when(suggestionCommentRepository)
        .findById(comments[0]);

    List<SuggestionComment> suggestionComments = new ArrayList<>();
    SuggestionComment suggestionComment = SuggestionComment
        .builder()
        .uuid("0f7f4e01-a295-11ed-a5de-00ff474fd3ea")
        .comment("Thông tin chưa rõ ràng/khó hiểu")
        .build();
    suggestionComments.add(suggestionComment);
    doReturn(suggestionComments).when(suggestionCommentService).findSuggestionComments(rate);

    try {
      feedbackService.submitFeedback(loandId, rate, comments, additionalComment);
      assertTrue(true);
    } catch (Exception ex) {
      assertFalse(true);
    }
  }
}
