package vn.com.msb.homeloan.core.service;

public interface FeedbackService {

  void submitFeedback(String loanId, Integer rate, String[] comments, String additionalComment);
}
