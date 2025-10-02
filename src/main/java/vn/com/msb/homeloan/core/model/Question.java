package vn.com.msb.homeloan.core.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.core.constant.QuestionStatusEnum;

@Getter
@Setter
@ToString
public class Question {

  private String uuid;

  private String content;

  private QuestionStatusEnum status;

  private List<Answer> answer;
}
