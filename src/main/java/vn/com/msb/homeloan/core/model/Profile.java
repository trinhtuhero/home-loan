package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Profile {

  String uuid;
  String fullName;
  String gender;
  String nationality;
  String idNo;
  Date issuedOn;
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
  Date birthday;
  String status;
}
