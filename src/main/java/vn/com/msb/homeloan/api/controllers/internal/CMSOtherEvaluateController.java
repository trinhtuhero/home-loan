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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CMSOtherEvaluateRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSOtherEvaluateResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CMSOtherEvaluateRequest;
import vn.com.msb.homeloan.api.dto.response.CMSOtherEvaluateResponse;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.OtherEvaluate;
import vn.com.msb.homeloan.core.service.OtherEvaluateService;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/v1/cms/other-evaluate")
@RequiredArgsConstructor
public class CMSOtherEvaluateController {

  private final OtherEvaluateService otherEvaluateService;

  @PostMapping
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_OTHER_EVALUATE_SAVE + "')")
  public ResponseEntity<ApiResource> update(
      @Valid @RequestBody List<CMSOtherEvaluateRequest> requests) {
    List<OtherEvaluate> otherEvaluates = otherEvaluateService.saves(
        CMSOtherEvaluateRequestMapper.INSTANCE.toModels(requests));
    List<CMSOtherEvaluateResponse> responses = CMSOtherEvaluateResponseMapper.INSTANCE.toCmsResponses(
        otherEvaluates);
    ApiResource apiResource = new ApiResource(responses, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_OTHER_EVALUATE_DELETE_BY_ID
          + "')")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    otherEvaluateService.deleteByUuid(uuid);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
