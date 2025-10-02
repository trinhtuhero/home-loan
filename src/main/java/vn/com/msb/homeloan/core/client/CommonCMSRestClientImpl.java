package vn.com.msb.homeloan.core.client;

import java.io.IOException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import vn.com.msb.homeloan.api.dto.response.ApiInternalResponse;
import vn.com.msb.homeloan.core.util.RestTemplateUtil;

@RequiredArgsConstructor
@Service
public class CommonCMSRestClientImpl implements CommonCMSRestClient {

  private final RestTemplateUtil restTemplate;

  @Value("${feign.common.cms.url}")
  private String url;

  @Value("${feign.common.cms.author-info}")
  private String authorPath;

  @Override
  @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 100))
  public ResponseEntity<ApiInternalResponse> authorInfo(String authorization) throws IOException {
    final URI uri = UriComponentsBuilder.fromHttpUrl(url + authorPath)
        .build().toUri();
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, authorization);
    HttpEntity entity = new HttpEntity<>(headers);
    ResponseEntity<ApiInternalResponse> clientInfoResponse = restTemplate.exChange(uri,
        HttpMethod.GET, entity, ApiInternalResponse.class);
    return clientInfoResponse;
  }
}
