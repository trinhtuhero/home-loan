package vn.com.msb.homeloan.api.controllers.external;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.LoanUploadFileUpdateStatusRequestMapper;
import vn.com.msb.homeloan.api.dto.request.LoanGetUrlUploadFileRequest;
import vn.com.msb.homeloan.api.dto.request.LoanUploadFileUpdateStatusRequest;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.FileRuleEnum;
import vn.com.msb.homeloan.core.model.FileConfigCategory;
import vn.com.msb.homeloan.core.model.LoanUploadFile;
import vn.com.msb.homeloan.core.model.LoanUrlUploadFile;
import vn.com.msb.homeloan.core.model.response.DownloadPresignedUrlResponse;
import vn.com.msb.homeloan.core.service.FileConfigCategoryService;
import vn.com.msb.homeloan.core.service.FileConfigService;
import vn.com.msb.homeloan.core.service.FileRuleService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.LoanUploadFileService;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/loan-upload")
@RequiredArgsConstructor
public class LoanUploadFileController {

  private final LoanUploadFileService loanUploadFileService;

  private final FileRuleService fileRuleService;

  private final FileConfigCategoryService fileConfigCategoryService;

  private final FileConfigService fileConfigService;

  private final LoanApplicationService loanApplicationService;

  @PostMapping("/pre-upload-file")
  public ResponseEntity<ApiResource> preUploadFile(
      @RequestBody @Valid LoanGetUrlUploadFileRequest request) {
    List<LoanUrlUploadFile> files = loanUploadFileService.preUploadFile(request.getLoanId(),
        request.getFileConfigId(),
        request.getFiles(),
        ClientTypeEnum.LDP);
    ApiResource apiResource = new ApiResource(files, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PatchMapping("/update-status")
  public ResponseEntity<ApiResource> updateStatus(
      @RequestBody @Valid List<LoanUploadFileUpdateStatusRequest> requests) {
    List<LoanUploadFile> loanUploadFiles = LoanUploadFileUpdateStatusRequestMapper.INSTANCE.toModels(
        requests);
    ApiResource apiResource = new ApiResource(loanUploadFileService.updateStatus(loanUploadFiles),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanApplicationId}")
  public ResponseEntity<ApiResource> getFileConfigCategories(
      @PathVariable String loanApplicationId) {
    List<FileConfigCategory> fileConfigCategories = fileConfigCategoryService.getFileConfigCategories(
        loanApplicationId, FileRuleEnum.LDP);
    ApiResource apiResource = new ApiResource(fileConfigCategories, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanUploadFileId}/view-file")
  public ResponseEntity<ApiResource> viewFile(@PathVariable String loanUploadFileId) {
    DownloadPresignedUrlResponse response = loanUploadFileService.viewFile(loanUploadFileId);
    ApiResource apiResource = new ApiResource(response.getData(), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @Transactional
  @DeleteMapping("/{loanUploadFileId}/delete")
  public ResponseEntity<ApiResource> deleteFile(@PathVariable String loanUploadFileId) {
    loanUploadFileService.deleteFile(loanUploadFileId, ClientTypeEnum.LDP);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
