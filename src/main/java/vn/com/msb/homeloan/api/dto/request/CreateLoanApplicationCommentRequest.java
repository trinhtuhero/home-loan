package vn.com.msb.homeloan.api.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
public class CreateLoanApplicationCommentRequest {

  @NotBlank
  String loanApplicationId;

  @NotBlank
  @Pattern(regexp = "^(CUSTOMER_INFO|MARRIED_INFO|CONTACT_INFO|PAYER_INFO|SALARY_INCOME_INFO|BUSINESS_INCOME_INFO|OTHER_INCOME_INFO|COLLATERAL_INFO|LOAN_INFO)$", message = "must match CUSTOMER_INFO|MARRIED_INFO|CONTACT_INFO|PAYER_INFO|SALARY_INCOME_INFO|BUSINESS_INCOME_INFO|OTHER_INCOME_INFO|COLLATERAL_INFO|LOAN_INFO")
  String code;

  @NotEmpty
  String comment;
}
