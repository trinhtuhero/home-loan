package vn.com.msb.homeloan;

import com.zaxxer.hikari.HikariDataSource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import java.time.LocalDateTime;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import vn.com.msb.homeloan.infras.configs.MockSecretRetriever;
import vn.com.msb.homeloan.infras.configs.SecretRetriever;

@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
@OpenAPIDefinition(info = @Info(title = "Home loan API", version = "2.0", description = "Home loan Information"),
    security = {
        @SecurityRequirement(
            name = "accessToken"
        )
    })
@SecurityScheme(name = "accessToken", scheme = "bearer", type = SecuritySchemeType.HTTP)
public class MainApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

  @Override
  public void run(String... args) {
    log.info("HOMELOAN SERVICE STARTED AT: " + LocalDateTime.now());
  }

  @Bean
  ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  @Qualifier("mockSecretRetriever")
  public SecretRetriever getMockSecretRetriever() {
    return new MockSecretRetriever();
  }

  @Bean(name = "dataSourceProperties")
  @ConfigurationProperties("spring.datasource")
  public DataSourceProperties dataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "datasource")
  @Primary
  public DataSource dataSource(@Qualifier("dataSourceProperties") DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().type(HikariDataSource.class)
        .build();
  }
}
