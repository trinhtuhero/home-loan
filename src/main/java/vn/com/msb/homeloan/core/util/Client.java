package vn.com.msb.homeloan.core.util;

import java.net.URI;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Client {

  private final RestTemplate restTemplate;

  public Client() {
    restTemplate = new RestTemplate(new CustomHttpComponentsClientHttpRequestFactory());
  }

  public <T> ResponseEntity<T> getWithBody(String url, @Nullable HttpEntity<?> requestEntity,
      Class<T> responseClass) {
    return restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseClass);
  }

  private static final class CustomHttpComponentsClientHttpRequestFactory extends
      HttpComponentsClientHttpRequestFactory {

    @Override
    protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {

      if (HttpMethod.GET.equals(httpMethod)) {
        return new HttpEntityEnclosingGetRequestBase(uri);
      }
      return super.createHttpUriRequest(httpMethod, uri);
    }
  }

  private static final class HttpEntityEnclosingGetRequestBase extends
      HttpEntityEnclosingRequestBase {

    public HttpEntityEnclosingGetRequestBase(final URI uri) {
      super.setURI(uri);
    }

    @Override
    public String getMethod() {
      return HttpMethod.GET.name();
    }
  }
}
