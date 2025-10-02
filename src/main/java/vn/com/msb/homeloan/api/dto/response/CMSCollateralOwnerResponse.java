package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CMSCollateralOwnerResponse {

  String uuid;
  String loanId;
  String fullName;
  String gender;
  String idNo;
  String issuedOn;
  String birthday;
  String placeOfIssue;
  String oldIdNo;
  String oldIdNo2;
  String oldIdNo3;
  String phone;
  String email;
  String nationality;
  String province;
  String provinceName;
  String district;
  String districtName;
  String ward;
  String wardName;
  String address;
  String maritalStatus;
  String relationship;
  String educationLevel;
  String contactProvince;
  String contactProvinceName;
  String contactDistrict;
  String contactDistrictName;
  String contactWard;
  String contactWardName;
  String contactAddress;
  String mapType;
  Boolean isCheck;
}
