package vn.com.msb.homeloan.api.dto.response.creditAppraisal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.CriterionGroup1Enum;
import vn.com.msb.homeloan.core.constant.CriterionGroup2Enum;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionItemResponse {

  String uuid;

  // loan_application_id
  String loanApplicationId;

  // credit_appraisal_id
  String creditAppraisalId;

  // criterion_group_1
  CriterionGroup1Enum criterionGroup1;

  // criteria_group_2
  CriterionGroup2Enum criteriaGroup2;

  // detail
  String detail;

  // regulation
  String regulation;

  // recommendation
  String recommendation;

}
