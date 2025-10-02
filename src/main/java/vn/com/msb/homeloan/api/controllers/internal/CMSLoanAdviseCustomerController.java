package vn.com.msb.homeloan.api.controllers.internal;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.LDPAdviseCustomerResponseMapper;
import vn.com.msb.homeloan.api.dto.request.LoanAdviseStatusRequest;
import vn.com.msb.homeloan.api.dto.response.advisecustomer.LDPAdviseCustomerResponse;
import vn.com.msb.homeloan.core.model.LoanAdviseCustomer;
import vn.com.msb.homeloan.core.service.LoanAdviseCustomerService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/loan-advise-customer")
@RequiredArgsConstructor
public class CMSLoanAdviseCustomerController {

  private final LoanAdviseCustomerService loanAdviseCustomerService;

  @GetMapping("/{loanId}")
  public ResponseEntity<Object> getAdvise(@PathVariable("loanId") String loanId) {
    LoanAdviseCustomer customer = loanAdviseCustomerService.get(loanId);

    LDPAdviseCustomerResponse response = LDPAdviseCustomerResponseMapper.INSTANCE.toResponse(
        customer);

    return ResponseEntity.ok(new ApiResource(response, HttpStatus.OK.value()));
  }

  @PatchMapping("/{loanId}/update-status")
  public ResponseEntity<Object> updateAdvise(@PathVariable("loanId") String loanId,
      @RequestBody @Valid LoanAdviseStatusRequest request) {
    LoanAdviseCustomer customer = loanAdviseCustomerService.changeStatus(loanId,
        request.getCurrentStatus());

    LDPAdviseCustomerResponse response = LDPAdviseCustomerResponseMapper.INSTANCE.toResponse(
        customer);

    return ResponseEntity.ok(new ApiResource(response, HttpStatus.OK.value()));
  }
}
