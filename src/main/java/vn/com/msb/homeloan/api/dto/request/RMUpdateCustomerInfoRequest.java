package vn.com.msb.homeloan.api.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RMUpdateCustomerInfoRequest {

  String uuid;

  @NotBlank
  @Length(max = 150)
  String fullName;

  @NotBlank
  @CustomDateConstraint
  String birthday;

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
  String phone;

  @Length(max = 12)
  @Pattern(regexp = "^[0-9]+$", message = "must match number pattern")
  String oldIdNo;

  @Length(max = 12)
  @Pattern(regexp = "^[0-9]+$", message = "must match number pattern")
  String oldIdNo2;

  @Length(max = 256)
  String oldIdNo3;

  @NotBlank
  @Email
  @Length(max = 100)
  String email;

  @NotBlank
  @Pattern(regexp = "^(MARRIED|SINGLE|DIVORCE|WIDOW)$", message = "must match MARRIED|SINGLE|DIVORCE|WIDOW")
  String maritalStatus;

  @NotNull
  @Max(10)
  @Min(0)
  Integer numberOfDependents;

  @NotBlank
  String province;

  @NotBlank
  String nationality;

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

  @Length(max = 50)
  String refCode;

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

  String partnerCode;

  @NotBlank
  @Pattern(regexp = "^(YP|HP|SBO|BO|M_FIRST|OTHER)$", message = "must match YP|HP|SBO|BO|M_FIRST|OTHER)")
  String customerSegment;
}
