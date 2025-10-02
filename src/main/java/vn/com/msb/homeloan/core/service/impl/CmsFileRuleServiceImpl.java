package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.model.CmsFileRule;
import vn.com.msb.homeloan.core.model.mapper.CmsFileRuleMapper;
import vn.com.msb.homeloan.core.repository.CmsFileRuleRepository;
import vn.com.msb.homeloan.core.service.CmsFileRuleService;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CmsFileRuleServiceImpl implements CmsFileRuleService {

  private final CmsFileRuleRepository cmsFileRuleRepository;

  // danh sách key, value biết trước
  public List<CmsFileRule> getFileRules(HashMap<String, Object> hm) {
    List<CmsFileRule> fileRules = findAll();
    List<CmsFileRule> list = new ArrayList<>();
    for (CmsFileRule fileRule : fileRules) {
      String exp = fileRule.getExpression();
      if (HomeLoanUtil.evalExpression(hm, exp)) {
        list.add(fileRule);
      }
    }
    return list;
  }

  public List<CmsFileRule> findAll() {
    return CmsFileRuleMapper.INSTANCE.toModels(cmsFileRuleRepository.findAll());
  }
}
