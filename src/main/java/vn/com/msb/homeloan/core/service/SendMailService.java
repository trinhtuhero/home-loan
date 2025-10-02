package vn.com.msb.homeloan.core.service;

import java.util.Date;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import vn.com.msb.homeloan.core.constant.FileRuleEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationCommentEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.UploadFileCommentEntity;
import vn.com.msb.homeloan.core.entity.UploadFileStatusEntity;

public interface SendMailService {

  void sendMailWhenSubmitLoanSuccess(LoanApplicationEntity loanApplication,
      List<MultipartFile> fileList);

  void sendMailWhenSubmitSubmitFeedback(LoanApplicationEntity loanApplication,
      List<LoanApplicationCommentEntity> loanApplicationCommentEntities,
      List<UploadFileCommentEntity> uploadFileCommentEntities,
      List<UploadFileStatusEntity> uploadFileStatusEntities);

  String genHtmlContentOfAttachFile(String loanApplicationId, Date signedDate);

  String genHtmlContentDocumentNeedProvide(String loanApplicationId, FileRuleEnum fileRuleEnum);

  void sendMailWhenRMCompleteToTrinh(LoanApplicationEntity loanApplication,
      List<MultipartFile> fileList);

  void sendMailToBMWhenRMCompleteToTrinh(LoanApplicationEntity loanApplication);

  void sendMailWhenCustomerApprovalChange(LoanApplicationEntity loanApplication,
      List<MultipartFile> fileList);
}
