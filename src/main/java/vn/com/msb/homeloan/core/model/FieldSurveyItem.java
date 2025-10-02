package vn.com.msb.homeloan.core.model;

import java.time.Instant;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.FieldSurveyEvaluationResultEnum;
import vn.com.msb.homeloan.core.constant.FieldSurveyRelationshipTypeEnum;
import vn.com.msb.homeloan.core.constant.LocationTypeEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class FieldSurveyItem {

  String uuid;

  // loan_application_id
  String loanApplicationId;

  // credit_appraisal_id
  String creditAppraisalId;

  // relationship_type
  FieldSurveyRelationshipTypeEnum relationshipType;

  // _time
  Date time;

  // location_type
  LocationTypeEnum locationType;

  // field_guide_person
  String fieldGuidePerson;

  // province
  String province;

  // province_name
  String provinceName;

  // district
  String district;

  // district_name
  String districtName;

  // ward
  String ward;

  // ward_name
  String wardName;

  // address
  String address;

  // evaluation_result
  FieldSurveyEvaluationResultEnum evaluationResult;

  Instant createdAt;

  Instant updatedAt;
}
