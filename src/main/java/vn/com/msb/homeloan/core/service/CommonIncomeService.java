package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.core.model.CommonIncome;

public interface CommonIncomeService {

  CommonIncome save(CommonIncome commonIncome);

  CommonIncome findById(String uuid);

  CommonIncome findByLoanApplicationId(String loanId);

  String[] getSelectedIncomeSubmittedByCustomer(String loanId);
}
