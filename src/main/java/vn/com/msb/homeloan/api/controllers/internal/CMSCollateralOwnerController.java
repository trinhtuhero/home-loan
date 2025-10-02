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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CMSCollateralOwnerRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSCollateralOwnerResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CMSCollateralOwnerRequest;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.CollateralOwner;
import vn.com.msb.homeloan.core.service.CollateralOwnerService;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/v1/cms/collateral-owner")
@RequiredArgsConstructor
public class CMSCollateralOwnerController {

  private final CollateralOwnerService collateralOwnerService;

  @PutMapping
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_COLLATERAL_OWNER_SAVES
          + "')")
  public ResponseEntity<ApiResource> saves(
      @Valid @RequestBody List<CMSCollateralOwnerRequest> requests) {
    List<CollateralOwner> collateralOwners = collateralOwnerService.saves(
        CMSCollateralOwnerRequestMapper.INSTANCE.toModels(requests));
    ApiResource apiResource = new ApiResource(
        CMSCollateralOwnerResponseMapper.INSTANCE.toCmsResponses(collateralOwners),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_COLLATERAL_OWNER_DELETE_BY_ID
          + "')")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    collateralOwnerService.deleteById(uuid, ClientTypeEnum.CMS);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<ApiResource> getById(@PathVariable String uuid) {
    CollateralOwner collateralOwner = collateralOwnerService.findById(uuid);
    ApiResource apiResource = new ApiResource(
        CMSCollateralOwnerResponseMapper.INSTANCE.toCmsResponse(collateralOwner),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanId}/list")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_COLLATERAL_GET_BY_LOAN_ID
          + "')")
  public ResponseEntity<ApiResource> getByLoanId(@PathVariable String loanId) {
    List<CollateralOwner> collateralOwners = collateralOwnerService.getAllByLoanId(loanId);
    ApiResource apiResource = new ApiResource(
        CMSCollateralOwnerResponseMapper.INSTANCE.toCmsResponses(collateralOwners),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
