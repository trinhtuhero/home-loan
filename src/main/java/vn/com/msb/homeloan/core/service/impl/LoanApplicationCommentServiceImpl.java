package vn.com.msb.homeloan.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.CommentStatusEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationCommentEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CmsUser;
import vn.com.msb.homeloan.core.model.LoanApplicationComment;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationCommentMapper;
import vn.com.msb.homeloan.core.model.response.cms.role.Role;
import vn.com.msb.homeloan.core.repository.LoanApplicationCommentRepository;
import vn.com.msb.homeloan.core.service.CmsUserService;
import vn.com.msb.homeloan.core.service.CommonCMSService;
import vn.com.msb.homeloan.core.service.LoanApplicationCommentService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanApplicationCommentServiceImpl implements LoanApplicationCommentService {

  private final LoanApplicationCommentRepository loanApplicationCommentRepository;

  private final LoanApplicationService loanApplicationService;

  private final CmsUserService cmsUserService;

  private final CommonCMSService commonCMSService;

  private final HttpServletRequest request;

  private final CacheManager cacheManager;

  private final EnvironmentProperties environmentProperties;

  @Value("${msb.common.cms.role.admin}")
  private String cj5Admin;

  @Value("${msb.common.cms.role.rm}")
  private String cj5RM;

  @Value("${msb.common.cms.role.bm}")
  private String cj5BM;

  @Override
  public LoanApplicationComment save(LoanApplicationComment loanApplicationComment) {
    loanApplicationService.findById(loanApplicationComment.getLoanApplicationId());
    String email = AuthorizationUtil.getEmail();
    CmsUser cmsUser = cmsUserService.findByEmail(email);
    LoanApplicationCommentEntity entity = LoanApplicationCommentMapper.INSTANCE.toEntity(
        loanApplicationComment);
    entity.setEmplId(cmsUser.getEmplId());
    entity.setStatus(CommentStatusEnum.DRAFT);
    LoanApplicationComment model = LoanApplicationCommentMapper.INSTANCE.toModel(
        loanApplicationCommentRepository.save(entity));
    model.setEmplFullName(cmsUser.getFullName());
    model.setEmplEmail(cmsUser.getEmail());
    model.setEmplRole(getRmRole(cmsUser.getEmail()));
    return model;
  }

  private String getRmRole(String rmEmail) {
    try {
      List<Role> roles = (List<Role>) cacheManager.getCache("rolesCache").get(rmEmail).get();
      if (AuthorizationUtil.containRole(roles, cj5Admin)) {
        return "Admin";
      } else if (AuthorizationUtil.containRole(roles, cj5BM)) {
        return "BM";
      } else {
        return "RM";
      }
    } catch (Exception ex) {
      log.error("getRmRole error: {}", ex.getMessage());
      return "RM";
    }
  }

  @Override
  public LoanApplicationComment update(LoanApplicationComment loanApplicationComment) {
    // Check loan application comment id
    LoanApplicationComment model = findById(loanApplicationComment.getUuid());
    // status = DRAFT allow update
    if (!CommentStatusEnum.DRAFT.equals(model.getStatus())) {
      throw new ApplicationException(ErrorEnum.RESOURCE_EDIT_NOT_ALLOW);
    }
    //
    String email = AuthorizationUtil.getEmail();
    CmsUser cmsUser = cmsUserService.findByEmail(email);

    LoanApplicationCommentEntity entity = LoanApplicationCommentMapper.INSTANCE.toEntity(model);

    entity.setComment(loanApplicationComment.getComment());
    entity.setEmplId(cmsUser.getEmplId());

    LoanApplicationComment returnModel = LoanApplicationCommentMapper.INSTANCE.toModel(
        loanApplicationCommentRepository.save(entity));
    returnModel.setEmplFullName(cmsUser.getFullName());
    returnModel.setEmplEmail(cmsUser.getEmail());
    returnModel.setEmplRole(getRmRole(cmsUser.getEmail()));
    return returnModel;
  }

  @Override
  public LoanApplicationComment findById(String uuid) {
    LoanApplicationCommentEntity entity = loanApplicationCommentRepository.findById(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid", uuid));
    return LoanApplicationCommentMapper.INSTANCE.toModel(entity);
  }

  @Override
  public List<LoanApplicationComment> getCommentsByLoanId(String loanId) {
    return LoanApplicationCommentMapper.INSTANCE.toModels(
        loanApplicationCommentRepository.findByLoanApplicationIdAndStatusOrderByCreatedAt(loanId,
            CommentStatusEnum.NEW.toString()));
  }

  @Override
  public void deleteByUuid(String uuid) {
    // Check loan application comment id
    LoanApplicationComment model = findById(uuid);
    // status = DRAFT allow update
    if (!CommentStatusEnum.DRAFT.equals(model.getStatus())) {
      throw new ApplicationException(ErrorEnum.RESOURCE_EDIT_NOT_ALLOW);
    }
    loanApplicationCommentRepository.deleteById(uuid);
  }

  @Override
  public List<LoanApplicationComment> history(String loanApplicationId, String code) {
    //
    String email = AuthorizationUtil.getEmail();
    CmsUser cmsUser = cmsUserService.findByEmail(email);

    List<LoanApplicationComment> loanApplicationComments = LoanApplicationCommentMapper.INSTANCE.toModels(
        loanApplicationCommentRepository.findByLoanApplicationIdAndCodeOrderByCreatedAt(
            loanApplicationId, code));
    loanApplicationComments.forEach(loanApplicationComment -> {
      loanApplicationComment.setEmplFullName(cmsUser.getFullName());
      loanApplicationComment.setEmplEmail(cmsUser.getEmail());
      loanApplicationComment.setEmplRole(getRmRole(cmsUser.getEmail()));
    });

    return loanApplicationComments;
  }
}
