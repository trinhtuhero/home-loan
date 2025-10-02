package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.PartnerChannelEntity;
import vn.com.msb.homeloan.core.model.PartnerChannel;
import vn.com.msb.homeloan.core.repository.PartnerChannelRepository;
import vn.com.msb.homeloan.core.service.PartnerChannelService;

@ExtendWith(MockitoExtension.class)
class PartnerChannelServiceTest {

  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  PartnerChannelService partnerChannelService;

  @Mock
  PartnerChannelRepository partnerChannelRepository;

  @BeforeEach
  void setUp() {
    this.partnerChannelService = new PartnerChannelServiceImpl(
        partnerChannelRepository);
  }

  @Test
  void givenEmpty_ThenSave_shouldSuccess() {
    PartnerChannel partnerChannel = PartnerChannel.builder()
        .loanId(LOAN_ID)
        .build();

    PartnerChannel result = partnerChannelService.save(partnerChannel);

    assertEquals(result.getUuid(), null);
  }

  @Test
  void givenValidInput_ThenSave_shouldSuccess() {
    PartnerChannel partnerChannel = PartnerChannel.builder()
        .loanId(LOAN_ID)
        .dealAssignee("dealAssignee")
        .dealReferenceCode("dealReferenceCode")
        .dealReferralChannel("dealReferralChannel")
        .build();

    PartnerChannelEntity entity = PartnerChannelEntity.builder()
        .uuid("uuid")
        .loanId(LOAN_ID)
        .dealAssignee("dealAssignee")
        .dealReferenceCode("dealReferenceCode")
        .dealReferralChannel("dealReferralChannel")
        .build();

    doReturn(entity).when(partnerChannelRepository).save(any());

    PartnerChannel result = partnerChannelService.save(partnerChannel);

    assertEquals(result.getUuid(), entity.getUuid());
  }
}