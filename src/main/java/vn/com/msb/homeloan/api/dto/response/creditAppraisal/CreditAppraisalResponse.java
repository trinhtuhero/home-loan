package vn.com.msb.homeloan.api.dto.response.creditAppraisal;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.CreditEvaluationResultEnum;
import vn.com.msb.homeloan.core.constant.SignatureLevelEnum;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditAppraisalResponse {

  String uuid;

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

  // css_score
  Double cssScore;

  // css_grade
  String cssGrade;

  // scoring_date
  String scoringDate;

  // business_area
  String businessArea;

  // business_area_name
  String businessAreaName;

  // business_code
  String businessCode;

  // business_name
  String businessName;

  // sale_full_name
  String saleFullName;

  // sale_phone
  String salePhone;

  // manager_full_name
  String managerFullName;

  // manager_phone
  String managerPhone;

  // signature_level
  SignatureLevelEnum signatureLevel;

  // officer_code
  String officerCode;

  // list
  @Valid
  List<FieldSurveyItemResponse> fieldSurveyItems;

  // list
  @Valid
  List<CreditworthinessItemResponse> creditworthinessItemRequests;

  // list
  @Valid
  List<ExceptionItemResponse> exceptionItems;
}
