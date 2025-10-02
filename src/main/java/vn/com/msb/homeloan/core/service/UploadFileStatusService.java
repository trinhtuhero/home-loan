package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.core.model.UploadFileStatus;

public interface UploadFileStatusService {

  UploadFileStatus save(UploadFileStatus uploadFileStatus);

  UploadFileStatus checkEnough(String loanApplicationId, String fileConfigId);
}
