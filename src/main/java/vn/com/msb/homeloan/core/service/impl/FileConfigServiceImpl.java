package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.FileRuleEnum;
import vn.com.msb.homeloan.core.entity.FileConfigCategoryEntity;
import vn.com.msb.homeloan.core.entity.FileConfigEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CmsFileRule;
import vn.com.msb.homeloan.core.model.FileConfig;
import vn.com.msb.homeloan.core.model.FileConfigCategory;
import vn.com.msb.homeloan.core.model.FileRule;
import vn.com.msb.homeloan.core.model.mapper.FileConfigCategoryMapper;
import vn.com.msb.homeloan.core.model.mapper.FileConfigMapper;
import vn.com.msb.homeloan.core.repository.FileConfigCategoryRepository;
import vn.com.msb.homeloan.core.repository.FileConfigRepository;
import vn.com.msb.homeloan.core.service.CmsFileRuleService;
import vn.com.msb.homeloan.core.service.FileConfigService;
import vn.com.msb.homeloan.core.service.FileRuleService;
import vn.com.msb.homeloan.core.service.LoanUploadFileService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FileConfigServiceImpl implements FileConfigService {

  private final FileConfigRepository fileConfigRepository;

  private final FileConfigCategoryRepository fileConfigCategoryRepository;

  private final FileRuleService fileRuleService;

  private final LoanUploadFileService loanUploadFileService;

  private final CmsFileRuleService cmsFileRuleService;

  public List<FileConfig> findByIds(List<String> list) {
    return FileConfigMapper.INSTANCE.toModels(fileConfigRepository.findByIds(list));
  }

  @Override
  public List<FileConfig> findByFileRuleIds(List<String> list) {
    return FileConfigMapper.INSTANCE.toModels(fileConfigRepository.findByFileRuleIds(list));
  }

  @Override
  public List<FileConfig> findByCmsFileRuleIds(List<String> list) {
    return FileConfigMapper.INSTANCE.toModels(fileConfigRepository.findByCmsFileRuleIds(list));
  }

  @Override
  public FileConfig findByUuid(String uuid) {
    FileConfigEntity entity = fileConfigRepository.findById(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid", uuid));
    return FileConfigMapper.INSTANCE.toModel(entity);
  }

  @Override
  public FileConfig setFileConfigCategory(FileConfig fileConfig) {
    Optional<FileConfigCategoryEntity> fileConfigCategoryOpt = fileConfigCategoryRepository.findByUuid(
        fileConfig.getFileConfigCategoryId());
    if (fileConfigCategoryOpt.isPresent()) {
      FileConfigCategory model = FileConfigCategoryMapper.INSTANCE.toModel(
          fileConfigCategoryOpt.get());
      recursiveCategory(model);
      fileConfig.setFileConfigCategory(model);
    }
    return fileConfig;
  }

  @Override
  public List<String> getUuidsSatisfyRuleEngine(String loanApplicationId, FileRuleEnum fileRuleEnum)
      throws SQLException {
    List<FileConfig> fileConfigList = new ArrayList<>();

    if (FileRuleEnum.LDP.equals(fileRuleEnum)) {

      List<FileRule> fileRuleList = fileRuleService.getFileRules(
          loanUploadFileService.getMapKeys(loanApplicationId, FileRuleEnum.CMS));
      List<String> fileRuleIds = fileRuleList.stream().map(fileRule -> fileRule.getUuid())
          .collect(Collectors.toList());

      fileConfigList = findByFileRuleIds(fileRuleIds);
    } else if (FileRuleEnum.CMS.equals(fileRuleEnum)) {

      List<CmsFileRule> fileRuleList = cmsFileRuleService.getFileRules(
          loanUploadFileService.getMapKeys(loanApplicationId, FileRuleEnum.CMS));
      List<String> fileRuleIds = fileRuleList.stream().map(fileRule -> fileRule.getUuid())
          .collect(Collectors.toList());

      fileConfigList = findByCmsFileRuleIds(fileRuleIds);
    }

    List<String> fileConfigIds = fileConfigList.stream().map(fileConfig -> fileConfig.getUuid())
        .collect(Collectors.toList());
    return fileConfigIds;
  }

  public FileConfigCategory recursiveCategory(FileConfigCategory fileConfigCategory) {
    Optional<FileConfigCategoryEntity> fileConfigCategoryOpt = fileConfigCategoryRepository.findByUuid(
        fileConfigCategory.getParentId());
    if (fileConfigCategoryOpt.isPresent()) {
      FileConfigCategory model = FileConfigCategoryMapper.INSTANCE.toModel(
          fileConfigCategoryOpt.get());
      fileConfigCategory.setFileConfigCategoryList(new ArrayList<>(Arrays.asList(model)));
      recursiveCategory(model);
    }
    return fileConfigCategory;
  }
}
