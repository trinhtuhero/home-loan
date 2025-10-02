package vn.com.msb.homeloan.core.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
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
public class OpsRiskCheckCRequest {

  @JsonProperty(value = "checkBlackListC")
  private OpsRiskCheckC opsRiskCheckC;

  @Data
  public static class OpsRiskCheckC {

    private Set<String> identifiesInfoSet;

    @JsonProperty(value = "authenInfo")
    private OpRiskAuthInfo authInfo;

    @JsonProperty(value = "collateralType")
    private String collateralType;

    @JsonProperty(value = "identifiesInfo")
    private String identifiesInfo;
  }
}
