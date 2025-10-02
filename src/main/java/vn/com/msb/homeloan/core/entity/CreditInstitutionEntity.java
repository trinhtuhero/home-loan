package vn.com.msb.homeloan.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "credit_institution", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditInstitutionEntity extends BaseEntity {

  @Column(name = "code")
  String code;

  @Column(name = "name")
  String name;

  @Column(name = "short_name")
  String shortName;
}
