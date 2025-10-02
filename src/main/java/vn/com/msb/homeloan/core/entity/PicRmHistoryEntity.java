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
@Table(name = "pic_rm_history", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PicRmHistoryEntity extends BaseEntity {

  @Column(name = "loan_application_id")
  String loanApplicationId;

  @Column(name = "pic_from")
  String picFrom;

  @Column(name = "pic_to")
  String picTo;

  @Column(name = "_system")
  String system;

  @Column(name = "message_id")
  String messageId;
}
