package vn.com.msb.homeloan.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.com.msb.homeloan.infras.configs.feign.EncoderFeignConfig;

@FeignClient(name = "${feign.notify.team.name}", url = "${feign.notify.team.webhook-url}", configuration = {
    EncoderFeignConfig.class})
public interface NotifyClient {

  @GetMapping
  ResponseEntity<String> send(@RequestBody String string);
}
