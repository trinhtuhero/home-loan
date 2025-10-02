package vn.com.msb.homeloan.api.controllers.external;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CollateralRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CollateralResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CollateralRequest;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.Collateral;
import vn.com.msb.homeloan.core.service.CollateralService;
import vn.com.msb.homeloan.core.util.StringUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/collateral")
@RequiredArgsConstructor
public class CollateralController {

  private final CollateralService collateralService;

  @GetMapping("/{id}")
  public ResponseEntity<ApiResource> findById(@PathVariable String id) {
    Collateral collateral = collateralService.findById(id);
    ApiResource apiResource = new ApiResource(
        CollateralResponseMapper.INSTANCE.toResponse(collateral), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanId}/list")
  public ResponseEntity<ApiResource> getByLoanId(@PathVariable String loanId) {
    List<Collateral> collaterals = collateralService.findByLoanId(loanId);
    ApiResource apiResource = new ApiResource(
        CollateralResponseMapper.INSTANCE.toResponses(collaterals), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping
  public ResponseEntity<ApiResource> insert(@Valid @RequestBody CollateralRequest request) {
    if (!StringUtils.isEmpty(request.getUuid())) {
      throw new ApplicationException(ErrorEnum.UUID_INVALID);
    }
    Collateral collateral = collateralService.save(
        CollateralRequestMapper.INSTANCE.toModel(request), ClientTypeEnum.LDP);
    ApiResource apiResource = new ApiResource(
        CollateralResponseMapper.INSTANCE.toResponse(collateral), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  // Validate Before Next Tab
  @GetMapping("/{uuid}/validate-before-next-tab")
  public ResponseEntity<ApiResource> validateBeforeNextTab(@PathVariable String uuid) {
    Boolean isInvalid = true;
    Map<String, Object> dataMap = new HashMap<>();
    List<String> values = collateralService.validateBeforeNextTab(uuid);
    if (CollectionUtils.isNotEmpty(values)) {
      isInvalid = false;
    }
    dataMap.put("is_valid", isInvalid);
    dataMap.put("values", values);
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PutMapping
  public ResponseEntity<ApiResource> update(@Valid @RequestBody CollateralRequest request) {
    if (StringUtils.isEmpty(request.getUuid())) {
      throw new ApplicationException(ErrorEnum.UUID_INVALID);
    }
    Collateral collateral = collateralService.save(
        CollateralRequestMapper.INSTANCE.toModel(request), ClientTypeEnum.LDP);
    ApiResource apiResource = new ApiResource(
        CollateralResponseMapper.INSTANCE.toResponse(collateral), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    collateralService.deleteById(uuid, ClientTypeEnum.LDP);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
