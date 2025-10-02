package vn.com.msb.homeloan.api.controllers.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.LoanUploadFileUpdateStatusRequestMapper;
import vn.com.msb.homeloan.api.dto.request.*;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.FileRuleEnum;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.entity.LoanUploadFileEntity;
import vn.com.msb.homeloan.core.model.FileConfigCategory;
import vn.com.msb.homeloan.core.model.LoanUploadFile;
import vn.com.msb.homeloan.core.model.LoanUrlUploadFile;
import vn.com.msb.homeloan.core.model.MvalueUploadFile;
import vn.com.msb.homeloan.core.model.mapper.MvalueUploadFileMapper;
import vn.com.msb.homeloan.core.model.response.DownloadPresignedUrlResponse;
import vn.com.msb.homeloan.core.service.FileConfigCategoryService;
import vn.com.msb.homeloan.core.service.LoanUploadFileService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/loan-upload")
@RequiredArgsConstructor
public class CMSLoanUploadFileController {

  private final LoanUploadFileService loanUploadFileService;

  private final FileConfigCategoryService fileConfigCategoryService;

  @GetMapping("/{loanUploadFileId}/view-file")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_UPLOAD_PRE_DOWNLOAD_FILE
          + "')")
  public ResponseEntity<ApiResource> viewFile(@PathVariable String loanUploadFileId) {
    DownloadPresignedUrlResponse response = loanUploadFileService.viewFile(loanUploadFileId);
    ApiResource apiResource = new ApiResource(response.getData(), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{id}/view-file-mvalue")
  @PreAuthorize(
    "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_UPLOAD_PRE_DOWNLOAD_FILE
      + "')")
  public ResponseEntity<ApiResource> viewFileMvalue(@PathVariable long id) {
    DownloadPresignedUrlResponse response = loanUploadFileService.viewFileMvalue(id);
    ApiResource apiResource = new ApiResource(response.getData(), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/pre-upload-file")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_UPLOAD_PRE_UPLOAD_FILE
          + "')")
  public ResponseEntity<ApiResource> preUploadFile(
      @RequestBody @Valid LoanGetUrlUploadFileRequest request) throws IOException {
    List<LoanUrlUploadFile> files = loanUploadFileService.preUploadFile(request.getLoanId(),
        request.getFileConfigId(),
        request.getFiles(),
        ClientTypeEnum.CMS);
    ApiResource apiResource = new ApiResource(files, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/add-file-exist")
  @PreAuthorize(
    "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_UPLOAD_PRE_UPLOAD_FILE
      + "')")
  public ResponseEntity<ApiResource> addFileExist(
    @RequestBody @Valid UploadFileExistRequest request) throws IOException {
    loanUploadFileService.addFileExist(
        request.getLoanId(),
        request.getDocumentMvalueId(),
        ClientTypeEnum.CMS,
        request.getCollateralId(),
        request.getFiles(),
        request.getCollateralType());
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/pre-upload-file-mvalue")
  @PreAuthorize(
    "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_UPLOAD_PRE_UPLOAD_FILE
      + "')")
  public ResponseEntity<ApiResource> preUploadFileMValue(
    @RequestBody @Valid MvalueGetUrlUploadFileRequest request) throws IOException {
    List<LoanUrlUploadFile> files = loanUploadFileService.preUploadFileMValue(request.getLoanId(),
      request.getDocumentMvalueId(),
      request.getFiles(),
      ClientTypeEnum.CMS,
      request.getCollateralId(),
      request.getCollateralType());
    ApiResource apiResource = new ApiResource(files, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PatchMapping("/update-status")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_UPLOAD_UPDATE_STATUS
          + "')")
  public ResponseEntity<ApiResource> updateStatus(
      @RequestBody @Valid List<LoanUploadFileUpdateStatusRequest> requests) {
    List<LoanUploadFile> loanUploadFiles = LoanUploadFileUpdateStatusRequestMapper.INSTANCE.toModels(
        requests);
    ApiResource apiResource = new ApiResource(loanUploadFileService.updateStatus(loanUploadFiles),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PatchMapping("/update-status-mvalue")
  @PreAuthorize(
    "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_UPLOAD_UPDATE_STATUS
      + "')")
  public ResponseEntity<ApiResource> updateStatusMvalue(
    @RequestBody @Valid List<MvalueUploadFileUpdateStatusRequest> requests) {
    List<MvalueUploadFile> loanUploadFiles = MvalueUploadFileMapper.INSTANCE.requestToDto(
      requests);
    ApiResource apiResource = new ApiResource(loanUploadFileService.updateStatusMvalue(loanUploadFiles),
      HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }



  @Transactional
  @DeleteMapping("/{loanUploadFileId}/delete")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_UPLOAD_DELETE_FILE
          + "')")
  public ResponseEntity<ApiResource> deleteFile(@PathVariable String loanUploadFileId)
      throws IOException {
    loanUploadFileService.deleteFile(loanUploadFileId, ClientTypeEnum.CMS);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @Transactional
  @DeleteMapping("/{loanUploadFileId}/delete-mvalue")
  @PreAuthorize(
    "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_UPLOAD_DELETE_FILE
      + "')")
  public ResponseEntity<ApiResource> deleteFileMvalue(@PathVariable long loanUploadFileId)
    throws IOException {
    loanUploadFileService.deleteFileMvalue(loanUploadFileId, ClientTypeEnum.CMS);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanApplicationId}")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_LOAN_UPLOAD_GET_FILE_CONFIG_CATEGORIES + "')")
  public ResponseEntity<ApiResource> getFileConfigCategories(
      @PathVariable String loanApplicationId) {
    List<FileConfigCategory> fileConfigCategories = fileConfigCategoryService.getFileConfigCategories(
        loanApplicationId, FileRuleEnum.CMS);
    ApiResource apiResource = new ApiResource(fileConfigCategories, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/{loanApplicationId}/validate")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_LOAN_UPLOAD_VALIDATE_UPLOAD_FILE + "')")
  public ResponseEntity<ApiResource> validateUploadFile(@PathVariable String loanApplicationId)
      throws IOException {
    fileConfigCategoryService.cmsCheckUploadFile(loanApplicationId);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/list")
  public ResponseEntity<ApiResource> checkDuplicate(String fileConfigID, String loanID) {
    List<LoanUploadFileEntity> entities = loanUploadFileService.getByFileConfigId(fileConfigID,loanID);
    ApiResource apiResource = new ApiResource(entities, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
