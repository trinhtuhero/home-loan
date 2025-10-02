package vn.com.msb.homeloan.core.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.entity.PartnerChannelEntity;
import vn.com.msb.homeloan.core.model.PartnerChannel;
import vn.com.msb.homeloan.core.model.mapper.PartnerChannelMapper;
import vn.com.msb.homeloan.core.repository.PartnerChannelRepository;
import vn.com.msb.homeloan.core.service.PartnerChannelService;
import vn.com.msb.homeloan.core.util.StringUtils;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PartnerChannelServiceImpl implements PartnerChannelService {

  private final PartnerChannelRepository partnerChannelRepository;

  @Override
  public PartnerChannel save(PartnerChannel partnerChannel) {
    if (StringUtils.isEmpty(partnerChannel.getDealAssignee())
        && StringUtils.isEmpty(partnerChannel.getDealReferralChannel())
        && StringUtils.isEmpty(partnerChannel.getDealReferenceCode())) {
      return partnerChannel;
    }

    Optional<PartnerChannelEntity> entityInDB = partnerChannelRepository.findByLoanId(
        partnerChannel.getLoanId());
    entityInDB.ifPresent(
        partnerChannelEntity -> partnerChannel.setUuid(partnerChannelEntity.getUuid()));

    PartnerChannelEntity entity = partnerChannelRepository.save(
        PartnerChannelMapper.INSTANCE.toEntity(partnerChannel));
    return PartnerChannelMapper.INSTANCE.toModel(entity);
  }
}
