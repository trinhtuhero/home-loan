package vn.com.msb.homeloan.core.model;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import vn.com.msb.homeloan.core.constant.CustomerSegmentEnum;
import vn.com.msb.homeloan.core.constant.EducationEnum;
import vn.com.msb.homeloan.core.constant.GenderEnum;
import vn.com.msb.homeloan.core.constant.InterestedEnum;
import vn.com.msb.homeloan.core.constant.LoanProductEnum;
import vn.com.msb.homeloan.core.constant.MaritalStatusEnum;
import vn.com.msb.homeloan.core.constant.MobioStatusEnum;
import vn.com.msb.homeloan.core.constant.NationalEnum;
import vn.com.msb.homeloan.core.constant.ReceiveChannelEnum;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;
import vn.com.msb.homeloan.core.util.StringUtils;

@Slf4j
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplication {

  String uuid;
  String profileId;
  String phone;
  String fullName;
  GenderEnum gender;
  NationalEnum nationality;
  String nationalityName;
  String email;
  String idNo;
  Date issuedOn;
  Date birthday;
  String placeOfIssue;
  String placeOfIssueName;
  String oldIdNo;
  String oldIdNo2;
  String oldIdNo3;
  String province;
  String provinceName;
  String district;
  String districtName;
  String ward;
  String wardName;
  String address;
  MaritalStatusEnum maritalStatus;
  Integer numberOfDependents;
  String refCode;
  String status;
  String currentStep;
  Integer downloadStatus;
  String picRm;
  Instant receiveDate;
  ReceiveChannelEnum receiveChannel;
  LoanProductEnum loanProduct;
  String loanCode;
  Date submitLoanRequestTime;
  List<LoanJourneyStatus> loanJourneyStatuses;
  Long totalIncome;
  Long totalIncomeExchange;

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
  String partnerCode;
  CustomerSegmentEnum customerSegment;

  Instant createdAt;
  Instant updatedAt;

  List<LoanApplicationItem> loanApplicationItems;
  CmsUser cmsUser;
  MobioStatusEnum mobioStatus;

  LoanAdviseCustomer loanAdviseCustomer;

  // cj4_interested
  InterestedEnum cj4Interested;

  // cj4_interested_date
  Date cj4InterestedDate;

  // cj4_cms_interested
  InterestedEnum cj4CmsInterested;

  // cj4_cms_interested_date
  Date cj4CmsInterestedDate;

  String cj4SendLeadStatus;

  Date cj4SendLeadDate;

  Boolean isCustomerDeclinedToConfirm;

  public LoanApplication(String uuid, String loanCode, String status, String receiveChannel,
      String picRm, String refCode) {
    this.status = status;
    this.loanCode = loanCode;
    this.uuid = uuid;
    if (!StringUtils.isEmpty(receiveChannel)) {
      this.receiveChannel = ReceiveChannelEnum.valueOf(receiveChannel);
    }
    this.picRm = picRm;
    this.refCode = refCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoanApplication that = (LoanApplication) o;
    return (uuid != null && that.uuid != null && HomeLoanUtil.compare(uuid, that.uuid))
        && HomeLoanUtil.compare(phone, that.phone)
        && HomeLoanUtil.compare(fullName, that.fullName)
        && HomeLoanUtil.compare(gender, that.gender)
        && HomeLoanUtil.compare(nationality, that.nationality)
        && HomeLoanUtil.compare(email, that.email)
        && HomeLoanUtil.compare(idNo, that.idNo)
        && (HomeLoanUtil.compare(issuedOn, that.issuedOn) || (issuedOn != null
        && HomeLoanUtil.compare(new Date(issuedOn.getTime()), that.issuedOn)))
        && (HomeLoanUtil.compare(birthday, that.birthday) || (birthday != null
        && HomeLoanUtil.compare(new Date(birthday.getTime()), that.birthday)))
        && HomeLoanUtil.compare(placeOfIssue, that.placeOfIssue)
        && HomeLoanUtil.compare(placeOfIssueName, that.placeOfIssueName)
        && HomeLoanUtil.compare(oldIdNo, that.oldIdNo)
        && HomeLoanUtil.compare(oldIdNo3, that.oldIdNo3)
        && HomeLoanUtil.compare(province, that.province)
        && HomeLoanUtil.compare(provinceName, that.provinceName)
        && HomeLoanUtil.compare(district, that.district)
        && HomeLoanUtil.compare(districtName, that.districtName)
        && HomeLoanUtil.compare(ward, that.ward)
        && HomeLoanUtil.compare(wardName, that.wardName)
        && HomeLoanUtil.compare(address, that.address)
        && HomeLoanUtil.compare(maritalStatus, that.maritalStatus)
        && ((Strings.isEmpty(refCode) && Strings.isEmpty(that.refCode)) || HomeLoanUtil.compare(
        refCode, that.refCode))
        && HomeLoanUtil.compare(numberOfDependents, that.numberOfDependents);
//                && Objects.equals(loanPurpose, that.loanPurpose)
//                && Objects.equals(loanAssetValue, that.loanAssetValue)
//                && Objects.equals(loanAmount, that.loanAmount)
//                && Objects.equals(loanTime, that.loanTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }

  @Override
  public String toString() {
    return "LoanApplication{" +
        "uuid='" + uuid + '\'' +
        ", phone='" + phone + '\'' +
        ", fullName='" + fullName + '\'' +
        ", gender=" + gender +
        ", nationality='" + nationality + '\'' +
        ", email='" + email + '\'' +
        ", idNo='" + idNo + '\'' +
        ", issuedOn=" + issuedOn +
        ", birthday=" + birthday +
        ", placeOfIssue='" + placeOfIssue + '\'' +
        ", placeOfIssueName='" + placeOfIssueName + '\'' +
        ", oldIdNo='" + oldIdNo + '\'' +
        ", province='" + province + '\'' +
        ", provinceName='" + provinceName + '\'' +
        ", district='" + district + '\'' +
        ", districtName='" + districtName + '\'' +
        ", ward='" + ward + '\'' +
        ", wardName='" + wardName + '\'' +
        ", address='" + address + '\'' +
        ", maritalStatus=" + maritalStatus +
        ", refCode='" + refCode + '\'' +
        ", numberOfDependents=" + numberOfDependents +
        '}';
  }
}
