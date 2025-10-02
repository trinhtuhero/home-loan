package vn.com.msb.homeloan.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "feedback", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FeedbackEntity extends BaseEntity {

  // loan_application_id
  @Column(name = "loan_application_id")
  String loanApplicationId;

  // rate
  @Column(name = "rate")
  Integer rate;

  // additional_comment
  @Column(name = "additional_comment")
  String additionalComment;
}
