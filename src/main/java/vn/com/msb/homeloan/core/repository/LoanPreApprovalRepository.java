package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.LoanPreApprovalEntity;

@Repository
public interface LoanPreApprovalRepository extends JpaRepository<LoanPreApprovalEntity, String> {

  @Query(value = "select * from loan_pre_approval where loan_id in :ids", nativeQuery = true)
  List<LoanPreApprovalEntity> findByLoanIds(@Param("ids") List<String> ids);

  Optional<LoanPreApprovalEntity> findOneByLoanId(String loanId);

}
