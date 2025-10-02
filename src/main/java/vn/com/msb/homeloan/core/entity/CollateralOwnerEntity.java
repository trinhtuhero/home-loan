package vn.com.msb.homeloan.core.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.ContactPersonTypeEnum;
import vn.com.msb.homeloan.core.constant.EducationEnum;
import vn.com.msb.homeloan.core.constant.GenderEnum;
import vn.com.msb.homeloan.core.constant.MaritalStatusEnum;
import vn.com.msb.homeloan.core.constant.NationalEnum;

@Data
@Entity
@Table(name = "collateral_owner", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CollateralOwnerEntity extends BaseEntity {

  @Column(name = "loan_id")
  private String loanId;

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  GenderEnum gender;

  @Column(name = "birthday")
  Date birthday;

  @Column(name = "phone")
  private String phone;

  @Column(name = "email")
  String email;

  @Column(name = "age")
  Long age;

  @Column(name = "education_level")
  @Enumerated(EnumType.STRING)
  EducationEnum educationLevel;

  @Enumerated(EnumType.STRING)
  @Column(name = "nationality")
  NationalEnum nationality;

  @Column(name = "id_no")
  String idNo;

  @Column(name = "issued_on")
  Date issuedOn;

  @Column(name = "place_of_issue")
  String placeOfIssue;

  @Column(name = "old_id_no")
  String oldIdNo;

  @Column(name = "old_id_no_2")
  String oldIdNo2;

  @Column(name = "old_id_no_3")
  String oldIdNo3;

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

  @Column(name = "contact_province")
  String contactProvince;

  @Column(name = "contact_province_name")
  String contactProvinceName;

  @Column(name = "contact_district")
  String contactDistrict;

  @Column(name = "contact_district_name")
  String contactDistrictName;

  @Column(name = "contact_ward")
  String contactWard;

  @Column(name = "contact_ward_name")
  String contactWardName;

  @Column(name = "contact_address")
  String contactAddress;

  @Enumerated(EnumType.STRING)
  @Column(name = "marital_status")
  private MaritalStatusEnum maritalStatus;

  @Enumerated(EnumType.STRING)
  @Column(name = "relationship")
  private ContactPersonTypeEnum relationship;
}
