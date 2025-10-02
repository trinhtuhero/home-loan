package vn.com.msb.homeloan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.PicRmHistoryEntity;

@Repository
public interface PicRmHistoryRepository extends JpaRepository<PicRmHistoryEntity, String> {

  @Query(value = "SELECT * FROM pic_rm_history WHERE loan_application_id = ?1 order by created_at asc limit 1", nativeQuery = true)
  PicRmHistoryEntity findFirstPic(String loanId);
}
