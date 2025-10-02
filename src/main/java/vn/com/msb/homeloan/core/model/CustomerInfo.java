package vn.com.msb.homeloan.core.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfo {

  String uuid;
  String profileId;
  String phone;
  String fullName;
  String gender;
  String nationality;
  String email;
  String idNo;
  Date issuedOn;
  String placeOfIssue;
  String oldIdNo;
  String oldIdNo3;
  String province;
  String provinceName;
  String district;
  String districtName;
  String ward;
  String wardName;
  String address;
  String maritalStatus;
  Integer numberOfDependents;
  String refCode;
}
