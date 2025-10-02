package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.CollateralOwnerMapEntity;

@Repository
public interface CollateralOwnerMapRepository extends
    JpaRepository<CollateralOwnerMapEntity, String> {

  List<CollateralOwnerMapEntity> findByLoanId(String loanId);

  List<CollateralOwnerMapEntity> findByCollateralId(String collateralId);

  int deleteByCollateralId(String collateralOwnerId);

  int deleteByLoanIdAndCollateralId(String loanID, String collateralOwnerId);

  int deleteByLoanId(String loanId);
}
