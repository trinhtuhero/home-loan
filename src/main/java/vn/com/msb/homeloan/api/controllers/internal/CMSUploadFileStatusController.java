package vn.com.msb.homeloan.api.controllers.internal;


import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.UploadFileStatusMapper;
import vn.com.msb.homeloan.api.dto.request.CheckEnoughRequest;
import vn.com.msb.homeloan.api.dto.request.CreateUploadFileStatusRequest;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.UploadFileStatus;
import vn.com.msb.homeloan.core.service.UploadFileStatusService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/upload-file-status")
@RequiredArgsConstructor
public class CMSUploadFileStatusController {

  private final UploadFileStatusService uploadFileStatusService;

  @PostMapping
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_UPLOAD_FILE_STATUS_SAVE
          + "')")
  public ResponseEntity<ApiResource> save(
      @Valid @RequestBody CreateUploadFileStatusRequest request) {
    UploadFileStatus model = UploadFileStatusMapper.INSTANCE.toModel(request);
    ApiResource apiResource = new ApiResource(uploadFileStatusService.save(model),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/check-enough")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_UPLOAD_FILE_STATUS_CHECK_ENOUGH + "')")
  public ResponseEntity<ApiResource> checkEnough(@Valid @RequestBody CheckEnoughRequest request) {
    UploadFileStatus model = uploadFileStatusService.checkEnough(request.getLoanApplicationId(),
        request.getFileConfigId());
    ApiResource apiResource = new ApiResource(model, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
