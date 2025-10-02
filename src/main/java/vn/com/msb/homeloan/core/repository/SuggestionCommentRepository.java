package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.SuggestionCommentEntity;

@Repository
public interface SuggestionCommentRepository extends
    JpaRepository<SuggestionCommentEntity, String> {

  @Query(value = "select * from suggestion_comment where rate_from <= :rate and rate_to >= :rate and status = 1", nativeQuery = true)
  List<SuggestionCommentEntity> findSuggestionComments(int rate);
}
