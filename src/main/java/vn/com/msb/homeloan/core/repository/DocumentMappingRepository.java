package vn.com.msb.homeloan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.DocumentMappingEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentMappingRepository extends JpaRepository<DocumentMappingEntity, String> {
    List<DocumentMappingEntity> findByCollateralTypeAndType(String collateralType, String type);

    Optional<DocumentMappingEntity> findById(long id);
}
