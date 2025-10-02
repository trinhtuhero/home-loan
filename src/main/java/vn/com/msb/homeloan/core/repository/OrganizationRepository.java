package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.OrganizationEntity;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, String> {

  List<OrganizationEntity> findByTypeAndStatus(String type, String status);

  List<OrganizationEntity> findByAreaCodeAndStatus(String areaCode, String status);

  @Query("select o from OrganizationEntity  o where o.code in :brands")
  List<OrganizationEntity> findByBrandCode(@Param("brands") Set<String> brands);

  OrganizationEntity findByCode(String code);
}
