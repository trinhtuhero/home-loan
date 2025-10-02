package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.CmsFileRuleEntity;

@Repository
public interface CmsFileRuleRepository extends JpaRepository<CmsFileRuleEntity, String> {

  List<CmsFileRuleEntity> findAll();
}
