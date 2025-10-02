package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.core.model.Collateral;
import vn.com.msb.homeloan.core.model.Mvalue;
import vn.com.msb.homeloan.core.model.request.mvalue.CMSCreateProfileRequest;
import vn.com.msb.homeloan.core.model.request.mvalue.GetCollateralByCodeRequest;
import vn.com.msb.homeloan.core.model.request.mvalue.GetPriceBracketInfoRequest;
import vn.com.msb.homeloan.core.model.response.mvalue.CreateProfileResponse;
import vn.com.msb.homeloan.core.model.response.mvalue.GetPriceBracketResponse;

import java.util.List;

public interface MvalueService {

  Mvalue getByCode(GetCollateralByCodeRequest getCollateralByCode, String loanApplicationId)
      throws Exception;

  List<Collateral> checkDuplicate(String loanId);

  String createProfile(CMSCreateProfileRequest request);

  GetPriceBracketResponse getPriceBracketInfo(GetPriceBracketInfoRequest request);
}
