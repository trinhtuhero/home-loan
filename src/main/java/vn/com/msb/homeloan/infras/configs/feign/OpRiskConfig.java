package vn.com.msb.homeloan.infras.configs.feign;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(value = "feign.op-risk")
public class OpRiskConfig {

  private String reqId;
  private String reqApp;
  private String authorizer;
  private String password;
  private String srvCheckP;
  private String srvCheckC;
}
