package vn.com.msb.homeloan.core.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.CollateralStatusEnum;
import vn.com.msb.homeloan.core.constant.CollateralTypeEnum;
import vn.com.msb.homeloan.core.constant.collateral.StatusUsingEnum;
import vn.com.msb.homeloan.core.constant.collateral.TypeOfDocEnum;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collateral {

  String uuid;
  String loanId;
  CollateralTypeEnum type;
  CollateralStatusEnum status;
  String fullName;
  String relationship;
  String province;
  String provinceName;
  String district;
  String districtName;
  String ward;
  String wardName;
  String address;
  Long value;
  String location;
  String description;
  String mvalueId;
  String coreId;
  String valuationCert;
  String assetCategory;
  String legalDoc;
  Date docIssuedOn;
  String docPlaceOfIssue;
  String docPlaceOfIssueName;
  Long guaranteedValue;
  Float ltvRate;

  //pricing_date(Ngày định giá)
  Date pricingDate;

  //next_pricing_date (Ngày định giá tiếp theo)
  Date nextPricingDate;

  //officer_name_pricing (Tên cán bộ định giá)
  String officerNamePricing;

  // type_of_doc (Loại giấy tờ)
  TypeOfDocEnum typeOfDoc;

  // chassis_no (Số khung)
  String chassisNo;

  // engine_no (Số máy)
  String engineNo;

  // duration_of_used(Thời gian sử dụng)
  Integer durationOfUsed;

  // status_using(Tình trạng sử dụng)
  StatusUsingEnum statusUsing;

  // asset_description(Mô tả tài sản)
  // String assetDescription;

  // account_number(Số tài khoản)
  // String accountNumber;

  // saving_book_no(Số sổ tiết kiệm)
  String savingBookNo;

  // maturity_date(Ngày đến hạn tiền gửi)
  Date maturityDate;

  // issued_branch(Chi nhánh phát hành)
  String issuedBranch;

  // interest_rate(Lãi suất tiết kiệm)
  Double interestRate;

  // registration_or_contract_no(Số đăng ký xe/HĐMB)
  String registrationOrContractNo;

  List<CollateralOwnerMap> collateralOwnerMaps;

  List<CollateralOwner> collateralOwners;

  //Mvalue check duplicate
  String soTsMvalue;

  String mValueValuationStatus;

  int yearValuationLast;

  String dsTBDG;

  String collateralOwnerName;

  int index;

  String mvalueProvince;

  String mvalueDistrict;

  String mvalueWard;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Collateral that = (Collateral) o;

    // nếu status =  bên thứ 3  thì  required trường này (cho tất cả các type)
    boolean condition1 = false;
    if (CollateralStatusEnum.THIRD_PARTY.equals(status)) {
      condition1 = true;
    }

    // nếu type = ND hoặc CC thì required
    boolean condition2 = false;
    if (CollateralTypeEnum.ND.equals(type) || CollateralTypeEnum.CC.equals(type)) {
      condition2 = true;
    }

    return (uuid != null && that.uuid != null && HomeLoanUtil.compare(uuid, that.uuid))
        && HomeLoanUtil.compare(type, that.type)
        && HomeLoanUtil.compare(status, that.status)
        && (!condition1 || HomeLoanUtil.compare(fullName, that.fullName))
        && (!condition1 || HomeLoanUtil.compare(relationship, that.relationship))
        && (!condition2 || HomeLoanUtil.compare(province, that.province))
        && (!condition2 || HomeLoanUtil.compare(provinceName, that.provinceName))
        && (!condition2 || HomeLoanUtil.compare(district, that.district))
        && (!condition2 || HomeLoanUtil.compare(districtName, that.districtName))
        && (!condition2 || HomeLoanUtil.compare(ward, that.ward))
        && (!condition2 || HomeLoanUtil.compare(wardName, that.wardName))
        && (!condition2 || HomeLoanUtil.compare(address, that.address))
        //&& Objects.equals(value, that.value)
        && HomeLoanUtil.compare(location, that.location)
        && HomeLoanUtil.compare(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }
}
