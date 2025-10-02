package vn.com.msb.homeloan.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoanCalculationRequest {

  @NotNull(message = "loanAmount mustn't be null")
  @DecimalMin(value = "100000000", message = "Minimum loan amount is 100 million VND")
  @DecimalMax(value = "20000000000", message = "Maximum loan amount is 20 billion VND")
  private BigDecimal loanAmount;

  @Min(value = 3, message = "Minimum loanTime is 3 month")
  @Max(value = 300, message = "Maximum loanTime is 300 month")
  @NotNull
  private Integer loanTime;

  @JsonProperty("debt_method")
  @NotNull(message = "debtPaymentMethodKey mustn't be null")
  private String debtPaymentMethodKey;

  @NotNull(message = "interestRateKey mustn't be null")
  @JsonProperty("interest_rate")
  private String interestRateKey;
}
