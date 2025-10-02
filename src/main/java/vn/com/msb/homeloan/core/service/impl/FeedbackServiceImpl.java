package vn.com.msb.homeloan.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.FeedbackEntity;
import vn.com.msb.homeloan.core.entity.FeedbackSuggestionMapEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.feedback.SuggestionComment;
import vn.com.msb.homeloan.core.repository.FeedbackRepository;
import vn.com.msb.homeloan.core.repository.FeedbackSuggestionMapRepository;
import vn.com.msb.homeloan.core.repository.SuggestionCommentRepository;
import vn.com.msb.homeloan.core.service.FeedbackService;
import vn.com.msb.homeloan.core.service.SuggestionCommentService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

  private final FeedbackRepository feedbackRepository;
  private final FeedbackSuggestionMapRepository feedbackSuggestionMapRepository;
  private final SuggestionCommentRepository suggestionCommentRepository;
  private final SuggestionCommentService suggestionCommentService;

  @Override
  @Transactional
  public void submitFeedback(String loanId, Integer rate, String[] comments,
      String additionalComment) {
    //
    FeedbackEntity feedbackEntity = FeedbackEntity
        .builder()
        .loanApplicationId(loanId)
        .rate(rate)
        .additionalComment(additionalComment)
        .build();
    feedbackRepository.save(feedbackEntity);

    //
    List<FeedbackSuggestionMapEntity> feedbackSuggestionMapEntities = new ArrayList<>();
    for (String suggestionCommentId : comments) {
      suggestionCommentRepository.findById(suggestionCommentId).orElseThrow(
          () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "suggestion_comment:uuid",
              suggestionCommentId));

      if (!CollectionUtils.emptyIfNull(suggestionCommentService.findSuggestionComments(rate))
          .stream().map(SuggestionComment::getUuid).collect(Collectors.toList())
          .contains(suggestionCommentId)) {
        throw new ApplicationException(ErrorEnum.DATA_INVALID, "suggestion_comment:uuid",
            suggestionCommentId);
      }

      FeedbackSuggestionMapEntity feedbackSuggestionMapEntity = FeedbackSuggestionMapEntity
          .builder()
          .feedbackId(feedbackEntity.getUuid())
          .suggestionCommentId(suggestionCommentId)
          .build();
      feedbackSuggestionMapEntities.add(feedbackSuggestionMapEntity);
    }
    feedbackSuggestionMapRepository.saveAll(feedbackSuggestionMapEntities);
  }
}
