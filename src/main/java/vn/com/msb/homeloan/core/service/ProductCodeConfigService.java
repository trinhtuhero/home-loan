package vn.com.msb.homeloan.core.service;

import java.util.HashMap;
import java.util.List;
import vn.com.msb.homeloan.core.model.ProductCodeConfig;

public interface ProductCodeConfigService {

  List<ProductCodeConfig> getProductCodeConfigs(HashMap<String, Object> hm);

  HashMap<String, Object> getMapKeys(String loanId, String loanItemId);
}
