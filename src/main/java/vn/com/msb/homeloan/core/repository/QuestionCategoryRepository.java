package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.com.msb.homeloan.core.entity.QuestionCategoryEntity;

public interface QuestionCategoryRepository extends
    JpaRepository<QuestionCategoryEntity, String> {

  @Query(value = "select c.* "
      + "from question_category c "
      + "where c.product like %?1% and c.status = ?2 order by created_at ASC", nativeQuery = true)
  List<QuestionCategoryEntity> findByProductInAndStatus(String product,
      String status);
}
