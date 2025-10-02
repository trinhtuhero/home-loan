package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.api.dto.request.CMSAmlCheckRequest;
import vn.com.msb.homeloan.core.model.Aml;
import vn.com.msb.homeloan.core.model.CMSGetAmlInfo;

public interface AmlService {

  List<Aml> checkAmls(String loanId, List<CMSAmlCheckRequest.AmlRequest> checkAmls);

  List<CMSGetAmlInfo> getAmlInfo(String loanId);
}
