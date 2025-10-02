package vn.com.msb.homeloan.core.service;

import java.util.List;
import javax.script.ScriptException;
import vn.com.msb.homeloan.core.model.LoanCalculate;
import vn.com.msb.homeloan.core.model.LoanCalculation;

public interface LoanCalculationService {

  List<LoanCalculation> calculateLoan(LoanCalculate loanCalculate) throws ScriptException;
}
