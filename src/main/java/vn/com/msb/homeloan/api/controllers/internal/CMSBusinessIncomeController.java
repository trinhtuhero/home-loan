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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CMSBusinessIncomeRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSBusinessIncomeResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CMSBusinessIncomeRequest;
import vn.com.msb.homeloan.api.dto.response.CMSBusinessIncomeResponse;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.BusinessIncome;
import vn.com.msb.homeloan.core.service.BusinessIncomeService;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/v1/cms/business-incomes")
@RequiredArgsConstructor
public class CMSBusinessIncomeController {

  private final BusinessIncomeService businessIncomeService;

  @PutMapping
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_BUSINESS_INCOMES_UPDATE
          + "')")
  public ResponseEntity<ApiResource> saves(
      @Valid @RequestBody List<CMSBusinessIncomeRequest> requests) {
    List<BusinessIncome> model = businessIncomeService.saves(
        CMSBusinessIncomeRequestMapper.INSTANCE.toModels(requests));
    List<CMSBusinessIncomeResponse> responses = CMSBusinessIncomeResponseMapper.INSTANCE.toCmsResponses(
        model);
    ApiResource apiResource = new ApiResource(responses, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_BUSINESS_INCOMES_DELETE_BY_ID
          + "')")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    businessIncomeService.deleteByUuid(uuid, ClientTypeEnum.CMS);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
