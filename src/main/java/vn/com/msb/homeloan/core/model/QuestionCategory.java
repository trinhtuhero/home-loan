package vn.com.msb.homeloan.core.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.QuestionStatusEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionCategory {

  private String uuid;

  private String name;

  private String parent;

  private String product;

  private QuestionStatusEnum status;

  private List<Question> question;

  private List<QuestionCategory> category;

  public QuestionCategory(List<Question> questionList) {
    this.question = questionList;
  }
}
