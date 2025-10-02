package vn.com.msb.homeloan.api.dto.request;

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
public class CMSOpRiskAuthInfoRequest {

  private String reqId;

  private String reqTime;

  private String reqApp;

  private String authorizer;

  private String password;

  private String srv;
}
