package vn.com.msb.homeloan.api.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.CollateralStatusEnum;
import vn.com.msb.homeloan.core.constant.CollateralTypeEnum;
import vn.com.msb.homeloan.core.constant.collateral.StatusUsingEnum;
import vn.com.msb.homeloan.core.constant.collateral.TypeOfDocEnum;
import vn.com.msb.homeloan.core.model.CollateralOwner;
import vn.com.msb.homeloan.core.model.CollateralOwnerMap;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CMSMvalueCollateralResponse {

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
}
