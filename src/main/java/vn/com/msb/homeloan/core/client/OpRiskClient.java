package vn.com.msb.homeloan.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import vn.com.msb.homeloan.core.model.request.OpsRiskCheckCRequest;
import vn.com.msb.homeloan.core.model.request.OpsRiskCheckPRequest;
import vn.com.msb.homeloan.core.model.response.opRisk.OpsRiskCheckCResponse;
import vn.com.msb.homeloan.core.model.response.opRisk.OpsRiskCheckPResponse;
import vn.com.msb.homeloan.infras.configs.feign.EncoderFeignConfig;

@FeignClient(name = "${feign.op-risk.name}", url = "${feign.op-risk.url}", configuration = {
    EncoderFeignConfig.class})
public interface OpRiskClient {

  @PostMapping("${feign.op-risk.api.checkP}")
  ResponseEntity<OpsRiskCheckPResponse> checkP(@RequestBody OpsRiskCheckPRequest request,
      @RequestHeader(value = "client_code") String clientCode);

  @PostMapping("${feign.op-risk.api.checkC}")
  ResponseEntity<OpsRiskCheckCResponse> checkC(@RequestBody OpsRiskCheckCRequest request,
      @RequestHeader(value = "client_code") String clientCode);
}
