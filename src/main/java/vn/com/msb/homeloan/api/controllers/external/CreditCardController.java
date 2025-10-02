package vn.com.msb.homeloan.api.controllers.external;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CreditCardRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CreditCardResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CRUCreditCardRequest;
import vn.com.msb.homeloan.api.dto.response.CRUCreditCardResponse;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.CreditCard;
import vn.com.msb.homeloan.core.service.CreditCardService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/credit-card")
@RequiredArgsConstructor
public class CreditCardController {

  private final CreditCardService creditCardService;

  @PostMapping
  public ResponseEntity<ApiResource> save(
      @Valid @RequestBody CRUCreditCardRequest cruCreditCardRequest) {
    cruCreditCardRequest.setUuid(null);
    CreditCard model = creditCardService.save(
        CreditCardRequestMapper.INSTANCE.toModel(cruCreditCardRequest), ClientTypeEnum.LDP);
    CRUCreditCardResponse data = CreditCardResponseMapper.INSTANCE.toDTO(model);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PutMapping
  public ResponseEntity<ApiResource> update(
      @Valid @RequestBody CRUCreditCardRequest cruCreditCardRequest) {
    CreditCard model = creditCardService.save(
        CreditCardRequestMapper.INSTANCE.toModel(cruCreditCardRequest), ClientTypeEnum.LDP);
    CRUCreditCardResponse data = CreditCardResponseMapper.INSTANCE.toDTO(model);
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    creditCardService.deleteByUuid(uuid, ClientTypeEnum.LDP);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
