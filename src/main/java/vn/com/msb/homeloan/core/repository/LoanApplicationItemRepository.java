package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.constant.LoanPurposeEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationItemEntity;

@Repository
public interface LoanApplicationItemRepository extends
    JpaRepository<LoanApplicationItemEntity, String> {
  //NOTE: Can luu y vs ban ghi la co product_text_code = CREDIT_CARD -> phan nay chi phuc vu cho viec search

  @Query(value = "select * from loan_application_item where loan_application_id = :loanId and loan_purpose not in ('CREDIT_CARD')", nativeQuery = true)
  List<LoanApplicationItemEntity> findByLoanApplicationIdWithoutCreditCard(
      @Param("loanId") String loanId);

  @Query(value = "select * from loan_application_item where loan_application_id = :loanId and loan_purpose not in ('CREDIT_CARD', 'THAU_CHI')", nativeQuery = true)
  List<LoanApplicationItemEntity> findByLoanApplicationIdWithoutCreditCardAndOverdraft(
      @Param("loanId") String loanId);

  List<LoanApplicationItemEntity> findByLoanApplicationId(@Param("loanId") String loanId);

  List<LoanApplicationItemEntity> findByLoanApplicationIdAndLoanPurposeNotIn(String loanId,
      List<LoanPurposeEnum> loanPurpose);

  List<LoanApplicationItemEntity> findByLoanApplicationIdAndLoanPurpose(String loanId,
      LoanPurposeEnum loanPurpose);

  @Query(value = "select * from loan_application_item where loan_application_id = :loanId and loan_purpose not in ('CREDIT_CARD', 'THAU_CHI') order by created_at asc", nativeQuery = true)
  List<LoanApplicationItemEntity> findByLoanApplicationIdOrderByCreatedAtAsc(
      @Param("loanId") String loanId);

  @Query(value = "select COALESCE(sum(loan_amount), 0) from loan_application_item where loan_application_id = :loanId and (product_text_code != 'CREDIT_CARD' or product_text_code is null)", nativeQuery = true)
  long totalLoanAmount(@Param("loanId") String loanId);

  @Query(value = "select count(1) from loan_application_item where loan_application_id = :loanId and (product_text_code != 'CREDIT_CARD' and product_text_code is not null)", nativeQuery = true)
  int countByLoanApplicationId(@Param("loanId") String loanId);
}
