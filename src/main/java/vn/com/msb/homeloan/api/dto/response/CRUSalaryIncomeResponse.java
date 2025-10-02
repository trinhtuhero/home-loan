package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.constant.PaymentMethodEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CRUSalaryIncomeResponse {

  String uuid;

  String loanApplicationId;

  OwnerTypeEnum ownerType;

  PaymentMethodEnum paymentMethod;

  String officeName;

  String officePhone;

  String officeProvince;

  String officeProvinceName;

  String officeDistrict;

  String officeDistrictName;

  @JsonProperty("office_ward")
  String officeWard;

  @JsonProperty("office_ward_name")
  String officeWardName;

  @JsonProperty("office_address")
  String officeAddress;

  @JsonProperty("office_title")
  String officeTitle;

  @JsonProperty("value")
  String value;

  String payerId;
}
