package vn.com.msb.homeloan.core.client;

import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import vn.com.msb.homeloan.core.model.request.CssRequest;
import vn.com.msb.homeloan.core.model.response.css.CssResponse;

@FeignClient(name = "${feign.css.name}", url = "${feign.css.url}")
public interface CssClient {

  @PostMapping()
  ResponseEntity<CssResponse> getScore(@RequestBody @Valid CssRequest cssRequest,
      @RequestHeader(value = "client_code") String clientCode);
}
