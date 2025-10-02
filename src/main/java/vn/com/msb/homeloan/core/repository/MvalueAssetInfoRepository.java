package vn.com.msb.homeloan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.MvalueAssetInfoEntity;

@Repository
public interface MvalueAssetInfoRepository extends JpaRepository<MvalueAssetInfoEntity, String> {

  MvalueAssetInfoEntity findByMvalueIdAndAssetIdMvalue(String mvalueId, Long assetId);
}
