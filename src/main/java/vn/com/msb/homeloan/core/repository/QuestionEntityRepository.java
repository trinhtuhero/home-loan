package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.com.msb.homeloan.core.entity.QuestionEntity;

public interface QuestionEntityRepository extends JpaRepository<QuestionEntity, String> {

  @Query("select q from QuestionEntity q where q.questionCategory.uuid = ?1 order by createdAt ASC")
  List<QuestionEntity> findByQuestionCategoryUuid(String uuid);

}