package vn.com.msb.homeloan.api.dto.request.insurance;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CJ4CalculateInsuranceRequest {

  @NotBlank
  String loanId;

  @NotBlank
  String productId;
}
