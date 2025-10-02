package vn.com.msb.homeloan.core.service.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.FileRuleEnum;
import vn.com.msb.homeloan.core.constant.GenderEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.constant.LoanUploadFileStatusEnum;
import vn.com.msb.homeloan.core.entity.FileConfigEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanUploadFileEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.FileConfig;
import vn.com.msb.homeloan.core.model.FileConfigCategory;
import vn.com.msb.homeloan.core.model.LoanUploadFile;
import vn.com.msb.homeloan.core.model.LoanUrlUploadFile;
import vn.com.msb.homeloan.core.model.UploadPresignedUrlData;
import vn.com.msb.homeloan.core.model.download.DownloadFile;
import vn.com.msb.homeloan.core.model.download.ZipBlock;
import vn.com.msb.homeloan.core.model.mapper.LoanUploadFileMapper;
import vn.com.msb.homeloan.core.model.response.UploadPresignedUrlResponse;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.FileConfigCategoryService;
import vn.com.msb.homeloan.core.service.FileConfigService;
import vn.com.msb.homeloan.core.service.FileService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.LoanUploadFileService;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@ExtendWith(MockitoExtension.class)
public class LoanUploadFileServiceImplTest {

  @Mock
  LoanUploadFileService loanUploadFileService;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  EnvironmentProperties environmentProperties;

  @Mock
  LoanUploadFileRepository loanUploadFileRepository;

  @Mock
  FileConfigRepository fileConfigRepository;

  @Mock
  FileService fileService;

  @Mock
  LoanApplicationService loanApplicationService;

  @Mock
  private JdbcTemplate jdbcTemplate;

  @Mock
  FileConfigService fileConfigService;

  @Mock
  ExecutorService executorService;

  @Mock
  HomeLoanUtil homeLoanUtil;

  @Mock
  FileConfigCategoryService fileConfigCategoryService;

  @Mock
  MvalueUploadFilesRepository mvalueUploadFilesRepository;

  @Mock
  DocumentMappingRepository documentMappingRepository;

  List<DownloadFile> filesNeedToDownload = new ArrayList<>();

  @BeforeEach
  void setUp() {
    this.environmentProperties = new EnvironmentProperties();
    this.environmentProperties.setLimitMaximumChoose(5);
    this.environmentProperties.setFileAllow(";doc;docx;pdf;jpg;png;jpeg;");
    this.loanUploadFileService = new LoanUploadFileServiceImpl(
        this.environmentProperties,
        loanUploadFileRepository,
        fileConfigRepository,
        loanApplicationRepository,
        fileService,
        loanApplicationService,
        jdbcTemplate,
        fileConfigService,
        executorService,
        homeLoanUtil,
        fileConfigCategoryService,
        mvalueUploadFilesRepository,
        documentMappingRepository
    );

    DownloadFile downloadFile1 = new DownloadFile("HSPL-KHACHHANG", 5120 * 1024, null, null, null);
    filesNeedToDownload.add(downloadFile1);
    DownloadFile downloadFile2 = new DownloadFile("HSPL-KHACHHANG", 5119 * 1024, null, null, null);
    filesNeedToDownload.add(downloadFile2);
    DownloadFile downloadFile3 = new DownloadFile("HSPL-KHACHHANG", 1 * 1024, null, null, null);
    filesNeedToDownload.add(downloadFile3);
    DownloadFile downloadFile4 = new DownloadFile("HSPL-KHACHHANG", 5119 * 1024, null, null, null);
    filesNeedToDownload.add(downloadFile4);
    DownloadFile downloadFile5 = new DownloadFile("HSPL-KHACHHANG", 10240 * 1024, null, null, null);
    filesNeedToDownload.add(downloadFile5);
    DownloadFile downloadFile6 = new DownloadFile("HSNT-LUONG", 10240 * 1024, null, null, null);
    filesNeedToDownload.add(downloadFile6);
    DownloadFile downloadFile7 = new DownloadFile("HSNT-LUONG", 10240 * 1024, null, null, null);
    filesNeedToDownload.add(downloadFile7);
    DownloadFile downloadFile8 = new DownloadFile("HSNT-LUONG", 10240 * 1024, null, null, null);
    filesNeedToDownload.add(downloadFile8);
    DownloadFile downloadFile9 = new DownloadFile("HSNT-LUONG", 10240 * 1024, null, null, null);
    filesNeedToDownload.add(downloadFile9);
        /*DownloadFile downloadFile10 = new DownloadFile("HSNT-DOANHNGHIEP", 10240, null, null);
        filesNeedToDownload.add(downloadFile10);
        DownloadFile downloadFile11 = new DownloadFile("HSNT-DOANHNGHIEP", 10240, null, null);
        filesNeedToDownload.add(downloadFile11);
        DownloadFile downloadFile12 = new DownloadFile("HSNT-DOANHNGHIEP", 10240, null, null);
        filesNeedToDownload.add(downloadFile12);
        DownloadFile downloadFile13 = new DownloadFile("HSNT-DOANHNGHIEP", 1, null, null);
        filesNeedToDownload.add(downloadFile13);
        */
    DownloadFile downloadFile14 = new DownloadFile("HSNT-DOANHNGHIEP", 5120 * 1024, null, null,
        null);
    filesNeedToDownload.add(downloadFile14);
    DownloadFile downloadFile15 = new DownloadFile("HSNT-DOANHNGHIEP", 5118 * 1024, null, null,
        null);
    filesNeedToDownload.add(downloadFile15);
    DownloadFile downloadFile16 = new DownloadFile("HSNT-DOANHNGHIEP", 1 * 1024, null, null, null);
    filesNeedToDownload.add(downloadFile16);
    DownloadFile downloadFile17 = new DownloadFile("HSNT-DOANHNGHIEP", 500 * 1024, null, null,
        null);
    filesNeedToDownload.add(downloadFile17);
    DownloadFile downloadFile18 = new DownloadFile("HSNT-DOANHNGHIEP", 500 * 1024, null, null,
        null);
    filesNeedToDownload.add(downloadFile18);

  }

  @Test
  @DisplayName("Test blocksNeedToZip function")
  void blocksNeedToZip() {
    List<ZipBlock> blocksNeedToZip = new ArrayList<>();
    String zipPrefix = null;
    int orderNumber = 0;
    int next = 0;
    int length = 0;
    List<ZipBlock> zipBlockList = loanUploadFileService.blocksNeedToZip(filesNeedToDownload,
        blocksNeedToZip, zipPrefix, orderNumber, next, length);
    assertEquals(zipBlockList.size(), 9);
  }

  @Test
  void test_renameDuplicateInZipBlock()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    ZipBlock zipBlock = new ZipBlock();
    //List<DownloadFile> downloadFiles = new ArrayList<>();
    DownloadFile downloadFile1 = new DownloadFile();
    downloadFile1.setFileName("TuTV");
    DownloadFile downloadFile2 = new DownloadFile();
    downloadFile2.setFileName("TuTV");
    DownloadFile downloadFile3 = new DownloadFile();
    downloadFile3.setFileName("TuTV");
    DownloadFile downloadFile4 = new DownloadFile();
    downloadFile4.setFileName("TuTV");
    DownloadFile downloadFile5 = new DownloadFile();
    downloadFile5.setFileName("TuTV4");
    DownloadFile downloadFile6 = new DownloadFile();
    downloadFile6.setFileName("TuTV4");
    zipBlock.setDownloadFileList(
        Arrays.asList(downloadFile1, downloadFile2, downloadFile3, downloadFile4, downloadFile5,
            downloadFile6));

    Method privateMethod = LoanUploadFileServiceImpl.class.getDeclaredMethod(
        "renameDuplicateInZipBlock", ZipBlock.class);
    privateMethod.setAccessible(true);
    privateMethod.invoke(loanUploadFileService, zipBlock);

    assertTrue("TuTV".equals(downloadFile1.getFileName()));
    assertTrue("TuTV_1".equals(downloadFile2.getFileName()));
    assertTrue("TuTV_2".equals(downloadFile3.getFileName()));
    assertTrue("TuTV_3".equals(downloadFile4.getFileName()));

    assertTrue("TuTV4".equals(downloadFile5.getFileName()));
    assertTrue("TuTV4_1".equals(downloadFile6.getFileName()));

  }

  @Test
  void givenValidInput_ThenNewFileName_shouldReturnSuccess()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    List<String> name2UploadFile = new ArrayList<>();

    LoanUploadFileEntity loanUploadFileEntity = new LoanUploadFileEntity();
    loanUploadFileEntity.setFileName("filename.jpg");
    name2UploadFile.add("filename.jpg");

    LoanUploadFileEntity loanUploadFileEntity1 = new LoanUploadFileEntity();
    loanUploadFileEntity1.setFileName("filename_1.jpg");
    name2UploadFile.add("filename_1.jpg");

    LoanUploadFileEntity loanUploadFileEntity2 = new LoanUploadFileEntity();
    loanUploadFileEntity2.setFileName("filename_2.jpg");
    name2UploadFile.add("filename_2.jpg");

    name2UploadFile.add("filename_3.jpg");

    name2UploadFile.add("filename_new1.jpg");

    name2UploadFile.add("filename_new2.jpg");

    name2UploadFile.add("filename_new2_2.jpg");

    Method privateMethod = LoanUploadFileServiceImpl.class.getDeclaredMethod("newFileName",
        String.class, List.class, int.class,String.class);
    privateMethod.setAccessible(true);
    String result1 = (String) privateMethod.invoke(loanUploadFileService, "filename.jpg",
        name2UploadFile, 0,"");

    String result2 = (String) privateMethod.invoke(loanUploadFileService,
        "filename_no_duplicate.jpg", name2UploadFile, 0,"");

    String resul3 = (String) privateMethod.invoke(loanUploadFileService, "filename_new.jpg",
        name2UploadFile, 0,"");

    String resul4 = (String) privateMethod.invoke(loanUploadFileService, "filename_new2.jpg",
        name2UploadFile, 0,"");

    String resul5 = (String) privateMethod.invoke(loanUploadFileService, "filename_new2_2.jpg",
        name2UploadFile, 0,"");

    assertThat(result1).isNotEmpty();
    assertThat(result2).isNotEmpty();
    assertThat(resul3).isNotEmpty();
    assertThat(resul4).isNotEmpty();
    assertThat(resul5).isNotEmpty();
  }

  @Test
  void givenValidInput_ThenPreUploadFile_shouldReturnResourceNotFound() {
    doReturn(Optional.empty()).when(loanApplicationRepository).findById("LOAN_ID");

    List<String> files = new ArrayList<>();
    files.add("file1.jpg");
    files.add("file2.jpg");

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanUploadFileService.preUploadFile("LOAN_ID", "fileConfigId", files, ClientTypeEnum.CMS);
    });
    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenPreUploadFile_shouldReturnFileConfigNotFound() {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
        .fullName("Nguyen van A")
        .gender(GenderEnum.MALE)
        .status(LoanInfoStatusEnum.DRAFT)
        .currentStep("LOAN_COLLATERAL")
        .createdAt((new Date()).toInstant())
        .build();

    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository)
        .findById("LOAN_ID");

    doReturn(Optional.empty()).when(fileConfigRepository).findById("fileConfigId");

    List<String> files = new ArrayList<>();
    files.add("file1.jpg");
    files.add("file2.jpg");

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanUploadFileService.preUploadFile("LOAN_ID", "fileConfigId", files, ClientTypeEnum.CMS);
    });
    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenPreUploadFile_shouldReturnFileUploadEmpty() {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
        .fullName("Nguyen van A")
        .gender(GenderEnum.MALE)
        .status(LoanInfoStatusEnum.DRAFT)
        .currentStep("LOAN_COLLATERAL")
        .createdAt((new Date()).toInstant())
        .build();

    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository)
        .findById("LOAN_ID");

    FileConfigEntity fileConfig = FileConfigEntity.builder()
        .maxLimit(10)
        .build();
    doReturn(Optional.of(fileConfig)).when(fileConfigRepository).findById("fileConfigId");

    List<String> files = new ArrayList<>();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanUploadFileService.preUploadFile("LOAN_ID", "fileConfigId", files, ClientTypeEnum.CMS);
    });
    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_UPLOAD_FILE_EMPTY.getCode());
  }

  @Test
  void givenValidInput_ThenPreUploadFile_shouldReturnMaxLimitFile() {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
        .fullName("Nguyen van A")
        .gender(GenderEnum.MALE)
        .status(LoanInfoStatusEnum.DRAFT)
        .currentStep("LOAN_COLLATERAL")
        .createdAt((new Date()).toInstant())
        .build();

    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository)
        .findById("LOAN_ID");

    FileConfigEntity fileConfig = FileConfigEntity.builder()
        .maxLimit(2)
        .build();
    doReturn(Optional.of(fileConfig)).when(fileConfigRepository).findById("fileConfigId");

    List<String> files = new ArrayList<>();
    files.add("file1.jpg");
    files.add("file2.jpg");
    files.add("file3.jpg");

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanUploadFileService.preUploadFile("LOAN_ID", "fileConfigId", files, ClientTypeEnum.CMS);
    });
    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_UPLOAD_FILE_MAX_LIMIT.getCode());
  }

  @Test
  void givenValidInput_ThenPreUploadFile_shouldReturnMaxLimitChooseFile() {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
        .fullName("Nguyen van A")
        .gender(GenderEnum.MALE)
        .status(LoanInfoStatusEnum.DRAFT)
        .currentStep("LOAN_COLLATERAL")
        .createdAt((new Date()).toInstant())
        .build();

    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository)
        .findById("LOAN_ID");

    FileConfigEntity fileConfig = FileConfigEntity.builder()
        .maxLimit(10)
        .build();
    doReturn(Optional.of(fileConfig)).when(fileConfigRepository).findById("fileConfigId");

    List<String> files = new ArrayList<>();
    files.add("file1.jpg");
    files.add("file2.jpg");
    files.add("file3.jpg");
    files.add("file4.jpg");
    files.add("file5.jpg");
    files.add("file6.jpg");

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanUploadFileService.preUploadFile("LOAN_ID", "fileConfigId", files, ClientTypeEnum.CMS);
    });
    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_UPLOAD_FILE_MAX_CHOOSE.getCode());
  }

  @Test
  void givenValidInput_ThenPreUploadFile_shouldReturnFileUploadNotAlow() {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
        .fullName("Nguyen van A")
        .gender(GenderEnum.MALE)
        .status(LoanInfoStatusEnum.DRAFT)
        .currentStep("LOAN_COLLATERAL")
        .createdAt((new Date()).toInstant())
        .build();

    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository)
        .findById("LOAN_ID");

    FileConfigEntity fileConfig = FileConfigEntity.builder()
        .maxLimit(2)
        .build();
    doReturn(Optional.of(fileConfig)).when(fileConfigRepository).findById("fileConfigId");

    List<String> files = new ArrayList<>();
    files.add("file1.xxx");

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanUploadFileService.preUploadFile("LOAN_ID", "fileConfigId", files, ClientTypeEnum.CMS);
    });
    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_UPLOAD_FILE_EX_NOT_ALLOW.getCode());
  }

  @Test
  void givenValidInput_ThenPreUploadFile_shouldReturnSuccess() throws IOException {
    String LOAN_ID = "928fa504-d390-4b70-9cdd-6ddfd7acdde9";
    LoanApplicationEntity loanApplicationBefore = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
//                .recognitionMethod1(RecognitionMethod1Enum.EXCHANGE)
        .fullName("Nguyen van A")
        .gender(GenderEnum.MALE)
        .status(LoanInfoStatusEnum.DRAFT)
        .currentStep("LOAN_COLLATERAL")
        .createdAt((new Date()).toInstant())
        .build();

    doReturn(Optional.of(loanApplicationBefore)).when(loanApplicationRepository)
        .findById("LOAN_ID");

    FileConfigEntity fileConfig = FileConfigEntity.builder()
        .maxLimit(10)
        .build();
    doReturn(Optional.of(fileConfig)).when(fileConfigRepository).findById("fileConfigId");

    List<LoanUploadFileEntity> loanUploadFiles = new ArrayList<>();
    loanUploadFiles.add(LoanUploadFileEntity.builder()
        .loanApplicationId("LOAN_ID")
        .fileConfigId("fileConfigId")
        .fileName("filename1.jpg")
        .status(LoanUploadFileStatusEnum.UPLOADED)
        .build());
    loanUploadFiles.add(LoanUploadFileEntity.builder()
        .loanApplicationId("LOAN_ID")
        .fileConfigId("fileConfigId")
        .fileName("filename2.jpg")
        .status(LoanUploadFileStatusEnum.UPLOADING)
        .build());
    doReturn(loanUploadFiles).when(loanUploadFileRepository).findByLoanApplicationId("LOAN_ID");

    UploadPresignedUrlResponse uploadPresignedUrlResponse = new UploadPresignedUrlResponse();
    uploadPresignedUrlResponse.setData(new UploadPresignedUrlData());
    doReturn(uploadPresignedUrlResponse).when(fileService).generatePreSignedUrlToUpload(any());

    List<String> files = new ArrayList<>();
    files.add("file1.jpg");
    files.add("file2.jpg");

    List<LoanUrlUploadFile> results = loanUploadFileService.preUploadFile("LOAN_ID", "fileConfigId",
        files, ClientTypeEnum.LDP);
    assertEquals(results.size(), 2);
  }

  @Test
  void givenValidInput_ThenUpdateStatus_shouldReturnLoanUploadFileEmpty() {
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanUploadFileService.updateStatus(new ArrayList<>());
    });
    assertEquals(exception.getCode().intValue(),
        ErrorEnum.LOAN_UPLOAD_FILE_UPDATE_STATUS_EMPTY.getCode());
  }

  @Test
  void givenValidInput_ThenUpdateStatus_shouldReturnLoanUploadFileNotFound() {
    List<LoanUploadFile> loanUploadFiles = new ArrayList<>();
    loanUploadFiles.add(LoanUploadFile.builder()
        .uuid("uuid")
        .build());
    doReturn(Optional.empty()).when(loanUploadFileRepository).findById(any());
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanUploadFileService.updateStatus(loanUploadFiles);
    });
    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_UPLOAD_FILE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenUpdateStatus_shouldReturnLoanNotFound() {
    List<LoanUploadFile> loanUploadFiles = new ArrayList<>();
    loanUploadFiles.add(LoanUploadFile.builder()
        .uuid("uuid")
        .status(LoanUploadFileStatusEnum.UPLOADED)
        .build());
    LoanUploadFile loanUploadFile = LoanUploadFile.builder()
        .uuid("uuid")
        .status(LoanUploadFileStatusEnum.UPLOADING)
        .build();
    doReturn(Optional.of(LoanUploadFileMapper.INSTANCE.toEntity(loanUploadFile))).when(
        loanUploadFileRepository).findById(any());

    doReturn(Optional.empty()).when(loanApplicationRepository).findById(any());

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanUploadFileService.updateStatus(loanUploadFiles);
    });
    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenUpdateStatus_shouldReturnSuccess() {
    List<LoanUploadFile> loanUploadFiles = new ArrayList<>();
    loanUploadFiles.add(LoanUploadFile.builder()
        .uuid("uuid")
        .status(LoanUploadFileStatusEnum.UPLOADED)
        .build());

    LoanUploadFile loanUploadFile = LoanUploadFile.builder()
        .uuid("uuid")
        .status(LoanUploadFileStatusEnum.UPLOADING)
        .build();
    doReturn(Optional.of(LoanUploadFileMapper.INSTANCE.toEntity(loanUploadFile))).when(
        loanUploadFileRepository).findById(any());

    doReturn(Optional.of(LoanApplicationEntity.builder().build())).when(loanApplicationRepository)
        .findById(any());
    doReturn(LoanUploadFileMapper.INSTANCE.toEntities(loanUploadFiles)).when(
        loanUploadFileRepository).saveAll(any());

    List<LoanUploadFile> results = loanUploadFileService.updateStatus(loanUploadFiles);
    assertEquals(results.size(), loanUploadFiles.size());
  }

  @Test
  void test_collectFilesNeedToCopy()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    List<FileConfigCategory> fileConfigCategories = new ArrayList<>();

    FileConfigCategory root1 = new FileConfigCategory();
    root1.setCode("ROOT1");

    FileConfigCategory sub1 = new FileConfigCategory();
    FileConfig fileConfig1 = new FileConfig();
    fileConfig1.setLoanUploadFileList(Arrays.asList(new LoanUploadFile(), new LoanUploadFile()));
    sub1.setCode("002-001");
    sub1.setFileConfigList(Arrays.asList(fileConfig1));

    FileConfigCategory sub5 = new FileConfigCategory();
    FileConfig fileConfig5 = new FileConfig();
    fileConfig5.setLoanUploadFileList(Arrays.asList(new LoanUploadFile()));
    sub5.setCode("002-006");
    sub5.setFileConfigList(Arrays.asList(fileConfig5));
    sub1.setFileConfigCategoryList(Arrays.asList(sub5));

    FileConfigCategory sub2 = new FileConfigCategory();
    FileConfig fileConfig2 = new FileConfig();
    fileConfig2.setLoanUploadFileList(Arrays.asList(new LoanUploadFile(), new LoanUploadFile()));
    sub2.setCode("ABC");
    sub2.setFileConfigList(Arrays.asList(fileConfig2));

    root1.setFileConfigCategoryList(Arrays.asList(sub1, sub2));

    FileConfigCategory root2 = new FileConfigCategory();
    root2.setCode("ROOT1");

    FileConfigCategory sub3 = new FileConfigCategory();
    FileConfig fileConfig3 = new FileConfig();
    fileConfig3.setLoanUploadFileList(Arrays.asList(new LoanUploadFile(), new LoanUploadFile()));
    sub3.setCode("002-002");
    sub3.setFileConfigList(Arrays.asList(fileConfig3));

    FileConfigCategory sub4 = new FileConfigCategory();
    FileConfig fileConfig4 = new FileConfig();
    fileConfig4.setLoanUploadFileList(Arrays.asList(new LoanUploadFile(), new LoanUploadFile()));
    sub4.setCode("002-003");
    sub4.setFileConfigList(Arrays.asList(fileConfig4));

    root2.setFileConfigCategoryList(Arrays.asList(sub3, sub4));

    fileConfigCategories.add(root1);
    fileConfigCategories.add(root2);

    Method privateMethod = LoanUploadFileServiceImpl.class.getDeclaredMethod(
        "collectFilesNeedToCopy", List.class, List.class);
    privateMethod.setAccessible(true);
    List<LoanUploadFile> list = (List<LoanUploadFile>) privateMethod.invoke(loanUploadFileService,
        fileConfigCategories, new ArrayList<>());

    assertEquals(7, list.size());

  }

  @Test
  void test_getCopyFiles() {
    List<FileConfigCategory> fileConfigCategories = new ArrayList<>();

    FileConfigCategory root1 = new FileConfigCategory();
    root1.setCode("ROOT1");

    FileConfigCategory sub1 = new FileConfigCategory();
    FileConfig fileConfig1 = new FileConfig();
    fileConfig1.setLoanUploadFileList(Arrays.asList(new LoanUploadFile(), new LoanUploadFile()));
    sub1.setCode("002-001");
    sub1.setFileConfigList(Arrays.asList(fileConfig1));

    FileConfigCategory sub2 = new FileConfigCategory();
    FileConfig fileConfig2 = new FileConfig();
    fileConfig2.setLoanUploadFileList(Arrays.asList(new LoanUploadFile(), new LoanUploadFile()));
    sub2.setCode("ABC");
    sub2.setFileConfigList(Arrays.asList(fileConfig2));

    root1.setFileConfigCategoryList(Arrays.asList(sub1, sub2));

    FileConfigCategory root2 = new FileConfigCategory();
    root2.setCode("ROOT1");

    FileConfigCategory sub3 = new FileConfigCategory();
    FileConfig fileConfig3 = new FileConfig();
    fileConfig3.setLoanUploadFileList(Arrays.asList(new LoanUploadFile(), new LoanUploadFile()));
    sub3.setCode("002-002");
    sub3.setFileConfigList(Arrays.asList(fileConfig3));

    FileConfigCategory sub4 = new FileConfigCategory();
    FileConfig fileConfig4 = new FileConfig();
    fileConfig4.setLoanUploadFileList(Arrays.asList(new LoanUploadFile(), new LoanUploadFile()));
    sub4.setCode("002-003");
    sub4.setFileConfigList(Arrays.asList(fileConfig4));

    root2.setFileConfigCategoryList(Arrays.asList(sub3, sub4));

    fileConfigCategories.add(root1);
    fileConfigCategories.add(root2);

    doReturn(fileConfigCategories).when(fileConfigCategoryService)
        .getFileConfigCategories("", FileRuleEnum.CMS);

    List list = loanUploadFileService.getCopyFiles("");

    assertEquals(6, list.size());
  }
}

