package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.msb.homeloan.core.entity.LoanApplicationCommentEntity;

public interface LoanApplicationCommentRepository extends
    JpaRepository<LoanApplicationCommentEntity, String> {

  @Query(value = "select * from loan_application_comment cm where cm.loan_application_id = :loanApplicationId and code = :code order by created_at", nativeQuery = true)
  List<LoanApplicationCommentEntity> findByLoanApplicationIdAndCodeOrderByCreatedAt(
      String loanApplicationId, String code);

  @Query(value = "select * from loan_application_comment lac where lac.loan_application_id = :loanApplicationId and lac.status = :status order by created_at", nativeQuery = true)
  List<LoanApplicationCommentEntity> findByLoanApplicationIdAndStatusOrderByCreatedAt(
      @Param("loanApplicationId") String loanApplicationId, @Param("status") String status);

  @Query(value = "select * from loan_application_comment lac where lac.loan_application_id = :loanApplicationId and lac.status = :status order by field(code, 'CUSTOMER_INFO', 'MARRIED_INFO', 'CONTACT_INFO', 'PAYER_INFO', 'SALARY_INCOME_INFO', 'BUSINESS_INCOME_INFO', 'OTHER_INCOME_INFO', 'COLLATERAL_INFO', 'LOAN_INFO')", nativeQuery = true)
  List<LoanApplicationCommentEntity> findByLoanApplicationIdAndStatusOrderByField(
      String loanApplicationId, String status);
}
