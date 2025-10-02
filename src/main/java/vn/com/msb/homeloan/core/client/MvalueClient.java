package vn.com.msb.homeloan.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import vn.com.msb.homeloan.core.model.request.mvalue.CheckDuplicateRequest;
import vn.com.msb.homeloan.core.model.request.mvalue.CreateProfileRequest;
import vn.com.msb.homeloan.core.model.request.mvalue.GetCollateralByCodeRequest;
import vn.com.msb.homeloan.core.model.request.mvalue.GetPriceBracketInfoRequest;
import vn.com.msb.homeloan.core.model.response.mvalue.CheckDuplicateResponse;
import vn.com.msb.homeloan.core.model.response.mvalue.CreateProfileResponse;
import vn.com.msb.homeloan.core.model.response.mvalue.GetCollateralByCodeResponse;
import vn.com.msb.homeloan.core.model.response.mvalue.GetPriceBracketResponse;
import vn.com.msb.homeloan.infras.configs.feign.EncoderFeignConfig;

@FeignClient(
    name = "${feign.m-value.name}",
    url = "${feign.m-value.url}",
    configuration = {EncoderFeignConfig.class})
public interface MvalueClient {

  @PostMapping("${feign.m-value.api.get-by-code}")
  ResponseEntity<GetCollateralByCodeResponse> getCollateralByCode(
      @RequestBody GetCollateralByCodeRequest request,
      @RequestHeader(value = "client_code") String clientCode);

  @PostMapping("${feign.m-value.api.check-duplicate}")
  ResponseEntity<CheckDuplicateResponse> checkDuplicate(
      @RequestBody CheckDuplicateRequest request,
      @RequestHeader(value = "client_code") String clientCode);

  @PostMapping("${feign.m-value.api.create-profile}")
  ResponseEntity<CreateProfileResponse> createProfile(
      @RequestBody CreateProfileRequest request,
      @RequestHeader(value = "client_code") String clientCode);

  @PostMapping("${feign.m-value.api.get-price-bracket-info}")
  ResponseEntity<GetPriceBracketResponse> getPriceBracketInfo(
      @RequestBody GetPriceBracketInfoRequest request,
      @RequestHeader(value = "client_code") String clientCode);
}
