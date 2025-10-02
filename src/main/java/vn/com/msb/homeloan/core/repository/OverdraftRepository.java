package vn.com.msb.homeloan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.constant.FormOfCreditEnum;
import vn.com.msb.homeloan.core.entity.OverdraftEntity;

import java.util.List;

@Repository
public interface OverdraftRepository extends JpaRepository<OverdraftEntity, String> {

  List<OverdraftEntity> findByLoanApplicationId(String loanApplicationId);

  List<OverdraftEntity> findByLoanApplicationIdOrderByCreatedAtAsc(String loanId);

  List<OverdraftEntity> findByLoanApplicationIdAndFormOfCredit(String loanId,
      FormOfCreditEnum formOfCreditEnum);
}
