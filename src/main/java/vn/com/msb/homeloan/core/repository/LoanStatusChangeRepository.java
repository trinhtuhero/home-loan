package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.LoanStatusChangeEntity;

@Repository
public interface LoanStatusChangeRepository extends JpaRepository<LoanStatusChangeEntity, String> {

  List<LoanStatusChangeEntity> findByLoanApplicationId(String loanApplicationId);

  @Query(value = "select * from loan_status_change where loan_application_id = :loanApplicationId and status_to in ('SUBMIT_LOAN_REQUEST', 'SUBMIT_LOAN_APPLICATION') order by created_at desc limit 1", nativeQuery = true)
  LoanStatusChangeEntity getLastestSubmitByCustomer(String loanApplicationId);

  @Query(value = "select \n" +
      "    count(1)\n" +
      "from\n" +
      "    loan_status_change\n" +
      "where\n" +
      "    loan_application_id = :loanApplicationId\n" +
      "        and ((status_from = 'DRAFT'\n" +
      "        and status_to = 'SUBMIT_LOAN_REQUEST')\n" +
      "        or (status_from = 'DRAFT'\n" +
      "        and status_to = 'SUBMIT_LOAN_APPLICATION')\n" +
      "        or (status_from = 'NEED_ADDITIONAL_INFO'\n" +
      "        and status_to = 'ACCEPT_LOAN_REQUEST')\n" +
      "        or (status_from = 'NEED_ADDITIONAL_INFO'\n" +
      "        and status_to = 'ACCEPT_LOAN_APPLICATION')\n" +
      "        or (status_from = 'WAITING_FOR_CONFIRM'\n" +
      "        and status_to = 'RM_COMPLETED'))", nativeQuery = true)
  int countLoanSubmited(String loanApplicationId);
}
