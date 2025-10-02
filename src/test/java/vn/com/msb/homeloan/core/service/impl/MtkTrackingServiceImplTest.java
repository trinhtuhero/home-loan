package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.MtkTrackingEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.MtkTracking;
import vn.com.msb.homeloan.core.model.mapper.MtkTrackingMapper;
import vn.com.msb.homeloan.core.repository.CustomerAdviseRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.MtkTrackingRepository;
import vn.com.msb.homeloan.core.service.MtkTrackingService;

@ExtendWith(MockitoExtension.class)
class MtkTrackingServiceImplTest {

  MtkTrackingService mtkTrackingService;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  MtkTrackingRepository mtkTrackingRepository;

  @Mock
  CustomerAdviseRepository customerAdviseRepository;

  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  @BeforeEach
  void setUp() {
    this.mtkTrackingService = new MtkTrackingServiceImpl(
        mtkTrackingRepository,
        loanApplicationRepository, customerAdviseRepository);
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnLoanNotFound() {
    doReturn(Optional.empty()).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      mtkTrackingService.save(MtkTrackingMapper.INSTANCE.toModel(buildMtkTracking()));
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnAdviseNotFound() {
    doReturn(Optional.empty()).when(customerAdviseRepository).findById(any());

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      mtkTrackingService.save(MtkTrackingMapper.INSTANCE.toModel(buildMtkTrackingAdvise()));
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnAlreadyExist() {
    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .loanCode("loanCode")
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    List<MtkTrackingEntity> entities = new ArrayList<>();
    entities.add(buildMtkTracking());
    doReturn(entities).when(mtkTrackingRepository).findByLoanIdOrAdviseId(LOAN_ID, null);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      mtkTrackingService.save(MtkTrackingMapper.INSTANCE.toModel(buildMtkTracking()));
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.ALREADY_EXIST_IN_SYSTEM.getCode());
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnSuccess() {
    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .loanCode("loanCode")
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    List<MtkTrackingEntity> entities = new ArrayList<>();
    doReturn(entities).when(mtkTrackingRepository).findByLoanIdOrAdviseId(LOAN_ID, null);

    MtkTrackingEntity entity = buildMtkTracking();
    entity.setLoanCode(loanApplication.getLoanCode());
    doReturn(entity).when(mtkTrackingRepository).save(any(MtkTrackingEntity.class));

    MtkTracking result = mtkTrackingService.save(MtkTrackingMapper.INSTANCE.toModel(entity));

    assertEquals(result.getLoanCode(), loanApplication.getLoanCode());
    assertEquals(result.getUtmCampaign(), "utmCampaign");
  }

  private MtkTrackingEntity buildMtkTracking() {
    return MtkTrackingEntity.builder()
        .loanId(LOAN_ID)
        .utmCampaign("utmCampaign")
        .utmContent("utmContent")
        .utmSource("utmSource")
        .utmMedium("utmMedium")
        .build();
  }

  private MtkTrackingEntity buildMtkTrackingAdvise() {
    return MtkTrackingEntity.builder()
        .adviseId(LOAN_ID)
        .utmCampaign("utmCampaign")
        .utmContent("utmContent")
        .utmSource("utmSource")
        .utmMedium("utmMedium")
        .build();
  }
}