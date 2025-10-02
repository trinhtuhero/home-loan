package vn.com.msb.homeloan.api.controllers.internal;

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
import vn.com.msb.homeloan.api.dto.mapper.CMSCicResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSGetCicInfoResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CMSCicRequest;
import vn.com.msb.homeloan.api.dto.response.CMSCicResponse;
import vn.com.msb.homeloan.api.dto.response.CMSGetCicInfoResponse;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.CMSCic;
import vn.com.msb.homeloan.core.model.CMSGetCicInfo;
import vn.com.msb.homeloan.core.service.CicService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/cic")
@RequiredArgsConstructor
public class CMSCicController {

  private final CicService cicService;

  @PostMapping("/check-type-debt")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_CIC_CHECK_TYPE_DEBT + "')")
  public ResponseEntity<ApiResource> checkTypeDebt(@RequestBody @Valid CMSCicRequest request)
      throws Exception {
    List<CMSCic> result = cicService.checkTypeDebt(request.getInfos(),
        request.getLoanApplicationId());
    List<CMSCicResponse> cmsCicResponses = CMSCicResponseMapper.INSTANCE.toCmsResponses(result);
    ApiResource apiResource = new ApiResource(cmsCicResponses, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanId}")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_CIC_GET_CIC_INFO + "')")
  public ResponseEntity<ApiResource> getCicInfo(@PathVariable String loanId) {
    List<CMSGetCicInfo> result = cicService.getCic(loanId);
    List<CMSGetCicInfoResponse> cmsGetCicInfoResponses = CMSGetCicInfoResponseMapper.INSTANCE.toCmsResponses(
        result);
    ApiResource apiResource = new ApiResource(cmsGetCicInfoResponses, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
