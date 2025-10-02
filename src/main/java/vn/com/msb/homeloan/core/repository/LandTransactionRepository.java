package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.com.msb.homeloan.core.entity.LandTransactionEntity;

public interface LandTransactionRepository extends
    JpaRepository<LandTransactionEntity, String>,
    JpaSpecificationExecutor<LandTransactionEntity> {

  void deleteByUuidNotInAndOtherIncomeId(
      List<String> uuids, String otherIncomeId);

  void deleteByOtherIncomeId(String otherIncomeId);

  Optional<LandTransactionEntity> findByUuidAndOtherIncomeId(String uuid, String otherIncomeId);


  List<LandTransactionEntity> findAllByOtherIncomeIdOrderByCreatedAt(String otherIncomeId);
}