package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.com.msb.homeloan.core.entity.CreditworthinessItemEntity;

@Repository
public interface CreditworthinessItemRepository extends
    JpaRepository<CreditworthinessItemEntity, String> {

  List<CreditworthinessItemEntity> findByLoanApplicationId(String loanApplicationId);

  List<CreditworthinessItemEntity> findByLoanApplicationIdOrderByCreatedAt(
      String loanApplicationId);

  List<CreditworthinessItemEntity> findByLoanApplicationIdAndTarget(String loanApplicationId,
      String target);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM creditworthiness_items" +
      " WHERE loan_application_id = :loanId and (loan_application_item_id = :loanApplicationItemId or credit_card_id = :creditCardId) ", nativeQuery = true)
  int deleteByConditions(@Param("loanId") String loanId,
      @Param("loanApplicationItemId") String loanApplicationItemId,
      @Param("creditCardId") String creditCardId);

  int countByLoanApplicationIdAndLoanApplicationItemIdAndCreditCardId(String loanId,
      String loanApplicationItemId, String creditCardId);
}
