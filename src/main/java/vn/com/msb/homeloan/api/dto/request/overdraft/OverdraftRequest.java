package vn.com.msb.homeloan.api.dto.request.overdraft;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OverdraftRequest {

  String uuid;

  // loan_application_id
  @NotBlank
  String loanApplicationId;

  // overdraft_purpose
  @NotNull
  @Pattern(regexp = "^(VAY_TIEU_DUNG)$", message = "must match pattern VAY_TIEU_DUNG")
  String overdraftPurpose;

  // loan_amount
  @NotNull
  @Min(1)
  Long loanAmount;

  // overdraft_subject
  @NotNull
  @Pattern(regexp = "^(CUSTOMER)$", message = "must match pattern CUSTOMER")
  String overdraftSubject;
}
