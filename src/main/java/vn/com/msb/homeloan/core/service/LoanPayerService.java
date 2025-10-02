package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.model.LoanPayer;

public interface LoanPayerService {

  LoanPayer findById(String id);

  List<LoanPayer> findByLoanId(String loanId);

  List<LoanPayer> findByLoanIdOrderByCreatedAtAsc(String loanId);

  List<LoanPayer> save(List<LoanPayer> loanPayers);

  LoanPayerEntity save(LoanPayer loanPayer, ClientTypeEnum clientType);

  void deleteById(String id, ClientTypeEnum clientType);
}
