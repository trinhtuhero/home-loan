package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.com.msb.homeloan.core.entity.LoanItemCollateralDistributionEntity;

@Repository
public interface LoanItemCollateralDistributeRepository extends
    JpaRepository<LoanItemCollateralDistributionEntity, String> {

  @Query(value = "select * from loan_item_collateral_distribution where loan_item_id IN (select uuid from loan_application_item where loan_application_id = :loanId order by created_at asc)", nativeQuery = true)
  List<LoanItemCollateralDistributionEntity> getByLoanApplicationId(@Param("loanId") String loanId);

  @Query(value = "select * from loan_item_collateral_distribution where (loan_item_id IN (select uuid from overdraft where loan_application_id = :loanId order by created_at asc) or loan_item_id IN (select uuid from loan_application_item where loan_application_id = :loanId order by created_at asc)) and type = :type", nativeQuery = true)
  List<LoanItemCollateralDistributionEntity> getByLoanApplicationIdAndType(
      @Param("loanId") String loanId, @Param("type") String type);

  @Query(value = "select * from loan_item_collateral_distribution where (loan_item_id IN (select uuid from overdraft where loan_application_id = :loanId order by created_at asc) or loan_item_id IN (select uuid from loan_application_item where loan_application_id = :loanId order by created_at asc)) and collateral_id = :collateralId", nativeQuery = true)
  List<LoanItemCollateralDistributionEntity> getByLoanApplicationIdAndCollateralId(
      @Param("loanId") String loanId, @Param("collateralId") String collateralId);

  @Query(value = "select * from loan_item_collateral_distribution where loan_item_id IN (select uuid from overdraft where loan_application_id = :loanId and type = 'THAU_CHI' order by created_at asc)", nativeQuery = true)
  List<LoanItemCollateralDistributionEntity> getOverdraftDistribute(@Param("loanId") String loanId);

  List<LoanItemCollateralDistributionEntity> findByCollateralId(String collateralId);

  @Transactional
  @Modifying
  @Query("delete from LoanItemCollateralDistributionEntity l where l.loanItemId = ?1")
  void deleteByLoanItemId(String loanItemId);


}
