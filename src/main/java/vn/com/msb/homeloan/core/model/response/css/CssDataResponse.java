package vn.com.msb.homeloan.core.model.response.css;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CssDataResponse {

  @JsonProperty(value = "response")
  private CssItemResponse response;
  @JsonProperty(value = "RB")
  private CssRBResponse RB;
}
