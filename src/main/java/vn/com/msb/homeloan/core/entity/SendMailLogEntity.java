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
@Table(name = "send_mail_log", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendMailLogEntity extends BaseEntity {

  @Column(name = "loan_id")
  String loanId;

  @Column(name = "send_mail_to")
  String sendMailTo;

  @Column(name = "email_type")
  String emailType;

  @Column(name = "status")
  String status;

  @Column(name = "description")
  String description;

  @Column(name = "meta_data")
  String metaData;
}
