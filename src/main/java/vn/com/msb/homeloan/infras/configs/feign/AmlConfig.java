package vn.com.msb.homeloan.infras.configs.feign;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class AmlConfig {

  @Value("${feign.aml.url}")
  private String baseUrl;

  @Value("${feign.aml.api.check}")
  private String apiCheck;
}
