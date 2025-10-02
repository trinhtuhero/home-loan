package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;
import vn.com.msb.homeloan.core.model.MarriedPerson;

public interface MarriedPersonService {

  MarriedPerson findById(String uuid);

  MarriedPersonEntity save(MarriedPerson marriedPerson, ClientTypeEnum clientType);

  void deleteById(String uuid);

  List<MarriedPerson> findByLoanId(String loanId);
}
