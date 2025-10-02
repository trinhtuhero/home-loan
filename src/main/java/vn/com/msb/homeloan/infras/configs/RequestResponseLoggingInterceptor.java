package vn.com.msb.homeloan.infras.configs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

@Slf4j
public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(
      RequestResponseLoggingInterceptor.class);

  private static final int BODY_MAX_LENGTH = 100000;

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] bytes,
      ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

    logRequest(request, bytes);
    long startTime = System.currentTimeMillis();
    ClientHttpResponse response = clientHttpRequestExecution.execute(request, bytes);
    logResponse(request, response, System.currentTimeMillis() - startTime);

    return response;
  }

  private void logRequest(HttpRequest request, byte[] bytes) throws IOException {
    int bodyLength = 0;
    String body = null;
    if (bytes != null) {
      bodyLength = bytes.length;
      if (bodyLength <= BODY_MAX_LENGTH) {
        body = new String(bytes, StandardCharsets.UTF_8);
      }
    }
    log.info(String.format("---> %s %s %s HTTP/1.1 (%s-byte body) ", request.getMethod().name(),
        request.getURI(), body, bodyLength));
  }

  private void logResponse(HttpRequest request, ClientHttpResponse response, long elapsedTime)
      throws IOException {
    int status = response.getStatusCode().value();
    log.info(String.format("<--- %s %s HTTP/1.1 %s (%sms) ", request.getMethod().name(),
        request.getURI(), status, elapsedTime));
  }
}
