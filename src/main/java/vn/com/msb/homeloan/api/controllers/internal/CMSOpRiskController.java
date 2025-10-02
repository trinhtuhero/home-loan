package vn.com.msb.homeloan.api.controllers.internal;

import java.text.ParseException;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CMSGetOpRiskCollateralInfoResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSGetOpRiskInfoResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSOpRiskResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CmsOpRiskRequestMapper;
import vn.com.msb.homeloan.api.dto.request.CMSOpsRiskCheckCRequest;
import vn.com.msb.homeloan.api.dto.request.CMSOpsRiskCheckPRequest;
import vn.com.msb.homeloan.api.dto.response.CMSGetOpRiskCollateralInfoResponse;
import vn.com.msb.homeloan.api.dto.response.CMSGetOpRiskInfoResponse;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.CMSGetOpRiskCollateralInfo;
import vn.com.msb.homeloan.core.model.CMSGetOpRiskInfo;
import vn.com.msb.homeloan.core.model.OpRisk;
import vn.com.msb.homeloan.core.service.OpRiskService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/op-risk")
@RequiredArgsConstructor
public class CMSOpRiskController {

  private final OpRiskService opRiskService;

  @PostMapping("/check-p")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_OP_RISK_CHECK_P + "')")
  public ResponseEntity<ApiResource> checkP(@RequestBody @Valid CMSOpsRiskCheckPRequest request)
      throws ParseException {
    List<OpRisk> result = opRiskService.checkP(
        CmsOpRiskRequestMapper.INSTANCE.toModelPs(request.getCmsOpsRiskRequests()),
        request.getLoanApplicationId());
    ApiResource apiResource = new ApiResource(
        CMSOpRiskResponseMapper.INSTANCE.toCmsResponses(result), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/check-c")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_OP_RISK_CHECK_P + "')")
  public ResponseEntity<ApiResource> checkC(@RequestBody @Valid CMSOpsRiskCheckCRequest request)
      throws ParseException {
    List<OpRisk> result = opRiskService.checkC(
        CmsOpRiskRequestMapper.INSTANCE.toModelCs(request.getCmsOpsRiskRequests()),
        request.getLoanApplicationId());
    ApiResource apiResource = new ApiResource(
        CMSOpRiskResponseMapper.INSTANCE.toCmsResponses(result), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanId}")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_OP_RISK_GET_OP_RISK_INFO
          + "')")
  public ResponseEntity<ApiResource> getOpRiskInfo(@PathVariable String loanId) {
    List<CMSGetOpRiskInfo> result = opRiskService.getOpRiskInfo(loanId);
    List<CMSGetOpRiskInfoResponse> lstCicInfo = CMSGetOpRiskInfoResponseMapper.INSTANCE.toCmsResponses(
        result);
    ApiResource apiResource = new ApiResource(lstCicInfo, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanId}/collateral")
//    @PreAuthorize("@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_OP_RISK_GET_OP_RISK_INFO + "')")
  public ResponseEntity<ApiResource> getOpRiskCollateralInfo(@PathVariable String loanId) {
    List<CMSGetOpRiskCollateralInfo> result = opRiskService.getOpRiskCollateralInfo(loanId);
    List<CMSGetOpRiskCollateralInfoResponse> lstCicInfo = CMSGetOpRiskCollateralInfoResponseMapper.INSTANCE.toCmsResponses(
        result);
    ApiResource apiResource = new ApiResource(lstCicInfo, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
