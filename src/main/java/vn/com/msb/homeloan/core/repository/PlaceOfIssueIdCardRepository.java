package vn.com.msb.homeloan.core.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.PlaceOfIssueIdCardEntity;

@Repository
public interface PlaceOfIssueIdCardRepository extends
    JpaRepository<PlaceOfIssueIdCardEntity, String> {

  Optional<PlaceOfIssueIdCardEntity> findByCode(String Code);
}
