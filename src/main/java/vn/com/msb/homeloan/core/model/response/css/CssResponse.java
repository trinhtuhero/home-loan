package vn.com.msb.homeloan.core.model.response.css;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CssResponse {

  @JsonProperty(value = "data")
  private CssDataResponse data;

  @JsonProperty(value = "status")
  private Integer status;
}
