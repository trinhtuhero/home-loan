package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.com.msb.homeloan.core.entity.AssetEvaluateItemEntity;

@Repository
public interface AssetEvaluateItemRepository extends
    JpaRepository<AssetEvaluateItemEntity, String> {

  Optional<AssetEvaluateItemEntity> findByUuid(String uuid);

  List<AssetEvaluateItemEntity> findByAssetEvalId(String assetEvalId);

  List<AssetEvaluateItemEntity> findByAssetEvalIdOrderByCreatedAtAsc(String assetEvalId);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM asset_evaluate_item" +
      " WHERE asset_eval_id IN (select uuid from asset_evaluate where loan_application_id = :loanId) "
      , nativeQuery = true)
  int deleteByLoanId(@Param("loanId") String loanId);
}
