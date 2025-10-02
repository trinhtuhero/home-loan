package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.DistrictEntity;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity, String> {

  List<DistrictEntity> findByProvinceCode(String provinceCode);

  List<DistrictEntity> findByMvalueProvinceCode(String Code);

  Optional<DistrictEntity> findByCode(String code);
}
