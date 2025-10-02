package vn.com.msb.homeloan.core.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.Collateral;

public interface CollateralService {

  Collateral findById(String id);

  List<Collateral> findByLoanId(String loanId);

  List<Collateral> findByLoanIdOrderByCreatedAtAsc(String loanId);

  List<Collateral> findCollateralInfoByLoanId(String loanId);

  Collateral save(Collateral collateral, ClientTypeEnum clientType);

  @Transactional
  List<Collateral> saves(List<Collateral> collaterals, ClientTypeEnum clientType);

  void deleteById(String id, ClientTypeEnum clientType);

  long totalValues(String loanId);

  long totalGuaranteedValues(String loanId);

  List<String> validateBeforeNextTab(String loanId);
}
