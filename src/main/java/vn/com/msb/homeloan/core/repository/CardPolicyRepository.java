package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.CardPolicyEntity;

@Repository
public interface CardPolicyRepository extends JpaRepository<CardPolicyEntity, String> {

  Optional<CardPolicyEntity> findByCode(String code);

  @Query(value = "select * from card_policy where code in :codes", nativeQuery = true)
  List<CardPolicyEntity> findByCodes(@Param("codes") List<String> codes);
}
