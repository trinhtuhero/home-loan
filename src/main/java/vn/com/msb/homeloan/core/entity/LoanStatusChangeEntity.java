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
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;

@Data
@Entity
@Table(name = "loan_status_change", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanStatusChangeEntity extends BaseEntity {

  @Column(name = "loan_application_id")
  String loanApplicationId;

  @Column(name = "status_from")
  @Enumerated(EnumType.STRING)
  LoanInfoStatusEnum statusFrom;

  @Column(name = "status_to")
  @Enumerated(EnumType.STRING)
  LoanInfoStatusEnum statusTo;

  @Column(name = "cause")
  String cause;

  @Column(name = "note")
  String note;

  @Column(name = "empl_id")
  String emplId;

}
