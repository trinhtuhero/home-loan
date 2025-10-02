package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.core.model.CreditAppraisal;

public interface CreditAppraisalService {

  CreditAppraisal save(CreditAppraisal creditAppraisal);

  CreditAppraisal getByLoanId(String loanId);
}
