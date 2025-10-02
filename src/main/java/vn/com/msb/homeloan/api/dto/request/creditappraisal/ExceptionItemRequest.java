package vn.com.msb.homeloan.api.dto.request.creditappraisal;

import javax.validation.constraints.NotBlank;
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
public class ExceptionItemRequest {

  String uuid;

  @NotBlank
  String loanApplicationId;

  String creditAppraisalId;

  // criterion_group_1
  // CriterionGroup1Enum
  @NotBlank
  @Pattern(regexp = "^(SPECIAL_RISK|HIGH_RISK|IMPORTANT_CHARACTERISTIC|UNIMPORTANT_CHARACTERISTIC|ORIENTATION_CRITERIA|DIFFERENCE_APPRAISAL_INSTRUCTION|EXCEEDING_POLICY)$")
  String criterionGroup1;

  // criteria_group_2
  // CriterionGroup2Enum
  @NotBlank
  @Pattern(regexp = "^(SPECIAL_RISK|HIGH_RISK|IMPORTANT_CHARACTERISTIC|UNIMPORTANT_CHARACTERISTIC|ORIENTATION_CRITERIA|DIFFERENCE_APPRAISAL_INSTRUCTION)$")
  String criteriaGroup2;

  // detail
  @NotBlank
  String detail;

  // regulation
  @NotBlank
  String regulation;

  // recommendation
  @NotBlank
  String recommendation;

}
