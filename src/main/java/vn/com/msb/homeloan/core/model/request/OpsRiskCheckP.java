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
public class OpsRiskCheckP {

  private Set<String> identityCards;

  @JsonProperty(value = "authenInfo")
  private OpRiskAuthInfo authInfo;

  @JsonProperty(value = "identityCard")
  private String identityCard;

  private String identityCardOther;

//  @JsonProperty(value = "name")
//  private String name;

//  @JsonProperty(value = "birthday")
//  private String birthday;

  @JsonProperty(value = "endDate")
  private String endDate;
}
