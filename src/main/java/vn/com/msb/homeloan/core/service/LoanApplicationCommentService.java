package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.model.LoanApplicationComment;

public interface LoanApplicationCommentService {

  LoanApplicationComment save(LoanApplicationComment loanApplicationComment);

  LoanApplicationComment update(LoanApplicationComment loanApplicationComment);

  LoanApplicationComment findById(String uuid);

  void deleteByUuid(String uuid);

  List<LoanApplicationComment> history(String loanApplicationId, String code);

  List<LoanApplicationComment> getCommentsByLoanId(String loanId);
}
