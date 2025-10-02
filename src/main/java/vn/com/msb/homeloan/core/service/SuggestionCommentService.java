package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.model.feedback.SuggestionComment;

public interface SuggestionCommentService {

  List<SuggestionComment> findSuggestionComments(Integer rate);
}
