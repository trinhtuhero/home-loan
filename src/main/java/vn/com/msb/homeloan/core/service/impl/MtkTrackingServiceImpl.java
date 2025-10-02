package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.MtkTypeEnum;
import vn.com.msb.homeloan.core.entity.AdviseCustomerEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.MtkTrackingEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.MtkTracking;
import vn.com.msb.homeloan.core.model.mapper.MtkTrackingMapper;
import vn.com.msb.homeloan.core.repository.CustomerAdviseRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.MtkTrackingRepository;
import vn.com.msb.homeloan.core.service.MtkTrackingService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class MtkTrackingServiceImpl implements MtkTrackingService {

  private final MtkTrackingRepository mtkTrackingRepository;
  private final LoanApplicationRepository loanApplicationRepository;

  private final CustomerAdviseRepository customerAdviseRepository;

  @Override
  public MtkTracking save(MtkTracking mtkTracking) {
    String loanId = null;
    String adviseId = null;
    MtkTrackingEntity entity = MtkTrackingMapper.INSTANCE.toEntity(mtkTracking);
    if (mtkTracking.getLoanId() != null) {
      LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findById(
              mtkTracking.getLoanId())
          .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
      loanId = loanApplicationEntity.getUuid();
      entity.setLoanCode(loanApplicationEntity.getLoanCode());
      entity.setMtkType(MtkTypeEnum.LOAN_APPLICATION);
    } else if (mtkTracking.getAdviseId() != null) {
      AdviseCustomerEntity adviseEntity = customerAdviseRepository.findById(
              mtkTracking.getAdviseId())
          .orElseThrow(
              () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "advise_customer:uuid",
                  mtkTracking.getAdviseId()));
      adviseId = adviseEntity.getUuid();
      entity.setMtkType(MtkTypeEnum.SUPPORT_REQUEST);
    }
    List<MtkTrackingEntity> entities = mtkTrackingRepository.findByLoanIdOrAdviseId(loanId,
        adviseId);
    if (!CollectionUtils.isEmpty(entities)) {
      List<MtkTrackingEntity> finds = entities.stream()
          .filter(x -> (mtkTracking.getUtmCampaign() != null && mtkTracking.getUtmCampaign()
              .equalsIgnoreCase(x.getUtmCampaign()))
              && (mtkTracking.getUtmMedium() != null && mtkTracking.getUtmMedium()
              .equalsIgnoreCase(x.getUtmMedium()))
              && (mtkTracking.getUtmSource() != null && mtkTracking.getUtmSource()
              .equalsIgnoreCase(x.getUtmSource()))
              && (mtkTracking.getUtmContent() != null && mtkTracking.getUtmContent()
              .equalsIgnoreCase(x.getUtmContent())))
          .collect(Collectors.toList());

      if (!CollectionUtils.isEmpty(finds)) {
        throw new ApplicationException(ErrorEnum.ALREADY_EXIST_IN_SYSTEM);
      }
    }

    return MtkTrackingMapper.INSTANCE.toModel(mtkTrackingRepository.save(entity));
  }
}
