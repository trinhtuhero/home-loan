package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.core.model.LoanAdviseCustomer;

public interface LoanAdviseCustomerService {

  LoanAdviseCustomer save(LoanAdviseCustomer loanAdviseCustomer);

  LoanAdviseCustomer get(String loanId);

  LoanAdviseCustomer changeStatus(String loanId, String Status);
}
