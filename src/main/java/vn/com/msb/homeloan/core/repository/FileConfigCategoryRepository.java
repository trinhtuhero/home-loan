package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.FileConfigCategoryEntity;

@Repository
public interface FileConfigCategoryRepository extends
    JpaRepository<FileConfigCategoryEntity, String> {

  @Query(value = "SELECT * FROM file_config_categories WHERE uuid IN (?1)", nativeQuery = true)
  List<FileConfigCategoryEntity> findByIds(List<String> list);

  Optional<FileConfigCategoryEntity> findByUuid(String uuid);

  @Query(value = "SELECT * FROM file_config_categories WHERE uuid IN (?1) ORDER BY FIELD(uuid, ?1)", nativeQuery = true)
  List<FileConfigCategoryEntity> findByIdsKeepOrder(List<String> list);
}
