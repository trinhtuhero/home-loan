package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.constant.FileRuleEnum;
import vn.com.msb.homeloan.core.model.FileConfigCategory;

public interface FileConfigCategoryService {

  List<FileConfigCategory> findByIds(List<String> list);

  FileConfigCategory findByUuid(String uuid);

  List<FileConfigCategory> getFileConfigCategories(List<String> fileConfigIds,
      String loanApplicationId);

  List<FileConfigCategory> getFileConfigCategories(String loanApplicationId,
      FileRuleEnum fileRuleEnum);

  boolean cmsCheckUploadFile(String loanId);
}
