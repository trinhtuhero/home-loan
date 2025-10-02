package vn.com.msb.homeloan.core.model.response.cic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CIC code information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CicCodeResponse {

  @JsonProperty("data")
  private List<CicCodeDetail> data;

  @JsonProperty("status")
  private Integer status;
}
