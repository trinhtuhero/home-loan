package vn.com.msb.homeloan.core.entity;

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
import vn.com.msb.homeloan.core.constant.CriterionGroup1Enum;
import vn.com.msb.homeloan.core.constant.CriterionGroup2Enum;

@Data
@Entity
@Table(name = "exception_items", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExceptionItemEntity extends BaseEntity {

  // loan_application_id
  @Column(name = "loan_application_id")
  String loanApplicationId;

  // credit_appraisal_id
  @Column(name = "credit_appraisal_id")
  String creditAppraisalId;

  // criterion_group_1
  @Column(name = "criterion_group_1")
  @Enumerated(EnumType.STRING)
  CriterionGroup1Enum criterionGroup1;

  // criteria_group_2
  @Column(name = "criteria_group_2")
  @Enumerated(EnumType.STRING)
  CriterionGroup2Enum criteriaGroup2;

  // detail
  @Column(name = "detail")
  String detail;

  // regulation
  @Column(name = "regulation")
  String regulation;

  // recommendation
  @Column(name = "recommendation")
  String recommendation;
}
