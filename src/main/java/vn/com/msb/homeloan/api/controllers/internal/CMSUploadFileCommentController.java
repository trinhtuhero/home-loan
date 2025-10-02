package vn.com.msb.homeloan.api.controllers.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.UploadFileCommentMapper;
import vn.com.msb.homeloan.api.dto.request.CreateUploadFileCommentRequest;
import vn.com.msb.homeloan.api.dto.request.UpdateUploadFileCommentRequest;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.UploadFileComment;
import vn.com.msb.homeloan.core.service.UploadFileCommentService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/upload-file-comment")
@RequiredArgsConstructor
public class CMSUploadFileCommentController {

  private final UploadFileCommentService uploadFileCommentService;

  @PostMapping
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_UPLOAD_FILE_COMMENT_SAVE
          + "')")
  public ResponseEntity<ApiResource> save(
      @Valid @RequestBody CreateUploadFileCommentRequest request) {
    UploadFileComment model = UploadFileCommentMapper.INSTANCE.toModel(request);
    ApiResource apiResource = new ApiResource(uploadFileCommentService.save(model),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PatchMapping
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_UPLOAD_FILE_COMMENT_UPDATE
          + "')")
  public ResponseEntity<ApiResource> update(
      @Valid @RequestBody UpdateUploadFileCommentRequest request) {
    UploadFileComment model = UploadFileCommentMapper.INSTANCE.toModel(request);
    ApiResource apiResource = new ApiResource(uploadFileCommentService.update(model),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_UPLOAD_FILE_COMMENT_DELETE
          + "')")
  public ResponseEntity<ApiResource> delete(@PathVariable String uuid) {
    uploadFileCommentService.deleteByUuid(uuid);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/history")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_UPLOAD_FILE_COMMENT_HISTORY
          + "')")
  public ResponseEntity<ApiResource> history(
      @RequestParam(value = "loan_application_id") String loanApplicationId,
      @RequestParam(value = "file_config_id") String fileConfigId) {
    List<UploadFileComment> list = uploadFileCommentService.history(loanApplicationId,
        fileConfigId);
    ApiResource apiResource = new ApiResource(list, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
