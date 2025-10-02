package vn.com.msb.homeloan.core.entity;

import java.util.Date;
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
import vn.com.msb.homeloan.core.constant.AdviseTimeFrameEnum;
import vn.com.msb.homeloan.core.constant.ContactStatusEnum;

@Data
@Entity
@Table(name = "loan_advise_customer", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanAdviseCustomerEntity extends BaseEntity {

  // loan_application_id
  @Column(name = "loan_application_id")
  String loanApplicationId;

  // advise_date
  @Column(name = "advise_date")
  Date adviseDate;

  // advise_time_frame
  @Column(name = "advise_time_frame")
  @Enumerated(EnumType.STRING)
  AdviseTimeFrameEnum adviseTimeFrame;

  // content
  @Column(name = "content")
  String content;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  ContactStatusEnum status;
}
