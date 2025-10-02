package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.model.CMSTabAction;

public interface CMSTabActionService {

  CMSTabAction save(String loanId, CMSTabEnum tab);

  void delete(String loanId, CMSTabEnum tab);

  List<CMSTabAction> getByLoanId(String loanId);
}
