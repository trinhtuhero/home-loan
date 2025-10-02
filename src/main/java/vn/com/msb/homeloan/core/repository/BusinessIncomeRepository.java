package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.constant.BusinessTypeEnum;
import vn.com.msb.homeloan.core.entity.BusinessIncomeEntity;

@Repository
public interface BusinessIncomeRepository extends JpaRepository<BusinessIncomeEntity, String> {

  Optional<BusinessIncomeEntity> findByUuid(String uuid);

  List<BusinessIncomeEntity> findByLoanApplicationId(String loanApplicationId);

  List<BusinessIncomeEntity> findByLoanApplicationIdAndBusinessTypeIn(String loanApplicationId,
      List<BusinessTypeEnum> businessTypes);

  List<BusinessIncomeEntity> findByLoanApplicationIdOrderByCreatedAtAsc(String loanApplicationId);

  List<BusinessIncomeEntity> findByLoanApplicationIdAndPayerId(String loanApplicationId,
      String payerId);

  Long countByLoanApplicationIdAndBusinessType(String loanId, BusinessTypeEnum businessTypeEnum);

  int countByLoanApplicationIdAndBusinessTypeIn(String loanId,
      List<BusinessTypeEnum> businessTypes);
}
