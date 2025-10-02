package vn.com.msb.homeloan.core.model;

import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.CriterionGroup1Enum;
import vn.com.msb.homeloan.core.constant.CriterionGroup2Enum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class ExceptionItem {

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

  Instant createdAt;

  Instant updatedAt;
}
