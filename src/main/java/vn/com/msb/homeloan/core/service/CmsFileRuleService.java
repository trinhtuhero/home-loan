package vn.com.msb.homeloan.core.service;

import java.util.HashMap;
import java.util.List;
import vn.com.msb.homeloan.core.model.CmsFileRule;

public interface CmsFileRuleService {

  List<CmsFileRule> getFileRules(HashMap<String, Object> hm);
}
