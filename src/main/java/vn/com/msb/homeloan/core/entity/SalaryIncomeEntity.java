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
import vn.com.msb.homeloan.core.constant.BandEnum;
import vn.com.msb.homeloan.core.constant.CompanyEnum;
import vn.com.msb.homeloan.core.constant.KindOfContractEnum;
import vn.com.msb.homeloan.core.constant.KpiRateEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.constant.PaymentMethodEnum;
import vn.com.msb.homeloan.core.constant.SpecialGroupEnum;
import vn.com.msb.homeloan.core.constant.WorkGroupingEnum;

@Data
@Entity
@Table(name = "salary_incomes", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalaryIncomeEntity extends BaseEntity {

  // loan_application_id
  @Column(name = "loan_application_id")
  String loanApplicationId;

  // owner_type
  // enum [Tôi, Vợ/Chồng, Người đồng trả nợ]
  @Enumerated(EnumType.STRING)
  @Column(name = "owner_type")
  OwnerTypeEnum ownerType;

  // payment_method
  // enum [Chuyển khoản qua MSB, Chuyển khoản qua tổ chức tín dụng khác, Tiền mặt]
  @Enumerated(EnumType.STRING)
  @Column(name = "payment_method")
  PaymentMethodEnum paymentMethod;

  // office_name
  @Column(name = "office_name")
  String officeName;

  // office_phone
  @Column(name = "office_phone")
  String officePhone;

  // office_province
  @Column(name = "office_province")
  String officeProvince;

  //office_province_name
  @Column(name = "office_province_name")
  String officeProvinceName;

  // office_district
  @Column(name = "office_district")
  String officeDistrict;

  // office_district_name
  @Column(name = "office_district_name")
  String officeDistrictName;

  // office_ward
  @Column(name = "office_ward")
  String officeWard;

  // office_ward_name
  @Column(name = "office_ward_name")
  String officeWardName;

  // office_address
  @Column(name = "office_address")
  String officeAddress;

  // office_title
  @Column(name = "office_title")
  String officeTitle;

  // value
  @Column(name = "value")
  Long value;

  @Column(name = "start_work_date")
  Date startWorkDate;

  @Column(name = "tax_code")
  String taxCode;

  @Column(name = "insurance_no")
  String insuranceNo;

  @Column(name = "kind_of_contract")
  @Enumerated(EnumType.STRING)
  KindOfContractEnum kindOfContract;

  @Column(name = "company")
  @Enumerated(EnumType.STRING)
  CompanyEnum company;

  @Column(name = "band")
  @Enumerated(EnumType.STRING)
  BandEnum band;

  @Column(name = "kpi_rate")
  @Enumerated(EnumType.STRING)
  KpiRateEnum kpiRate;

  @Column(name = "empl_code")
  String emplCode;

  @Column(name = "special_group")
  @Enumerated(EnumType.STRING)
  SpecialGroupEnum specialGroup;

  @Column(name = "rm_review")
  String rmReview;

  @Column(name = "work_grouping")
  @Enumerated(EnumType.STRING)
  WorkGroupingEnum workGrouping;

  @Column(name = "payer_id")
  String payerId;

  // Chủ nguồn thu là CBNV/MSB
  @Column(name = "msb_staff")
  Boolean msbStaff;
}
