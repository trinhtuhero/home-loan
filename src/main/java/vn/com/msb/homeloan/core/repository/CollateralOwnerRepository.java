package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.com.msb.homeloan.core.entity.CollateralOwnerEntity;

@Repository
public interface CollateralOwnerRepository extends JpaRepository<CollateralOwnerEntity, String> {

  List<CollateralOwnerEntity> findByLoanId(String loanId);

  List<CollateralOwnerEntity> findByLoanIdOrderByCreatedAtAsc(String loanId);

  @Query(value = "select * from collateral_owner co where co.loan_id = :loanId AND (co.id_no IN (:identities) or co.old_id_no IN (:identities)) limit 1", nativeQuery = true)
  CollateralOwnerEntity findByLoanIdAndIdentities(String loanId, Set<String> identities);

  int deleteByLoanId(String loanId);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM collateral_owner " +
      " where loan_id = :loanId " +
      "       and uuid not in (SELECT collateral_owner_id FROM collateral_owner_map where loan_id = :loanId) "
      , nativeQuery = true)
  int refreshData(@Param("loanId") String loanId);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM collateral_owner" +
      " WHERE uuid IN (select collateral_owner_id from collateral_owner_map where loan_id = :loanId and collateral_id = :collateralId) "
      , nativeQuery = true)
  int deleteByLoanIdEndCollateralId(@Param("loanId") String loanId,
      @Param("collateralId") String collateralId);

  @Query(value = "select * from collateral_owner co\n" +
      "join collateral_owner_map com on co.uuid = com.collateral_owner_id\n" +
      "where com.collateral_id = (select uuid from collaterals where uuid = com.collateral_id)\n" +
      "and com.collateral_id = :collateralId", nativeQuery = true)
  List<CollateralOwnerEntity> getOwnersOfCollateral(String collateralId);

  @Query(value = "select * from collateral_owner where uuid in (select collateral_owner_id from collateral_owner_map where loan_id = :loanId)", nativeQuery = true)
  List<CollateralOwnerEntity> getByLoanId(@Param("loanId") String loanId);
}
