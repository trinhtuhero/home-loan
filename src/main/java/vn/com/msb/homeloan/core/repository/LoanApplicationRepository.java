package vn.com.msb.homeloan.core.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, String>,
    LoanApplicationRepositoryCustom {

  Optional<LoanApplicationEntity> findByUuid(String uuid);

  List<LoanApplicationEntity> findByProfileId(String profileId);

  LoanApplicationEntity findByLoanCode(String loanCode);

  List<LoanApplicationEntity> findByProfileIdOrderByUpdatedAtDesc(String profileId);

  @Modifying
  @Transactional
  @Query(value = "update loan_applications set status = :status, loan_code = if(:loanCode is null, loan_code, :loanCode), current_step = if(:currentStep is null, current_step, :currentStep), download_status = if(:downloadStatus is null, download_status, :downloadStatus), mobio_status = if(:mobioStatus is null, mobio_status, :mobioStatus), ref_code = if(:refEmail is null, ref_code, :refEmail) where uuid = :uuid", nativeQuery = true)
  int updateStatus(@Param("status") String status, @Param("loanCode") String loanCode,
      @Param("uuid") String uuid, @Param("currentStep") String currentStep,
      @Param("downloadStatus") Integer downloadStatus, @Param("mobioStatus") String mobioStatus,
      @Param("refEmail") String refEmail);


  @Query(value = "select * from loan_applications la where la.download_status = :downloadStatus and la.status != 'DRAFT'", nativeQuery = true)
  List<LoanApplicationEntity> getLoanApplicationListNeedToUploadZip(Pageable pageable,
      @Param("downloadStatus") Integer downloadStatus);

  @Modifying
  @Transactional
  @Query(value = "UPDATE loan_applications " +
      "SET status = :status " +
      "WHERE uuid = :uuid ", nativeQuery = true)
  int updateStatusByUuid(@Param("status") String status, @Param("uuid") String uuid);

  @Modifying
  @Transactional
  @Query(value = "UPDATE loan_applications " +
      "SET cj4_send_lead_status = :status , cj4_send_lead_date =:date  " +
      "WHERE uuid = :uuid ", nativeQuery = true)
  void updateCj4SendLeadStatus(String status, Date date, String uuid);

  @Modifying
  @Transactional
  @Query(value = "update loan_applications la set la.download_status = 0 where uuid in (:list)", nativeQuery = true)
  void zipLoans(List<String> list);

  @Modifying
  @Transactional
  @Query(value = "update loan_applications set status = :status where uuid = :uuid", nativeQuery = true)
  int updateStatus(@Param("status") String status, @Param("uuid") String uuid);

  @Query(value = "select COALESCE(sum(temp.val), 0) from ( select sum(value) as val from salary_incomes where loan_application_id = :loanId UNION ALL select sum(value) as val from business_incomes where loan_application_id = :loanId UNION ALL select sum(value) as val from other_incomes where loan_application_id = :loanId UNION ALL select sum(rm_input_value) as val from asset_evaluate where loan_application_id = :loanId UNION ALL select sum(rm_input_value) as val from other_evaluate where loan_application_id = :loanId ) as temp", nativeQuery = true)
  long totalIncome(String loanId);

  @Query(value = "select COALESCE(sum(temp.val), 0) from\n" +
      "(\n" +
      "select sum(rm_input_value) as val from asset_evaluate where loan_application_id = :loanId\n"
      +
      "UNION ALL select sum(rm_input_value) as val from other_evaluate where loan_application_id = :loanId\n"
      +
      ") as temp", nativeQuery = true)
  long totalIncomeExchange(String loanId);

  @Query(value = "select COALESCE(sum(temp.val), 0) from\n" +
      "(\n" +
      "select sum(value) as val from salary_incomes where loan_application_id = :loanId\n" +
      "UNION ALL select sum(value) as val from business_incomes where loan_application_id = :loanId\n"
      +
      "UNION ALL select sum(value) as val from other_incomes where loan_application_id = :loanId\n"
      +
      ") as temp", nativeQuery = true)
  long totalIncomeActuallyReceived(String loanId);

}
