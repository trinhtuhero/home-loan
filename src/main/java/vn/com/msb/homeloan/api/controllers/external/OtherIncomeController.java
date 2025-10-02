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
import vn.com.msb.homeloan.api.dto.mapper.OtherIncomeMapper;
import vn.com.msb.homeloan.api.dto.request.CRUOtherIncomeRequest;
import vn.com.msb.homeloan.api.dto.response.CRUOtherIncomeResponse;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.OtherIncome;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.OtherIncomeService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/other-incomes")
@RequiredArgsConstructor
public class OtherIncomeController {

  private final OtherIncomeService otherIncomeService;
  private final LoanApplicationRepository loanApplicationRepository;
  private final LoanApplicationService loanApplicationService;

  @PostMapping
  public ResponseEntity<ApiResource> save(
      @Valid @RequestBody CRUOtherIncomeRequest createOtherIncomeRequest) {
    createOtherIncomeRequest.validate();
    createOtherIncomeRequest.setUuid(null);
    OtherIncome model = otherIncomeService.save(
        OtherIncomeMapper.INSTANCE.toModel(createOtherIncomeRequest), ClientTypeEnum.LDP);
    CRUOtherIncomeResponse data = OtherIncomeMapper.INSTANCE.toDTO(model);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PutMapping
  public ResponseEntity<ApiResource> update(
      @Valid @RequestBody CRUOtherIncomeRequest updateOtherIncomeRequest) {
    updateOtherIncomeRequest.validate();
    OtherIncome model = otherIncomeService.save(
        OtherIncomeMapper.INSTANCE.toModel(updateOtherIncomeRequest), ClientTypeEnum.LDP);
    CRUOtherIncomeResponse data = OtherIncomeMapper.INSTANCE.toDTO(model);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<ApiResource> findById(@PathVariable String uuid) {
    OtherIncome model = otherIncomeService.findByUuid(uuid);
    CRUOtherIncomeResponse data = OtherIncomeMapper.INSTANCE.toDTO(model);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    otherIncomeService.deleteByUuid(uuid, ClientTypeEnum.LDP);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanApplicationId}/list")
  public ResponseEntity<ApiResource> list(@PathVariable String loanApplicationId) {
    List<OtherIncome> models = otherIncomeService.findByLoanApplicationId(loanApplicationId);
    List<CRUOtherIncomeResponse> data = OtherIncomeMapper.INSTANCE.toDTOs(models);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}

