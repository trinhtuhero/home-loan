package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.BandEnum;
import vn.com.msb.homeloan.core.constant.CompanyEnum;
import vn.com.msb.homeloan.core.constant.KindOfContractEnum;
import vn.com.msb.homeloan.core.constant.KpiRateEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.constant.PaymentMethodEnum;
import vn.com.msb.homeloan.core.constant.SpecialGroupEnum;
import vn.com.msb.homeloan.core.constant.WorkGroupingEnum;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalaryIncome {

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

  Long value;

  @JsonIgnore
  Instant createdAt;

  @JsonIgnore
  Instant updatedAt;

  Date startWorkDate;

  String taxCode;

  String insuranceNo;

  KindOfContractEnum kindOfContract;

  CompanyEnum company;

  BandEnum band;

  KpiRateEnum kpiRate;

  String emplCode;

  SpecialGroupEnum specialGroup;

  String rmReview;

  WorkGroupingEnum workGrouping;

  String payerId;

  String payerName;

  Boolean msbStaff;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SalaryIncome that = (SalaryIncome) o;
    return (uuid != null && that.uuid != null && HomeLoanUtil.compare(uuid, that.uuid))
        && HomeLoanUtil.compare(ownerType, that.ownerType)
        && HomeLoanUtil.compare(paymentMethod, that.paymentMethod)
        && HomeLoanUtil.compare(officeName, that.officeName)
        && HomeLoanUtil.compare(officePhone, that.officePhone)
        && HomeLoanUtil.compare(officeProvince, that.officeProvince)
        && HomeLoanUtil.compare(officeProvinceName, that.officeProvinceName)
        && HomeLoanUtil.compare(officeDistrict, that.officeDistrict)
        && HomeLoanUtil.compare(officeDistrictName, that.officeDistrictName)
        && HomeLoanUtil.compare(officeWard, that.officeWard)
        && HomeLoanUtil.compare(officeWardName, that.officeWardName)
        && HomeLoanUtil.compare(officeAddress, that.officeAddress)
        && HomeLoanUtil.compare(officeTitle, that.officeTitle)
        && HomeLoanUtil.compare(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }
}
