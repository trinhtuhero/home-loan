package vn.com.msb.homeloan.infras.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(value = "msb.common.cms.role")
public class RoleConfig {

  private String admin;
  private String rm;
  private String bm;
}
