package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.entity.CmsTabActionEntity;
import vn.com.msb.homeloan.core.model.CMSTabAction;
import vn.com.msb.homeloan.core.model.mapper.CMSTabActionMapper;
import vn.com.msb.homeloan.core.repository.CMSTabActionRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CMSTabActionServiceImpl implements CMSTabActionService {

  CMSTabActionRepository cmsTabActionRepository;

  @Override
  public CMSTabAction save(String loanId, CMSTabEnum tab) {
    CMSTabAction tabAction = CMSTabAction.builder()
        .loanId(loanId)
        .tabCode(tab.getCode())
        .status("SUCCESS")
        .emplId(AuthorizationUtil.getEmail())
        .build();
    Optional<CmsTabActionEntity> entity = cmsTabActionRepository.findByLoanIdAndTabCode(loanId,
        tab);
    entity.ifPresent(cmsTabActionEntity -> tabAction.setUuid(cmsTabActionEntity.getUuid()));
    return CMSTabActionMapper.INSTANCE.toModel(
        cmsTabActionRepository.save(CMSTabActionMapper.INSTANCE.toEntity(tabAction)));
  }

  @Override
  public void delete(String loanId, CMSTabEnum tab) {
    Optional<CmsTabActionEntity> opEntity = cmsTabActionRepository.findByLoanIdAndTabCode(loanId,
        tab);
    if (opEntity.isPresent()) {
      cmsTabActionRepository.delete(opEntity.get());
    }
  }

  @Override
  public List<CMSTabAction> getByLoanId(String loanId) {
    return CMSTabActionMapper.INSTANCE.toModels(cmsTabActionRepository.findByLoanId(loanId));
  }
}
