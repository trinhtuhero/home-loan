package vn.com.msb.homeloan.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import vn.com.msb.homeloan.api.dto.request.customer.FullCustomerInfo;
import vn.com.msb.homeloan.api.dto.request.customer.GetAccountInfoByCifResponse;
import vn.com.msb.homeloan.api.dto.response.ApiInternalResponse;
import vn.com.msb.homeloan.api.dto.response.customer.GetAccountInfoByCifRequest;
import vn.com.msb.homeloan.api.dto.response.customer.InquiryFullCustomerInfoRequest;

@FeignClient(value = "customer-gw", url = "${feign.customer.url}")
public interface CustomerClient {

  @PostMapping(value = "${feign.customer.getAccountInfoByCif}")
  ApiInternalResponse<GetAccountInfoByCifResponse> getAccountInfoByCif(
      @RequestHeader(value = "client_code", required = false) String clientCode,
      @RequestBody GetAccountInfoByCifRequest request);

  @PostMapping(value = "${feign.customer.getFullCustomerInfo}")
  ApiInternalResponse<FullCustomerInfo> getFullCustomerInfo(
      @RequestHeader(value = "client_code", required = false) String clientCode,
      @RequestBody InquiryFullCustomerInfoRequest request);

}
