package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.LoanApplicationItem;
import vn.com.msb.homeloan.core.model.LoanItemAndOverdraft;
import vn.com.msb.homeloan.core.model.overdraft.Overdraft;

import java.util.List;

public interface LoanApplicationItemService {

  List<LoanApplicationItem> findByLoanApplicationIdOrderByCreatedAtAsc(String loanId);

  void delete(String uuid, ClientTypeEnum clientType);

  List<LoanApplicationItem> saves(List<LoanApplicationItem> list, int countOverdraft, ClientTypeEnum clientType);

  LoanApplicationItem save(LoanApplicationItem loanApplicationItem, int countOverdraft, ClientTypeEnum clientType);

  LoanApplicationItem findById(String uuid);

  List<LoanApplicationItem> getItemCollateralDistribute(String loanId);

  List<Overdraft> getOverdraftCollateralDistribute(String loanId);

  long totalLoanAmount(String loanId);

  LoanItemAndOverdraft saveLoanItemAndOverdraft(LoanItemAndOverdraft loanItemAndOverdraft,
      ClientTypeEnum clientTypeEnum);
}
