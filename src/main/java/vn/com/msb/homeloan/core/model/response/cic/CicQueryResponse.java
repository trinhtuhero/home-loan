package vn.com.msb.homeloan.core.model.response.cic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CicQueryResponse {

  @JsonProperty("data")
  private CicQueryData data;

  @JsonProperty("status")
  private Integer status;
}
