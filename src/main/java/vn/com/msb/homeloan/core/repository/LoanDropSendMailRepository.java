package vn.com.msb.homeloan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.LoanDropSendMailEntity;

@Repository
public interface LoanDropSendMailRepository extends JpaRepository<LoanDropSendMailEntity, String> {

}
