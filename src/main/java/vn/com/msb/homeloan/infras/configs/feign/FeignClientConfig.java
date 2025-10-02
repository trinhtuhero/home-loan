package vn.com.msb.homeloan.infras.configs.feign;

import okhttp3.OkHttpClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"vn.com.msb.homeloan"})
public class FeignClientConfig {

  @Bean
  public OkHttpClient.Builder okHttpClientBuilder() {
    return new OkHttpClient.Builder();
  }
}
