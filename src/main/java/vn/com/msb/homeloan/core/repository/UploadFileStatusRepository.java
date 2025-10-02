package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.com.msb.homeloan.core.entity.UploadFileStatusEntity;

public interface UploadFileStatusRepository extends JpaRepository<UploadFileStatusEntity, String> {

  @Query(value = "select * from upload_file_status u where u.loan_application_id = :loanApplicationId and u.file_config_id = :fileConfigId order by created_at desc limit 1", nativeQuery = true)
  UploadFileStatusEntity findFirstByLoanApplicationIdAndFileConfigIdOrderByCreatedAtDesc(
      String loanApplicationId, String fileConfigId);

  @Query(value = "select * from upload_file_status u where u.loan_application_id = :loanApplicationId and status = :status", nativeQuery = true)
  List<UploadFileStatusEntity> findByLoanApplicationIdAndStatus(String loanApplicationId,
      String status);
}
