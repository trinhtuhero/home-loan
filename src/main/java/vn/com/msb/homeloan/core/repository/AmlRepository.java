package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.AmlEntity;

@Repository
public interface AmlRepository extends JpaRepository<AmlEntity, String> {

  AmlEntity findByLoanApplicationIdAndIdPassport(String loanId, String idPassport);

  List<AmlEntity> findByLoanApplicationIdAndIdPassportIn(String loanId, Set<String> idPassports);
}
