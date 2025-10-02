package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.core.model.MvalueDocument;

import java.util.List;

public interface DocumentMappingService {
  List<MvalueDocument> getDocumentMapping(String loanId, String collateralType, String type, String collateralId);
}
