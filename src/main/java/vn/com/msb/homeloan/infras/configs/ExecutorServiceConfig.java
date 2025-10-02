package vn.com.msb.homeloan.infras.configs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorServiceConfig {

  @Bean("fixedThreadPool")
  public ExecutorService fixedThreadPool() {
    return Executors.newFixedThreadPool(30);
  }
}
