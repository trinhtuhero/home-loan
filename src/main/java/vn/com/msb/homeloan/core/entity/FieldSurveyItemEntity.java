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
import vn.com.msb.homeloan.core.constant.FieldSurveyEvaluationResultEnum;
import vn.com.msb.homeloan.core.constant.FieldSurveyRelationshipTypeEnum;
import vn.com.msb.homeloan.core.constant.LocationTypeEnum;

@Data
@Entity
@Table(name = "field_survey_items", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldSurveyItemEntity extends BaseEntity {

  // loan_application_id
  @Column(name = "loan_application_id")
  String loanApplicationId;

  // credit_appraisal_id
  @Column(name = "credit_appraisal_id")
  String creditAppraisalId;

  // relationship_type
  @Column(name = "relationship_type")
  @Enumerated(EnumType.STRING)
  FieldSurveyRelationshipTypeEnum relationshipType;

  // _time
  @Column(name = "_time")
  Date time;

  // location_type
  @Column(name = "location_type")
  @Enumerated(EnumType.STRING)
  LocationTypeEnum locationType;

  // field_guide_person
  @Column(name = "field_guide_person")
  String fieldGuidePerson;

  // province
  @Column(name = "province")
  String province;

  // province_name
  @Column(name = "province_name")
  String provinceName;

  // district
  @Column(name = "district")
  String district;

  // district_name
  @Column(name = "district_name")
  String districtName;

  // ward
  @Column(name = "ward")
  String ward;

  // ward_name
  @Column(name = "ward_name")
  String wardName;

  // address
  @Column(name = "address")
  String address;

  // evaluation_result
  @Column(name = "evaluation_result")
  @Enumerated(EnumType.STRING)
  FieldSurveyEvaluationResultEnum evaluationResult;

}
