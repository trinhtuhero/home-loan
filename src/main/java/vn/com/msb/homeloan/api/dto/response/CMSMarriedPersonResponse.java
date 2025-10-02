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
public class CMSMarriedPersonResponse {

  String uuid;
  String fullName;
  String gender;
  String nationality;
  String nationalityName;
  String idNo;
  String issuedOn;
  String placeOfIssue;
  String oldIdNo;
  String oldIdNo2;
  String oldIdNo3;
  String phone;
  String email;
  String birthday;
  String cifNo;
  Integer age;
  EducationEnum education;
  String province;
  String provinceName;
  String district;
  String districtName;
  String ward;
  String wardName;
  String address;
  String residenceProvince;
  String residenceProvinceName;
  String residenceDistrict;
  String residenceDistrictName;
  String residenceWard;
  String residenceWardName;
  String residenceAddress;
  String loanId;
}
