package vn.com.msb.homeloan.core.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.AssetEvaluateEntity;

@Repository
public interface AssetEvaluateRepository extends JpaRepository<AssetEvaluateEntity, String> {

  Optional<AssetEvaluateEntity> findByUuid(String uuid);

  AssetEvaluateEntity findByLoanApplicationId(String loanApplicationId);

  int deleteByLoanApplicationId(String loanId);
}
