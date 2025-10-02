package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.FileRuleEnum;
import vn.com.msb.homeloan.core.entity.FileConfigEntity;
import vn.com.msb.homeloan.core.model.CmsFileRule;
import vn.com.msb.homeloan.core.model.FileRule;
import vn.com.msb.homeloan.core.repository.FileConfigRepository;
import vn.com.msb.homeloan.core.service.CmsFileRuleService;
import vn.com.msb.homeloan.core.service.FileConfigService;
import vn.com.msb.homeloan.core.service.FileRuleService;
import vn.com.msb.homeloan.core.service.LoanUploadFileService;

@ExtendWith(MockitoExtension.class)
class FileConfigServiceImplTest {

  FileConfigService fileConfigService;

  @Mock
  FileConfigRepository fileConfigRepository;

  @Mock
  FileRuleService fileRuleService;

  @Mock
  LoanUploadFileService loanUploadFileService;

  @Mock
  CmsFileRuleService cmsFileRuleService;

  @BeforeEach
  void setUp() {
    this.fileConfigService = new FileConfigServiceImpl(
        fileConfigRepository,
        null,
        fileRuleService,
        loanUploadFileService,
        cmsFileRuleService
    );
  }

  @Test
  void givenEmpty_thenFindByFileRuleIds_shouldReturnEmpty() {
    assertTrue(fileConfigRepository.findByFileRuleIds(new ArrayList<>()).isEmpty());
  }

  @Test
  void givenValidInput_getUuidsSatisfyRuleEngine_shouldReturnSuccess() throws SQLException {
    FileRule fileRule1 = new FileRule();
    fileRule1.setUuid("1111");
    FileRule fileRule2 = new FileRule();
    fileRule2.setUuid("2222");
    FileRule fileRule3 = new FileRule();
    fileRule3.setUuid("3333");

    FileConfigEntity fileConfigEntity1 = new FileConfigEntity();
    fileConfigEntity1.setUuid("1111");
    FileConfigEntity fileConfigEntity2 = new FileConfigEntity();
    fileConfigEntity2.setUuid("2222");
    FileConfigEntity fileConfigEntity3 = new FileConfigEntity();
    fileConfigEntity3.setUuid("3333");
    FileConfigEntity fileConfigEntity4 = new FileConfigEntity();
    fileConfigEntity4.setUuid("4444");

    CmsFileRule cmsFileRule1 = new CmsFileRule();
    cmsFileRule1.setUuid("1111");
    CmsFileRule cmsFileRule2 = new CmsFileRule();
    cmsFileRule2.setUuid("2222");
    CmsFileRule cmsFileRule3 = new CmsFileRule();
    cmsFileRule3.setUuid("3333");

    FileConfigEntity cmsfileConfigEntity1 = new FileConfigEntity();
    cmsfileConfigEntity1.setUuid("1111");
    FileConfigEntity cmsfileConfigEntity2 = new FileConfigEntity();
    cmsfileConfigEntity2.setUuid("2222");
    FileConfigEntity cmsfileConfigEntity3 = new FileConfigEntity();
    cmsfileConfigEntity3.setUuid("3333");
    FileConfigEntity cmsfileConfigEntity4 = new FileConfigEntity();
    cmsfileConfigEntity4.setUuid("4444");

    List<FileRule> fileRuleList = Arrays.asList(fileRule1, fileRule2, fileRule3);
    List<CmsFileRule> cmsfileRuleList = Arrays.asList(cmsFileRule1, cmsFileRule3, cmsFileRule3);

    doReturn(new HashMap<>()).when(loanUploadFileService).getMapKeys("", FileRuleEnum.CMS);

    doReturn(fileRuleList).when(fileRuleService).getFileRules(any());

    doReturn(cmsfileRuleList).when(cmsFileRuleService).getFileRules(any());

    doReturn(Arrays.asList(fileConfigEntity1, fileConfigEntity2)).when(fileConfigRepository)
        .findByFileRuleIds(any());
    doReturn(Arrays.asList(fileConfigEntity1, fileConfigEntity2, fileConfigEntity3,
        fileConfigEntity4)).when(fileConfigRepository).findByCmsFileRuleIds(any());

    List<String> list1 = fileConfigService.getUuidsSatisfyRuleEngine("", FileRuleEnum.LDP);
    assertTrue(list1.size() == 2);

    List<String> list2 = fileConfigService.getUuidsSatisfyRuleEngine("", FileRuleEnum.CMS);
    assertTrue(list2.size() == 4);
  }
}
