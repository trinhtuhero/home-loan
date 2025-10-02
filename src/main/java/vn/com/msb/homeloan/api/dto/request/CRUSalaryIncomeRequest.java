package vn.com.msb.homeloan.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.exception.ApplicationException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CRUSalaryIncomeRequest {

  @JsonProperty("uuid")
  String uuid;

  @JsonProperty("loan_application_id")
  @NotBlank
  String loanApplicationId;

  @JsonProperty("owner_type")
  @NotBlank
  @Pattern(regexp = "^(ME|MARRIED_PERSON|PAYER_PERSON)$", message = "must match pattern")
  String ownerType;

  @JsonProperty("payment_method")
  @Pattern(regexp = "^(MSB|OTHERS|CASH)$", message = "must match pattern")
  @NotBlank
  String paymentMethod;

  @JsonProperty("office_name")
  @NotBlank
  String officeName;

  @JsonProperty("office_phone")
  //@Pattern(regexp = "((0)[3|5|7|8|9])+([0-9]{8})\\b", message = "must match phone pattern")
  @Pattern(regexp = "^(^$|\\d+)$", message = "must match phone pattern")
  String officePhone;

  @JsonProperty("office_province")
  @NotBlank
  String officeProvince;

  @NotBlank
  String officeProvinceName;

  @JsonProperty("office_district")
  @NotBlank
  String officeDistrict;

  @NotBlank
  String officeDistrictName;

  @JsonProperty("office_ward")
  @NotBlank
  String officeWard;

  @NotBlank
  @JsonProperty("office_ward_name")
  String officeWardName;

  @JsonProperty("office_address")
  @NotBlank
  String officeAddress;

  @JsonProperty("office_title")
  @NotBlank
  String officeTitle;

  @JsonProperty("value")
  @NotBlank
  String value;

  String payerId;


  public void validate() {
    try {
      Long.parseLong(value);
    } catch (NumberFormatException ex) {
      throw new ApplicationException(ErrorEnum.VALUE_INVALID);
    }
  }
}
