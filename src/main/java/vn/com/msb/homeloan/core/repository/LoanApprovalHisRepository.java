package vn.com.msb.homeloan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.LoanApprovalHisEntity;

@Repository
public interface LoanApprovalHisRepository extends JpaRepository<LoanApprovalHisEntity, String> {

}
