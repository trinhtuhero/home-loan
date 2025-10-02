package vn.com.msb.homeloan.api.dto.request.advisecustomer;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.api.validation.CustomDateConstraint;

@Getter
@Setter
@ToString
public class LDPAdviseCustomerRequest {

  @NotBlank
  private String loanApplicationId;

  @NotBlank
  @CustomDateConstraint
  private String adviseDate;

  @NotNull
  @Pattern(regexp = "^(EARLY_POSSIBLE|FROM_8_TO_12|FROM_13_TO_16|FROM_16_TO_20)$", message = "must match EARLY_POSSIBLE|FROM_8_TO_12|FROM_13_TO_16|FROM_16_TO_20")
  private String adviseTimeFrame;

  private String content;
}
