package vn.com.msb.homeloan.core.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.msb.homeloan.core.entity.RequestPricingEntity;

public interface RequestPricingRepository extends
  JpaRepository<RequestPricingEntity, Integer> {

}