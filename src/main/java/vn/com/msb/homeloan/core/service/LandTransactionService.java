package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.api.dto.request.LandTransactionRequest;
import vn.com.msb.homeloan.api.dto.response.LandTransactionResponse;

public interface LandTransactionService {

  List<LandTransactionResponse> saveOrUpdate(List<LandTransactionRequest> request,
      String otherIncomeId);

  List<LandTransactionResponse> getByOtherIncomeId(String otherIncomeId);

  void deleteById(String uuid);

  void deleteByOtherIncomeId(String otherIncomeId);
}
