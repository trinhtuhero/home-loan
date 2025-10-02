package vn.com.msb.homeloan.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.core.constant.QuestionStatusEnum;

@ToString
@Getter
@Setter
public class Answer {

  private String uuid;

  private String content;

  private QuestionStatusEnum status;
}
