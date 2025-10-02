package vn.com.msb.homeloan.api.controllers.integration;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.request.InterestedInInsuranceRequest;
import vn.com.msb.homeloan.core.constant.InterestedEnum;
import vn.com.msb.homeloan.core.service.LoanApplicationService;

@Slf4j
@RestController
@RequestMapping("/api/v1/integration/loan-application")
@RequiredArgsConstructor
public class ILoanApplicationController {

  private final LoanApplicationService loanApplicationService;

  @PostMapping("/interested-insurance")
  public ResponseEntity<ApiResource> interestedInInsurance(
      @Valid @RequestBody InterestedInInsuranceRequest request) {
    loanApplicationService.interestedInInsurance(request.getLoanCode(),
        InterestedEnum.valueOf(request.getStatus()));
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
