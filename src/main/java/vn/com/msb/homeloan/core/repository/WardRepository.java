package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.WardEntity;

@Repository
public interface WardRepository extends JpaRepository<WardEntity, String> {

  List<WardEntity> findByProvinceCodeAndDistrictCode(String provinceCode, String districtCode);

  List<WardEntity> findByMvalueProvinceCodeAndMvalueDistrictCode(String provinceCode, String districtCode);

  Optional<WardEntity> findByCode(String code);
}
