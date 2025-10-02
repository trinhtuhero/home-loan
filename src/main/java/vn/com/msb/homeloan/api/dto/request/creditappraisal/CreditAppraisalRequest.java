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
public class CreditAppraisalRequest {

  // loan_application_id
  @NotBlank
  String loanApplicationId;

  @NotBlank
  @Pattern(regexp = "^(PASS|NOT_PASS)$")
  String creditEvaluationResult;

  // before_open_limit_condition
  String beforeOpenLimitCondition;

  // before_disbursement_condition
  String beforeDisbursementCondition;

  // after_disbursement_condition
  String afterDisbursementCondition;

  // business_review
  String businessReview;

  // css_profile_id
  String cssProfileId;

  // business_area
  @NotBlank
  String businessArea;

  // business_area_name
  @NotBlank
  String businessAreaName;

  // business_code
  @NotBlank
  String businessCode;

  // business_name
  @NotBlank
  String businessName;

  // sale_full_name
  @NotBlank
  String saleFullName;

  // sale_phone
  @NotBlank
  String salePhone;

  // manager_full_name
  @NotBlank
  String managerFullName;

  // manager_phone
  @NotBlank
  String managerPhone;

  // signature_level
  // SignatureLevelEnum
  @Pattern(regexp = "^(GĐKD|GĐ_ĐVKD|GĐ_HUB|GĐ_VUNG|TN_TD)$")
  String signatureLevel;

  // officer_code
  @NotBlank
  String officerCode;
}
