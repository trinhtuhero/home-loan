package vn.com.msb.homeloan.api.controllers.internal;

import java.io.IOException;
import java.sql.SQLException;
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
import vn.com.msb.homeloan.api.dto.mapper.FieldSurveyItemRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.FieldSurveyItemResponseMapper;
import vn.com.msb.homeloan.api.dto.request.creditappraisal.FieldSurveyItemRequest;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.FieldSurveyItem;
import vn.com.msb.homeloan.core.service.FieldSurveyItemService;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/v1/cms/field-survey")
@RequiredArgsConstructor
public class CMSFieldSurveyItemController {

  private final FieldSurveyItemService fieldSurveyItemService;

  @PutMapping("/{loanId}")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_CREDIT_APPRAISAL_SAVE + "')")
  public ResponseEntity<ApiResource> saves(@PathVariable String loanId,
      @Valid @RequestBody List<FieldSurveyItemRequest> request) {
    List<FieldSurveyItem> results = fieldSurveyItemService.saves(
        FieldSurveyItemRequestMapper.INSTANCE.toModels(request), loanId);
    ApiResource apiResource = new ApiResource(
        FieldSurveyItemResponseMapper.INSTANCE.toResponses(results), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{uuid}")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_CREDIT_APPRAISAL_GET_BY_LOANID + "')")
  public ResponseEntity<ApiResource> getById(@PathVariable String uuid) throws SQLException {
    FieldSurveyItem result = fieldSurveyItemService.getById(uuid);
    ApiResource apiResource = new ApiResource(
        FieldSurveyItemResponseMapper.INSTANCE.toResponse(result), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_CREDIT_APPRAISAL_GET_BY_LOANID + "')")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid)
      throws SQLException, IOException {
    fieldSurveyItemService.deleteById(uuid);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("{loanId}/list")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_CREDIT_APPRAISAL_GET_BY_LOANID + "')")
  public ResponseEntity<ApiResource> getByLoanId(@PathVariable String loanId) throws SQLException {
    List<FieldSurveyItem> results = fieldSurveyItemService.getByLoanId(loanId);
    ApiResource apiResource = new ApiResource(
        FieldSurveyItemResponseMapper.INSTANCE.toResponses(results), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

}
