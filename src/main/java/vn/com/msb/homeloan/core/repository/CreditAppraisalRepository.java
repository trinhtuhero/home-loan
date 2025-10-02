package vn.com.msb.homeloan.core.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.CreditAppraisalEntity;

@Repository
public interface CreditAppraisalRepository extends JpaRepository<CreditAppraisalEntity, String> {

  Optional<CreditAppraisalEntity> findByLoanApplicationId(String loanApplicationId);
}
