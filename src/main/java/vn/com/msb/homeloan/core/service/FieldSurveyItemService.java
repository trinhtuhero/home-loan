package vn.com.msb.homeloan.core.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import vn.com.msb.homeloan.core.model.FieldSurveyItem;

public interface FieldSurveyItemService {

  @Transactional
  List<FieldSurveyItem> saves(List<FieldSurveyItem> fieldSurveyItems, String loanId);

  FieldSurveyItem save(FieldSurveyItem fieldSurveyItem);

  FieldSurveyItem getById(String uuid);

  List<FieldSurveyItem> getByLoanId(String loanId);

  void deleteById(String uuid);
}
