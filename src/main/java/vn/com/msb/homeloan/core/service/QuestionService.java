package vn.com.msb.homeloan.core.service;

import java.util.List;

public interface QuestionService {

  List getFrequentlyQuestion(String product, Boolean oneLevel);
}
