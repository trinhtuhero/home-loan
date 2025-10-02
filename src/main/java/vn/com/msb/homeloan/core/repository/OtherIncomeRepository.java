package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.OtherIncomeEntity;

@Repository
public interface OtherIncomeRepository extends JpaRepository<OtherIncomeEntity, String> {

  Optional<OtherIncomeEntity> findByUuid(String uuid);

  List<OtherIncomeEntity> findByLoanApplicationId(String loanApplicationId);

  List<OtherIncomeEntity> findByLoanApplicationIdOrderByCreatedAtAsc(String loanApplicationId);

  List<OtherIncomeEntity> findByLoanApplicationIdAndPayerId(String loanApplicationId,
      String payerId);

  int countByLoanApplicationId(String loanId);

  @Query(value = "select count(1) from other_incomes where loan_application_id = :loanId and income_from in :incomeFromIds", nativeQuery = true)
  int countByLoanIdEndIncomeFromIds(@Param("loanId") String loanId,
      @Param("incomeFromIds") List<String> incomeFromIds);
}
