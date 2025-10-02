package vn.com.msb.homeloan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.FeedbackSuggestionMapEntity;

@Repository
public interface FeedbackSuggestionMapRepository extends
    JpaRepository<FeedbackSuggestionMapEntity, String> {

}
