package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;

@Repository
public interface MarriedPersonRepository extends JpaRepository<MarriedPersonEntity, String> {

  Optional<MarriedPersonEntity> findByUuid(String uuid);

  MarriedPersonEntity findOneByLoanId(String loanId);

  List<MarriedPersonEntity> findByLoanId(String loanId);
}
