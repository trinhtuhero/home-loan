package vn.com.msb.homeloan.api.dto.response.creditAppraisal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.CreditEvaluationResultEnum;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditRatioResponse {

  // loan_application_id
  String loanApplicationId;

  // total_of_debits
  Long totalOfDebits;

  // total_income
  Long totalIncome;

  // dti
  Double dti;

  // ltv
  Double ltv;

  // credit_evaluation_result
  CreditEvaluationResultEnum creditEvaluationResult;
}
