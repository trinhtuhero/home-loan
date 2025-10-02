package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.entity.CmsTabActionEntity;
import vn.com.msb.homeloan.core.model.CMSTabAction;
import vn.com.msb.homeloan.core.repository.CMSTabActionRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;

@ExtendWith(MockitoExtension.class)
class CMSTabActionServiceTest {

  private final String LOAN_APPLICATION_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String TAB_ACTION_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  CMSTabActionService cmsTabActionService;

  @Mock
  CMSTabActionRepository cmsTabActionRepository;

  @BeforeEach
  void setUp() {
    this.cmsTabActionService = new CMSTabActionServiceImpl(
        cmsTabActionRepository);
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnSuccess() {
    try (MockedStatic<AuthorizationUtil> mockedStatic = Mockito.mockStatic(
        AuthorizationUtil.class)) {
      mockedStatic.when(AuthorizationUtil::getEmail).thenReturn("a");
      CmsTabActionEntity cmsTabActionEntity = CmsTabActionEntity.builder()
          .uuid(TAB_ACTION_ID)
          .loanId(LOAN_APPLICATION_ID)
          .tabCode(CMSTabEnum.MARRIED_PERSON).build();

      doReturn(Optional.of(cmsTabActionEntity)).when(cmsTabActionRepository)
          .findByLoanIdAndTabCode(LOAN_APPLICATION_ID, CMSTabEnum.MARRIED_PERSON);
      cmsTabActionEntity.setStatus("SUCCESS");
      cmsTabActionEntity.setEmplId("a");
      doReturn(cmsTabActionEntity).when(cmsTabActionRepository).save(cmsTabActionEntity);
      CMSTabAction result = cmsTabActionService.save(LOAN_APPLICATION_ID,
          CMSTabEnum.MARRIED_PERSON);

      assertEquals(result.getUuid(), TAB_ACTION_ID);
    }
  }

  @Test
  void givenValidInput_ThenDelete_shouldReturnSuccess() {
    CmsTabActionEntity cmsTabActionEntity = CmsTabActionEntity.builder()
        .uuid(TAB_ACTION_ID)
        .loanId(LOAN_APPLICATION_ID)
        .tabCode(CMSTabEnum.MARRIED_PERSON).build();

    doReturn(Optional.of(cmsTabActionEntity)).when(cmsTabActionRepository)
        .findByLoanIdAndTabCode(LOAN_APPLICATION_ID, CMSTabEnum.MARRIED_PERSON);
    cmsTabActionService.delete(LOAN_APPLICATION_ID, CMSTabEnum.MARRIED_PERSON);

    verify(cmsTabActionRepository, times(1)).delete(cmsTabActionEntity);
  }

  @Test
  void givenValidInput_ThenGetByLoanId_shouldReturnSuccess() {
    CmsTabActionEntity cmsTabActionEntity = CmsTabActionEntity.builder()
        .uuid(TAB_ACTION_ID)
        .loanId(LOAN_APPLICATION_ID)
        .tabCode(CMSTabEnum.MARRIED_PERSON).build();
    List<CmsTabActionEntity> cmsTabActionEntities = new ArrayList<>();
    cmsTabActionEntities.add(cmsTabActionEntity);

    doReturn(cmsTabActionEntities).when(cmsTabActionRepository).findByLoanId(LOAN_APPLICATION_ID);
    List<CMSTabAction> result = cmsTabActionService.getByLoanId(LOAN_APPLICATION_ID);

    assertEquals(result.get(0).getUuid(), TAB_ACTION_ID);
  }
}
