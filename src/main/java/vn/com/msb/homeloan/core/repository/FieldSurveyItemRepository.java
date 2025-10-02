package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.FieldSurveyItemEntity;

@Repository
public interface FieldSurveyItemRepository extends JpaRepository<FieldSurveyItemEntity, String> {

  List<FieldSurveyItemEntity> findByLoanApplicationId(String loanApplicationId);

  List<FieldSurveyItemEntity> findByLoanApplicationIdOrderByCreatedAt(String loanApplicationId);
}
