package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.FileRuleEnum;
import vn.com.msb.homeloan.core.entity.LoanUploadFileEntity;
import vn.com.msb.homeloan.core.model.*;
import vn.com.msb.homeloan.core.model.download.DownloadFile;
import vn.com.msb.homeloan.core.model.download.ZipBlock;
import vn.com.msb.homeloan.core.model.response.DownloadPresignedUrlResponse;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface LoanUploadFileService {

  List<LoanUrlUploadFile> preUploadFile(String loanId, String fileConfigId, List<String> files,
      ClientTypeEnum clientType);

  List<LoanUploadFile> updateStatus(List<LoanUploadFile> loanUploadFiles);

  HashMap<String, Object> getMapKeys(String loanApplicationId, FileRuleEnum fileRuleEnum);

  DownloadPresignedUrlResponse viewFile(String loanUploadFileId);

  DownloadPresignedUrlResponse viewFileMvalue(long id);

  void deleteFile(String loanUploadFileId, ClientTypeEnum clientType);

  List<LoanUploadFile> findFilesUploadedByLoanApplicationId(String loanApplicationId);

  List<DownloadFile> filesNeedToDownload(List<LoanUploadFile> list, LoanApplication loanApplication)
      throws SQLException;

  List<ZipBlock> blocksNeedToZip(List<DownloadFile> filesNeedToDownload,
      List<ZipBlock> blocksNeedToZip, String zipPrefix, int orderNumber, int next, int length);

  byte[] zipAllForLoanApplication(LoanApplication loanApplication) throws Exception;

  void zipLoanApplicationAndThenUploadToS3(LoanApplication loanApplication) throws Exception;

  String getFolderForLoanApp(String loanApplicationId);

  List<LoanUploadFile> getCopyFiles(String loanId);

  LoanUploadFile save(LoanUploadFile loanUploadFile);

  List<LoanUploadFileEntity> getByFileConfigId(String fileConfigID, String loanID);

  List<LoanUrlUploadFile> preUploadFileMValue(String loanId,long documentMvalueId, List<String> files,
                                        ClientTypeEnum clientType, String collateralId, String collateralType);

  List<MvalueUploadFile> updateStatusMvalue(List<MvalueUploadFile> mvalueUploadFiles);

  void deleteFileMvalue(long mvalueUploadFileId, ClientTypeEnum clientType);

  void addFileExist(String loanId, long documentMvalueId, ClientTypeEnum clientType, String collateralId, List<FileUploadExist> files, String collateralType);
}
