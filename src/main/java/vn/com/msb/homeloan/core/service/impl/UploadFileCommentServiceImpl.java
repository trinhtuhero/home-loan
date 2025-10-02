package vn.com.msb.homeloan.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.CommentStatusEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.UploadFileCommentEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CmsUser;
import vn.com.msb.homeloan.core.model.UploadFileComment;
import vn.com.msb.homeloan.core.model.mapper.UploadFileCommentMapper;
import vn.com.msb.homeloan.core.model.response.cms.role.Role;
import vn.com.msb.homeloan.core.repository.UploadFileCommentRepository;
import vn.com.msb.homeloan.core.service.CmsUserService;
import vn.com.msb.homeloan.core.service.FileConfigService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.UploadFileCommentService;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadFileCommentServiceImpl implements UploadFileCommentService {

  private final UploadFileCommentRepository uploadFileCommentRepository;

  private final LoanApplicationService loanApplicationService;

  private final CmsUserService cmsUserService;

  private final FileConfigService fileConfigService;

  @Value("${msb.common.cms.role.admin}")
  private String cj5Admin;

  @Value("${msb.common.cms.role.rm}")
  private String cj5RM;

  @Value("${msb.common.cms.role.bm}")
  private String cj5BM;

  private final CacheManager cacheManager;

  @Override
  public UploadFileComment save(UploadFileComment uploadFileComment) {
    loanApplicationService.findById(uploadFileComment.getLoanApplicationId());
    fileConfigService.findByUuid(uploadFileComment.getFileConfigId());
    String email = AuthorizationUtil.getEmail();
    CmsUser cmsUser = cmsUserService.findByEmail(email);
    UploadFileCommentEntity entity = UploadFileCommentMapper.INSTANCE.toEntity(uploadFileComment);
    entity.setEmplId(cmsUser.getEmplId());
    entity.setStatus(CommentStatusEnum.DRAFT);

    UploadFileComment model = UploadFileCommentMapper.INSTANCE.toModel(
        uploadFileCommentRepository.save(entity));
    model.setEmplFullName(cmsUser.getFullName());
    model.setEmplEmail(cmsUser.getEmail());
    model.setEmplRole(getRmRole(cmsUser.getEmail()));

    return model;
  }

  @Override
  public UploadFileComment update(UploadFileComment uploadFileComment) {
    // Check uuid
    UploadFileComment model = findById(uploadFileComment.getUuid());
    // status = DRAFT allow update
    if (!CommentStatusEnum.DRAFT.equals(model.getStatus())) {
      throw new ApplicationException(ErrorEnum.RESOURCE_EDIT_NOT_ALLOW);
    }

    String email = AuthorizationUtil.getEmail();
    CmsUser cmsUser = cmsUserService.findByEmail(email);

    UploadFileCommentEntity entity = UploadFileCommentMapper.INSTANCE.toEntity(model);

    entity.setComment(uploadFileComment.getComment());
    entity.setEmplId(cmsUser.getEmplId());

    UploadFileComment returnModel = UploadFileCommentMapper.INSTANCE.toModel(
        uploadFileCommentRepository.save(entity));
    returnModel.setEmplFullName(cmsUser.getFullName());
    returnModel.setEmplEmail(cmsUser.getEmail());
    returnModel.setEmplRole(getRmRole(cmsUser.getEmail()));

    return returnModel;
  }

  @Override
  public UploadFileComment findById(String uuid) {
    UploadFileCommentEntity entity = uploadFileCommentRepository.findById(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid", uuid));
    return UploadFileCommentMapper.INSTANCE.toModel(entity);
  }

  @Override
  public void deleteByUuid(String uuid) {
    // Check loan application comment id
    UploadFileComment model = findById(uuid);
    // status = DRAFT allow update
    if (!CommentStatusEnum.DRAFT.equals(model.getStatus())) {
      throw new ApplicationException(ErrorEnum.RESOURCE_EDIT_NOT_ALLOW);
    }
    uploadFileCommentRepository.deleteById(uuid);
  }

  @Override
  public List<UploadFileComment> history(String loanApplicationId, String fileConfigId) {
    List<UploadFileComment> uploadFileComments = UploadFileCommentMapper.INSTANCE.toModels(
        uploadFileCommentRepository.findByLoanApplicationIdAndFileConfigIdOrderByCreatedAt(
            loanApplicationId, fileConfigId));

    String email = AuthorizationUtil.getEmail();
    CmsUser cmsUser = cmsUserService.findByEmail(email);

    uploadFileComments.forEach(uploadFileComment -> {
      uploadFileComment.setEmplFullName(cmsUser.getFullName());
      uploadFileComment.setEmplEmail(cmsUser.getEmail());
      uploadFileComment.setEmplRole(getRmRole(cmsUser.getEmail()));
    });

    return uploadFileComments;
  }

  private String getRmRole(String rmEmail) {
    try {
      List<Role> roles = (List<Role>) cacheManager.getCache("rolesCache").get(rmEmail).get();
      if (AuthorizationUtil.containRole(roles, cj5Admin)) {
        return "Admin";
      } else if (AuthorizationUtil.containRole(roles, cj5BM)) {
        return "PM";
      } else {
        return "RM";
      }
    } catch (Exception ex) {
      log.error("getRmRole error: {}", ex.getMessage());
      return "RM";
    }
  }
}
