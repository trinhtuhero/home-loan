package vn.com.msb.homeloan.api.dto.request;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.api.validation.CustomDateConstraint;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CMSCollateralRequest {

  String uuid;
  @NotBlank
  String loanId;

  @NotBlank
  @Pattern(regexp = "^(ND|CC|PTVT|GTCG)$")
  String type;

  @NotBlank
  @Pattern(regexp = "^(ME|COUPLE|THIRD_PARTY|SPOUSE|OTHERS)$")
  String status;

  String fullName;

  String relationship;

  String province;

  String provinceName;

  String district;

  String districtName;

  String ward;

  String wardName;

  String address;

  @Min(0)
  Long value;

  String location;

  String description;

  String mvalueId;

  String coreId;

  String valuationCert;

  @NotBlank
  String assetCategory;

  //    @NotBlank
  String legalDoc;

  //    @NotBlank
  @CustomDateConstraint
  String docIssuedOn;

  //    @NotBlank
  String docPlaceOfIssue;

  @Min(0)
  Long guaranteedValue;

  @NotNull
  @Min(0)
  Float ltvRate;

  @CustomDateConstraint
  String pricingDate;

  @CustomDateConstraint
  String nextPricingDate;

  String officerNamePricing;

  // type_of_doc (Loại giấy tờ)
  @Pattern(regexp = "^(ĐKX|HĐMB)$")
  String typeOfDoc;

  // chassis_no (Số khung)
  String chassisNo;

  // engine_no (Số máy)
  String engineNo;

  // duration_of_used(Thời gian sử dụng)
  Integer durationOfUsed;

  // status_using(Tình trạng sử dụng)
  @Pattern(regexp = "^(NEW_CAR|OLD_CAR)$")
  String statusUsing;

  // asset_description(Mô tả tài sản)
  // String assetDescription;

  // account_number(Số tài khoản)
  // String accountNumber;

  // saving_book_no(Số sổ tiết kiệm)
  String savingBookNo;

  // maturity_date(Ngày đến hạn tiền gửi)
  @CustomDateConstraint
  String maturityDate;

  // issued_branch(Chi nhánh phát hành)
  String issuedBranch;

  // interest_rate(Lãi suất tiết kiệm)
  @DecimalMin(value = "0.01")
  @DecimalMax(value = "100")
  BigDecimal interestRate;

  // registration_or_contract_no(Số đăng ký xe/HĐMB)
  String registrationOrContractNo;

  List<CollateralOwnerMapRequest> collateralOwnerMaps;
}
