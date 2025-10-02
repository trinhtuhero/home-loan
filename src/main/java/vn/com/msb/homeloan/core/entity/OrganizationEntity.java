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
@Table(name = "organization", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationEntity extends AuditEntity {

  @Id
  @Column(name = "code")
  String code;

  @Column(name = "name")
  String name;

  @Column(name = "specialized_bank")
  String specializedBank;

  @Column(name = "area_code")
  String areaCode;

  @Column(name = "branch_code")
  String branchCode;

  @Column(name = "type")
  String type;

  @Column(name = "ecm_code")
  String ecmCode;

  @Column(name = "status")
  String status;

  @Column(name = "cic_code")
  String cicCode;

  @Column(name = "mvalue_code")
  String mvalueCode;
}
