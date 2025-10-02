package vn.com.msb.homeloan.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.msb.homeloan.core.entity.UploadFileCommentEntity;

public interface UploadFileCommentRepository extends
    JpaRepository<UploadFileCommentEntity, String> {

  @Query(value = "select * from upload_file_comment cm where cm.loan_application_id = :loanApplicationId and file_config_id = :fileConfigId order by created_at", nativeQuery = true)
  List<UploadFileCommentEntity> findByLoanApplicationIdAndFileConfigIdOrderByCreatedAt(
      String loanApplicationId, String fileConfigId);

  @Query(value = "select * from upload_file_comment ufc where ufc.loan_application_id = :loanApplicationId and ufc.status = :status and ufc.file_config_id = :fileConfigId order by created_at", nativeQuery = true)
  List<UploadFileCommentEntity> findByLoanApplicationIdAndStatusAndFileConfigIdOrderByCreatedAt(
      @Param("loanApplicationId") String loanApplicationId, @Param("status") String status,
      @Param("fileConfigId") String fileConfigId);

  @Query(value = "select * from upload_file_comment ufc join file_config fc on ufc.file_config_id = fc.uuid where ufc.loan_application_id = :loanApplicationId and ufc.status = :status order by order_number, ufc.created_at", nativeQuery = true)
  List<UploadFileCommentEntity> findByLoanApplicationIdAndStatusKeepOrder(String loanApplicationId,
      String status);
}
