package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.msb.homeloan.core.entity.DebtPaymentMethodEntity;

public interface DebtPaymentMethodRepository extends
    JpaRepository<DebtPaymentMethodEntity, String> {

  List<DebtPaymentMethodEntity> findAllByKey(String key);
}