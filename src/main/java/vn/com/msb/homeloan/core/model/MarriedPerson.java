package vn.com.msb.homeloan.core.model;

import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.EducationEnum;
import vn.com.msb.homeloan.core.constant.GenderEnum;
import vn.com.msb.homeloan.core.constant.NationalEnum;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarriedPerson {

  String uuid;
  String loanId;
  String fullName;
  GenderEnum gender;
  String idNo;
  Date issuedOn;
  Date birthday;
  String placeOfIssue;
  String placeOfIssueName;
  String oldIdNo;
  String oldIdNo2;
  String oldIdNo3;
  String phone;
  String email;
  NationalEnum nationality;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MarriedPerson that = (MarriedPerson) o;
    return (uuid != null && that.uuid != null && HomeLoanUtil.compare(uuid, that.uuid))
        && HomeLoanUtil.compare(fullName, that.fullName)
        && HomeLoanUtil.compare(gender, that.gender)
        && HomeLoanUtil.compare(nationality, that.nationality)
        && HomeLoanUtil.compare(idNo, that.idNo)
        && (HomeLoanUtil.compare(issuedOn, that.issuedOn) || (issuedOn != null
        && HomeLoanUtil.compare(new Date(issuedOn.getTime()), that.issuedOn)))
        && HomeLoanUtil.compare(placeOfIssue, that.placeOfIssue)
        && HomeLoanUtil.compare(placeOfIssueName, that.placeOfIssueName)
        && HomeLoanUtil.compare(oldIdNo, that.oldIdNo)
        && HomeLoanUtil.compare(oldIdNo3, that.oldIdNo3)
        && HomeLoanUtil.compare(phone, that.phone)
        //&& Objects.equals(email, that.email)
        && (HomeLoanUtil.compare(birthday, that.birthday) || (birthday != null
        && HomeLoanUtil.compare(new Date(birthday.getTime()), that.birthday)))
        && HomeLoanUtil.compare(province, that.province)
        && HomeLoanUtil.compare(provinceName, that.provinceName)
        && HomeLoanUtil.compare(district, that.district)
        && HomeLoanUtil.compare(districtName, that.districtName)
        && HomeLoanUtil.compare(ward, that.ward)
        && HomeLoanUtil.compare(wardName, that.wardName)
        && HomeLoanUtil.compare(address, that.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }

}
