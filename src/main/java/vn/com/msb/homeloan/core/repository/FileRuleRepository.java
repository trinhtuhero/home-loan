package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.FileRuleEntity;

@Repository
public interface FileRuleRepository extends JpaRepository<FileRuleEntity, String> {

  List<FileRuleEntity> findAll();
}
