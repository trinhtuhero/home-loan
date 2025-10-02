package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.FileConfigEntity;

@Repository
public interface FileConfigRepository extends JpaRepository<FileConfigEntity, String> {

  /*@Query(value = "SELECT * FROM file_config WHERE uuid IN (SELECT file_config_id FROM file_config_rule WHERE file_rule_id IN (?1)) ORDER BY FIELD(uuid, ?1)", nativeQuery = true)*/
  @Query(value = "SELECT * FROM file_config WHERE uuid IN (SELECT file_config_id FROM file_config_rule WHERE file_rule_id IN (?1)) ORDER BY order_number", nativeQuery = true)
  List<FileConfigEntity> findByFileRuleIds(List<String> list);

  @Query(value = "SELECT * FROM file_config WHERE uuid IN (SELECT file_config_id FROM cms_file_config_rule WHERE cms_file_rule_id IN (?1)) ORDER BY order_number", nativeQuery = true)
  List<FileConfigEntity> findByCmsFileRuleIds(List<String> list);

  @Query(value = "SELECT * FROM file_config WHERE uuid IN (?1)", nativeQuery = true)
  List<FileConfigEntity> findByIds(List<String> list);

  @Query(value = "SELECT * FROM file_config WHERE uuid IN (?1) ORDER BY FIELD(uuid, ?1)", nativeQuery = true)
  List<FileConfigEntity> findByIdsKeepOrder(List<String> list);

  Optional<FileConfigEntity> findByCode(String code);

}
