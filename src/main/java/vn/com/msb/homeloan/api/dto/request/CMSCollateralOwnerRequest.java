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
public class CMSCollateralOwnerRequest {

  String uuid;

  @NotBlank
  String loanId;

  @NotBlank
  String fullName;

  @NotBlank
  @Pattern(regexp = "^(MALE|FEMALE|OTHERS)$", message = "must match MALE|FEMALE|OTHERS")
  String gender;

  @NotBlank
  @Length(max = 12)
  @Pattern(regexp = "^[0-9]+$", message = "must match number pattern")
  String idNo;

  @NotBlank
  @CustomDateConstraint
  String issuedOn;

  @NotBlank
  String placeOfIssue;

  @NotBlank
  @CustomDateConstraint
  String birthday;

  @Pattern(regexp = "^[0-9]+$", message = "must match number pattern")
  @Length(max = 12)
  String oldIdNo;

  @Pattern(regexp = "^[0-9]+$", message = "must match number pattern")
  @Length(max = 12)
  String oldIdNo2;


  @Length(max = 256)
  String oldIdNo3;

  @NotBlank
  @Pattern(regexp = "((0)[3|5|7|8|9])+([0-9]{8})\\b", message = "must match phone pattern")
  String phone;

  @NotBlank
  @Pattern(regexp = "^(VIETNAM)$|^(OTHER)$", message = "must match VIETNAM or OTHER")
  String nationality;

  @Email
  @Length(max = 100)
  String email;

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
  @Pattern(regexp = "^(MARRIED|SINGLE|DIVORCE|WIDOW)$", message = "must match MARRIED|SINGLE|DIVORCE|WIDOW")
  String maritalStatus;

  @NotBlank
  @Pattern(regexp = "^(WIFE|HUSBAND|FATHER|FATHER_IN_LAW|MOTHER|MOTHER_IN_LAW|SIBLINGS|SIBLINGS_IN_LAW|SON|STEP_CHILD|HOUSEHOLD_MEM|CAPITAL_CONTRIBUTING_MEM|NEIGHTBOR|FRIEND|FELLOW_SHIP|OTHERS)$")
  String relationship;

  @NotBlank
  @Pattern(regexp = "^(POSTGRADUATE|UNIVERSITY|BACHELOR|INTERMEDIATE|FINISH_SECONDARY)$")
  String educationLevel;

  @NotBlank
  String contactProvince;

  @NotBlank
  String contactProvinceName;

  @NotBlank
  String contactDistrict;

  @NotBlank
  String contactDistrictName;

  @NotBlank
  String contactWard;

  @NotBlank
  String contactWardName;

  String contactAddress;
}
