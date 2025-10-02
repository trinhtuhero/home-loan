package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.model.QuestionCategory;
import vn.com.msb.homeloan.core.repository.AnswerEntityRepository;
import vn.com.msb.homeloan.core.repository.QuestionCategoryRepository;
import vn.com.msb.homeloan.core.repository.QuestionEntityRepository;
import vn.com.msb.homeloan.core.service.QuestionService;
import vn.com.msb.homeloan.core.service.mockdata.QuestionDataBuild;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

  QuestionService questionService;

  @Mock
  private QuestionCategoryRepository questionCategoryRepository;

  @Mock
  private AnswerEntityRepository answerEntityRepository;

  @Mock
  private QuestionEntityRepository questionEntityRepository;

  @BeforeEach
  public void setup() {
    questionService = new QuestionServiceImpl(questionCategoryRepository,
        answerEntityRepository, questionEntityRepository);
  }

  @Test
  void test__getFrequentlyQuestion__thenReturnSuccess() {
    doReturn(QuestionDataBuild.buildQuestionCategoryEntities()).when(questionCategoryRepository)
        .findByProductInAndStatus(anyString(), anyString());
    doReturn(Collections.singletonList(QuestionDataBuild.buildQuestionEntity())).when(
            questionEntityRepository)
        .findByQuestionCategoryUuid(anyString());
    doReturn(Collections.singletonList(QuestionDataBuild.buildAnswerEntity())).when(
            answerEntityRepository)
        .findByQuestionUuid(anyString());

    List<QuestionCategory> categories = questionService.getFrequentlyQuestion("test", false);

    assertEquals(1, categories.size());
  }
}
