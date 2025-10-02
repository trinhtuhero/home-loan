package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.model.ExceptionItem;

public interface ExceptionItemService {

  ExceptionItem save(ExceptionItem exceptionItem);

  List<ExceptionItem> getByLoanId(String loanId);

  void deleteByUuid(String uuid);
}
