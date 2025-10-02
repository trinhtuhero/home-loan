package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.model.CreditworthinessItem;

public interface CreditworthinessItemService {

  CreditworthinessItem save(CreditworthinessItem creditworthinessItem);

  List<CreditworthinessItem> getByLoanId(String loanId);

  void deleteByUuid(String uuid);

  void deleteByConditions(CreditworthinessItem creditworthinessItem);
}
