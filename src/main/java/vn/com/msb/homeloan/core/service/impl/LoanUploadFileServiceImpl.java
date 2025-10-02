package vn.com.msb.homeloan.core.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.api.dto.request.DownloadPresignedUrlRequest;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.entity.*;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.*;
import vn.com.msb.homeloan.core.model.download.DownloadFile;
import vn.com.msb.homeloan.core.model.download.ZipBlock;
import vn.com.msb.homeloan.core.model.mapper.FileConfigMapper;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.model.mapper.LoanUploadFileMapper;
import vn.com.msb.homeloan.core.model.mapper.MvalueUploadFileMapper;
import vn.com.msb.homeloan.core.model.request.DeleteFileRequest;
import vn.com.msb.homeloan.core.model.request.UploadPresignedUrlRequest;
import vn.com.msb.homeloan.core.model.response.DownloadPresignedUrlResponse;
import vn.com.msb.homeloan.core.model.response.UploadPresignedUrlResponse;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.*;
import vn.com.msb.homeloan.core.util.*;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

import javax.transaction.Transactional;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class LoanUploadFileServiceImpl implements LoanUploadFileService {

  private final EnvironmentProperties environmentProperties;
  private final LoanUploadFileRepository loanUploadFileRepository;
  private final FileConfigRepository fileConfigRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final FileService fileService;
  private final LoanApplicationService loanApplicationService;
  private JdbcTemplate jdbcTemplate;

  private final FileConfigService fileConfigService;

  @Qualifier("fixedThreadPool")
  private final ExecutorService executorService;

  // Zip length size max 10240 bytes
  private static final int ZIP_MAX_SIZE = 10485760;

  private final HomeLoanUtil homeLoanUtil;

  private final FileConfigCategoryService fileConfigCategoryService;
  private final MvalueUploadFilesRepository mvalueUploadFilesRepository;
  private final DocumentMappingRepository documentMappingRepository;

  @Override
  public List<LoanUrlUploadFile> preUploadFile(String loanId, String fileConfigId,
      List<String> files, ClientTypeEnum clientType) {
    List<LoanUploadFileEntity> loanUploadFiles = loanUploadFileRepository.findByLoanApplicationId(
        loanId);
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(loanId)
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loanId", loanId));
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);
    validateUploadFile(fileConfigId, files, loanUploadFiles);

    List<String> fileNames =
        emptyIfNull(loanUploadFiles).stream()
            .map(a -> a.getFileName())
            .collect(Collectors.toList());
    List<LoanUrlUploadFile> loanUrlUploadFiles = generatePreSigned(files,fileNames,loanApplication,"");

    //Insert data to loan upload file with status UPLOADING
    List<LoanUploadFileEntity> entities = new ArrayList<>();
    if (!CollectionUtils.isEmpty(loanUrlUploadFiles)) {
      for (LoanUrlUploadFile loanUrlUploadFile : loanUrlUploadFiles) {
        LoanUploadFileEntity entity = LoanUploadFileEntity.builder()
            .fileConfigId(fileConfigId)
            .loanApplicationId(loanId)
            .folder(loanUrlUploadFile.getFilePath())
            .fileName(loanUrlUploadFile.getFileName())
            .status(LoanUploadFileStatusEnum.UPLOADING)
            .build();

        if (!CollectionUtils.isEmpty(loanUploadFiles)) {
          List<LoanUploadFileEntity> lstTemp = loanUploadFiles.stream()
              .filter(item -> item.getFileName().equalsIgnoreCase(loanUrlUploadFile.getFileName())
                  && item.getStatus() != null
                  && item.getStatus().equals(LoanUploadFileStatusEnum.UPLOADING))
              .collect(Collectors.toList());
          entity.setUuid(CollectionUtils.isEmpty(lstTemp) ? null : lstTemp.get(0).getUuid());
        }

        entities.add(entity);
      }
    }

    if (CollectionUtils.isEmpty(entities)) {
      return loanUrlUploadFiles;
    }

    entities = loanUploadFileRepository.saveAll(entities);
    for (int i = 0; i < loanUrlUploadFiles.size(); i++) {
      int finalI = i;
      List<LoanUploadFileEntity> lstTemp = entities.stream()
          .filter(item -> item.getFileName()
              .equalsIgnoreCase(loanUrlUploadFiles.get(finalI).getFileName()))
          .collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(lstTemp)) {
        loanUrlUploadFiles.get(i).setLoanUploadFileId(lstTemp.get(0).getUuid());
      }
      // re-assign file name
      loanUrlUploadFiles.get(i).setFileName(files.get(i));
    }

    if (LoanCurrentStepEnum.LOAN_COLLATERAL.getCode()
        .equalsIgnoreCase(loanApplication.getCurrentStep())) {
      loanApplication.setCurrentStep(LoanCurrentStepEnum.LOAN_FILE.getCode());
      loanApplicationRepository.save(loanApplication);
    }

    return loanUrlUploadFiles;
  }

  @Override
  public List<LoanUrlUploadFile> preUploadFileMValue(String loanId,long documentMvalueId, List<String> files, ClientTypeEnum clientType, String collateralId, String collateralType) {

    List<MvalueUploadFilesEntity> mvalueUploadFiles = mvalueUploadFilesRepository.findByLoanApplicationId(
      loanId);
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(loanId)
      .orElseThrow(
        () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loanId", loanId));
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);
    if (CollectionUtils.isEmpty(files)) {
      throw new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_EMPTY);
    }

    DocumentMappingEntity documentMappingEntity = documentMappingRepository.findById(documentMvalueId)
      .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "documentMvalueId", documentMvalueId +""));

    for (String file : files) {
      String ex = String.format(";%s;", FilenameUtils.getExtension(file));
      if (!environmentProperties.getFileMValueAllow().contains(ex.toLowerCase())) {
        throw new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_EX_NOT_ALLOW,
          environmentProperties.getFileMValueAllow());
      }
    }
    List<String> fileNames =
      emptyIfNull(mvalueUploadFiles).stream()
        .map(a -> a.getFileName())
        .collect(Collectors.toList());
    List<LoanUrlUploadFile> loanUrlUploadFiles =
        generatePreSigned(files, fileNames, loanApplication, "MValue");

    String email = AuthorizationUtil.getEmail();
    String user = "";
    if (ObjectUtil.isNotEmpty(email)) {
      user = email.split("@")[0];
    }
    List<MvalueUploadFilesEntity> entities = new ArrayList<>();
    if (!CollectionUtils.isEmpty(loanUrlUploadFiles)) {
      for (LoanUrlUploadFile loanUrlUploadFile : loanUrlUploadFiles) {
        MvalueUploadFilesEntity entity = MvalueUploadFilesEntity
          .builder()
          .documentMvalueId(String.valueOf(documentMvalueId))
          .loanApplicationId(loanId)
          .folder(loanUrlUploadFile.getFilePath())
          .fileName(loanUrlUploadFile.getFileName())
          .mvalueCode(documentMappingEntity.getCode())
          .collateralId(collateralId)
          .collateralTypeECM(collateralType)
          .uploadUser(user)
          .status(LoanUploadFileStatusEnum.UPLOADING.getCode())
          .build();

        entities.add(entity);
      }
    }

    if (CollectionUtils.isEmpty(entities)) {
      return loanUrlUploadFiles;
    }

    entities = mvalueUploadFilesRepository.saveAll(entities);
    for (int i = 0; i < loanUrlUploadFiles.size(); i++) {
      int finalI = i;
      List<MvalueUploadFilesEntity> lstTemp = entities.stream()
        .filter(item -> item.getFileName()
          .equalsIgnoreCase(loanUrlUploadFiles.get(finalI).getFileName()))
        .collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(lstTemp)) {
        loanUrlUploadFiles.get(i).setLoanUploadFileId(lstTemp.get(0).getId()+"");
      }
      // re-assign file name
      loanUrlUploadFiles.get(i).setFileName(files.get(i));
    }
    return loanUrlUploadFiles;
  }

  @Override
  public List<MvalueUploadFile> updateStatusMvalue(List<MvalueUploadFile> mvalueUploadFiles) {
    if (CollectionUtils.isEmpty(mvalueUploadFiles)) {
      throw new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_UPDATE_STATUS_EMPTY);
    }

    List<MvalueUploadFilesEntity> entities = new ArrayList<>();
    for (MvalueUploadFile mvalueUploadFile : mvalueUploadFiles) {
      MvalueUploadFilesEntity entity = mvalueUploadFilesRepository.findById(mvalueUploadFile.getId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.UPLOAD_FILE_NOT_FOUND));

      if (!mvalueUploadFile.getStatus().equals(entity.getStatus())) {
        entity.setStatus(mvalueUploadFile.getStatus());
        if(mvalueUploadFile.getStatus().equalsIgnoreCase(LoanUploadFileStatusEnum.UPLOADED.getCode())){
          entity.setMvalueStatus(RequestPricingEnum.WAITING.name());
        }
        entities.add(entity);

        loanApplicationRepository.findById(entity.getLoanApplicationId())
          .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
      }
    }

    return MvalueUploadFileMapper.INSTANCE.toModels(mvalueUploadFilesRepository.saveAll(entities));
  }

  @Override
  @Transactional
  public List<LoanUploadFile> updateStatus(List<LoanUploadFile> loanUploadFiles) {
    if (CollectionUtils.isEmpty(loanUploadFiles)) {
      throw new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_UPDATE_STATUS_EMPTY);
    }

    List<LoanUploadFileEntity> entities = new ArrayList<>();
    for (LoanUploadFile loanUploadFile : loanUploadFiles) {
      LoanUploadFileEntity entity = loanUploadFileRepository.findById(loanUploadFile.getUuid())
          .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_NOT_FOUND,
              loanUploadFile.getUuid()));

      if (!loanUploadFile.getStatus().equals(entity.getStatus())) {
        entity.setStatus(loanUploadFile.getStatus());
        entities.add(entity);

        loanApplicationRepository.findById(entity.getLoanApplicationId())
            .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
      }
    }

    return LoanUploadFileMapper.INSTANCE.toModels(loanUploadFileRepository.saveAll(entities));
  }

  private LoanUrlUploadFile preUploadFile(String fileName, String path) {
    UploadPresignedUrlRequest request = UploadPresignedUrlRequest
        .builder()
        .clientCode(environmentProperties.getClientCode())
        .scopes(environmentProperties.getClientScopes())
        .documentType("document")
        .filename(fileName)
        .path(path)
        .priority(1)
        .build();

    UploadPresignedUrlResponse uploadPresignedUrlResponse = fileService.generatePreSignedUrlToUpload(
        request);

    return LoanUrlUploadFile.builder()
        .fileName(fileName)
        .filePath(path)
        .urlData(uploadPresignedUrlResponse.getData())
        .build();
  }

  private void validateUploadFile(String fileConfigId, List<String> files,
      List<LoanUploadFileEntity> loanUploadFiles) {
    FileConfigEntity fileConfig = fileConfigRepository.findById(fileConfigId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "fileConfigId",
            fileConfigId));

    if (CollectionUtils.isEmpty(files)) {
      throw new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_EMPTY);
    }
    long countFileUploaded = emptyIfNull(loanUploadFiles).stream()
        .filter(x -> x.getStatus().equals(LoanUploadFileStatusEnum.UPLOADED)
            && (x.getFileConfigId() != null && x.getFileConfigId().equalsIgnoreCase(fileConfigId)))
        .count();

    int countFile = (int) countFileUploaded + files.size();
    int maxLimit = fileConfig.getMaxLimit() == null ? environmentProperties.getFileUploadMaxItem()
        : fileConfig.getMaxLimit();
    if (countFile > maxLimit) {
      throw new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_MAX_LIMIT, fileConfigId,
          String.valueOf(maxLimit));
    }

    if (files.size() > environmentProperties.getLimitMaximumChoose()) {
      throw new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_MAX_CHOOSE,
          String.valueOf(environmentProperties.getLimitMaximumChoose()));
    }

    for (String file : files) {
      String ex = String.format(";%s;", FilenameUtils.getExtension(file));
      if (!environmentProperties.getFileAllow().contains(ex.toLowerCase())) {
        throw new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_EX_NOT_ALLOW,
          environmentProperties.getFileAllow());
      }
    }
  }

  private List<LoanUrlUploadFile> generatePreSigned(List<String> files,List<String> fileNames,LoanApplicationEntity loanApplication,String type){
    List<LoanUrlUploadFile> loanUrlUploadFiles = new ArrayList<>();
    List<String> results = new ArrayList<>();

    // put to map
    for (String file : files) {
      String fileName = file;
      fileName = newFileName(fileName, fileNames, 0,type);
      results.add(fileName);
    }

    String path = String.format("%s/%s/%s/", Constants.SERVICE_NAME,
      DateUtils.convertToSimpleFormat(Date.from(loanApplication.getCreatedAt()), "yyyyMMdd"),
      loanApplication.getUuid());

    results.stream().parallel().forEach(file -> {
      LoanUrlUploadFile uploadFile = preUploadFile(file, path);
      if (uploadFile != null) {
        loanUrlUploadFiles.add(uploadFile);
      }
    });
    return loanUrlUploadFiles;
  }

  private String newFileName(String fileName, List name2UploadFile, int seenCount, String type) {
    if (name2UploadFile == null || name2UploadFile.isEmpty()) {
      return type + fileName;
    }

    if (fileName == null) {
      return null;
    }

    if (name2UploadFile.contains(fileName)) {
      seenCount++;

      String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
      // remove _%
      if (seenCount > 1) {
        fileNameWithOutExt = fileNameWithOutExt.substring(0, fileNameWithOutExt.lastIndexOf("_"));
      }
      String extension = FilenameUtils.getExtension(fileName);

      fileName =
          fileNameWithOutExt
              + "_"
              + seenCount
              + (org.apache.commons.lang3.StringUtils.isNotEmpty(extension) ? "." + extension : "");

      return newFileName(fileName, name2UploadFile, seenCount, type);
    } else {
      name2UploadFile.add(fileName);
      return fileName;
    }
  }

  @Override
  public HashMap<String, Object> getMapKeys(String loanApplicationId, FileRuleEnum fileRuleEnum) {
    Connection connection = null;
    LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
    try {
      connection = jdbcTemplate.getDataSource().getConnection();
      CallableStatement callableStatement = null;

      if (fileRuleEnum.equals(FileRuleEnum.LDP)) {
        callableStatement = connection.prepareCall("{call get_keys(?)}");
      } else if (fileRuleEnum.equals(FileRuleEnum.CMS)) {
        callableStatement = connection.prepareCall("{call get_cms_keys(?)}");
      }

      callableStatement.setString(1, loanApplicationId);
      ResultSet rs = callableStatement.executeQuery();
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      // The column count starts from 1
      if (rs.next()) {
        for (int i = 1; i <= columnCount; i++) {
          String key = rsmd.getColumnName(i);
          Object value = rs.getObject(i);
          hashMap.put(key, value);
        }
      }
    } catch (Exception ignored) {
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException ex) {
          log.error("getMapKeys error: " + ex.getMessage());
        }
      }
    }
    return hashMap;
  }

  @Override
  public DownloadPresignedUrlResponse viewFile(String loanUploadFileId) {
    LoanUploadFileEntity entity = loanUploadFileRepository.findById(loanUploadFileId)
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_NOT_FOUND, loanUploadFileId));

    if (!entity.getStatus().equals(LoanUploadFileStatusEnum.UPLOADED)
        || StringUtils.isEmpty(entity.getFileName())
        || StringUtils.isEmpty(entity.getFolder())) {
      throw new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_NOT_FOUND, loanUploadFileId);
    }

    String fullPath = String.format("%s%s", entity.getFolder(), entity.getFileName());
    DownloadPresignedUrlRequest request = DownloadPresignedUrlRequest.builder()
        .clientCode(environmentProperties.getClientCode())
        .scopes(environmentProperties.getClientScopes())
        .path(fullPath)
        .build();
    return fileService.generatePreSignedUrlToDownload(request);
  }

  @Override
  public DownloadPresignedUrlResponse viewFileMvalue(long id) {
    MvalueUploadFilesEntity entity = mvalueUploadFilesRepository.findById(id)
      .orElseThrow(
        () -> new ApplicationException(ErrorEnum.UPLOAD_FILE_NOT_FOUND));

    DownloadPresignedUrlRequest request = genRequest(entity.getStatus(),entity.getFileName(),entity.getFolder());
    return fileService.generatePreSignedUrlToDownload(request);
  }

  private DownloadPresignedUrlRequest genRequest(String status, String fileName, String folder){
    if (!status.equals(LoanUploadFileStatusEnum.UPLOADED.getCode())
      || StringUtils.isEmpty(fileName)
      || StringUtils.isEmpty(folder)) {
      throw new ApplicationException(ErrorEnum.UPLOAD_FILE_NOT_FOUND);
    }
    String fullPath = String.format("%s%s", folder, fileName);
    DownloadPresignedUrlRequest request = DownloadPresignedUrlRequest.builder()
      .clientCode(environmentProperties.getClientCode())
      .scopes(environmentProperties.getClientScopes())
      .path(fullPath)
      .build();
    return request;
  }

  @Override
  @Transactional
  public void deleteFile(String loanUploadFileId, ClientTypeEnum clientType) {
    LoanUploadFileEntity entity = loanUploadFileRepository.findById(loanUploadFileId)
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_NOT_FOUND, loanUploadFileId));
    if (!LoanUploadFileStatusEnum.UPLOADED.equals(entity.getStatus())
        || StringUtils.isEmpty(entity.getFileName())
        || StringUtils.isEmpty(entity.getFolder())) {
      throw new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_NOT_FOUND, loanUploadFileId);
    }
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            entity.getLoanApplicationId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));

    loanApplicationService.checkEditLoanApp(loanApplication, clientType);

    entity.setStatus(LoanUploadFileStatusEnum.DELETE);
    loanUploadFileRepository.save(entity);

    List<MvalueUploadFilesEntity> lsMvalueUploadFiles = mvalueUploadFilesRepository.findByLoanUploadFileId(entity.getUuid());
    if (!lsMvalueUploadFiles.isEmpty()) {
      lsMvalueUploadFiles.stream()
          .forEach(t -> t.setStatus(LoanUploadFileStatusEnum.DELETE.getCode()));
      mvalueUploadFilesRepository.saveAll(lsMvalueUploadFiles);
    }
    // update tab action if delete

    // Delete file from S3
    String fileName = String.format("%s%s", entity.getFolder(), entity.getFileName());
    DeleteFileRequest request = new DeleteFileRequest();
    request.setFileName(fileName);
    executorService.execute(() -> fileService.deleteFile(request));
  }

  @Override
  @Transactional
  public void deleteFileMvalue(long mvalueUploadFileId, ClientTypeEnum clientType) {
    MvalueUploadFilesEntity entity = mvalueUploadFilesRepository.findById(mvalueUploadFileId)
      .orElseThrow(
        () -> new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_NOT_FOUND, mvalueUploadFileId+""));
    if (!LoanUploadFileStatusEnum.UPLOADED.getCode().equals(entity.getStatus())
      || StringUtils.isEmpty(entity.getFileName())
      || StringUtils.isEmpty(entity.getFolder())) {
      throw new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_NOT_FOUND, mvalueUploadFileId+"");
    }
    entity.setStatus(LoanUploadFileStatusEnum.DELETE.getCode());
    mvalueUploadFilesRepository.save(entity);

    // update tab action if delete

    // Delete file from S3
    if (StringUtils.isEmpty(entity.getLoanUploadFileId())) {
      String fileName = String.format("%s%s", entity.getFolder(), entity.getFileName());
      DeleteFileRequest request = new DeleteFileRequest();
      request.setFileName(fileName);
      executorService.execute(() -> fileService.deleteFile(request));
    }
  }

  @Override
  public void addFileExist(String loanId, long documentMvalueId, ClientTypeEnum clientType, String collateralId, List<FileUploadExist> files, String collateralType) {
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(loanId)
      .orElseThrow(
        () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loanId", loanId));

    loanApplicationService.checkEditLoanApp(loanApplication, clientType);
    DocumentMappingEntity documentMappingEntity = documentMappingRepository.findById(documentMvalueId)
      .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "documentMvalueId", documentMvalueId +""));
    String email = AuthorizationUtil.getEmail();
    String user = "";
    if (ObjectUtil.isNotEmpty(email)) {
      user = email.split("@")[0];
    }
    List<MvalueUploadFilesEntity> entities = new ArrayList<>();
    if (!CollectionUtils.isEmpty(files))
      for (FileUploadExist file : files) {
        MvalueUploadFilesEntity entity =
            MvalueUploadFilesEntity.builder()
                .documentMvalueId(String.valueOf(documentMvalueId))
                .loanApplicationId(loanId)
                .folder(file.getFolder())
                .fileName(file.getFileName())
                .mvalueCode(documentMappingEntity.getCode())
                .collateralId(collateralId)
                .collateralTypeECM(collateralType)
                .loanUploadFileId(file.getId())
                .uploadUser(user)
                .status(LoanUploadFileStatusEnum.UPLOADED.getCode())
                .mvalueStatus(RequestPricingEnum.WAITING.name())
                .build();

        entities.add(entity);
      }

    mvalueUploadFilesRepository.saveAll(entities);

  }

  @Override
  public List<LoanUploadFile> findFilesUploadedByLoanApplicationId(String loanApplicationId) {
    List<LoanUploadFileEntity> entities = loanUploadFileRepository.findFilesUploadedByLoanApplicationIdAndStatus(
        loanApplicationId, LoanUploadFileStatusEnum.UPLOADED);
    List<LoanUploadFile> models = LoanUploadFileMapper.INSTANCE.toModels(entities);
    for (LoanUploadFile loanUploadFile : models) {
      String fileConfigId = loanUploadFile.getFileConfigId();
      FileConfigEntity fileConfigEntity = fileConfigRepository.findById(fileConfigId).orElseThrow(
          () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "fileConfigId",
              fileConfigId));
      FileConfig fileConfigModel = FileConfigMapper.INSTANCE.toModel(fileConfigEntity);
      loanUploadFile.setFileConfig(fileConfigModel);
    }
    return models;
  }

  @Override
  public List<DownloadFile> filesNeedToDownload(List<LoanUploadFile> list,
      LoanApplication loanApplication) throws SQLException {
    List<DownloadFile> downloadFileList = new ArrayList<>();
    List<String> fileConfigUuids = fileConfigService.getUuidsSatisfyRuleEngine(
        loanApplication.getUuid(), FileRuleEnum.CMS);

    boolean dnvvExists = false;

    for (LoanUploadFile loanUploadFile : list) {
      // Check
      if (fileConfigUuids.contains(loanUploadFile.getFileConfigId())) {
        DownloadFile downloadFile = new DownloadFile();
        // set zip prefix
        downloadFile.setZipPrefix(loanUploadFile.getFileConfig().getZipPrefix());

        // get presigned url for download
        String fullPath = String.format("%s%s", loanUploadFile.getFolder(),
            loanUploadFile.getFileName());
        byte[] downloadFileContent = homeLoanUtil.downloadFileFromS3(fullPath);

        /*
         * If file exist in database but not S3
         * Skip these files and continue
         */
        if (downloadFileContent == null) {
          continue;
        }

        // set length
        downloadFile.setLength(ArrayUtils.getLength(downloadFileContent));
        // set content
        downloadFile.setContent(downloadFileContent);
        downloadFile.setFileName(loanUploadFile.getFileName());
        downloadFile.setCode(loanUploadFile.getFileConfig().getCode());
        downloadFileList.add(downloadFile);

        if (dnvvExists) {
          continue;
        }
        if (Constants.FILE_CONFIG_CODE_DNVV.equals(downloadFile.getCode())) {
          dnvvExists = true;
        }
      }
    }

    // Add de nghi vay von
    // get presigned url for download
    if (!dnvvExists) {
      String fullPath = String.format("%s/%s/%s/%s", Constants.SERVICE_NAME,
          DateUtils.convertToSimpleFormat(Date.from(loanApplication.getCreatedAt()), "yyyyMMdd"),
          loanApplication.getUuid(), Constants.FILE_NAME_DNVV);
      byte[] downloadFileContent = homeLoanUtil.downloadFileFromS3(fullPath);

      if (downloadFileContent != null) {
        DownloadFile dnvv = new DownloadFile(Constants.ZIP_PREFIX_HSPHUONGAN,
            downloadFileContent.length, downloadFileContent, Constants.FILE_NAME_DNVV,
            Constants.FILE_CONFIG_CODE_DNVV);
        downloadFileList.add(dnvv);
      }
    }

    return downloadFileList;
  }

  // Tại đây các filesNeedToDownload đã được sắp xếp theo zip prefix
  // Đệ quy cho đến khi các files đã được đưa hết vào blocks
  @Override
  public List<ZipBlock> blocksNeedToZip(List<DownloadFile> filesNeedToDownload,
      List<ZipBlock> blocksNeedToZip, String zipPrefix, int orderNumber, int next, int length) {
    List<DownloadFile> downloadFileList = new ArrayList<>();

    if (next == filesNeedToDownload.size()) {
      return blocksNeedToZip;
    }

    DownloadFile downloadFile = filesNeedToDownload.get(next);

    if (downloadFile != null) {
      // Next element
      if (next < filesNeedToDownload.size()) {
        next++;
      }
      // Nếu file >= 10M
      if (downloadFile.getLength() >= ZIP_MAX_SIZE) {
        if (!zipPrefix.equals(downloadFile.getZipPrefix())) {
          orderNumber = 1;
        } else {
          orderNumber++;
        }
        downloadFileList.add(downloadFile);
        // Set zip prefix
        zipPrefix = downloadFile.getZipPrefix();
        ZipBlock zipBlock = new ZipBlock(new ArrayList<>(Arrays.asList(downloadFile)), orderNumber);
        blocksNeedToZip.add(zipBlock);
      } else {
        // Nếu block vẫn còn chỗ trống và cùng zip prefix
        // block hiện tại
        if (blocksNeedToZip.isEmpty()) {
          orderNumber++;
        }
        ZipBlock currentBlock =
            !blocksNeedToZip.isEmpty() ? blocksNeedToZip.get(blocksNeedToZip.size() - 1)
                : new ZipBlock(new ArrayList<>(), orderNumber);
        boolean currentBlockIsEmpty = currentBlock.getDownloadFileList().isEmpty();
        // Nếu currentBlock is empty
        if (length + downloadFile.getLength() < ZIP_MAX_SIZE && currentBlockIsEmpty) {
          currentBlock.getDownloadFileList().add(downloadFile);
          length = length + downloadFile.getLength();
          // Set zip prefix
          zipPrefix = downloadFile.getZipPrefix();
          // block mới
          orderNumber = 1;
          currentBlock.setOrderNumber(orderNumber);
          blocksNeedToZip.add(currentBlock);
        }
        // Nếu currentBlock not empty và còn chỗ trống
        if (length + downloadFile.getLength() < ZIP_MAX_SIZE && !currentBlockIsEmpty
            && zipPrefix.equals(downloadFile.getZipPrefix())) {
          currentBlock.getDownloadFileList().add(downloadFile);
          length = length + downloadFile.getLength();
        } else {
          // Nếu currentBlock not empty và hết chỗ trống
          if (length + downloadFile.getLength() >= ZIP_MAX_SIZE && !currentBlockIsEmpty
              && zipPrefix.equals(downloadFile.getZipPrefix())) {
            ZipBlock zipBlock = new ZipBlock(new ArrayList<>(Arrays.asList(downloadFile)),
                orderNumber);
            // block mới
            orderNumber = orderNumber + 1;
            zipBlock.setOrderNumber(orderNumber);
            blocksNeedToZip.add(zipBlock);
            length = downloadFile.getLength();
          } else if (!zipPrefix.equals(downloadFile.getZipPrefix())) {
            ZipBlock zipBlock = new ZipBlock(new ArrayList<>(Arrays.asList(downloadFile)),
                orderNumber);
            zipPrefix = downloadFile.getZipPrefix();
            // block mới
            orderNumber = 1;
            zipBlock.setOrderNumber(orderNumber);
            blocksNeedToZip.add(zipBlock);
            length = downloadFile.getLength();
          }
        }
      }

      // Đệ quy
      blocksNeedToZip(filesNeedToDownload, blocksNeedToZip, zipPrefix, orderNumber, next, length);
    }

    return blocksNeedToZip;
  }

  @Override
  public byte[] zipAllForLoanApplication(LoanApplication loanApplication) throws Exception {
    List<LoanUploadFile> loanUploadFiles = findFilesUploadedByLoanApplicationId(
        loanApplication.getUuid());
    List<DownloadFile> downloadFiles = filesNeedToDownload(loanUploadFiles, loanApplication);

    // Grouping files
    List<DownloadFile> groupingFiles = groupingFiles(downloadFiles);

    List<ZipBlock> blocksNeedToZip = new ArrayList<>();
    String zipPrefix = "";
    int orderNumber = 0;
    int next = 0;
    int length = 0;

    blocksNeedToZip = blocksNeedToZip(groupingFiles, blocksNeedToZip, zipPrefix, orderNumber, next,
        length);

    Map<String, byte[]> filesMap = new LinkedHashMap<>();
    for (ZipBlock zipBlock : blocksNeedToZip) {
      //TODO
      // rename duplicate in zip block
      renameDuplicateInZipBlock(zipBlock);
      String zipName = HomeLoanUtil.getZipNameForBlock(zipBlock);
      filesMap.put(zipName, ZipUtils.zipBlock(zipBlock));
    }
    //TODO
    //
    return filesMap.size() > 0 ? ZipUtils.zipAll(filesMap) : new byte[0];
  }

  private List<DownloadFile> groupingFiles(List<DownloadFile> files) {
    Map<String, List<DownloadFile>> uploadFileAfterGrouping =
        files.stream().collect(Collectors.groupingBy(w -> w.getZipPrefix()));

    List<DownloadFile> newList = new ArrayList();
    for (Map.Entry<String, List<DownloadFile>> entry : uploadFileAfterGrouping.entrySet()) {
      newList.addAll(entry.getValue());
    }
    return newList;
  }

  @Override
  @Transactional
  public void zipLoanApplicationAndThenUploadToS3(LoanApplication loanApplication)
      throws Exception {
    byte[] content = zipAllForLoanApplication(loanApplication);
    if (content.length > 0) {
      // Lấy thông tin folder
      String folder = loanUploadFileRepository.getFolderForLoanApp(loanApplication.getUuid());
      if (folder == null) {
        folder = String.format("%s/%s/%s/", Constants.SERVICE_NAME,
            DateUtils.convertToSimpleFormat(Date.from(loanApplication.getCreatedAt()), "yyyyMMdd"),
            loanApplication.getUuid());
      }
      log.info("zipLoanApplicationAndThenUploadToS3: " + folder);

      // Đẩy lên S3 và cập nhật trạng thái download_status
      UploadPresignedUrlRequest uploadPresignedUrlRequest = UploadPresignedUrlRequest.builder()
          .clientCode(environmentProperties.getClientCode())
          .scopes(environmentProperties.getClientScopes())
          .documentType("document")
          .priority(1)
          .filename(HomeLoanUtil.getZipNameForLoanApplication(loanApplication))
          .path(folder != null ? folder : "homeloan_service")
          .build();
      fileService.upload(uploadPresignedUrlRequest, content, false);
      // Cập nhật trạng thái download_status
      loanApplication.setDownloadStatus(DownloadStatus.READY_FOR_DOWNLOAD.getValue());
      LoanApplicationEntity entity = LoanApplicationMapper.INSTANCE.toEntity(loanApplication);
      loanApplicationRepository.save(entity);
    } else {
      // Cập nhật trạng thái download status = file not exists
      loanApplication.setDownloadStatus(DownloadStatus.NO_FILE_EXISTS.getValue());
      LoanApplicationEntity entity = LoanApplicationMapper.INSTANCE.toEntity(loanApplication);
      loanApplicationRepository.save(entity);
    }
  }

  @Override
  public String getFolderForLoanApp(String loanApplicationId) {
    return loanUploadFileRepository.getFolderForLoanApp(loanApplicationId);
  }

  @Override
  public List<LoanUploadFile> getCopyFiles(String loanId) {
    List<FileConfigCategory> fileConfigCategories = fileConfigCategoryService.getFileConfigCategories(
        loanId, FileRuleEnum.CMS);
    return collectFilesNeedToCopy(fileConfigCategories, new ArrayList());
  }

  @Override
  public LoanUploadFile save(LoanUploadFile loanUploadFile) {
    return LoanUploadFileMapper.INSTANCE.toModel(
        loanUploadFileRepository.save(LoanUploadFileMapper.INSTANCE.toEntity(loanUploadFile)));
  }

  @Override
  public List<LoanUploadFileEntity> getByFileConfigId(String fileConfigID, String loanID) {
    List<LoanUploadFileEntity> entities = loanUploadFileRepository.findByFileConfigIdAndLoanApplicationIdAndStatus(fileConfigID,loanID,LoanUploadFileStatusEnum.UPLOADED);
    return entities;
  }

  private List<LoanUploadFile> collectFilesNeedToCopy(List<FileConfigCategory> fileConfigCategories,
      List list) {
    for (FileConfigCategory cat : fileConfigCategories) {
      if (cat != null && Constants.FILE_CATEGORIES_NEED_TO_COPY.contains(cat.getCode())
          && !CollectionUtils.isEmpty(cat.getFileConfigList())) {
        for (FileConfig fileConfig : cat.getFileConfigList()) {
          list.addAll(fileConfig.getLoanUploadFileList());
        }
      }
      if (cat != null && !CollectionUtils.isEmpty(cat.getFileConfigCategoryList())) {
        collectFilesNeedToCopy(cat.getFileConfigCategoryList(), list);
      }
    }
    return list;
  }

  private void renameDuplicateInZipBlock(ZipBlock zipBlock) {
    if (zipBlock != null && zipBlock.getDownloadFileList() != null) {
      List<DownloadFile> downloadFiles = zipBlock.getDownloadFileList();
      HashMap<String, Integer> seenBefore = new HashMap<>();
      for (DownloadFile downloadFile : downloadFiles) {
        String fileName = downloadFile.getFileName();

        if (fileName != null) {
          String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
          String extension = FilenameUtils.getExtension(fileName);

          Integer lookup = seenBefore.get(fileName);
          if (lookup == null) {
            seenBefore.put(fileName, 1);
          } else {
            downloadFile.setFileName(fileNameWithOutExt + "_" + lookup + (
                org.apache.commons.lang3.StringUtils.isNotEmpty(extension) ? "." + extension : ""));
            seenBefore.put(fileName, lookup + 1);
          }
        }
      }
    }
  }
}
