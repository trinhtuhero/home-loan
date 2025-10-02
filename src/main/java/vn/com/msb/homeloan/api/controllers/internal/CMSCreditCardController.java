package vn.com.msb.homeloan.api.controllers.internal;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CMSCreditCardRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSCreditCardResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CMSCreditCardRequest;
import vn.com.msb.homeloan.api.dto.response.CMSCreditCardResponse;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.CreditCard;
import vn.com.msb.homeloan.core.service.CreditCardService;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/v1/cms/credit-card")
@RequiredArgsConstructor
public class CMSCreditCardController {

  private final CreditCardService creditCardService;

  @PostMapping
  public ResponseEntity<ApiResource> save(
      @Valid @RequestBody CMSCreditCardRequest cmsCreditCardRequest) {
    cmsCreditCardRequest.setUuid(null);
    CreditCard model = creditCardService.save(
        CMSCreditCardRequestMapper.INSTANCE.toModel(cmsCreditCardRequest), ClientTypeEnum.CMS);
    CMSCreditCardResponse data = CMSCreditCardResponseMapper.INSTANCE.toCmsResponse(model);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PutMapping
  public ResponseEntity<ApiResource> update(
      @Valid @RequestBody CMSCreditCardRequest cmsCreditCardRequest) {
    CreditCard model = creditCardService.save(
        CMSCreditCardRequestMapper.INSTANCE.toModel(cmsCreditCardRequest), ClientTypeEnum.CMS);
    CMSCreditCardResponse data = CMSCreditCardResponseMapper.INSTANCE.toCmsResponse(model);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    creditCardService.deleteByUuid(uuid, ClientTypeEnum.CMS);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/validate/{loanId}")
  public ResponseEntity<ApiResource> validateById(@PathVariable("loanId") String loanId) {
    String message = creditCardService.validateCreditCard(loanId);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", message);
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}