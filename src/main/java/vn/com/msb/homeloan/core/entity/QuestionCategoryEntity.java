package vn.com.msb.homeloan.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.core.constant.QuestionStatusEnum;

@Getter
@Setter
@ToString
@Entity
@Table(name = "question_category")
public class QuestionCategoryEntity extends BaseEntity {

  private String name;

  private String parent;

  private String product;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private QuestionStatusEnum status;
}
