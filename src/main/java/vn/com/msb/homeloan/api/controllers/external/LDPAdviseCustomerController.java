package vn.com.msb.homeloan.api.controllers.internal;

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
import vn.com.msb.homeloan.api.dto.mapper.LDPAdviseCustomerRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.LDPAdviseCustomerResponseMapper;
import vn.com.msb.homeloan.api.dto.request.advisecustomer.LDPAdviseCustomerRequest;
import vn.com.msb.homeloan.api.dto.response.advisecustomer.LDPAdviseCustomerResponse;
import vn.com.msb.homeloan.core.model.LoanAdviseCustomer;
import vn.com.msb.homeloan.core.service.LoanAdviseCustomerService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/advise-customer")
@RequiredArgsConstructor
public class LDPAdviseCustomerController {

  private final LoanAdviseCustomerService loanAdviseCustomerService;

  @PostMapping
  public ResponseEntity<ApiResource> save(@RequestBody @Valid LDPAdviseCustomerRequest request) {

    LoanAdviseCustomer loanAdviseCustomer = loanAdviseCustomerService.save(
        LDPAdviseCustomerRequestMapper.INSTANCE.toModel(request));

    LDPAdviseCustomerResponse response = LDPAdviseCustomerResponseMapper.INSTANCE.toResponse(
        loanAdviseCustomer);

    return ResponseEntity.ok(new ApiResource(response, HttpStatus.OK.value()));
  }
}
