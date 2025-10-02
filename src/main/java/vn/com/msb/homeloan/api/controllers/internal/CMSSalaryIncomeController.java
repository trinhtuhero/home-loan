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
import vn.com.msb.homeloan.api.dto.mapper.CMSSalaryIncomeRequestMapper;
import vn.com.msb.homeloan.api.dto.request.CMSSalaryIncomeRequest;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.SalaryIncome;
import vn.com.msb.homeloan.core.service.SalaryIncomeService;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/v1/cms/salary-incomes")
@RequiredArgsConstructor
public class CMSSalaryIncomeController {

  private final SalaryIncomeService salaryIncomeService;

  @PutMapping
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_SALARY_INCOMES_SAVE + "')")
  public ResponseEntity<ApiResource> saves(
      @Valid @RequestBody List<CMSSalaryIncomeRequest> salaryIncomeRequests) {
    List<SalaryIncome> salaryIncomes = salaryIncomeService.saves(
        CMSSalaryIncomeRequestMapper.INSTANCE.toModels(salaryIncomeRequests));
    ApiResource apiResource = new ApiResource(salaryIncomes, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_SALARY_INCOMES_DELETE_BY_ID
          + "')")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    salaryIncomeService.deleteByUuid(uuid, ClientTypeEnum.CMS);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
