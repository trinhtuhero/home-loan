package vn.com.msb.homeloan.infras.configs;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import lombok.AllArgsConstructor;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.zalando.logbook.httpclient.LogbookHttpRequestInterceptor;
import org.zalando.logbook.httpclient.LogbookHttpResponseInterceptor;


@Configuration
@AllArgsConstructor
public class DefaultRestTemplateConfig {

  private final LogbookHttpRequestInterceptor logbookHttpRequestInterceptor;
  private final LogbookHttpResponseInterceptor logbookHttpResponseInterceptor;

  @Bean
  @ConfigurationProperties(prefix = "rest.default.connection")
  public HttpComponentsClientHttpRequestFactory defaultHttpRequestFactory(
      PoolingHttpClientConnectionManager defaultHttpPoolingConnectionManager) {
    CloseableHttpClient closeableHttpClient = HttpClients.custom()
        .setConnectionManager(defaultHttpPoolingConnectionManager)
        .setConnectionManagerShared(true)
        .addInterceptorFirst(logbookHttpRequestInterceptor)
        .addInterceptorFirst(logbookHttpResponseInterceptor)
        .build();
    return new HttpComponentsClientHttpRequestFactory(closeableHttpClient);
  }

  @Bean
  public RestTemplate defaultRestTemplate(
      HttpComponentsClientHttpRequestFactory defaultHttpRequestFactory,
      ObjectMapper objectMapper) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
      @Override
      public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatus.Series series = response.getStatusCode().series();
        return HttpStatus.Series.CLIENT_ERROR.equals(series) || HttpStatus.Series.SERVER_ERROR
            .equals(series);
      }
    });
    restTemplate.setRequestFactory(defaultHttpRequestFactory);

    restTemplate.getInterceptors().add(new RequestResponseLoggingInterceptor());

    restTemplate.getMessageConverters().stream()
        .filter(c -> c instanceof MappingJackson2HttpMessageConverter)
        .findAny().map(MappingJackson2HttpMessageConverter.class::cast)
        .ifPresent(c -> c.setObjectMapper(objectMapper));
    return restTemplate;
  }

  @Bean
  @ConfigurationProperties(prefix = "rest.default.connection-pool")
  public PoolingHttpClientConnectionManager defaultHttpPoolingConnectionManager()
      throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    return new PoolingHttpClientConnectionManager(getSocketFactoryRegistry());
  }

  public Registry<ConnectionSocketFactory> getSocketFactoryRegistry()
      throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
    SSLContextBuilder builder = SSLContexts.custom();
    builder.loadTrustMaterial(null, (chain, authType) -> true);
    SSLContext sslContext = builder.build();
    SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
        (s, sslSession) -> s.equalsIgnoreCase(sslSession.getPeerHost()));
    return RegistryBuilder
        .<ConnectionSocketFactory>create()
        .register("https", sslSocketFactory)
        .register("http", new PlainConnectionSocketFactory())
        .build();
  }
}