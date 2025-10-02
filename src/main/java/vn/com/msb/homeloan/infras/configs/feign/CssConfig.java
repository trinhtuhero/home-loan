package vn.com.msb.homeloan.infras.configs.feign;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(value = "feign.css")
public class CssConfig {

  private String channel;
  private String userAuthen;
  private String password;
  private String specializedBank;
}
