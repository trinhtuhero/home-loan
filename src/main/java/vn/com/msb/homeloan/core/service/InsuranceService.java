package vn.com.msb.homeloan.core.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import vn.com.msb.homeloan.core.constant.InterestedEnum;
import vn.com.msb.homeloan.core.model.request.integration.cj4.CalculateInsuranceRequest;
import vn.com.msb.homeloan.core.model.response.cj4.CalculateInsurance;
import vn.com.msb.homeloan.core.model.response.cj4.GetLifeInfo;
import vn.com.msb.homeloan.core.model.response.cj4.ProductInfo;

public interface InsuranceService {

  boolean sendLead(String loanId, String emplId) throws ParseException, IOException;

  String getLink(String loanId) throws ParseException, IOException;

  boolean updateLead(String loanId, String emplId, InterestedEnum status, String channel)
      throws ParseException, IOException;

  List<ProductInfo> getProducts(String loanId) throws IOException;

  CalculateInsurance calculateInsurance(CalculateInsuranceRequest request, String loanId)
      throws ParseException, IOException;

  GetLifeInfo getLifeInfo(String loanId) throws ParseException, IOException;

  boolean updateLeadCms(String loanId, InterestedEnum status) throws ParseException, IOException;
}
