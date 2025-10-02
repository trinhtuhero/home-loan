package vn.com.msb.homeloan.core.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.CollateralOwnerMapTypeEnum;
import vn.com.msb.homeloan.core.constant.ContactPersonTypeEnum;
import vn.com.msb.homeloan.core.constant.EducationEnum;
import vn.com.msb.homeloan.core.constant.GenderEnum;
import vn.com.msb.homeloan.core.constant.MaritalStatusEnum;
import vn.com.msb.homeloan.core.constant.NationalEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollateralOwner {

  String uuid;
  String loanId;
  String fullName;
  GenderEnum gender;
  String idNo;
  Date issuedOn;
  Date birthday;
  String placeOfIssue;
  String oldIdNo;
  String oldIdNo2;
  String oldIdNo3;
  String phone;
  String email;
  NationalEnum nationality;
  String province;
  String provinceName;
  String district;
  String districtName;
  String ward;
  String wardName;
  String address;
  MaritalStatusEnum maritalStatus;
  String relationship;
  EducationEnum educationLevel;
  String contactProvince;
  String contactProvinceName;
  String contactDistrict;
  String contactDistrictName;
  String contactWard;
  String contactWardName;
  String contactAddress;
  CollateralOwnerMapTypeEnum mapType;
  Boolean isCheck;

  public CollateralOwner(LoanApplication loanApplication) {
    if (loanApplication == null) {
      return;
    }
    this.fullName = loanApplication.getFullName();
    this.gender = loanApplication.getGender();
    this.idNo = loanApplication.getIdNo();
    this.issuedOn = loanApplication.getIssuedOn();
    this.birthday = loanApplication.getBirthday();
    this.placeOfIssue = loanApplication.getPlaceOfIssue();
    this.oldIdNo = loanApplication.getOldIdNo();
    this.oldIdNo2 = loanApplication.getOldIdNo2();
    this.oldIdNo3 = loanApplication.getOldIdNo3();
    this.phone = loanApplication.getPhone();
    this.email = loanApplication.getEmail();
    this.nationality = loanApplication.getNationality();
    this.province = loanApplication.getResidenceProvince();
    this.provinceName = loanApplication.getResidenceProvinceName();
    this.district = loanApplication.getResidenceDistrict();
    this.districtName = loanApplication.getResidenceDistrictName();
    this.ward = loanApplication.getResidenceWard();
    this.wardName = loanApplication.getResidenceWardName();
    this.address = loanApplication.getResidenceAddress();
    this.maritalStatus = loanApplication.getMaritalStatus();
    this.educationLevel = loanApplication.getEducation();
    this.contactProvince = loanApplication.getProvince();
    this.contactProvinceName = loanApplication.getProvinceName();
    this.contactDistrict = loanApplication.getDistrict();
    this.contactDistrictName = loanApplication.getResidenceDistrictName();
    this.contactWard = loanApplication.getWard();
    this.contactWardName = loanApplication.getWardName();
    this.contactAddress = loanApplication.getAddress();
    this.relationship = ContactPersonTypeEnum.HOUSEHOLD_MEM.getCode();
  }

  public CollateralOwner(MarriedPerson marriedPerson) {
    if (marriedPerson == null) {
      return;
    }
    this.fullName = marriedPerson.getFullName();
    this.gender = marriedPerson.getGender();
    this.idNo = marriedPerson.getIdNo();
    this.issuedOn = marriedPerson.getIssuedOn();
    this.birthday = marriedPerson.getBirthday();
    this.placeOfIssue = marriedPerson.getPlaceOfIssue();
    this.oldIdNo = marriedPerson.getOldIdNo();
    this.oldIdNo2 = marriedPerson.getOldIdNo2();
    this.oldIdNo3 = marriedPerson.getOldIdNo3();
    this.phone = marriedPerson.getPhone();
    this.email = marriedPerson.getEmail();
    this.nationality = marriedPerson.getNationality();
    this.province = marriedPerson.getResidenceProvince();
    this.provinceName = marriedPerson.getResidenceProvinceName();
    this.district = marriedPerson.getResidenceDistrict();
    this.districtName = marriedPerson.getResidenceDistrictName();
    this.ward = marriedPerson.getResidenceWard();
    this.wardName = marriedPerson.getResidenceWardName();
    this.address = marriedPerson.getResidenceAddress();
    this.maritalStatus = MaritalStatusEnum.MARRIED;
    this.educationLevel = marriedPerson.getEducation();
    this.contactProvince = marriedPerson.getProvince();
    this.contactProvinceName = marriedPerson.getProvinceName();
    this.contactDistrict = marriedPerson.getDistrict();
    this.contactDistrictName = marriedPerson.getResidenceDistrictName();
    this.contactWard = marriedPerson.getWard();
    this.contactWardName = marriedPerson.getWardName();
    this.contactAddress = marriedPerson.getAddress();
    if (GenderEnum.MALE.equals(this.gender)) {
      this.relationship = ContactPersonTypeEnum.HUSBAND.getCode();
    } else {
      this.relationship = ContactPersonTypeEnum.WIFE.getCode();
    }
  }

  public CollateralOwner(LoanPayer loanPayer) {
    if (loanPayer == null) {
      return;
    }
    this.fullName = loanPayer.getFullName();
    this.gender = loanPayer.getGender();
    this.idNo = loanPayer.getIdNo();
    this.issuedOn = loanPayer.getIssuedOn();
    this.birthday = loanPayer.getBirthday();
    this.placeOfIssue = loanPayer.getPlaceOfIssue();
    this.oldIdNo = loanPayer.getOldIdNo();
    this.oldIdNo2 = loanPayer.getOldIdNo2();
    this.oldIdNo3 = loanPayer.getOldIdNo3();
    this.phone = loanPayer.getPhone();
    this.email = loanPayer.getEmail();
    this.nationality = loanPayer.getNationality();
    this.province = loanPayer.getResidenceProvince();
    this.provinceName = loanPayer.getResidenceProvinceName();
    this.district = loanPayer.getResidenceDistrict();
    this.districtName = loanPayer.getResidenceDistrictName();
    this.ward = loanPayer.getResidenceWard();
    this.wardName = loanPayer.getResidenceWardName();
    this.address = loanPayer.getResidenceAddress();
    this.maritalStatus = loanPayer.getMaritalStatus();
    this.educationLevel = loanPayer.getEducation();
    this.contactProvince = loanPayer.getProvince();
    this.contactProvinceName = loanPayer.getProvinceName();
    this.contactDistrict = loanPayer.getDistrict();
    this.contactDistrictName = loanPayer.getResidenceDistrictName();
    this.contactWard = loanPayer.getWard();
    this.contactWardName = loanPayer.getWardName();
    this.contactAddress = loanPayer.getAddress();
    this.relationship = loanPayer.getRelationshipType().getCode();
  }
}
