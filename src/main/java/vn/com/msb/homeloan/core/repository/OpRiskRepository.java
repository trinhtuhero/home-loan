package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.constant.oprisk.OpRiskCheckTypeEnum;
import vn.com.msb.homeloan.core.entity.OpRiskEntity;

@Repository
public interface OpRiskRepository extends JpaRepository<OpRiskEntity, String> {

  Optional<OpRiskEntity> findByUuid(String uuid);

  List<OpRiskEntity> findByLoanApplicationIdAndCheckTypeAndActive(String loanApplicationId,
      OpRiskCheckTypeEnum checkType, boolean active);

  List<OpRiskEntity> findByLoanApplicationIdAndCheckType(String loanApplicationId,
      OpRiskCheckTypeEnum checkType);

  List<OpRiskEntity> findByLoanApplicationIdAndIdentityCardIn(String loanApplicationId,
      Set<String> identityCards);

  List<OpRiskEntity> findByLoanApplicationIdAndIdentityCardInAndActive(String loanApplicationId,
      Set<String> identityCards, boolean active);
}
