package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.BusinessLineEnum;
import vn.com.msb.homeloan.core.constant.BusinessTypeEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CRUBusinessIncomeResponse {

  String uuid;

  @JsonProperty("loan_application_id")
  String loanApplicationId;

  @JsonProperty("owner_type")
  OwnerTypeEnum ownerType;

  @JsonProperty("business_type")
  BusinessTypeEnum businessType;

  @JsonProperty("business_line")
  BusinessLineEnum businessLine;

  @JsonProperty("business_code")
  String businessCode;

  @JsonProperty("name")
  String name;

  @JsonProperty("province")
  String province;

  @JsonProperty("province_name")
  String provinceName;

  @JsonProperty("district")
  String district;

  @JsonProperty("district_name")
  String districtName;

  @JsonProperty("ward")
  String ward;

  @JsonProperty("ward_name")
  String wardName;

  @JsonProperty("address")
  String address;

  @JsonProperty("phone")
  String phone;

  @JsonProperty("value")
  String value;

  String payerId;
}
