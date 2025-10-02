package vn.com.msb.homeloan.core.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class OpRiskAuthInfo {

  @JsonProperty(value = "req_id")
  private String reqId;
  @JsonProperty(value = "req_time")
  private String reqTime;
  @JsonProperty(value = "req_app")
  private String reqApp;
  @JsonProperty(value = "authorizer")
  private String authorizer;
  @JsonProperty(value = "password")
  private String password;
  @JsonProperty(value = "srv")
  private String srv;
}
