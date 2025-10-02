package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.SalaryIncome;

public interface SalaryIncomeService {

  SalaryIncome findByUuid(String uuid);

  void deleteByUuid(String uuid, ClientTypeEnum clientType);

  List<SalaryIncome> findByLoanApplicationId(String loanApplicationId);

  List<SalaryIncome> findByLoanApplicationIdOrderByCreatedAtAsc(String loanApplicationId);

  List<SalaryIncome> saves(List<SalaryIncome> salaryIncomes);

  SalaryIncome save(SalaryIncome salaryIncome, ClientTypeEnum clientType);
}
