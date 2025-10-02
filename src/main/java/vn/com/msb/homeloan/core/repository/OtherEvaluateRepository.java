package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.OtherEvaluateEntity;

@Repository
public interface OtherEvaluateRepository extends JpaRepository<OtherEvaluateEntity, String> {

  Optional<OtherEvaluateEntity> findByUuid(String uuid);

  List<OtherEvaluateEntity> findByLoanApplicationId(String loanApplicationId);

  List<OtherEvaluateEntity> findByLoanApplicationIdOrderByCreatedAtAsc(String loanApplicationId);

  List<OtherEvaluateEntity> findByLoanApplicationIdAndPayerId(String loanApplicationId,
      String payerId);

  int deleteByLoanApplicationId(String loanApplicationId);
}
