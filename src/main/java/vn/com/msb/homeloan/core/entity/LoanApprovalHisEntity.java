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
@Table(name = "loan_approval_his", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanApprovalHisEntity extends AuditEntity {

  @Id
  @Column(name = "loan_id")
  String loanId;

  @Column(name = "status")
  String status;

  @Column(name = "meta_data_from")
  String metaDataFrom;

  @Column(name = "meta_data_to")
  String metaDataTo;
}
