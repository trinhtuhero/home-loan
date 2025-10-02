package vn.com.msb.homeloan.api.controllers.external;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import vn.com.msb.homeloan.api.dto.mapper.BusinessIncomeMapper;
import vn.com.msb.homeloan.api.dto.request.CRUBusinessIncomeRequest;
import vn.com.msb.homeloan.api.dto.response.CRUBusinessIncomeResponse;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.BusinessIncome;
import vn.com.msb.homeloan.core.service.BusinessIncomeService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/business-incomes")
@RequiredArgsConstructor
public class BusinessIncomeController {

  private final BusinessIncomeService businessIncomeService;

  @PostMapping
  public ResponseEntity<ApiResource> save(
      @Valid @RequestBody CRUBusinessIncomeRequest createBusinessIncomeRequest) {
    createBusinessIncomeRequest.validate();
    createBusinessIncomeRequest.setUuid(null);
    BusinessIncome model = businessIncomeService.save(
        BusinessIncomeMapper.INSTANCE.toModel(createBusinessIncomeRequest), ClientTypeEnum.LDP);
    CRUBusinessIncomeResponse data = BusinessIncomeMapper.INSTANCE.toDTO(model);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PutMapping
  public ResponseEntity<ApiResource> update(
      @Valid @RequestBody CRUBusinessIncomeRequest updateBusinessIncomeRequest) {
    updateBusinessIncomeRequest.validate();
    BusinessIncome model = businessIncomeService.save(
        BusinessIncomeMapper.INSTANCE.toModel(updateBusinessIncomeRequest), ClientTypeEnum.LDP);
    CRUBusinessIncomeResponse data = BusinessIncomeMapper.INSTANCE.toDTO(model);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<ApiResource> findById(@PathVariable String uuid) {
    BusinessIncome model = businessIncomeService.findByUuid(uuid);
    CRUBusinessIncomeResponse data = BusinessIncomeMapper.INSTANCE.toDTO(model);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    businessIncomeService.deleteByUuid(uuid, ClientTypeEnum.LDP);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanApplicationId}/list")
  public ResponseEntity<ApiResource> list(@PathVariable String loanApplicationId) {
    List<BusinessIncome> models = businessIncomeService.findByLoanApplicationId(loanApplicationId);
    List<CRUBusinessIncomeResponse> data = BusinessIncomeMapper.INSTANCE.toDTOs(models);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
