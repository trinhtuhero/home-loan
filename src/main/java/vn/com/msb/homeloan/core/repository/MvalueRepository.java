package vn.com.msb.homeloan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.MvalueEntity;

@Repository
public interface MvalueRepository extends JpaRepository<MvalueEntity, String> {

  MvalueEntity findByLoanApplicationIdAndAssetCode(String loanId, String assetCode);
}
