package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.SalaryIncomeEntity;

@Repository
public interface SalaryIncomeRepository extends JpaRepository<SalaryIncomeEntity, Long> {

  Optional<SalaryIncomeEntity> findByUuid(String uuid);

  List<SalaryIncomeEntity> findByLoanApplicationId(String loanApplicationId);

  List<SalaryIncomeEntity> findByLoanApplicationIdOrderByCreatedAtAsc(String loanApplicationId);

  List<SalaryIncomeEntity> findByLoanApplicationIdAndPayerId(String loanApplicationId,
      String payerId);

  int countByLoanApplicationId(String loanId);
}
