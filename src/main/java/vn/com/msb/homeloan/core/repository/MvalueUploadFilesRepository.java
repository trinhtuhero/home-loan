package vn.com.msb.homeloan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.entity.MvalueUploadFilesEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface MvalueUploadFilesRepository
    extends JpaRepository<MvalueUploadFilesEntity, String> {
  List<MvalueUploadFilesEntity> findByLoanApplicationIdAndDocumentMvalueIdAndStatus(
      String loanID, String documentID, String status);

  List<MvalueUploadFilesEntity> findByLoanApplicationId(String loanID);

  List<MvalueUploadFilesEntity> findByLoanApplicationIdAndDocumentMvalueIdAndCollateralIdAndStatus(
      String loanID, String documentID, String collateralId, String status);

  Optional<MvalueUploadFilesEntity> findById(long id);

  List<MvalueUploadFilesEntity> findByIdIn(List<Long> lst);

  List<MvalueUploadFilesEntity> findByLoanUploadFileId(String id);
}
