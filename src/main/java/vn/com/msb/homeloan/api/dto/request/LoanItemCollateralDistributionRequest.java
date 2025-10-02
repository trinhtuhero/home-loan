package vn.com.msb.homeloan.api.dto.request;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanItemCollateralDistributionRequest {

  @NotBlank
  String collateralId;

  @DecimalMin(value = "0.01")
  @DecimalMax(value = "100")
  BigDecimal percent;
}
