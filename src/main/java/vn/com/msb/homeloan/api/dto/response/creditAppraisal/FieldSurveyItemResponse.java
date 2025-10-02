package vn.com.msb.homeloan.api.dto.response.creditAppraisal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.FieldSurveyEvaluationResultEnum;
import vn.com.msb.homeloan.core.constant.FieldSurveyRelationshipTypeEnum;
import vn.com.msb.homeloan.core.constant.LocationTypeEnum;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldSurveyItemResponse {

  String uuid;

  // loan_application_id
  String loanApplicationId;

  // credit_appraisal_id
  String creditAppraisalId;

  // relationship_type
  FieldSurveyRelationshipTypeEnum relationshipType;

  // _time
  String time;

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

}
