package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.constant.CardPriorityEnum;
import vn.com.msb.homeloan.core.entity.CreditCardEntity;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCardEntity, String> {

  Optional<CreditCardEntity> findByUuid(String uuid);

  List<CreditCardEntity> findByLoanId(String loanId);

  List<CreditCardEntity> findByLoanIdAndCardPriority(String loanId, CardPriorityEnum cardPriority);

  List<CreditCardEntity> findByLoanIdAndCardPriorityOrderByCreatedAtAsc(String loanId,
      CardPriorityEnum cardPriority);

  List<CreditCardEntity> findByLoanIdOrderByCreatedAtAsc(String loanId);

  List<CreditCardEntity> findByLoanIdAndCardPriorityAndParentId(String loanId,
      CardPriorityEnum cardPriority, String parentId);
}
