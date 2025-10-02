package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.CollateralOwner;

public interface CollateralOwnerService {

  CollateralOwner findById(String id);

  List<CollateralOwner> findByLoanId(String loanId);

  List<CollateralOwner> getAllByLoanId(String loanId);

  CollateralOwner save(CollateralOwner collateralOwner);

  List<CollateralOwner> saves(List<CollateralOwner> collateralOwner);

  void deleteById(String id, ClientTypeEnum clientType);
}
