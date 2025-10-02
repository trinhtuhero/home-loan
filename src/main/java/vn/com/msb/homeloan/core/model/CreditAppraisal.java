package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.CreditEvaluationResultEnum;
import vn.com.msb.homeloan.core.constant.SignatureLevelEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class CreditAppraisal {

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
  @JsonFormat(pattern = "yyyyMMdd")
  Date scoringDate;

  // business_area
  String businessArea;

  // business_area_name
  String businessAreaName;

  // business_name
  String businessName;

  // business_code
  String businessCode;

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

  Instant createdAt;

  Instant updatedAt;

  List<FieldSurveyItem> fieldSurveyItems;

  List<CreditworthinessItem> creditworthinessItems;

  List<ExceptionItem> exceptionItems;
}
