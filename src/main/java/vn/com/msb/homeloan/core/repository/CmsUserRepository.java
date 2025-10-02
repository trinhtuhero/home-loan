package vn.com.msb.homeloan.core.repository;

import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.CmsUserEntity;

@Repository
public interface CmsUserRepository extends JpaRepository<CmsUserEntity, String>,
    CmsUserRepositoryCustom {

  Optional<CmsUserEntity> findByEmail(String email);

  Optional<CmsUserEntity> findByEmplId(String emplId);

  @Query("select u from CmsUserEntity u where u.email in :emails ")
  Set<CmsUserEntity> findByEmails(@Param("emails") Set<String> emails);

  List<CmsUserEntity> findByBranchCode(String branchCode);
}
