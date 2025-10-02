package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.MtkTrackingEntity;

@Repository
public interface MtkTrackingRepository extends JpaRepository<MtkTrackingEntity, String> {

  List<MtkTrackingEntity> findByLoanId(String loanId);

  @Query(nativeQuery = true, value = "SELECT * FROM mtk_tracking WHERE (?1 IS NULL OR loan_id=?1) "
      +
      "AND (?2 IS NULL OR advise_id=?2)")
  List<MtkTrackingEntity> findByLoanIdOrAdviseId(String loanId, String adviseId);
}
