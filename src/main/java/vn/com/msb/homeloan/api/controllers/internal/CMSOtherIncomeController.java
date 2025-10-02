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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CMSOtherIncomeRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSOtherIncomeResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CMSOtherIncomeRequest;
import vn.com.msb.homeloan.api.dto.response.CMSOtherIncomeResponse;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.OtherIncome;
import vn.com.msb.homeloan.core.service.OtherIncomeService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/other-incomes")
@RequiredArgsConstructor
public class CMSOtherIncomeController {

  private final OtherIncomeService otherIncomeService;

  @PutMapping
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_OTHER_INCOMES_SAVE + "')")
  public ResponseEntity<ApiResource> saves(
      @Valid @RequestBody List<CMSOtherIncomeRequest> requests) {
    List<OtherIncome> otherIncomes = otherIncomeService.saves(
        CMSOtherIncomeRequestMapper.INSTANCE.toModels(requests));
    List<CMSOtherIncomeResponse> responses = CMSOtherIncomeResponseMapper.INSTANCE.toCmsResponses(
        otherIncomes);
    ApiResource apiResource = new ApiResource(responses, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_OTHER_INCOMES_DELETE_BY_ID
          + "')")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    otherIncomeService.deleteByUuid(uuid, ClientTypeEnum.CMS);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}

