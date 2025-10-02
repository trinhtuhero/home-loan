package vn.com.msb.homeloan.core.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.CreditEvaluationResultEnum;
import vn.com.msb.homeloan.core.constant.SignatureLevelEnum;

@Data
@Entity
@Table(name = "credit_appraisal", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditAppraisalEntity extends BaseEntity {

  // loan_application_id
  @Column(name = "loan_application_id")
  String loanApplicationId;

  // total_of_debits
  @Column(name = "total_of_debits")
  Long totalOfDebits;

  // total_income
  @Column(name = "total_income")
  Long totalIncome;

  // dti
  @Column(name = "dti")
  Double dti;

  // ltv
  @Column(name = "ltv")
  Double ltv;

  // credit_evaluation_result
  @Column(name = "credit_evaluation_result")
  @Enumerated(EnumType.STRING)
  CreditEvaluationResultEnum creditEvaluationResult;

  // before_open_limit_condition
  @Column(name = "before_open_limit_condition")
  String beforeOpenLimitCondition;

  // before_disbursement_condition
  @Column(name = "before_disbursement_condition")
  String beforeDisbursementCondition;

  // after_disbursement_condition
  @Column(name = "after_disbursement_condition")
  String afterDisbursementCondition;

  // business_review
  @Column(name = "business_review")
  String businessReview;

  // css_profile_id
  @Column(name = "css_profile_id")
  String cssProfileId;

  // css_score
  @Column(name = "css_score")
  Double cssScore;

  // css_grade
  @Column(name = "css_grade")
  String cssGrade;

  // scoring_date
  @Column(name = "scoring_date")
  Date scoringDate;

  // business_area
  @Column(name = "business_area")
  String businessArea;

  @Column(name = "business_area_name")
  String businessAreaName;

  // business_code
  @Column(name = "business_code")
  String businessCode;

  // business_name
  @Column(name = "business_name")
  String businessName;

  // sale_full_name
  @Column(name = "sale_full_name")
  String saleFullName;

  // sale_phone
  @Column(name = "sale_phone")
  String salePhone;

  // manager_full_name
  @Column(name = "manager_full_name")
  String managerFullName;

  // manager_phone
  @Column(name = "manager_phone")
  String managerPhone;

  // signature_level
  @Column(name = "signature_level")
  @Enumerated(EnumType.STRING)
  SignatureLevelEnum signatureLevel;

  // officer_code(Mã officer code)
  @Column(name = "officer_code")
  String officerCode;
}
