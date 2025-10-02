package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.CmsUserEntity;
import vn.com.msb.homeloan.core.entity.OrganizationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CmsUser;
import vn.com.msb.homeloan.core.model.mapper.CmsUserMapper;
import vn.com.msb.homeloan.core.repository.CmsUserRepository;
import vn.com.msb.homeloan.core.repository.OrganizationRepository;
import vn.com.msb.homeloan.core.service.CmsUserService;
import vn.com.msb.homeloan.core.service.CommonCMSService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;
import vn.com.msb.homeloan.infras.configs.RoleConfig;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@ExtendWith(MockitoExtension.class)
class CmsUserServiceTest {

  private final String EMAIL = "abc@gmail.com";

  LoanApplicationService loanApplicationService;

  CmsUserService cmsUserService;

  @Mock
  CmsUserRepository cmsUserRepository;

  @Mock
  OrganizationRepository organizationRepository;

  @Mock
  RoleConfig roleConfig;

  @Mock
  CommonCMSService commonCMSService;

  @Mock
  EnvironmentProperties environmentProperties;

  @Mock
  MockHttpServletRequest request;


  @BeforeEach
  void setUp() {
    this.cmsUserService = new CmsUserServiceImpl(
        cmsUserRepository,
        organizationRepository,
        roleConfig,
        commonCMSService,
        environmentProperties);
  }

  @Test
  void givenValidInput_ThenFindByEmailNotExistEmail_shouldReturnCmsUserEmailNotFound() {
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      cmsUserService.findByEmail(null);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.CMS_USER_EMAIL_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenFindByEmailExistEmail_shouldReturnCmsUserEmailNotFound() {
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      cmsUserService.findByEmail(EMAIL);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.CMS_USER_EMAIL_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenFindByEmail_shouldReturnSucess() {
    CmsUserEntity entity = CmsUserEntity.builder()
        .email(EMAIL)
        .build();

    doReturn(Optional.of(entity)).when(cmsUserRepository).findByEmail(EMAIL);

    CmsUser result = cmsUserService.findByEmail(EMAIL);

    assertEquals(result.getEmail(), entity.getEmail());
  }


  @Test
  void givenInvalidPhoneNumber_ThenValidate_ShouldReturnFail() {
    try {
      File file = ResourceUtils.getFile(
          "classpath:fileTestImport/import_cms_users_fail_phonenumber.xlsx");
      FileInputStream input = new FileInputStream(file);
      MultipartFile multipartFile = new MockMultipartFile(file.getName(),
          file.getName(), "text/plain", IOUtils.toByteArray(input));
      byte[] data = cmsUserService.importCmsUsers(multipartFile);
      String rsExpected = Constants.ImportCmsUser.ERROR_PHONE_INVALID;
      String rsActual = getErrorFromFileExcel(data, 1);
      assertTrue(rsActual.contains(rsExpected));
    } catch (Exception e) {
      e.getStackTrace();
    }
  }

  @Test
  void givenInvalidTemplateFile_ThenValidateFile_ShouldReturnMessageFailTemplateFile()
      throws Exception {
    File file = ResourceUtils.getFile(
        "classpath:fileTestImport/import_cms_users_failtemplate.xlsx");
    FileInputStream input = new FileInputStream(file);
    MultipartFile multipartFile = new MockMultipartFile(file.getName(),
        file.getName(), "text/plain", IOUtils.toByteArray(input));
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      cmsUserService.importCmsUsers(multipartFile);
    });
    assertEquals(exception.getCode(), ErrorEnum.IMPORT_TEMPLATE_FILE_INVALID.getCode());
  }

  @Test
  void givenvalidFileInput_ThenValidateFile_ShouldReturnMessageSuccess() throws Exception {
    List<OrganizationEntity> organizationEntities = new ArrayList<>();
    OrganizationEntity organizationEntity = OrganizationEntity.builder().code("3114").build();
    organizationEntities.add(organizationEntity);
    Set<String> brandCodes = new HashSet<>();
    brandCodes.add("3114");
    doReturn(organizationEntities).when(organizationRepository).findByBrandCode(brandCodes);
    File file = ResourceUtils.getFile("classpath:fileTestImport/import_cms_users.xlsx");
    FileInputStream input = new FileInputStream(file);
    MultipartFile multipartFile = new MockMultipartFile(file.getName(),
        file.getName(), "text/plain", IOUtils.toByteArray(input));
    doReturn("CJ5-BM").when(roleConfig).getBm();
    doReturn("CJ5-ADMIN").when(roleConfig).getAdmin();
    byte[] data = cmsUserService.importCmsUsers(multipartFile);
    assertEquals(0, data.length);
  }


  @Test
  void givenInputLeaderMailNotExit_ThenValidateFile_ShouldReturnMessageSFailInFile()
      throws Exception {
    File file = ResourceUtils.getFile(
        "classpath:fileTestImport/import_cms_users_fail_leaderemail.xlsx");
    FileInputStream input = new FileInputStream(file);
    MultipartFile multipartFile = new MockMultipartFile(file.getName(),
        file.getName(), "text/plain", IOUtils.toByteArray(input));
    doReturn("CJ5-BM").when(roleConfig).getBm();
    doReturn("CJ5-ADMIN").when(roleConfig).getAdmin();
    byte[] data = cmsUserService.importCmsUsers(multipartFile);
    String rsActual = getErrorFromFileExcel(data, 1);
    assertTrue(rsActual.contains(Constants.ImportCmsUser.ERROR_LEADER_EMAIL_INVALID));
  }

  @Test
  void givenInputEmplIdTooLong_ThenValidateFile_ShouldReturnMessageSFailInFile() throws Exception {
    File file = ResourceUtils.getFile(
        "classpath:fileTestImport/import_cms_users_fail_leaderemail.xlsx");
    FileInputStream input = new FileInputStream(file);
    //("CJ5-RM").when(roleConfig).getRm();
    doReturn("CJ5-BM").when(roleConfig).getBm();
    doReturn("CJ5-ADMIN").when(roleConfig).getAdmin();
    MultipartFile multipartFile = new MockMultipartFile(file.getName(),
        file.getName(), "text/plain", IOUtils.toByteArray(input));
    byte[] data = cmsUserService.importCmsUsers(multipartFile);
    String rsActual = getErrorFromFileExcel(data, 1);
    assertTrue(rsActual.contains(
        String.format(Constants.ImportCmsUser.ERROR_EMPL_ID_EXCEED_MAX_LENGTH,
            Constants.ImportCmsUser.MAX_LENGTH_EMPL_ID)));
  }

  private String getErrorFromFileExcel(byte[] data, Integer row) throws Exception {
    InputStream is = new ByteArrayInputStream(data);
    Workbook book = WorkbookFactory.create(is);
    return book.getSheetAt(0).getRow(row).getCell(8).getStringCellValue();
  }

  @Test
  void givenValidInput_ThenCreateCmsUser_shouldReturnSuccess() {
    doReturn("CJ5-RM").when(roleConfig).getRm();
    doReturn("CJ5-BM").when(roleConfig).getBm();
    doReturn("CJ5-ADMIN").when(roleConfig).getAdmin();
    CmsUser cmsUserRM = CmsUser.builder()
        .email("email")
        .fullName("fullName")
        .emplId("emplId")
        .branchCode("branchCode")
        .leaderEmail("emailBM")
        .phone("0987654321")
        .role("CJ5-RM")
        .area("area")
        .branchName("branchName").build();
    CmsUserEntity cmsUserRMEntity = CmsUserMapper.INSTANCE.toEntity(cmsUserRM);
    doReturn(Optional.of(cmsUserRMEntity)).when(cmsUserRepository)
        .findByEmail(cmsUserRM.getEmail());

    CmsUser cmsUserBM = CmsUser.builder()
        .email("emailBM")
        .fullName("fullName")
        .emplId("emplId")
        .branchCode("branchCode")
        .phone("0987654321")
        .role("CJ5-BM")
        .area("area")
        .branchName("branchName").build();
    CmsUserEntity cmsUserBMEntity = CmsUserMapper.INSTANCE.toEntity(cmsUserBM);
    doReturn(Optional.of(cmsUserBMEntity)).when(cmsUserRepository)
        .findByEmail(cmsUserRM.getLeaderEmail());

    OrganizationEntity organizationEntity = OrganizationEntity.builder()
        .code("branchCode")
        .name("branchName").build();
    doReturn(organizationEntity).when(organizationRepository)
        .findByCode(organizationEntity.getCode());

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      doReturn(cmsUserRMEntity).when(cmsUserRepository).save(any());
      CmsUser result = cmsUserService.createCmsUser(cmsUserRM);

      assertEquals(result.getEmail(), cmsUserRM.getEmail());
    }
  }

  @Test
  void givenValidInput_ThenCreateCmsUser_shouldReturnFailCmsUserEmailExist() {
    CmsUser cmsUserRM = CmsUser.builder()
        .email("email")
        .fullName("fullName")
        .emplId("emplId")
        .branchCode("branchCode")
        .leaderEmail("emailBM")
        .phone("0987654321")
        .role("role")
        .area("area")
        .branchName("branchName").build();

    CmsUser cmsUserBM = CmsUser.builder()
        .email("emailBM")
        .fullName("fullName")
        .emplId("emplId2")
        .branchCode("branchCode")
        .phone("0987654321")
        .role("role")
        .area("area")
        .branchName("branchName").build();
    CmsUserEntity cmsUserBMEntity = CmsUserMapper.INSTANCE.toEntity(cmsUserBM);
    doReturn(Optional.of(cmsUserBMEntity)).when(cmsUserRepository)
        .findByEmail(cmsUserRM.getEmail());

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      cmsUserService.createCmsUser(cmsUserRM);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.CMS_USER_EMAIL_EXIST.getCode());
  }

  @Test
  void givenValidInput_ThenCreateCmsUser_shouldReturnFailBranchCodeNotFound() {
    CmsUser cmsUserRM = CmsUser.builder()
        .email("email")
        .fullName("fullName")
        .emplId("emplId")
        .branchCode("branchCode")
        .leaderEmail("emailBM")
        .phone("0987654321")
        .role("role")
        .area("area")
        .branchName("branchName").build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      cmsUserService.createCmsUser(cmsUserRM);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.BRANCH_CODE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenCreateCmsUser_shouldReturnFailRoleCmsUserNotFound() {
    CmsUser cmsUserRM = CmsUser.builder()
        .email("email")
        .fullName("fullName")
        .emplId("emplId")
        .branchCode("branchCode")
        .leaderEmail("emailBM")
        .phone("0987654321")
        .role("roleRM")
        .area("area")
        .branchName("branchName").build();
    OrganizationEntity organizationEntity = OrganizationEntity.builder()
        .code("branchCode")
        .name("branchName").build();
    doReturn(organizationEntity).when(organizationRepository)
        .findByCode(organizationEntity.getCode());

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      cmsUserService.createCmsUser(cmsUserRM);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.ROLE_CMS_USER_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenGetAllChildrenUser_shouldReturnSuccess() {
    try (MockedStatic<AuthorizationUtil> mockedStatic = Mockito.mockStatic(
        AuthorizationUtil.class)) {
      CmsUserEntity cmsUserLogin = CmsUserEntity.builder()
          .email("1@msb.com.vn")
          .fullName("fullName")
          .emplId("01")
          .branchCode("branchCode")
          .leaderEmail(null)
          .phone("0987654321")
          .role("roleRM")
          .area("area")
          .branchName("branchName").build();
      mockedStatic.when(AuthorizationUtil::getEmail).thenReturn(cmsUserLogin.getEmail());

      doReturn(Optional.of(cmsUserLogin)).when(cmsUserRepository)
          .findByEmail(cmsUserLogin.getEmail());

      doReturn(buildListUser(cmsUserLogin)).when(cmsUserRepository)
          .findByBranchCode(cmsUserLogin.getBranchCode());
      List<String> children = cmsUserService.getAllChildrenUser();
      assertEquals(children.size(), 5);
      assertEquals(children.get(0), cmsUserLogin.getEmplId());

    }
  }

  private List<CmsUserEntity> buildListUser(CmsUserEntity cmsUserRM) {
    List<CmsUserEntity> userEntities = new ArrayList<>();

    userEntities.add(CmsUserEntity.builder()
        .emplId("01")
        .email("1@msb.com.vn")
        .leaderEmail(null)
        .build());

    userEntities.add(CmsUserEntity.builder()
        .emplId("02")
        .email("2@msb.com.vn")
        .leaderEmail("1@msb.com.vn")
        .build());

    userEntities.add(CmsUserEntity.builder()
        .emplId("03")
        .email("3@msb.com.vn")
        .leaderEmail("1@msb.com.vn")
        .build());

    userEntities.add(CmsUserEntity.builder()
        .emplId("04")
        .email("4@msb.com.vn")
        .leaderEmail("2@msb.com.vn")
        .build());

    userEntities.add(CmsUserEntity.builder()
        .emplId("05")
        .email("5@msb.com.vn")
        .leaderEmail("2@msb.com.vn")
        .build());

    userEntities.add(CmsUserEntity.builder()
        .emplId("06")
        .email("6@msb.com.vn")
        .leaderEmail(null)
        .build());

    return userEntities;
  }
}
