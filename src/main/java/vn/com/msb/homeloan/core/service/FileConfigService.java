package vn.com.msb.homeloan.core.service;

import java.sql.SQLException;
import java.util.List;
import vn.com.msb.homeloan.core.constant.FileRuleEnum;
import vn.com.msb.homeloan.core.model.FileConfig;

public interface FileConfigService {

  List<FileConfig> findByIds(List<String> list);

  List<FileConfig> findByFileRuleIds(List<String> list);

  List<FileConfig> findByCmsFileRuleIds(List<String> list);

  FileConfig findByUuid(String uuid);

  FileConfig setFileConfigCategory(FileConfig fileConfig);

  // Lấy danh sách file config thỏa mãn rule
  List<String> getUuidsSatisfyRuleEngine(String loanApplicationId, FileRuleEnum fileRuleEnum)
      throws SQLException;
}
