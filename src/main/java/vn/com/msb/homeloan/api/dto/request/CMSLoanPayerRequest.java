package vn.com.msb.homeloan.api.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import vn.com.msb.homeloan.api.validation.CustomDateConstraint;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CMSLoanPayerRequest {

  String uuid;

  @NotBlank
  String loanId;

  @NotBlank
  @Pattern(regexp = "^(WIFE|HUSBAND|FATHER|FATHER_IN_LAW|MOTHER|MOTHER_IN_LAW|SIBLINGS|SIBLINGS_IN_LAW|SON|STEP_CHILD|HOUSEHOLD_MEM)$")
  String relationshipType;

  @NotBlank
  @Length(max = 150)
  String fullName;

  @NotBlank
  @Pattern(regexp = "^(MALE|FEMALE|OTHERS)$", message = "must match MALE|FEMALE|OTHERS")
  String gender;

  @NotBlank
  @Pattern(regexp = "^[0-9]+$", message = "must match number pattern")
  @Length(max = 12)
  String idNo;

  @NotBlank
  @CustomDateConstraint
  String issuedOn;

  @NotBlank
  String placeOfIssue;

  @Pattern(regexp = "^[0-9]+$", message = "must match number pattern")
  @Length(max = 12)
  String oldIdNo;

  @Pattern(regexp = "^[0-9]+$", message = "must match number pattern")
  @Length(max = 12)
  String oldIdNo2;


  @Length(max = 256)
  String oldIdNo3;

  @NotBlank(message = "Phone must not be null")
  @Pattern(regexp = "((0)[3|5|7|8|9])+([0-9]{8})\\b", message = "must match phone pattern")
  String phone;

  @Email
  String email;

  @NotBlank
  @Pattern(regexp = "^(MARRIED|SINGLE|DIVORCE|WIDOW)$", message = "must match MARRIED|SINGLE|DIVORCE|WIDOW")
  String maritalStatus;

  @NotBlank
  @CustomDateConstraint
  String birthday;

  String cifNo;

  @NotBlank
  @Pattern(regexp = "^(POSTGRADUATE|UNIVERSITY|BACHELOR|INTERMEDIATE|FINISH_SECONDARY)$", message = "must match POSTGRADUATE|UNIVERSITY|BACHELOR|INTERMEDIATE|FINISH_SECONDARY)")
  String education;

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
  String residenceProvince;

  @NotBlank
  String residenceProvinceName;

  @NotBlank
  String residenceDistrict;

  @NotBlank
  String residenceDistrictName;

  @NotBlank
  String residenceWard;

  @NotBlank
  String residenceWardName;

  String residenceAddress;

  @NotBlank
  @Pattern(regexp = "^(VIETNAM)$|^(OTHER)$", message = "must match VIETNAM or OTHER")
  String nationality;
}
