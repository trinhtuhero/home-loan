package vn.com.msb.homeloan.core.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.LoanApplicationSequenceEntity;

@Repository
public interface LoanApplicationSequenceRepository extends
    JpaRepository<LoanApplicationSequenceEntity, String> {

  Optional<LoanApplicationSequenceEntity> findByDay(Long day);
}
