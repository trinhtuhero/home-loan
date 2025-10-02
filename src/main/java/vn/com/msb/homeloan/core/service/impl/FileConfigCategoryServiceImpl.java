package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.entity.LoanUploadFileEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.*;
import vn.com.msb.homeloan.core.model.mapper.*;
import vn.com.msb.homeloan.core.repository.FileConfigCategoryRepository;
import vn.com.msb.homeloan.core.repository.FileConfigRepository;
import vn.com.msb.homeloan.core.repository.LoanUploadFileRepository;
import vn.com.msb.homeloan.core.repository.UploadFileCommentRepository;
import vn.com.msb.homeloan.core.service.*;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class FileConfigCategoryServiceImpl implements FileConfigCategoryService {

  private final FileConfigCategoryRepository fileConfigCategoryRepository;

  private final FileConfigRepository fileConfigRepository;

  private final LoanUploadFileRepository loanUploadFileRepository;

  private final UploadFileCommentRepository uploadFileCommentRepository;

  private final UploadFileStatusService uploadFileStatusService;

  private final LoanApplicationService loanApplicationService;

  private final FileRuleService fileRuleService;

  private final LoanUploadFileService loanUploadFileService;

  private final FileConfigService fileConfigService;

  private final CmsFileRuleService cmsFileRuleService;

  private final CMSTabActionService cmsTabActionService;

  private final CommonIncomeService commonIncomeService;

  @Override
  public List<FileConfigCategory> getFileConfigCategories(List<String> fileConfigIds,
      String loanApplicationId) {
    List<FileConfigCategory> topList = new ArrayList<>();
    List<String> addedStrs = new ArrayList<>();
    List<FileConfig> fileConfigList = FileConfigMapper.INSTANCE.toModels(
        fileConfigRepository.findByIdsKeepOrder(fileConfigIds));
    List<String> fileConfigCategoryIds = fileConfigList.stream()
        .map(fileConfig -> fileConfig.getFileConfigCategoryId()).collect(Collectors.toList());
    recursiveCategories(fileConfigCategoryIds, fileConfigIds, topList, addedStrs,
        loanApplicationId);
    return topList;
  }

  @Override
  public List<FileConfigCategory> getFileConfigCategories(String loanApplicationId,
      FileRuleEnum fileRuleEnum) {
    // hold on -> Ok
    CommonIncome commonIncome = commonIncomeService.findByLoanApplicationId(loanApplicationId);

    List<String> fileRuleIds;
    List<FileConfig> fileConfigList = new ArrayList<>();

    if (FileRuleEnum.LDP.equals(fileRuleEnum)) {
      List<FileRule> fileRuleList = fileRuleService.getFileRules(
          loanUploadFileService.getMapKeys(loanApplicationId, fileRuleEnum));
      fileRuleIds = fileRuleList.stream().map(fileRule -> fileRule.getUuid())
          .collect(Collectors.toList());

      fileConfigList = fileConfigService.findByFileRuleIds(fileRuleIds);
    } else if (FileRuleEnum.CMS.equals(fileRuleEnum)) {
      List<CmsFileRule> fileRuleList = cmsFileRuleService.getFileRules(
          loanUploadFileService.getMapKeys(loanApplicationId, fileRuleEnum));
      fileRuleIds = fileRuleList.stream().map(fileRule -> fileRule.getUuid())
          .collect(Collectors.toList());

      fileConfigList = fileConfigService.findByCmsFileRuleIds(fileRuleIds);

      fileConfigList.forEach(fileConfig -> fileConfigService.setFileConfigCategory(fileConfig));
      HomeLoanUtil.applyRulesToIncomeFiles(commonIncome, fileConfigList);
    }

    List<String> fileConfigIds = fileConfigList.stream().map(fileConfig -> fileConfig.getUuid())
        .collect(Collectors.toList());
    List<FileConfigCategory> fileConfigCategories = getFileConfigCategories(fileConfigIds,
        loanApplicationId);

    if (FileRuleEnum.CMS.equals(fileRuleEnum)) {
      List<FileConfig> fileConfigLst = new ArrayList<>();

      for (FileConfigCategory fileConfigCategory : fileConfigCategories) {
        fileConfigLst.addAll(
            collectFileConfigsFromFileConfigCategory(new ArrayList<>(), fileConfigCategory));
      }

      fileConfigLst.forEach(
          fileConfig -> {
            fileConfigService.setFileConfigCategory(fileConfig);
            if (FileRuleEnum.CMS.equals(fileRuleEnum)) {
              fileConfig.setLongGuideDesc(fileConfig.getCmsLongGuideDesc());
            }
          }
      );

      HomeLoanUtil.applyRulesToIncomeFiles(commonIncome, fileConfigLst);
    }

    return fileConfigCategories;
  }

  private List<FileConfig> collectFileConfigsFromFileConfigCategory(List<FileConfig> fileConfigLst,
      FileConfigCategory fileConfigCategory) {
    if (!fileConfigCategory.getFileConfigList().isEmpty()) {
      fileConfigLst.addAll(fileConfigCategory.getFileConfigList());
    }
    if (!fileConfigCategory.getFileConfigCategoryList().isEmpty()) {
      for (FileConfigCategory fileConfigCat : fileConfigCategory.getFileConfigCategoryList()) {
        collectFileConfigsFromFileConfigCategory(fileConfigLst, fileConfigCat);
      }
    }
    return fileConfigLst;
  }

  private void recursiveCategories(List<String> fileConfigCategoryIds, List<String> fileConfigIds,
      List<FileConfigCategory> topList, List<String> addedStrs, String loanApplicationId) {
    // Danh sách categorie là lá
    List<FileConfigCategory> list = getLeftCategoryNodes(fileConfigCategoryIds, fileConfigIds,
        loanApplicationId);
    // Loop
    for (FileConfigCategory fileConfigCategory : list) {
      // Thêm lá vào cây nếu chưa được duyệt
      // Chưa được duyệt và parent cũng chưa được duyệt
      if (!addedStrs.contains(fileConfigCategory.getUuid()) && !addedStrs.contains(
          fileConfigCategory.getParentId())) {
        topList.add(fileConfigCategory);
        addedStrs.add(fileConfigCategory.getUuid());
      }
      // Đệ qui đến khi gặp root = null
      if (fileConfigCategory.getParentId() != null) {
        // Gặp node cha - Đã được duyệt
        // Thêm con nếu con chưa có trong cây
        if (addedStrs.contains(fileConfigCategory.getParentId()) && !addedStrs.contains(
            fileConfigCategory.getUuid())) {
          List<FileConfigCategory> list1 = new ArrayList<>();
          findParent(topList, fileConfigCategory.getParentId(), list1);
          list1.get(0).getFileConfigCategoryList().add(fileConfigCategory);
          addedStrs.add(fileConfigCategory.getUuid());
        }

        // Gặp node cha - Đã được duyệt
        // Nhưng con không trong cây của cha
        //Cha
        List<FileConfigCategory> list3 = new ArrayList<>();
        findParent(topList, fileConfigCategory.getParentId(), list3);
        //Con không trong cha
        if (list3.size() > 0) {
          List<FileConfigCategory> list4 = new ArrayList<>();
          findParent(Arrays.asList(list3.get(0)), fileConfigCategory.getUuid(), list4);
          if (addedStrs.contains(fileConfigCategory.getParentId()) && list4.isEmpty()) {
            // Thêm con vào cha
            List<FileConfigCategory> list5 = new ArrayList<>();
            findParent(topList, fileConfigCategory.getUuid(), list5);
            list3.get(0).getFileConfigCategoryList().add(list5.get(0));
            //
            topList.remove(list5.get(0));
          }
        }

        // TODO
        // Gặp node cha - Chưa được duyệt
        if (!addedStrs.contains(fileConfigCategory.getParentId())) {
          List<FileConfigCategory> list2 = new ArrayList<>();
          findParent(topList, fileConfigCategory.getUuid(), list2);
          // Thêm cha vào cây
          FileConfigCategory parent2 = findByUuid(fileConfigCategory.getParentId());
          // Thêm con vào cha
          parent2.getFileConfigCategoryList().add(list2.get(0));
          topList.add(parent2);
          topList.remove(list2.get(0));
          addedStrs.add(parent2.getUuid());
        }
        recursiveCategories(Arrays.asList(fileConfigCategory.getParentId()), fileConfigIds, topList,
            addedStrs, loanApplicationId);
      }
    }
  }

  //Tìm ra cha trong topList dựa vào uuid của cha
  private void findParent(List<FileConfigCategory> topList, String uuid,
      List<FileConfigCategory> list) {
    for (FileConfigCategory fileConfigCategory : topList) {
      if (fileConfigCategory.getUuid().equals(uuid)) {
        list.add(fileConfigCategory);
      } else {
        findParent(fileConfigCategory.getFileConfigCategoryList(), uuid, list);
      }
    }
  }

  private List<FileConfigCategory> getLeftCategoryNodes(List<String> fileConfigCategoryIds,
      List<String> fileConfigIds, String loanApplicationId) {
    List<FileConfigCategory> fileConfigCategoryList = findByIdsKeepOrder(fileConfigCategoryIds);
    List<FileConfig> fileConfigList = FileConfigMapper.INSTANCE.toModels(
        fileConfigRepository.findByIdsKeepOrder(fileConfigIds));
    for (FileConfigCategory fileConfigCategory : fileConfigCategoryList) {
      List<FileConfig> list = new ArrayList<>();
      for (FileConfig fileConfig : fileConfigList) {
        if (fileConfigIds.contains(fileConfig.getUuid()) && fileConfigCategory.getUuid()
            .equals(fileConfig.getFileConfigCategoryId())) {
          // Get loan upload files by file config and loan application id
          List<LoanUploadFileEntity> loanApplicationEntityList = loanUploadFileRepository.findByFileConfigIdAndLoanApplicationIdAndStatus(
              fileConfig.getUuid(), loanApplicationId, LoanUploadFileStatusEnum.UPLOADED);
          List<LoanUploadFile> loanUploadFileList = LoanUploadFileMapper.INSTANCE.toModels(
              loanApplicationEntityList);
          fileConfig.setLoanUploadFileList(loanUploadFileList);
          fileConfig.setComments(UploadFileCommentMapper.INSTANCE.toModels(
              uploadFileCommentRepository.findByLoanApplicationIdAndStatusAndFileConfigIdOrderByCreatedAt(
                  loanApplicationId, CommentStatusEnum.NEW.toString(), fileConfig.getUuid())));

          // 10082022
          // Check enough
          UploadFileStatus uploadFileStatus = uploadFileStatusService.checkEnough(loanApplicationId,
              fileConfig.getUuid());
          fileConfig.setUploadFileStatus(uploadFileStatus);

          list.add(fileConfig);
        }
      }
      fileConfigCategory.setFileConfigList(list);
    }
    return fileConfigCategoryList;
  }

  public List<FileConfigCategory> findByIdsKeepOrder(List<String> fileConfigCategoryIds) {
    return FileConfigCategoryMapper.INSTANCE.toModels(
        fileConfigCategoryRepository.findByIdsKeepOrder(fileConfigCategoryIds));
  }

  public List<FileConfigCategory> findByIds(List<String> list) {
    return FileConfigCategoryMapper.INSTANCE.toModels(fileConfigCategoryRepository.findByIds(list));
  }

  @Override
  public FileConfigCategory findByUuid(String uuid) {
    return FileConfigCategoryMapper.INSTANCE.toModel(fileConfigCategoryRepository.findByUuid(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid", uuid)));
  }

  @Override
  public boolean cmsCheckUploadFile(String loanId) {
    LoanApplication loanApplication = loanApplicationService.findById(loanId);
    loanApplicationService.checkEditLoanApp(
        LoanApplicationMapper.INSTANCE.toEntity(loanApplication), ClientTypeEnum.CMS);

        /*
        // Check require hồ sơ
        List<CmsFileRule> fileRules = cmsFileRuleService.getFileRules(loanUploadFileService.getMapKeys(loanId, FileRuleEnum.CMS));
        List<String> fileRuleIds = fileRules.stream().map(fileRule -> fileRule.getUuid()).collect(Collectors.toList());
        List<FileConfig> fileConfigs = fileConfigService.findByCmsFileRuleIds(fileRuleIds);

        fileConfigs.forEach(fileConfig -> fileConfigService.setFileConfigCategory(fileConfig));

        HomeLoanUtil.applyRulesToIncomeFiles(loanApplication, fileConfigs);

        HashMap<String, List<LoanUploadFileEntity>> code2ListOfFiles = new HashMap<>();
        List<ErrorEnum> errorEnums = new ArrayList<>();

        HashMap<String, String> code2FileConfigCategory = new HashMap<>();
        HashMap<String, String> code2FileConfig = new HashMap<>();
        HashMap<String, List<String>> linkedHashMap = new LinkedHashMap();

        for (FileConfig fileConfig : fileConfigs) {
            if (fileConfig.getCode() != null) code2FileConfig.put(fileConfig.getCode(), fileConfig.getName());
            if (fileConfig.getFileConfigCategory() != null && fileConfig.getFileConfigCategory().getCode() != null)
                code2FileConfigCategory.put(fileConfig.getFileConfigCategory().getCode(), fileConfig.getFileConfigCategory().getName());

            if (fileConfig.getRequire() != null && fileConfig.getRequire()) {
                List<LoanUploadFileEntity> loanUploadFileEntities = loanUploadFileRepository.findByFileConfigIdAndLoanApplicationIdAndStatus(fileConfig.getUuid(), loanId, LoanUploadFileStatusEnum.UPLOADED);
                if (CollectionUtils.isEmpty(loanUploadFileEntities)) {
                    String category = fileConfig.getFileConfigCategory() != null ? fileConfig.getFileConfigCategory().getCode() : null;
                    if (category != null) {
                        if (linkedHashMap.containsKey(fileConfig.getFileConfigCategory().getCode())) {
                            linkedHashMap.get(fileConfig.getFileConfigCategory().getCode()).add(fileConfig.getCode());
                        } else {
                            linkedHashMap.put(fileConfig.getFileConfigCategory().getCode(), new ArrayList<>(Arrays.asList(fileConfig.getCode())));
                        }
                    }
                }
            }
            if (Constants.TSTL_FILE_CONFIG_CODES.contains(fileConfig.getCode())) {
                List<LoanUploadFileEntity> loanUploadFileEntities = loanUploadFileRepository.findByFileConfigIdAndLoanApplicationIdAndStatus(fileConfig.getUuid(), loanId, LoanUploadFileStatusEnum.UPLOADED);
                code2ListOfFiles.put(fileConfig.getCode(), loanUploadFileEntities);
            }
        }
        //
        if (!linkedHashMap.isEmpty()) {
            errorEnums.add(ErrorEnum.FILE_UPLOAD_REQUIRED);
        }
        //
        if (!code2ListOfFiles.isEmpty()
                && code2ListOfFiles.get(Constants.TSTL_FILE_CONFIG_CODES.get(0)).isEmpty()
                && code2ListOfFiles.get(Constants.TSTL_FILE_CONFIG_CODES.get(1)).isEmpty()
                && code2ListOfFiles.get(Constants.TSTL_FILE_CONFIG_CODES.get(2)).isEmpty()) {
            errorEnums.add(ErrorEnum.UPLOAD_AT_LEAST_ONE);
        }

        if (!errorEnums.isEmpty()) {
            List requireItems = new ArrayList();
            for (Map.Entry<String, List<String>> set : linkedHashMap.entrySet()) {
                RequireItem requireItem = new RequireItem();
                requireItem.setCategory(code2FileConfigCategory.get(set.getKey()));
                List<String> files = new ArrayList<>();
                set.getValue().forEach(s -> files.add(code2FileConfig.get(s)));
                requireItem.setFiles(files);
                requireItems.add(requireItem);
            }
            throw new ApplicationException(errorEnums, requireItems);
        }
        */

    //Save tab action
    cmsTabActionService.save(loanId, CMSTabEnum.FILE_UPLOAD);
    return true;
  }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class RequireItem {

  private String category;
  private List<String> files;
}