package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.entity.CmsTabActionEntity;

@Repository
public interface CMSTabActionRepository extends JpaRepository<CmsTabActionEntity, String> {

  Optional<CmsTabActionEntity> findByLoanIdAndTabCode(String loanId, CMSTabEnum tabCode);

  List<CmsTabActionEntity> findByLoanId(String loanId);
}
