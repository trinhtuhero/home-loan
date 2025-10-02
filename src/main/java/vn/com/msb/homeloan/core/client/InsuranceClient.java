package vn.com.msb.homeloan.core.client;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import vn.com.msb.homeloan.core.model.request.integration.cj4.CalculateInsuranceRequest;
import vn.com.msb.homeloan.core.model.request.integration.cj4.SendLeadInfoRequest;
import vn.com.msb.homeloan.core.model.request.integration.cj4.UpdateLeadRequest;
import vn.com.msb.homeloan.core.model.response.cj4.CJ4Response;
import vn.com.msb.homeloan.core.model.response.cj4.CalculateInsurance;
import vn.com.msb.homeloan.core.model.response.cj4.GetLifeInfo;
import vn.com.msb.homeloan.core.model.response.cj4.ProductInfo;
import vn.com.msb.homeloan.core.util.RestTemplateUtil;

@RequiredArgsConstructor
@Service
public class InsuranceClient {

  private final RestTemplateUtil restTemplate;

  @Value("${feign.cj4.url}")
  private String baseUrl;

  @Value("${feign.cj4.path.send-lead}")
  private String pathSendLead;

  @Value("${feign.cj4.path.get-link}")
  private String pathGetLink;

  @Value("${feign.cj4.path.update-lead}")
  private String pathUpdateLead;

  @Value("${feign.cj4.path.get-products}")
  private String pathGetProducts;

  @Value("${feign.cj4.path.calculate-insurance}")
  private String pathCalculateInsurance;

  @Value("${feign.cj4.path.get-life-info}")
  private String pathGetLifeInfo;

  @Value("${feign.cj4.key}")
  private String msbInsuranceKey;


  public ResponseEntity<CJ4Response> sendLead(SendLeadInfoRequest request) throws IOException {
    URI url = URI.create(String.format("%s%s", baseUrl, pathSendLead));

    ResponseEntity<CJ4Response> clientInfoResponse = restTemplate.exChange(url, HttpMethod.POST,
        getRequestHeaders(request), CJ4Response.class);
    return clientInfoResponse;
  }

  public ResponseEntity<CJ4Response<String>> getLink(String loanCode, String requestDate,
      String channel, String token) throws IOException {
    final URI url = UriComponentsBuilder.fromHttpUrl(String.format("%s%s", baseUrl, pathGetLink))
        .queryParam("loanId", loanCode)
        .queryParam("requestDate", requestDate)
        .queryParam("channel", channel)
        .queryParam("token", token)
        .build().toUri();

    ParameterizedTypeReference<CJ4Response<String>> parameterizedTypeReference
        = new ParameterizedTypeReference<CJ4Response<String>>() {
    };

    ResponseEntity<CJ4Response<String>> responseEntity = restTemplate.exChange(url, HttpMethod.GET,
        getRequestHeaders(null), parameterizedTypeReference);
    return responseEntity;
  }

  public ResponseEntity<CJ4Response> updateLead(UpdateLeadRequest request) throws IOException {
    URI url = URI.create(String.format("%s%s", baseUrl, pathUpdateLead));

    ResponseEntity<CJ4Response> clientInfoResponse = restTemplate.exChange(url, HttpMethod.PUT,
        getRequestHeaders(request), CJ4Response.class);
    return clientInfoResponse;
  }

  public ResponseEntity<CJ4Response<List<ProductInfo>>> getProducts(String loanCode)
      throws IOException {

    final URI url = UriComponentsBuilder.fromHttpUrl(
            String.format("%s%s", baseUrl, pathGetProducts))
        .queryParam("loanId", loanCode)
        .build().toUri();

    ParameterizedTypeReference<CJ4Response<List<ProductInfo>>> parameterizedTypeReference
        = new ParameterizedTypeReference<CJ4Response<List<ProductInfo>>>() {
    };

    ResponseEntity<CJ4Response<List<ProductInfo>>> responseEntity = restTemplate.exChange(url,
        HttpMethod.GET, getRequestHeaders(null), parameterizedTypeReference);
    return responseEntity;
  }

  public ResponseEntity<CJ4Response<CalculateInsurance>> calculateInsurance(
      CalculateInsuranceRequest request) throws IOException {

    URI url = URI.create(String.format("%s%s", baseUrl, pathCalculateInsurance));

    ParameterizedTypeReference<CJ4Response<CalculateInsurance>> parameterizedTypeReference
        = new ParameterizedTypeReference<CJ4Response<CalculateInsurance>>() {
    };

    ResponseEntity<CJ4Response<CalculateInsurance>> responseEntity = restTemplate.exChange(url,
        HttpMethod.POST, getRequestHeaders(request), parameterizedTypeReference);
    return responseEntity;
  }

  public ResponseEntity<CJ4Response<GetLifeInfo>> getLifeInfo(String loanCode, String requestDate,
      String channel, String token) throws IOException {

    final URI url = UriComponentsBuilder.fromHttpUrl(
            String.format("%s%s", baseUrl, pathGetLifeInfo))
        .queryParam("loanId", loanCode)
        .queryParam("channel", channel)
        .queryParam("requestDate", requestDate)
        .queryParam("token", token)
        .build().toUri();

    ParameterizedTypeReference<CJ4Response<GetLifeInfo>> parameterizedTypeReference
        = new ParameterizedTypeReference<CJ4Response<GetLifeInfo>>() {
    };

    ResponseEntity<CJ4Response<GetLifeInfo>> responseEntity = restTemplate.exChange(url,
        HttpMethod.GET, getRequestHeaders(null), parameterizedTypeReference);
    return responseEntity;
  }

  private HttpEntity getRequestHeaders(Object request) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("msb-insurance-key", msbInsuranceKey);
    HttpEntity entity = new HttpEntity<>(request, headers);
    return entity;
  }
}
