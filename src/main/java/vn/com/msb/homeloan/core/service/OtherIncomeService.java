package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.OtherIncome;

public interface OtherIncomeService {

  OtherIncome findByUuid(String uuid);

  void deleteByUuid(String uuid, ClientTypeEnum clientType);

  List<OtherIncome> findByLoanApplicationId(String loanApplicationId);

  List<OtherIncome> findByLoanApplicationIdOrderByCreatedAtAsc(String loanApplicationId);

  List<OtherIncome> saves(List<OtherIncome> otherIncomes);

  OtherIncome save(OtherIncome otherIncome, ClientTypeEnum clientType);
}
