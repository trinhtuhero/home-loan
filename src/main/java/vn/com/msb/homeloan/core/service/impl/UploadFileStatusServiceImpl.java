package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.CommentStatusEnum;
import vn.com.msb.homeloan.core.entity.UploadFileStatusEntity;
import vn.com.msb.homeloan.core.model.CmsUser;
import vn.com.msb.homeloan.core.model.UploadFileStatus;
import vn.com.msb.homeloan.core.model.mapper.UploadFileStatusMapper;
import vn.com.msb.homeloan.core.repository.UploadFileStatusRepository;
import vn.com.msb.homeloan.core.service.CmsUserService;
import vn.com.msb.homeloan.core.service.FileConfigService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.UploadFileStatusService;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;

@Slf4j
@Service
@AllArgsConstructor
public class UploadFileStatusServiceImpl implements UploadFileStatusService {

  private UploadFileStatusRepository uploadFileStatusRepository;

  private LoanApplicationService loanApplicationService;

  private FileConfigService fileConfigService;

  private CmsUserService cmsUserService;

  @Override
  public UploadFileStatus save(UploadFileStatus uploadFileStatus) {
    loanApplicationService.findById(uploadFileStatus.getLoanApplicationId());
    fileConfigService.findByUuid(uploadFileStatus.getFileConfigId());
    String email = AuthorizationUtil.getEmail();
    CmsUser cmsUser = cmsUserService.findByEmail(email);

    UploadFileStatusEntity entity = UploadFileStatusMapper.INSTANCE.toEntity(uploadFileStatus);
    entity.setEmplId(cmsUser.getEmplId());
    entity.setStatus(CommentStatusEnum.DRAFT);

    return UploadFileStatusMapper.INSTANCE.toModel(uploadFileStatusRepository.save(entity));
  }

  @Override
  public UploadFileStatus checkEnough(String loanApplicationId, String fileConfigId) {
    UploadFileStatusEntity entity = uploadFileStatusRepository.findFirstByLoanApplicationIdAndFileConfigIdOrderByCreatedAtDesc(
        loanApplicationId, fileConfigId);
    return UploadFileStatusMapper.INSTANCE.toModel(entity);
  }
}
