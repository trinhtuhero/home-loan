package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.model.FileRule;
import vn.com.msb.homeloan.core.model.mapper.FileRuleMapper;
import vn.com.msb.homeloan.core.repository.FileRuleRepository;
import vn.com.msb.homeloan.core.service.FileRuleService;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FileRuleServiceImpl implements FileRuleService {

  private final FileRuleRepository fileRuleRepository;

  // danh sách key, value biết trước
  public List<FileRule> getFileRules(HashMap<String, Object> hm) {
    List<FileRule> fileRules = findAll();
    List<FileRule> list = new ArrayList<>();
    for (FileRule fileRule : fileRules) {
      String exp = fileRule.getExpression();
      if (HomeLoanUtil.evalExpression(hm, exp)) {
        list.add(fileRule);
      }
    }
    return list;
  }

  public List<FileRule> findAll() {
    return FileRuleMapper.INSTANCE.toModels(fileRuleRepository.findAll());
  }
}
