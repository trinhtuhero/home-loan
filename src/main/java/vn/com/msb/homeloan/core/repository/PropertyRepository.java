package vn.com.msb.homeloan.core.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.PropertyEntity;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, String> {

  Optional<PropertyEntity> findByName(String name);
}
