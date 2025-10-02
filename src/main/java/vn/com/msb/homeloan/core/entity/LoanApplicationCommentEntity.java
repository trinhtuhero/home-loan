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
import vn.com.msb.homeloan.core.constant.CommentStatusEnum;
import vn.com.msb.homeloan.core.constant.LoanApplicationCommentEnum;

@Data
@Entity
@Table(name = "loan_application_comment", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanApplicationCommentEntity extends BaseEntity {

  @Column(name = "loan_application_id")
  String loanApplicationId;

  @Column(name = "code")
  @Enumerated(EnumType.STRING)
  LoanApplicationCommentEnum code;

  @Column(name = "comment")
  String comment;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  CommentStatusEnum status;

  @Column(name = "empl_id")
  String emplId;
}
