package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.model.UploadFileComment;

public interface UploadFileCommentService {

  UploadFileComment save(UploadFileComment uploadFileComment);

  UploadFileComment update(UploadFileComment uploadFileComment);

  UploadFileComment findById(String uuid);

  void deleteByUuid(String uuid);

  List<UploadFileComment> history(String loanApplicationId, String fileConfigId);
}
