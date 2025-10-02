package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.CreditInstitutionEntity;

@Repository
public interface CreditInstitutionRepository extends
    JpaRepository<CreditInstitutionEntity, String> {

  Optional<CreditInstitutionEntity> findByCode(String code);

  @Query(value = "select * from credit_institution where code in :codes", nativeQuery = true)
  List<CreditInstitutionEntity> findByCodes(@Param("codes") List<String> codes);
}
