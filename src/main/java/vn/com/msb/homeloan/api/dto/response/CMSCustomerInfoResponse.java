package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.CustomerSegmentEnum;
import vn.com.msb.homeloan.core.constant.EducationEnum;
import vn.com.msb.homeloan.core.constant.GenderEnum;
import vn.com.msb.homeloan.core.constant.MaritalStatusEnum;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CMSCustomerInfoResponse {

  String uuid;
  String fullName;
  String birthday;
  Integer age;
  GenderEnum gender;
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
  MaritalStatusEnum maritalStatus;
  Integer numberOfDependents;
  String province;
  String provinceName;
  String district;
  String districtName;
  String ward;
  String wardName;
  String address;
  String refCode;
  String residenceProvince;
  String residenceProvinceName;
  String residenceDistrict;
  String residenceDistrictName;
  String residenceWard;
  String residenceWardName;
  String residenceAddress;
  String cifNo;
  EducationEnum education;
  String partnerCode;
  CustomerSegmentEnum customerSegment;
  String loanCode;
  String status;
}
