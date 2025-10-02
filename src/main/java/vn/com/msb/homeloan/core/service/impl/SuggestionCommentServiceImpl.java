package vn.com.msb.homeloan.core.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.model.feedback.SuggestionComment;
import vn.com.msb.homeloan.core.model.mapper.SuggestionCommentMapper;
import vn.com.msb.homeloan.core.repository.SuggestionCommentRepository;
import vn.com.msb.homeloan.core.service.SuggestionCommentService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuggestionCommentServiceImpl implements SuggestionCommentService {

  private final SuggestionCommentRepository suggestionCommentRepository;

  @Override
  public List<SuggestionComment> findSuggestionComments(Integer rate) {
    if (rate == null || rate == 0) {
      return new ArrayList<>();
    }
    return SuggestionCommentMapper.INSTANCE.toModels(
        suggestionCommentRepository.findSuggestionComments(rate));
  }
}
