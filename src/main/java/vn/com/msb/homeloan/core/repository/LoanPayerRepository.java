package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;

@Repository
public interface LoanPayerRepository extends JpaRepository<LoanPayerEntity, String> {

  List<LoanPayerEntity> findByLoanId(String loanId);

  List<LoanPayerEntity> findByLoanIdOrderByCreatedAtAsc(String loanId);

  Optional<LoanPayerEntity> findByUuid(String uuid);

  Long countByLoanId(String loanId);

  @Query(value = "select * from loan_payers lp where lp.loan_application_id = :loanId AND (lp.id_no IN (:identities) or lp.old_id_no IN (:identities)) limit 1", nativeQuery = true)
  LoanPayerEntity findByLoanIdAndIdentities(String loanId, Set<String> identities);
}
