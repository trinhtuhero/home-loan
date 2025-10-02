package vn.com.msb.homeloan.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.QuestionStatusEnum;
import vn.com.msb.homeloan.core.entity.AnswerEntity;
import vn.com.msb.homeloan.core.entity.QuestionCategoryEntity;
import vn.com.msb.homeloan.core.entity.QuestionEntity;
import vn.com.msb.homeloan.core.model.Answer;
import vn.com.msb.homeloan.core.model.Question;
import vn.com.msb.homeloan.core.model.QuestionCategory;
import vn.com.msb.homeloan.core.model.mapper.AnswerMapper;
import vn.com.msb.homeloan.core.model.mapper.QuestionCategoryMapper;
import vn.com.msb.homeloan.core.model.mapper.QuestionMapper;
import vn.com.msb.homeloan.core.repository.AnswerEntityRepository;
import vn.com.msb.homeloan.core.repository.QuestionCategoryRepository;
import vn.com.msb.homeloan.core.repository.QuestionEntityRepository;
import vn.com.msb.homeloan.core.service.QuestionService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

  private final QuestionCategoryRepository repository;

  private final AnswerEntityRepository answerEntityRepository;

  private final QuestionEntityRepository questionEntityRepository;

  @Override
  @Cacheable(value = "frequently", key = "#products")
  public List<QuestionCategory> getFrequentlyQuestion(String product, Boolean oneLevel) {
    log.info("Start getFrequentlyQuestion by: {}", product);
    List<QuestionCategory> categoryList = new ArrayList<>();
    List<QuestionCategoryEntity> categoryEntities = repository.findByProductInAndStatus(product,
        QuestionStatusEnum.ACTIVE.name());
    log.info("Categories found: {}", categoryEntities);
    List<QuestionCategoryEntity> parentCateList = categoryEntities.stream()
        .filter(entity -> entity.getParent() == null).collect(
            Collectors.toList());
    log.info("Categories parent found: {}", parentCateList);

    List<Question> questionList = new ArrayList<>();
    for (QuestionCategoryEntity questionCategoryEntity : parentCateList) {
      List<Question> questions = new ArrayList<>();
      QuestionCategory questionCategory = buildDataQuestion(questionCategoryEntity,
          categoryEntities, questions);
      if (oneLevel == null || Boolean.TRUE.equals(oneLevel)) {
        questionList.addAll(questions);
      } else {
        categoryList.add(questionCategory);
      }
    }

    if (oneLevel == null || Boolean.TRUE.equals(oneLevel)) {
      categoryList.add(new QuestionCategory(questionList));
    }

    return categoryList;
  }

  private QuestionCategory buildDataQuestion(QuestionCategoryEntity parent,
      List<QuestionCategoryEntity> categoryEntities, List<Question> questionList) {
    log.info("Start buildDataQuestion for: {}", parent);
    categoryEntities.remove(parent);
    QuestionCategory category = QuestionCategoryMapper.INSTANCE.toDto(parent);
    List<QuestionCategoryEntity> questionCategoryEntities = categoryEntities.stream()
        .filter(entity -> entity.getParent() != null && entity.getParent().equals(parent.getUuid()))
        .collect(Collectors.toList());

    log.info("list children found: {}", questionCategoryEntities);
    if (questionCategoryEntities.isEmpty()) {
      log.info("Start get question and answer");
      List<Question> questions = new ArrayList<>();
      List<QuestionEntity> questionEntities = questionEntityRepository.findByQuestionCategoryUuid(
          parent.getUuid());
      questionEntities.forEach(questionEntity -> {
        List<AnswerEntity> answerEntities = answerEntityRepository.findByQuestionUuid(
            questionEntity.getUuid());
        Question question = QuestionMapper.INSTANCE.toDto(questionEntity);
        List<Answer> answers = answerEntities.stream().map(AnswerMapper.INSTANCE::toDto).collect(
            Collectors.toList());
        question.setAnswer(answers);
        questions.add(question);

        questionList.add(question);

      });
      category.setQuestion(questions);
      return category;
    }
    List<QuestionCategory> categoriesChild = new ArrayList<>();

    questionCategoryEntities.forEach(
        children -> categoriesChild.add(
            buildDataQuestion(children, categoryEntities, questionList)));
    category.setCategory(categoriesChild);

    log.info("category build success: {}", category);
    return category;
  }
}
