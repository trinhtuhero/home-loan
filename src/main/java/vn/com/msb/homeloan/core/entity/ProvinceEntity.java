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
@Table(name = "province", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProvinceEntity extends AuditEntity {

  @Id
  @Column(name = "code", unique = true)
  String code;

  @Column(name = "name")
  String name;

  @Column(name = "mobioCode")
  String mobioCode;

  @Column(name = "mercury_name")
  String mercuryName;

  @Column(name = "order_by")
  String order;

  @Column(name = "mvalue_code")
  String mvalueCode;

  @Column(name = "mvalue_name")
  String mvalueName;
}
