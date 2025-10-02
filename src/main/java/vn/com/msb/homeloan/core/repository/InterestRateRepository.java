package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.com.msb.homeloan.core.entity.InterestRateEntity;

public interface InterestRateRepository extends JpaRepository<InterestRateEntity, String> {

  @Query("select i from InterestRateEntity i where i.key = ?1")
  List<InterestRateEntity> findAllByKey(String key);
}