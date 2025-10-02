package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.CardTypeEntity;

@Repository
public interface CardTypeRepository extends JpaRepository<CardTypeEntity, String> {

  @Query(value = "select * from card_type where code in :codes", nativeQuery = true)
  List<CardTypeEntity> findByCodes(@Param("codes") List<String> codes);
}
