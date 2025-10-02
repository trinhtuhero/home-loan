package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.ProvinceEntity;

@Repository
public interface ProvinceRepository extends JpaRepository<ProvinceEntity, String> {

  List<ProvinceEntity> findByCodeNotNullOrderByOrderAsc();

  Optional<ProvinceEntity> findByCode(String code);

  List<ProvinceEntity> findByMvalueCodeNotNullOrderByOrderAsc();
}
