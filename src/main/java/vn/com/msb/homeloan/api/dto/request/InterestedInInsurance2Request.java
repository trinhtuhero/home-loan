package vn.com.msb.homeloan.api.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestedInInsurance2Request {

  @NotBlank
  @Pattern(regexp = "^(INTERESTED|NOT_INTERESTED)$", message = "must match pattern INTERESTED|NOT_INTERESTED")
  String status;

  @NotBlank
  String loanId;
}
