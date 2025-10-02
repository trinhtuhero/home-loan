package vn.com.msb.homeloan.core.entity;

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
import vn.com.msb.homeloan.core.constant.ContactPersonTypeEnum;

@Data
@Entity
@Table(name = "contact_persons", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContactPersonEntity extends BaseEntity {

  @Column(name = "loan_application_id")
  String loanId;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  ContactPersonTypeEnum type;

  @Column(name = "full_name")
  String fullName;

  @Column(name = "phone")
  String phone;
}
