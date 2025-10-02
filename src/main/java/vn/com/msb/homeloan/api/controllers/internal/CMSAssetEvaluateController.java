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
import vn.com.msb.homeloan.api.dto.mapper.CMSAssetEvaluateRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSAssetEvaluateResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CMSAssetEvaluateRequest;
import vn.com.msb.homeloan.api.dto.response.CMSAssetEvaluateResponse;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.AssetEvaluate;
import vn.com.msb.homeloan.core.service.AssetEvaluateService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/asset_evaluate")
@RequiredArgsConstructor
public class CMSAssetEvaluateController {

  private final AssetEvaluateService assetEvaluateService;

  @PostMapping
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_ASSET_EVALUATE_SAVE + "')")
  public ResponseEntity<ApiResource> save(@Valid @RequestBody CMSAssetEvaluateRequest requests) {
    AssetEvaluate model = assetEvaluateService.save(
        CMSAssetEvaluateRequestMapper.INSTANCE.toModel(requests));
    CMSAssetEvaluateResponse result = CMSAssetEvaluateResponseMapper.INSTANCE.toCmsResponse(model);
    ApiResource apiResource = new ApiResource(result, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}

