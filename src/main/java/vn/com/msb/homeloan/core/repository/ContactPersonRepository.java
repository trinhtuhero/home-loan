package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.ContactPersonEntity;

@Repository
public interface ContactPersonRepository extends JpaRepository<ContactPersonEntity, String> {

  ContactPersonEntity findOneByLoanId(String loanId);

  List<ContactPersonEntity> findByLoanId(String loanId);
}
