package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.model.FileConfig;
import vn.com.msb.homeloan.core.model.FileConfigCategory;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.repository.FileConfigCategoryRepository;
import vn.com.msb.homeloan.core.repository.FileConfigRepository;
import vn.com.msb.homeloan.core.repository.LoanUploadFileRepository;
import vn.com.msb.homeloan.core.repository.UploadFileCommentRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CmsFileRuleService;
import vn.com.msb.homeloan.core.service.FileConfigCategoryService;
import vn.com.msb.homeloan.core.service.FileConfigService;
import vn.com.msb.homeloan.core.service.FileRuleService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.LoanUploadFileService;
import vn.com.msb.homeloan.core.service.UploadFileStatusService;

@ExtendWith(MockitoExtension.class)
class FileConfigCategoryServiceTest {

  FileConfigCategoryService fileConfigCategoryService;

  @Mock
  FileConfigCategoryRepository fileConfigCategoryRepository;

  @Mock
  FileConfigRepository fileConfigRepository;

  @Mock
  LoanUploadFileRepository loanUploadFileRepository;

  @Mock
  UploadFileCommentRepository uploadFileCommentRepository;

  @Mock
  UploadFileStatusService uploadFileStatusService;

  @Mock
  LoanApplicationService loanApplicationService;

  @Mock
  FileRuleService fileRuleService;

  @Mock
  LoanUploadFileService loanUploadFileService;

  @Mock
  FileConfigService fileConfigService;

  @Mock
  CmsFileRuleService cmsFileRuleService;

  @Mock
  CMSTabActionService cmsTabActionService;

  @BeforeEach
  void setUp() {
    this.fileConfigCategoryService = new FileConfigCategoryServiceImpl(
        fileConfigCategoryRepository,
        fileConfigRepository,
        loanUploadFileRepository,
        uploadFileCommentRepository,
        uploadFileStatusService,
        loanApplicationService,
        fileRuleService,
        loanUploadFileService,
        fileConfigService,
        cmsFileRuleService,
        cmsTabActionService,
        null
    );
  }

  @Test
  void givenValidInput_ThenCheckUploadFile_shouldReturnSuccess() {
    LoanApplication loanApplication = new LoanApplication();
    loanApplication.setUuid("loanId");

    doReturn(loanApplication).when(loanApplicationService).findById("loanId");

    // luồng ưu tiên, kê khai

    // hold on
//        loanApplication.setApprovalFlow(ApprovalFlowEnum.PRIORITY);
//        loanApplication.setRecognitionMethod1(RecognitionMethod1Enum.DECLARATION);
    FileConfig fileConfig1 = new FileConfig();
    FileConfigCategory fileConfigCategory1 = new FileConfigCategory();
    fileConfigCategory1.setCode("002-001");
    fileConfig1.setFileConfigCategory(fileConfigCategory1);
    FileConfig fileConfig2 = new FileConfig();
    FileConfigCategory fileConfigCategory2 = new FileConfigCategory();
    fileConfig2.setFileConfigCategory(fileConfigCategory2);
    FileConfig fileConfig3 = new FileConfig();
    FileConfigCategory fileConfigCategory3 = new FileConfigCategory();
    fileConfigCategory3.setCode("002-005");
    fileConfig3.setFileConfigCategory(fileConfigCategory3);
    FileConfig fileConfig4 = new FileConfig();
    FileConfigCategory fileConfigCategory4 = new FileConfigCategory();
    fileConfigCategory4.setCode("002-006");
    fileConfig4.setFileConfigCategory(fileConfigCategory4);

    List<FileConfig> fileConfigs = new ArrayList<>(
        Arrays.asList(fileConfig1, fileConfig2, fileConfig3, fileConfig4));

    // doReturn(fileConfigs).when(fileConfigService).findByCmsFileRuleIds(any());

    boolean result = fileConfigCategoryService.cmsCheckUploadFile("loanId");

    // assertTrue(fileConfig1.getRequire() == false);
    // assertTrue(fileConfigs.size() == 2 && fileConfigs.containsAll(Arrays.asList(fileConfig1, fileConfig2)));

    assertTrue(result);
  }

  @Test
  void collectFileConfigsFromFileConfigCategory()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    FileConfigCategory fileConfigCategory = new FileConfigCategory();

    FileConfigCategory fileConfigCategoryChild1 = new FileConfigCategory();

    FileConfigCategory fileConfigCategoryChild2 = new FileConfigCategory();
    FileConfig fileConfig1 = new FileConfig();
    FileConfig fileConfig2 = new FileConfig();
    fileConfigCategoryChild2.setFileConfigList(Arrays.asList(fileConfig1, fileConfig2));

    FileConfigCategory fileConfigCategoryChild3 = new FileConfigCategory();

    FileConfigCategory fileConfigCategoryChild4 = new FileConfigCategory();
    FileConfig fileConfig3 = new FileConfig();
    FileConfig fileConfig4 = new FileConfig();
    fileConfigCategoryChild4.setFileConfigList(Arrays.asList(fileConfig3, fileConfig4));

    fileConfigCategoryChild1.setFileConfigCategoryList(
        Arrays.asList(fileConfigCategoryChild3, fileConfigCategoryChild4));

    fileConfigCategory.setFileConfigCategoryList(
        Arrays.asList(fileConfigCategoryChild1, fileConfigCategoryChild2));

    List<FileConfig> fileConfigLst = new ArrayList<>();
    Method privateMethod = FileConfigCategoryServiceImpl.class.getDeclaredMethod(
        "collectFileConfigsFromFileConfigCategory", List.class, FileConfigCategory.class);
    privateMethod.setAccessible(true);
    privateMethod.invoke(fileConfigCategoryService, fileConfigLst, fileConfigCategory);
    assertTrue(fileConfigLst.size() == 4);
  }
}
