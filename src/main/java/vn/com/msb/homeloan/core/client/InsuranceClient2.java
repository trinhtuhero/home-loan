package vn.com.msb.homeloan.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.com.msb.homeloan.core.model.request.integration.cj4.SendLeadInfoRequest;
import vn.com.msb.homeloan.infras.configs.feign.EncoderFeignConfig;


@FeignClient(name = "insurance-feign-client", url = "${feign.cj4.url}", configuration = {
    EncoderFeignConfig.class})
public interface InsuranceClient2 {

  @PostMapping(value = "/rb-bancassurance-be/api/v1/internal/life/save-lead")
  ResponseEntity saveLead(@RequestBody SendLeadInfoRequest request);
}
