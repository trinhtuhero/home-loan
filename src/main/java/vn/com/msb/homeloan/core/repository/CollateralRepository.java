package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.CollateralEntity;

@Repository
public interface CollateralRepository extends JpaRepository<CollateralEntity, String> {

  List<CollateralEntity> findByLoanId(String loanId);

  List<CollateralEntity> findByLoanIdOrderByCreatedAtAsc(String loanId);

  @Query(value = "select COALESCE(sum(value), 0) from collaterals where loan_application_id = :loanId", nativeQuery = true)
  long totalValues(String loanId);

  @Query(value = "select COALESCE(sum(guaranteed_value), 0) from collaterals where loan_application_id = :loanId", nativeQuery = true)
  long totalGuaranteedValues(String loanId);
}
