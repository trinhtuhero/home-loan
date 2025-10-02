package vn.com.msb.homeloan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.LoanAdviseCustomerEntity;

@Repository
public interface LoanAdviseCustomerRepository extends
    JpaRepository<LoanAdviseCustomerEntity, String> {

  LoanAdviseCustomerEntity findByLoanApplicationId(String loanApplicationId);
}