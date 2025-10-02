package vn.com.msb.homeloan.infras.configs.feign;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@Configuration
public class BasicAuthConfiguration {

  @Autowired
  private EnvironmentProperties environmentProperties;

  @Bean
  public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
    return new BasicAuthRequestInterceptor(environmentProperties.getHrisUserName(),
        environmentProperties.getHrisPassword());
  }

}
