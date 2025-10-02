package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.ContactPerson;

public interface ContactPersonService {

  ContactPerson findById(String uuid);

  ContactPerson save(ContactPerson marriedPerson, ClientTypeEnum clientType);

  List<ContactPerson> findByLoanId(String loanId);
}
