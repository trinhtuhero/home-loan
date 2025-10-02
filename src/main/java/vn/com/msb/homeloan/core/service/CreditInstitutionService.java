package vn.com.msb.homeloan.core.service;

import java.util.List;
import java.util.Map;
import vn.com.msb.homeloan.core.entity.CreditInstitutionEntity;
import vn.com.msb.homeloan.core.model.CreditInstitution;

public interface CreditInstitutionService {

  List<CreditInstitution> getAll();

  Map<String, CreditInstitutionEntity> getMapByCodes(List<String> codes);
}
