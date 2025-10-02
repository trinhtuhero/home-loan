package vn.com.msb.homeloan.api.controllers.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CMSCollateralResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSMvalueDocumentResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSMvalueResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CmsGetCollateralByCodeRequestMapper;
import vn.com.msb.homeloan.api.dto.request.CMSGetCollateralByCodeRequest;
import vn.com.msb.homeloan.api.dto.response.CMSMvalueCollateralResponse;
import vn.com.msb.homeloan.core.model.Collateral;
import vn.com.msb.homeloan.core.model.Mvalue;
import vn.com.msb.homeloan.core.model.MvalueDocument;
import vn.com.msb.homeloan.core.model.request.mvalue.CMSCreateProfileRequest;
import vn.com.msb.homeloan.core.service.DocumentMappingService;
import vn.com.msb.homeloan.core.service.MvalueService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/m-value")
@RequiredArgsConstructor
public class CMSMvalueController {

  private final MvalueService mvalueService;
  private final DocumentMappingService documentMappingService;

  @PostMapping("/collateral/get-by-code")
  public ResponseEntity<ApiResource> getByCode(
      @RequestBody @Valid CMSGetCollateralByCodeRequest request) throws Exception {
    Mvalue result =
        mvalueService.getByCode(
            CmsGetCollateralByCodeRequestMapper.INSTANCE.toModelP(request),
            request.getLoanApplicationId());
    ApiResource apiResource =
        new ApiResource(
            CMSMvalueResponseMapper.INSTANCE.toCmsResponse(result), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/collateral/{loanId}/check-duplicate")
  public ResponseEntity<ApiResource> checkDuplicate(@PathVariable String loanId) {
    List<Collateral> obj = mvalueService.checkDuplicate(loanId);
    List<CMSMvalueCollateralResponse> responses =
        obj.stream()
            .map(CMSCollateralResponseMapper.INSTANCE::toMvalueResponse)
            .collect(Collectors.toList());
    ApiResource apiResource = new ApiResource(responses, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/collateral/get-document-mapping")
  public ResponseEntity<ApiResource> getDocumentMapping(
      String loanId, String collateralType, String type, String collateralId) {
    List<MvalueDocument> entities =
        documentMappingService.getDocumentMapping(loanId, collateralType, type, collateralId);
    ApiResource apiResource =
        new ApiResource(
            CMSMvalueDocumentResponseMapper.INSTANCE.toCmsResponses(entities),
            HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/collateral/create-profile")
  public ResponseEntity<ApiResource> createProfile(@RequestBody CMSCreateProfileRequest request)
      throws Exception {
    String response = mvalueService.createProfile(request);
    Map<String, String> dataMap = new HashMap<>();
    ApiResource apiResource;
    dataMap.put("message", response);
    if (response.equalsIgnoreCase("Successfully")) {
      apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    } else {
      apiResource = new ApiResource(dataMap, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    return ResponseEntity.ok(apiResource);
  }
}
