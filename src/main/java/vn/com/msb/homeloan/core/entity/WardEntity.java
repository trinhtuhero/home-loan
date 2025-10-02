package vn.com.msb.homeloan.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "ward", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WardEntity extends AuditEntity {

  @Id
  @Column(name = "code", unique = true)
  String code;

  @Column(name = "name")
  String name;

  @Column(name = "district_code")
  String districtCode;

  @Column(name = "province_code")
  String provinceCode;

  @Column(name = "mobio_district_code")
  String mobioDistrictCode;

  @Column(name = "mobio_province_code")
  String mobioProvinceCode;

  @Column(name = "mobio_code")
  String mobioCode;

  @Column(name = "mvalue_code")
  String mvalueCode;

  @Column(name = "mvalue_name")
  String mvalueName;

  @Column(name = "mvalue_district_code")
  String mvalueDistrictCode;

  @Column(name = "mvalue_province_code")
  String mvalueProvinceCode;
}
