package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.model.CollateralOwnerMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CMSCollateralResponse extends CollateralResponse {

  String mvalueId;
  String coreId;
  String valuationCert;
  String assetCategory;
  String legalDoc;
  String docIssuedOn;
  String docPlaceOfIssue;
  String docPlaceOfIssueName;
  Long guaranteedValue;
  Float ltvRate;

  //pricing_date(Ngày định giá)
  String pricingDate;

  //next_pricing_date (Ngày định giá tiếp theo)
  String nextPricingDate;

  //officer_name_pricing (Tên cán bộ định giá)
  String officerNamePricing;

  // type_of_doc (Loại giấy tờ)
  String typeOfDoc;

  // chassis_no (Số khung)
  String chassisNo;

  // engine_no (Số máy)
  String engineNo;

  // duration_of_used(Thời gian sử dụng)
  Integer durationOfUsed;

  // status_using(Tình trạng sử dụng)
  String statusUsing;

  // asset_description(Mô tả tài sản)
  String assetDescription;

  // account_number(Số tài khoản)
  String accountNumber;

  // saving_book_no(Số sổ tiết kiệm)
  String savingBookNo;

  // maturity_date(Ngày đến hạn tiền gửi)
  String maturityDate;

  // issued_branch(Chi nhánh phát hành)
  String issuedBranch;

  // interest_rate(Lãi suất tiết kiệm)
  Double interestRate;

  // registration_or_contract_no(Số đăng ký xe/HĐMB)
  String registrationOrContractNo;

  List<CMSCollateralOwnerResponse> collateralOwners;

  List<CollateralOwnerMap> collateralOwnerMaps;

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
