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
import vn.com.msb.homeloan.core.constant.EducationEnum;
import vn.com.msb.homeloan.core.constant.GenderEnum;
import vn.com.msb.homeloan.core.constant.LoanPayerRelationshipEnum;
import vn.com.msb.homeloan.core.constant.MaritalStatusEnum;
import vn.com.msb.homeloan.core.constant.NationalEnum;

@Data
@Entity
@Table(name = "loan_payers", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanPayerEntity extends BaseEntity {

  @Column(name = "loan_application_id")
  String loanId;

  @Enumerated(EnumType.STRING)
  @Column(name = "relationship_type")
  LoanPayerRelationshipEnum relationshipType;

  @Column(name = "full_name")
  String fullName;

  @Column(name = "age")
  Integer age;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  GenderEnum gender;

  @Column(name = "nationality")
  @Enumerated(EnumType.STRING)
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

  @Column(name = "phone")
  String phone;

  @Column(name = "email")
  String email;

  @Column(name = "birthday")
  Date birthday;

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

  @Column(name = "residence_province")
  String residenceProvince;

  @Column(name = "residence_province_name")
  String residenceProvinceName;

  @Column(name = "residence_district")
  String residenceDistrict;

  @Column(name = "residence_district_name")
  String residenceDistrictName;

  @Column(name = "residence_ward")
  String residenceWard;

  @Column(name = "residence_ward_name")
  String residenceWardName;

  @Column(name = "residence_address")
  String residenceAddress;

  @Enumerated(EnumType.STRING)
  @Column(name = "marital_status")
  MaritalStatusEnum maritalStatus;

  @Column(name = "number_of_dependents")
  Integer numberOfDependents;

  @Column(name = "cif_no")
  String cifNo;

  @Column(name = "education")
  @Enumerated(EnumType.STRING)
  EducationEnum education;
}
