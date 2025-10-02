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
import vn.com.msb.homeloan.core.constant.MobioHistoryStatusEnum;

@Data
@Entity
@Table(name = "mobio_history", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MobioHistoryEntity extends BaseEntity {

  @Column(name = "loan_application_id")
  String loanApplicationId;

  @Column(name = "mobio_status")
  @Enumerated(EnumType.STRING)
  MobioHistoryStatusEnum mobioStatus;

  @Column(name = "loan_status")
  @Enumerated(EnumType.STRING)
  LoanInfoStatusEnum loanStatus;

  @Column(name = "request")
  String request;

  @Column(name = "response")
  String response;
}
