package vn.com.msb.homeloan.core.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.CollateralLocationEnum;
import vn.com.msb.homeloan.core.constant.CollateralStatusEnum;
import vn.com.msb.homeloan.core.constant.CollateralTypeEnum;
import vn.com.msb.homeloan.core.constant.ContactPersonTypeEnum;
import vn.com.msb.homeloan.core.constant.collateral.StatusUsingEnum;
import vn.com.msb.homeloan.core.constant.collateral.TypeOfDocEnum;

@Data
@Entity
@Table(name = "collaterals", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CollateralEntity extends BaseEntity {

  @Column(name = "loan_application_id")
  String loanId;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  CollateralTypeEnum type;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  CollateralStatusEnum status;

  @Column(name = "full_name")
  String fullName;

  @Enumerated(EnumType.STRING)
  @Column(name = "relationship")
  ContactPersonTypeEnum relationship;

  @Column(name = "province")
  String province;

  @Column(name = "province_name")
  String provinceName;

  @Column(name = "district")
  String district;

  @Column(name = "district_name")
  String districtName;

  @Column(name = "ward")
  String ward;

  @Column(name = "ward_name")
  String wardName;

  @Column(name = "address")
  String address;

  @Column(name = "value")
  Long value;

  @Column(name = "location")
  @Enumerated(EnumType.STRING)
  CollateralLocationEnum location;

  @Column(name = "description")
  String description;

  @Column(name = "m_value_id")
  String mvalueId;

  @Column(name = "core_id")
  String coreId;

  @Column(name = "valuation_cert")
  String valuationCert;

  @Column(name = "asset_category")
  String assetCategory;

  @Column(name = "legal_doc")
  String legalDoc;

  @Column(name = "doc_issued_on")
  Date docIssuedOn;

  @Column(name = "doc_place_of_issue")
  String docPlaceOfIssue;

  @Column(name = "guaranteed_value")
  Long guaranteedValue;

  @Column(name = "ltv_rate")
  Float ltvRate;

  //pricing_date(Ngày định giá)
  @Column(name = "pricing_date")
  Date pricingDate;

  //next_pricing_date (Ngày định giá tiếp theo)
  @Column(name = "next_pricing_date")
  Date nextPricingDate;

  //officer_name_pricing (Tên cán bộ định giá)
  @Column(name = "officer_name_pricing")
  String officerNamePricing;

  // type_of_doc (Loại giấy tờ)
  @Column(name = "type_of_doc")
  @Enumerated(EnumType.STRING)
  TypeOfDocEnum typeOfDoc;

  // chassis_no (Số khung)
  @Column(name = "chassis_no")
  String chassisNo;

  // engine_no (Số máy)
  @Column(name = "engine_no")
  String engineNo;

  // duration_of_used(Thời gian sử dụng)
  @Column(name = "duration_of_used")
  Integer durationOfUsed;

  // status_using(Tình trạng sử dụng)
  @Column(name = "status_using")
  @Enumerated(EnumType.STRING)
  StatusUsingEnum statusUsing;

  // asset_description(Mô tả tài sản)
    /*@Column(name = "asset_description")
    String assetDescription;*/

  // account_number(Số tài khoản)
    /*@Column(name = "account_number")
    String accountNumber;*/

  // saving_book_no(Số sổ tiết kiệm)
  @Column(name = "saving_book_no")
  String savingBookNo;

  // maturity_date(Ngày đến hạn tiền gửi)
  @Column(name = "maturity_date")
  Date maturityDate;

  // issued_branch(Chi nhánh phát hành)
  @Column(name = "issued_branch")
  String issuedBranch;

  // interest_rate(Lãi suất tiết kiệm)
  @Column(name = "interest_rate")
  Double interestRate;

  // registration_or_contract_no(Số đăng ký xe/HĐMB)
  String registrationOrContractNo;

  @Column(name = "so_ts_mvalue")
  String soTsMvalue;
}
