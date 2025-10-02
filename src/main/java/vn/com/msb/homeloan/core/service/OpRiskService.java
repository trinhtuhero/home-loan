package vn.com.msb.homeloan.core.service;

import java.text.ParseException;
import java.util.List;
import vn.com.msb.homeloan.core.model.CMSGetOpRiskCollateralInfo;
import vn.com.msb.homeloan.core.model.CMSGetOpRiskInfo;
import vn.com.msb.homeloan.core.model.OpRisk;
import vn.com.msb.homeloan.core.model.request.OpsRiskCheckCRequest;
import vn.com.msb.homeloan.core.model.request.OpsRiskCheckP;

public interface OpRiskService {

  List<OpRisk> checkP(List<OpsRiskCheckP> opsRiskCheckPs, String loanApplicationId)
      throws ParseException;

  List<OpRisk> checkC(List<OpsRiskCheckCRequest.OpsRiskCheckC> opsRiskCheckCs,
      String loanApplicationId) throws ParseException;

  List<CMSGetOpRiskInfo> getOpRiskInfo(String loanId);

  List<CMSGetOpRiskCollateralInfo> getOpRiskCollateralInfo(String loanId);
}
