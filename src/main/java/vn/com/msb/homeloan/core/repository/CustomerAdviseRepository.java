package vn.com.msb.homeloan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.AdviseCustomerEntity;

@Repository
public interface CustomerAdviseRepository extends JpaRepository<AdviseCustomerEntity, String>,
    JpaSpecificationExecutor<AdviseCustomerEntity> {

}