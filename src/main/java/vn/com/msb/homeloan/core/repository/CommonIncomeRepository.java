package vn.com.msb.homeloan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.CommonIncomeEntity;

@Repository
public interface CommonIncomeRepository extends JpaRepository<CommonIncomeEntity, String> {

  CommonIncomeEntity findByLoanApplicationId(String loanId);
}

