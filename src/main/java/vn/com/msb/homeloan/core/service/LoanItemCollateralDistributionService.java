package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.model.LoanItemCollateralDistribution;

public interface LoanItemCollateralDistributionService {

  List<LoanItemCollateralDistribution> getByLoanId(String loanId);

  List<LoanItemCollateralDistribution> getByLoanIdAndType(String loanId, String type);

  public List<LoanItemCollateralDistribution> getByLoanIdAndCollateralId(String loanId,
      String collateralId);

  List<LoanItemCollateralDistribution> getCollateralAndDistributionByLoanIdAndType(String loanId,
      String type);

  List<LoanItemCollateralDistribution> saves(List<LoanItemCollateralDistribution> models,
      String type);

  LoanItemCollateralDistribution save(LoanItemCollateralDistribution model);

  void deleteAll(List<LoanItemCollateralDistribution> models);

  void deleteAllByOverdraftId(String overdraftId);
}
