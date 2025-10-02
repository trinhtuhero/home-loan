package vn.com.msb.homeloan.api.controllers.internal;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CMSCollateralRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSCollateralResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CMSCollateralRequest;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.Collateral;
import vn.com.msb.homeloan.core.service.CollateralService;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/v1/cms/collateral")
@RequiredArgsConstructor
public class CMSCollateralController {

  private final CollateralService collateralService;

  @GetMapping("/{loanId}/list")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_COLLATERAL_GET_BY_LOANID
          + "')")
  public ResponseEntity<ApiResource> getByLoanId(@PathVariable String loanId) {
    List<Collateral> collaterals = collateralService.findCollateralInfoByLoanId(loanId);
    ApiResource apiResource = new ApiResource(
        CMSCollateralResponseMapper.INSTANCE.toCmsResponses(collaterals), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PutMapping
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_COLLATERAL_SAVES + "')")
  public ResponseEntity<ApiResource> saves(@Valid @RequestBody List<CMSCollateralRequest> requests)
      throws IOException {
    List<Collateral> collaterals = collateralService.saves(
        CMSCollateralRequestMapper.INSTANCE.toModels(requests), ClientTypeEnum.CMS);
    ApiResource apiResource = new ApiResource(
        CMSCollateralResponseMapper.INSTANCE.toCmsResponses(collaterals), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_COLLATERAL_DELETE_BY_ID
          + "')")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) throws IOException {
    collateralService.deleteById(uuid, ClientTypeEnum.CMS);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
