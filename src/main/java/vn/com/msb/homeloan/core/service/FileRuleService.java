package vn.com.msb.homeloan.core.service;

import java.util.HashMap;
import java.util.List;
import vn.com.msb.homeloan.core.model.FileRule;

public interface FileRuleService {

  List<FileRule> getFileRules(HashMap<String, Object> bien);
}
