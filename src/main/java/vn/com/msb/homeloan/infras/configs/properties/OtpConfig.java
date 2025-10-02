package vn.com.msb.homeloan.infras.configs.properties;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(value = "msb.otp")
public class OtpConfig {

  private List<RetryConfig> retryConfigs;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class RetryConfig {

    private int count;
    private int blockTime;
    private Boolean isReset;
  }
}
