package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.ProductCodeConfigEntity;

@Repository
public interface ProductCodeConfigRepository extends
    JpaRepository<ProductCodeConfigEntity, String> {

  @Query(value = "select * from product_code_config where product_type = :productType and loan_time_from <= :from and loan_time_to >= :to and approval_flow = :approvalFlow and income_method = :incomeMethod", nativeQuery = true)
  List<ProductCodeConfigEntity> getProductConfig(@Param("productType") String productType,
      @Param("from") Integer from, @Param("to") Integer to,
      @Param("approvalFlow") String approvalFlow, @Param("incomeMethod") String incomeMethod);
}
