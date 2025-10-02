package vn.com.msb.homeloan.core.model.response.opRisk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RespMessage {

  @JsonProperty(value = "respCode")
  private String respCode;
  @JsonProperty(value = "respDesc")
  private String respDesc;
}
