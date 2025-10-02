package vn.com.msb.homeloan.core.model.request.aml;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CheckAmlRequest {

  @JsonProperty("idPassport")
  private String idPassport;
}
