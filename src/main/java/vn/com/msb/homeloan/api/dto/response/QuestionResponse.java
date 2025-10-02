package vn.com.msb.homeloan.api.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.core.constant.QuestionStatusEnum;
import vn.com.msb.homeloan.core.model.Question;

@Getter
@Setter
@ToString
public class QuestionResponse {

  private String uuid;

  private String name;

  private String parent;

  private String product;

  private QuestionStatusEnum status;

  private List<Question> question;

  private List<QuestionResponse> category;
}
