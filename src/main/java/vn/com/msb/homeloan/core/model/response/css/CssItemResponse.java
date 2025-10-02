package vn.com.msb.homeloan.core.model.response.css;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Data;

@Data
public class CssItemResponse {

  @JsonProperty(value = "code")
  private String code;
  @JsonProperty(value = "scoringTime")
  private String scoringTime;
  @JsonProperty(value = "message")
  private String message;
  @JsonProperty(value = "timestamp")
  private Date timestamp;
}
