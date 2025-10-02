package vn.com.msb.homeloan.api.dto.request.creditappraisal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.api.validation.CustomDateConstraint;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldSurveyItemRequest {

  String uuid;

  @NotBlank
  @Pattern(regexp = "^(ME|MARRIED_PERSON|PAYER_PERSON|PROPERTY_OWNER)$")
  String relationshipType;

  @NotBlank
  @CustomDateConstraint
  String time;

  @NotBlank
  @Pattern(regexp = "^(NOI_CU_TRU|NOI_LAM_VIEC|ĐIA_DIEM_KINH_DOANH|TAI_SAN_CHO_THUE)$")
  String locationType;

  @NotBlank
  String fieldGuidePerson;

  @NotBlank
  String province;

  @NotBlank
  String provinceName;

  @NotBlank
  String district;

  @NotBlank
  String districtName;

  @NotBlank
  String ward;

  @NotBlank
  String wardName;

  String address;

  @NotBlank
  @Pattern(regexp = "^(PASS|NOT_PASS)$")
  String evaluationResult;

}
