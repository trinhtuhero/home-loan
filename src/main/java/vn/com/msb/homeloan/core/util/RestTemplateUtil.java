package vn.com.msb.homeloan.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateUtil {

  private static final Logger logger = LoggerFactory.getLogger(RestTemplateUtil.class);

  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;
  private final MessageUtil messageUtil;

  @Autowired
  public RestTemplateUtil(RestTemplate restTemplate, ObjectMapper objectMapper,
      MessageUtil messageUtil) {
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
    this.messageUtil = messageUtil;
  }

  public <T> ResponseEntity<T> exChange(URI url, HttpMethod method, HttpEntity<?> requestEntity,
      Class<T> responseType) throws IOException {
    Locale currentLocale = LocaleContextHolder.getLocale();
    try {
      ResponseEntity<T> responseEntity = restTemplate.exchange(url, method, requestEntity,
          responseType);
      logger.info("Client response http code: {}", responseEntity.getStatusCode().value());
      return responseEntity;
    } catch (HttpClientErrorException ex) {
      logger.info("Client response http code: {}", ex.getStatusCode().value());
      switch (ex.getStatusCode()) {
        default:
          logger.info("Response body as string: {}", ex.getResponseBodyAsString());
          return ResponseEntity.status(ex.getStatusCode())
              .body(objectMapper.readValue(ex.getResponseBodyAsString(), responseType));
      }
    } catch (ResourceAccessException ex) {
      //Connection timed out
      try {
        return restTemplate.exchange(url, method, requestEntity, responseType);
      } catch (ResourceAccessException ex1) {
        logger.info("Connection timeout");
        throw ex1;
      }
    }
  }

  public <T> ResponseEntity<T> exChange(URI url, HttpMethod method, HttpEntity<?> requestEntity,
      ParameterizedTypeReference<T> responseType) throws IOException {
    Locale currentLocale = LocaleContextHolder.getLocale();
    try {
      ResponseEntity<T> responseEntity = restTemplate.exchange(url, method, requestEntity,
          responseType);
      logger.info("Client response http code: {}", responseEntity.getStatusCode().value());
      return responseEntity;
    } catch (HttpClientErrorException ex) {
      logger.info("Client response http code: {}", ex.getStatusCode().value());
      switch (ex.getStatusCode()) {
        default:
          logger.info("Response body as string: {}", ex.getResponseBodyAsString());
          TypeReference<T> type = new TypeReference<T>() {
            public Type getType() {
              return responseType.getType();
            }
          };
          return ResponseEntity.status(ex.getStatusCode())
              .body(objectMapper.readValue(ex.getResponseBodyAsString(), type));
      }
    } catch (ResourceAccessException ex) {
      //Connection timed out
      try {
        return restTemplate.exchange(url, method, requestEntity, responseType);
      } catch (ResourceAccessException ex1) {
        logger.info("Connection timeout");
        throw ex1;
      }
    }
  }

  public final Supplier<MultiValueMap<String, String>> JSON_REQUEST = () -> {
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    return requestHeaders;
  };

  public HttpHeaders getHttpHeadersWithToken(String token) {
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    requestHeaders.setBearerAuth(token);
    return requestHeaders;
  }

  public HttpHeaders getHttpFormDataHeadersWithToken(String token) {
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
    requestHeaders.setBearerAuth(token);
    return requestHeaders;
  }

  public <T> T requestMsbService(String url, HttpMethod httpMethod, HttpEntity<?> entity,
      Class<T> responseClass) {
    URI endpoint = URI.create(url);
    ResponseEntity<T> response = null;
    try {
      response = this.exChange(endpoint, httpMethod, entity, responseClass);
    } catch (Exception e) {

    }
    return Objects.nonNull(response) ? response.getBody() : null;
  }

  public <T> ResponseEntity<T> requestDownloadFile(String url, HttpMethod httpMethod,
      HttpEntity<?> entity,
      Class<T> responseClass) {
    URI endpoint = URI.create(url);
    ResponseEntity<T> response = null;
    try {
      response = this.exChange(endpoint, httpMethod, entity, responseClass);
    } catch (Exception ex) {
      logger.error("Send request to server has error.", ex);
    }
    return Objects.nonNull(response) ? response : null;
  }
}
