package vn.com.msb.homeloan.core.model.response.mvalue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetCollateralByCodeResponse {

  @JsonProperty(value = "data")
  private GetCollateralByCodeData data;

  @JsonProperty(value = "status")
  private Integer status;
}
