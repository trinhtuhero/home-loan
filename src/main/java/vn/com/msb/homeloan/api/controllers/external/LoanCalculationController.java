package vn.com.msb.homeloan.api.controllers.external;

import java.util.List;
import javax.script.ScriptException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.GetPriceBracketMapper;
import vn.com.msb.homeloan.api.dto.mapper.LoanCalculationRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.data.GetLifeInfoDataMapper;
import vn.com.msb.homeloan.api.dto.request.LoanCalculationRequest;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.model.LoanCalculation;
import vn.com.msb.homeloan.core.model.request.mvalue.GetPriceBracketInfoRequest;
import vn.com.msb.homeloan.core.model.response.mvalue.GetPriceBracketResponse;
import vn.com.msb.homeloan.core.service.LoanCalculationService;
import vn.com.msb.homeloan.core.service.MvalueService;
import vn.com.msb.homeloan.core.service.PropertyService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/loan-cal")
@RequiredArgsConstructor
public class LoanCalculationController {

  private final LoanCalculationService loanCalculationService;

  private final PropertyService propertyService;

  private final MvalueService mvalueService;

  @PostMapping
  public ResponseEntity<Object> calculateLoan(
      @RequestBody @Valid LoanCalculationRequest loanCalculationRequest) throws ScriptException {

    List<LoanCalculation> loanCalculations =
        loanCalculationService.calculateLoan(
            LoanCalculationRequestMapper.INSTANCE.toModel(loanCalculationRequest));
    return ResponseEntity.ok(new ApiResource(loanCalculations, HttpStatus.OK.value()));
  }

  @GetMapping("/config")
  public ResponseEntity<Object> getConfig() {
    String string = propertyService.getByName(Constants.LOAN_CALCULATION_CONFIG, String.class);
    return ResponseEntity.ok(string);
  }

  @PostMapping("/get-price-bracket-info")
  public ResponseEntity<ApiResource> getPriceBracketInfo(
      @RequestBody @Valid GetPriceBracketInfoRequest request) {

    GetPriceBracketResponse result = mvalueService.getPriceBracketInfo(request);
    ApiResource apiResource =
        new ApiResource(
            GetPriceBracketMapper.INSTANCE.toResponse(result).getData(), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
