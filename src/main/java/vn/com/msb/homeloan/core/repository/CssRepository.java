package vn.com.msb.homeloan.core.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.CssEntity;

@Repository
public interface CssRepository extends JpaRepository<CssEntity, String> {

  Optional<CssEntity> findByUuid(String uuid);

  CssEntity findByLoanApplicationId(String loanApplicationId);
}
