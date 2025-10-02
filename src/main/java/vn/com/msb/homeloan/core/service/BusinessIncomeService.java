package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.BusinessIncome;

public interface BusinessIncomeService {

  BusinessIncome findByUuid(String uuid);

  void deleteByUuid(String uuid, ClientTypeEnum clientType);

  List<BusinessIncome> findByLoanApplicationId(String loanApplicationId);

  List<BusinessIncome> findByLoanApplicationIdOrderByCreatedAtAsc(String loanApplicationId);

  List<BusinessIncome> saves(List<BusinessIncome> businessIncomes);

  BusinessIncome save(BusinessIncome businessIncome, ClientTypeEnum clientType);
}
