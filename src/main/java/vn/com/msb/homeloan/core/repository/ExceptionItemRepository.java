package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.ExceptionItemEntity;

@Repository
public interface ExceptionItemRepository extends JpaRepository<ExceptionItemEntity, String> {

  List<ExceptionItemEntity> findByLoanApplicationId(String loanApplicationId);

  List<ExceptionItemEntity> findByLoanApplicationIdOrderByCreatedAtAsc(String loanApplicationId);

  int countByLoanApplicationIdAndCreditAppraisalId(String loanApplicationId,
      String creditAppraisalId);

  int countByLoanApplicationId(String loanApplicationId);
}
