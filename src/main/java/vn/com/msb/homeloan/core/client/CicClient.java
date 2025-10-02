package vn.com.msb.homeloan.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import vn.com.msb.homeloan.core.model.request.cic.CicCodeRequest;
import vn.com.msb.homeloan.core.model.request.cic.CicQueryRequest;
import vn.com.msb.homeloan.core.model.response.cic.CicCodeResponse;
import vn.com.msb.homeloan.core.model.response.cic.CicQueryResponse;
import vn.com.msb.homeloan.infras.configs.feign.EncoderFeignConfig;

@FeignClient(name = "${feign.cic.name}", url = "${feign.cic.url}", configuration = {
    EncoderFeignConfig.class})
public interface CicClient {

  @PostMapping("${feign.cic.api.search-code}")
  ResponseEntity<CicCodeResponse> searchCode(@RequestBody CicCodeRequest cicCodeRequest,
      @RequestHeader(value = "client_code") String clientCode);

  @PostMapping("${feign.cic.api.search}")
  ResponseEntity<CicQueryResponse> search(@RequestBody CicQueryRequest cicQueryRequest,
      @RequestHeader(value = "client_code") String clientCode);
}
