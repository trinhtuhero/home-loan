package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.CicItemEntity;

@Repository
public interface CicItemRepository extends JpaRepository<CicItemEntity, String> {

  Optional<CicItemEntity> findByUuid(String uuid);

  CicItemEntity findByCicIdAndIdentityCard(String cicId, String identityCard);

  @Query(value = "select * from cic_item ci LEFT JOIN cic c ON ci.cic_id = c.uuid where c.loan_application_id = :loanApplicationId AND ci.identity_card IN (:identityCards)", nativeQuery = true)
  List<CicItemEntity> findByLoanApplicationAndIdentityCardIn(
      @Param("loanApplicationId") String loanApplicationId,
      @Param("identityCards") Set<String> identityCards);

  @Query(value = "select * from cic_item ci LEFT JOIN cic c ON ci.cic_id = c.uuid where c.loan_application_id = :loanApplicationId AND ci.identity_card IN (:identityCards) and active = :active", nativeQuery = true)
  List<CicItemEntity> findByLoanApplicationAndIdentityCardInAndActive(
      @Param("loanApplicationId") String loanApplicationId,
      @Param("identityCards") Set<String> identityCards, @Param("active") boolean active);
}
