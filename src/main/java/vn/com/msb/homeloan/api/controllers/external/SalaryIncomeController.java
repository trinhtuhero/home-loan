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
import vn.com.msb.homeloan.api.dto.mapper.SalaryIncomeMapper;
import vn.com.msb.homeloan.api.dto.request.CRUSalaryIncomeRequest;
import vn.com.msb.homeloan.api.dto.response.CRUSalaryIncomeResponse;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.SalaryIncome;
import vn.com.msb.homeloan.core.service.SalaryIncomeService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/salary-incomes")
@RequiredArgsConstructor
public class SalaryIncomeController {

  private final SalaryIncomeService salaryIncomeService;

  @PostMapping
  public ResponseEntity<ApiResource> save(
      @Valid @RequestBody CRUSalaryIncomeRequest createSalaryIncomeRequest) {
    createSalaryIncomeRequest.validate();
    createSalaryIncomeRequest.setUuid(null);
    SalaryIncome salaryIncome = salaryIncomeService.save(
        SalaryIncomeMapper.INSTANCE.toModel(createSalaryIncomeRequest), ClientTypeEnum.LDP);
    CRUSalaryIncomeResponse data = SalaryIncomeMapper.INSTANCE.toDTO(salaryIncome);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PutMapping
  public ResponseEntity<ApiResource> update(
      @Valid @RequestBody CRUSalaryIncomeRequest updateSalaryIncomeRequest) {
    updateSalaryIncomeRequest.validate();
    SalaryIncome salaryIncome = salaryIncomeService.save(
        SalaryIncomeMapper.INSTANCE.toModel(updateSalaryIncomeRequest), ClientTypeEnum.LDP);
    CRUSalaryIncomeResponse data = SalaryIncomeMapper.INSTANCE.toDTO(salaryIncome);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<ApiResource> findById(@PathVariable String uuid) {
    SalaryIncome model = salaryIncomeService.findByUuid(uuid);
    CRUSalaryIncomeResponse data = SalaryIncomeMapper.INSTANCE.toDTO(model);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    salaryIncomeService.deleteByUuid(uuid, ClientTypeEnum.LDP);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanApplicationId}/list")
  public ResponseEntity<ApiResource> list(@PathVariable String loanApplicationId) {
    List<SalaryIncome> models = salaryIncomeService.findByLoanApplicationId(loanApplicationId);
    List<CRUSalaryIncomeResponse> data = SalaryIncomeMapper.INSTANCE.toDTOs(models);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
