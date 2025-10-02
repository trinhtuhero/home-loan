package vn.com.msb.homeloan.api.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.BandEnum;
import vn.com.msb.homeloan.core.constant.CompanyEnum;
import vn.com.msb.homeloan.core.constant.KpiRateEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.constant.PaymentMethodEnum;
import vn.com.msb.homeloan.core.constant.SpecialGroupEnum;
import vn.com.msb.homeloan.core.constant.WorkGroupingEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CMSSalaryIncomeResponse {

  String uuid;

  String loanApplicationId;

  OwnerTypeEnum ownerType;

  PaymentMethodEnum paymentMethod;

  String officeName;

  String officePhone;

  String officeProvince;

  String officeProvinceName;

  String officeDistrict;

  String officeDistrictName;

  String officeWard;

  String officeWardName;

  String officeAddress;

  String officeTitle;

  String value;

  String startWorkDate;

  String taxCode;

  String insuranceNo;

  String kindOfContract;

  CompanyEnum company;

  BandEnum band;

  KpiRateEnum kpiRate;

  String emplCode;

  SpecialGroupEnum specialGroup;

  String rmReview;

  WorkGroupingEnum workGrouping;

  String payerId;
}
