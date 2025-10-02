package vn.com.msb.homeloan.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.EducationEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarriedPersonResponse {

  String uuid;
  String loanId;
  String fullName;
  String gender;
  String idNo;
  String issuedOn;
  String birthday;
  String placeOfIssue;
  String placeOfIssueName;
  String oldIdNo;
  String oldIdNo2;
  String oldIdNo3;
  String phone;
  String email;
  String nationality;
  String nationalityName;
  String province;
  String provinceName;
  String district;
  String districtName;
  String ward;
  String wardName;
  String address;
  Integer age;
  String residenceProvince;
  String residenceProvinceName;
  String residenceDistrict;
  String residenceDistrictName;
  String residenceWard;
  String residenceWardName;
  String residenceAddress;
  String cifNo;
  EducationEnum education;

}
