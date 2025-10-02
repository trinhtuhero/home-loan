package vn.com.msb.homeloan.core.entity;

import java.util.Date;
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
@Table(name = "loan_drop_send_mail", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanDropSendMailEntity extends BaseEntity {

  @Column(name = "loan_id")
  String loanId;

  @Column(name = "next_time")
  Date nextTime;

  @Column(name = "count")
  int count;
}
