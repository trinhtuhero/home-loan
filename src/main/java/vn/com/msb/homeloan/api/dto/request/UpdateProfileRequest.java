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
public class UpdateProfileRequest {

  @NotBlank
  String uuid;

  @NotBlank
  @Length(max = 150)
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
  String issuedOn;

  @NotBlank
  String placeOfIssue;

  @Length(max = 12)
  @Pattern(regexp = "^[0-9]+$", message = "must match number pattern")
  String oldIdNo;

  @Length(max = 256)
  String oldIdNo3;

  @NotBlank(message = "Phone must not be null")
  @Pattern(regexp = "((0)[3|5|7|8|9])+([0-9]{8})\\b", message = "must match phone pattern")
  String phone;

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
  String provinceName;

  @NotBlank
  String district;

  @NotBlank
  String districtName;

  @NotBlank
  String ward;

  @NotBlank
  String wardName;

  @NotBlank
  String address;

  @NotBlank
  @CustomDateConstraint
  String birthday;
}
