package vn.com.msb.homeloan.api.controllers.external;

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
import vn.com.msb.homeloan.api.dto.mapper.CustomerAdviseRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CustomerAdviseResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CustomerRegistryRequest;
import vn.com.msb.homeloan.api.dto.response.CustomerRegistryAdviseResponse;
import vn.com.msb.homeloan.core.constant.LoanPurposeEnum;
import vn.com.msb.homeloan.core.model.AdviseCustomer;
import vn.com.msb.homeloan.core.service.CustomerAdviseService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/advise-customer")
@RequiredArgsConstructor
public class AdviseCustomerController {

  private final CustomerAdviseService customerAdviseService;

  @PostMapping
  public ResponseEntity<ApiResource> save(@RequestBody @Valid CustomerRegistryRequest dto) {

    if ("BAT_DONG_SAN".equals(dto.getProduct())) {
      dto.setProduct(LoanPurposeEnum.LAND.getCode());
    }

    AdviseCustomer adviseCustomer = customerAdviseService.save(
        CustomerAdviseRequestMapper.INSTANCE.toModel(dto));

    CustomerRegistryAdviseResponse response = CustomerAdviseResponseMapper.INSTANCE.toResponse(
        adviseCustomer);
    return ResponseEntity.ok(new ApiResource(response, HttpStatus.OK.value()));
  }
}
