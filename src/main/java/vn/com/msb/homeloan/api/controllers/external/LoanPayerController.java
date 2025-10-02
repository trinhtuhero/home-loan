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
import vn.com.msb.homeloan.api.dto.mapper.LoanPayerRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.LoanPayerResponseMapper;
import vn.com.msb.homeloan.api.dto.response.LoanPayerRequest;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.LoanPayer;
import vn.com.msb.homeloan.core.service.LoanPayerService;
import vn.com.msb.homeloan.core.util.StringUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/loan-payer")
@RequiredArgsConstructor
public class LoanPayerController {

  private final LoanPayerService loanPayerService;

  @GetMapping("/{id}")
  public ResponseEntity<ApiResource> findById(@PathVariable String id) {
    LoanPayer loanPayer = loanPayerService.findById(id);
    ApiResource apiResource = new ApiResource(LoanPayerResponseMapper.INSTANCE.toDto(loanPayer),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanId}/list")
  public ResponseEntity<ApiResource> getByLoanId(@PathVariable String loanId) {
    List<LoanPayer> loanPayers = loanPayerService.findByLoanId(loanId);
    ApiResource apiResource = new ApiResource(LoanPayerResponseMapper.INSTANCE.toDtos(loanPayers),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping
  public ResponseEntity<ApiResource> insert(@Valid @RequestBody LoanPayerRequest request) {
    if (!StringUtils.isEmpty(request.getUuid())) {
      throw new ApplicationException(ErrorEnum.UUID_INVALID);
    }
    LoanPayerEntity loanPayerEntity = loanPayerService.save(
        LoanPayerRequestMapper.INSTANCE.toModel(request), ClientTypeEnum.LDP);
    ApiResource apiResource = new ApiResource(
        LoanPayerRequestMapper.INSTANCE.toResponse(loanPayerEntity), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PutMapping
  public ResponseEntity<ApiResource> update(@Valid @RequestBody LoanPayerRequest request) {
    if (StringUtils.isEmpty(request.getUuid())) {
      throw new ApplicationException(ErrorEnum.UUID_INVALID);
    }
    LoanPayerEntity loanPayerEntity = loanPayerService.save(
        LoanPayerRequestMapper.INSTANCE.toModel(request), ClientTypeEnum.LDP);
    ApiResource apiResource = new ApiResource(
        LoanPayerRequestMapper.INSTANCE.toResponse(loanPayerEntity), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    loanPayerService.deleteById(uuid, ClientTypeEnum.LDP);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
