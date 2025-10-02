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
public class CMSMarriedPersonRequest {

  String uuid;

  @NotBlank
  String loanId;

  @NotBlank
  String fullName;

  @NotBlank
  @Pattern(regexp = "^(MALE|FEMALE|OTHERS)$", message = "must match MALE|FEMALE|OTHERS")
  String gender;

  @NotBlank
  @Pattern(regexp = "^(VIETNAM)$|^(OTHER)$", message = "must match VIETNAM or OTHER")
  String nationality;

  @NotBlank
  @Length(max = 12)
  @Pattern(regexp = "^[0-9]+$", message = "must match number pattern")
  String idNo;

  @NotBlank
  @CustomDateConstraint
  String birthday;

  @NotBlank
  @CustomDateConstraint
  String issuedOn;

  @NotBlank
  String placeOfIssue;

  @Length(max = 12)
  @Pattern(regexp = "^[0-9]+$", message = "must match number pattern")
  String oldIdNo;

  @Length(max = 12)
  @Pattern(regexp = "^[0-9]+$", message = "must match number pattern")
  String oldIdNo2;

  @Length(max = 256)
  String oldIdNo3;

  @Pattern(regexp = "((0)[3|5|7|8|9])+([0-9]{8})\\b", message = "must match phone pattern")
  String phone;

  @Email
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

  String cifNo;

  @NotBlank
  @Pattern(regexp = "^(POSTGRADUATE|UNIVERSITY|BACHELOR|INTERMEDIATE|FINISH_SECONDARY)$", message = "must match POSTGRADUATE|UNIVERSITY|BACHELOR|INTERMEDIATE|FINISH_SECONDARY)")
  String education;
}
