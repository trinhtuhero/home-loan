package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.com.msb.homeloan.core.entity.AnswerEntity;

public interface AnswerEntityRepository extends JpaRepository<AnswerEntity, String> {

  @Query("select a from AnswerEntity a where a.question.uuid = ?1")
  List<AnswerEntity> findByQuestionUuid(String uuid);

}