package vn.com.msb.homeloan.core.service.mockdata;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import vn.com.msb.homeloan.core.constant.QuestionStatusEnum;
import vn.com.msb.homeloan.core.entity.AnswerEntity;
import vn.com.msb.homeloan.core.entity.QuestionCategoryEntity;
import vn.com.msb.homeloan.core.entity.QuestionEntity;

public class QuestionDataBuild {


  public static List<QuestionCategoryEntity> buildQuestionCategoryEntities() {
    QuestionCategoryEntity category1 = new QuestionCategoryEntity();
    category1.setUuid("35150d39-ca97-4d49-a1e9-780c34ac6909");
    category1.setName("category1");
    category1.setStatus(QuestionStatusEnum.ACTIVE);
    category1.setProduct("BDS");

    QuestionCategoryEntity category2 = new QuestionCategoryEntity();
    category2.setUuid("a6213c6d-f5e1-4328-8560-370e54ae2ce1");
    category2.setName("category1");
    category2.setStatus(QuestionStatusEnum.ACTIVE);
    category2.setProduct("BDS");
    category2.setParent(category1.getUuid());
    List<QuestionCategoryEntity> entities = new ArrayList<>();
    entities.add(category1);
    entities.add(category2);
    return entities;
  }

  public static QuestionEntity buildQuestionEntity() {
    QuestionEntity question = new QuestionEntity();
    question.setQuestionCategory(buildQuestionCategoryEntities().get(1));
    question.setUuid(String.valueOf(UUID.randomUUID()));
    question.setContent("question");
    question.setStatus(QuestionStatusEnum.ACTIVE);

    return question;
  }

  public static AnswerEntity buildAnswerEntity() {
    AnswerEntity answer = new AnswerEntity();
    answer.setQuestion(buildQuestionEntity());
    answer.setUuid(String.valueOf(UUID.randomUUID()));
    answer.setContent("answer");
    answer.setStatus(QuestionStatusEnum.ACTIVE);

    return answer;
  }
}
