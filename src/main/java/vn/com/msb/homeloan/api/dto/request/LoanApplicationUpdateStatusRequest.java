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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationUpdateStatusRequest {

  @NotBlank
  @Pattern(regexp = "^(SUBMIT_LOAN_REQUEST|ACCEPT_LOAN_REQUEST|SUBMIT_LOAN_APPLICATION|ACCEPT_LOAN_APPLICATION)$", message = "must match SUBMIT_LOAN_REQUEST|ACCEPT_LOAN_REQUEST|SUBMIT_LOAN_APPLICATION|ACCEPT_LOAN_APPLICATION")
  String status;

  String refEmail;
}
