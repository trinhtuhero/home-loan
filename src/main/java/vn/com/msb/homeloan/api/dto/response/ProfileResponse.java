package vn.com.msb.homeloan.api.dto.response;

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
public class ProfileResponse {

  String uuid;
  String fullName;
  String gender;
  String nationality;
  String idNo;
  String issuedOn;
  String placeOfIssue;
  String placeOfIssueName;
  String oldIdNo;
  String oldIdNo3;
  String phone;
  String email;
  String maritalStatus;
  Integer numberOfDependents;
  String province;
  String provinceName;
  String district;
  String districtName;
  String ward;
  String wardName;
  String address;
  String birthday;
}
