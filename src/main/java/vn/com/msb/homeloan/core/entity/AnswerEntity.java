package vn.com.msb.homeloan.core.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.core.constant.QuestionStatusEnum;

@Getter
@Setter
@ToString
@Entity
@Table(name = "answer")
public class AnswerEntity extends BaseEntity {

  private String content;

  @Enumerated(EnumType.STRING)
  private QuestionStatusEnum status;

  @ManyToOne(targetEntity = QuestionEntity.class)
  @JoinColumn(name = "question_id")
  private QuestionEntity question;
}
