package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.api.dto.request.CMSCicRequest;
import vn.com.msb.homeloan.core.model.CMSCic;
import vn.com.msb.homeloan.core.model.CMSGetCicInfo;

public interface CicService {

  List<CMSCic> checkTypeDebt(List<CMSCicRequest.Info> infos, String loanApplicationId)
      throws Exception;

  List<CMSGetCicInfo> getCic(String loanId);
}
