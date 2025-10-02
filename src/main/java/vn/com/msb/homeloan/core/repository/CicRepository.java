package vn.com.msb.homeloan.core.repository;

import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.CicEntity;

@Repository
public interface CicRepository extends JpaRepository<CicEntity, String> {

  Optional<CicEntity> findByUuid(String uuid);

//    CicEntity findByLoanApplicationId(String loanApplicationId);

  @Query(value = "select * from cic c LEFT JOIN cic_item ci ON c.uuid = ci.cic_id where c.loan_application_id = :loanApplicationId AND ci.identity_card IN (:identityCards)", nativeQuery = true)
  CicEntity findByLoanApplicationIdAndIdentityCards(
      @Param("loanApplicationId") String loanApplicationId,
      @Param("identityCards") Set<String> identityCards);
}
