package vn.com.msb.homeloan.core.model.response.mvalue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPriceBracketResponse {
  @JsonProperty(value = "data")
  private ResponseGetPriceBracketInfo data;

  @JsonProperty(value = "status")
  private Integer status;
}
