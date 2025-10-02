package vn.com.msb.homeloan.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.constant.LoanUploadFileStatusEnum;
import vn.com.msb.homeloan.core.entity.LoanUploadFileEntity;

@Repository
public interface LoanUploadFileRepository extends JpaRepository<LoanUploadFileEntity, String> {

  List<LoanUploadFileEntity> findByLoanApplicationId(String loanId);

  Long countByFileConfigId(String fileConfigId);

  List<LoanUploadFileEntity> findByFileConfigIdAndLoanApplicationIdAndStatus(String fileConfigId,
      String loanApplicationId, LoanUploadFileStatusEnum status);

  List<LoanUploadFileEntity> findFilesUploadedByLoanApplicationIdAndStatus(String loanApplicationId,
      LoanUploadFileStatusEnum status);

  @Query(value = "select folder from loan_upload_files where loan_application_id = :loanApplicationId limit 1", nativeQuery = true)
  String getFolderForLoanApp(String loanApplicationId);

  @Query(value =
      "select * from loan_upload_files where loan_application_id = :loanId and file_config_id = (select uuid from file_config where code = '"
          + Constants.FILE_CONFIG_CODE_DNVV
          + "') and file_name = 'de_nghi_vay_von.pdf' limit 1", nativeQuery = true)
  Optional<LoanUploadFileEntity> getDNVVByLoanId(String loanId);

  List<LoanUploadFileEntity> findByFileConfigIdAndLoanApplicationId(String id,String loanID);

  Optional<LoanUploadFileEntity> findFirstByLoanApplicationIdAndFileConfigIdAndStatus(String loanId, String fileConfigId, LoanUploadFileStatusEnum status);
}
