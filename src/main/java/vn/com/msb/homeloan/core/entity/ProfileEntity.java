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
import vn.com.msb.homeloan.core.constant.GenderEnum;
import vn.com.msb.homeloan.core.constant.MaritalStatusEnum;
import vn.com.msb.homeloan.core.constant.NationalEnum;
import vn.com.msb.homeloan.core.constant.ProfileStatusEnum;

@Data
@Entity
@Table(name = "profiles", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProfileEntity extends BaseEntity {

  @Column(name = "phone")
  private String phone;

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  GenderEnum gender;

  @Enumerated(EnumType.STRING)
  @Column(name = "nationality")
  NationalEnum nationality;

  @Column(name = "email")
  String email;

  @Column(name = "id_no")
  String idNo;

  @Column(name = "issued_on")
  Date issuedOn;

  @Column(name = "birthday")
  Date birthday;

  @Column(name = "place_of_issue")
  String placeOfIssue;

  @Column(name = "old_id_no")
  String oldIdNo;

  @Column(name = "old_id_no3")
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

  @Column(name = "number_of_dependents")
  Integer numberOfDependents;

  @Column(name = "address")
  String address;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  ProfileStatusEnum status;

  @Column(name = "ref_code")
  String refCode;

  @Enumerated(EnumType.STRING)
  @Column(name = "marital_status")
  private MaritalStatusEnum maritalStatus;
}
