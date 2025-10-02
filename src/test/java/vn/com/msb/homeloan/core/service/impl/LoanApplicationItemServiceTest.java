package vn.com.msb.homeloan.core.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.lang.reflect.Method;
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
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.DisbursementMethodEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationItemEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.LoanApplicationItem;
import vn.com.msb.homeloan.core.model.mapper.CMSLoanItemOverDraftMapperImpl;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationItemMapper;
import vn.com.msb.homeloan.core.repository.CollateralRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationItemRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanItemCollateralDistributeRepository;
import vn.com.msb.homeloan.core.repository.OverdraftRepository;
import vn.com.msb.homeloan.core.service.CollateralService;
import vn.com.msb.homeloan.core.service.CreditworthinessItemService;
import vn.com.msb.homeloan.core.service.LoanApplicationItemService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.OverdraftService;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;

@ExtendWith(MockitoExtension.class)
public class LoanApplicationItemServiceTest {

  private final String LOAN_APPLICATION_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  LoanApplicationItemService loanApplicationItemService;

  @Mock
  LoanApplicationItemRepository loanApplicationItemRepository;

  @Mock
  LoanApplicationService loanApplicationService;

  @Mock
  LoanItemCollateralDistributeRepository loanItemCollateralDistributeRepository;

  @Mock
  CollateralRepository collateralRepository;

  @Mock
  CollateralService collateralService;

  @Mock
  CreditworthinessItemService creditworthinessItemService;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  OverdraftService overdraftService;

  @Mock
  OverdraftRepository overdraftRepository;

  @BeforeEach
  void setUp() {
    this.loanApplicationItemService = new LoanApplicationItemServiceImpl(
        loanApplicationItemRepository,
        loanApplicationService,
        loanItemCollateralDistributeRepository,
        collateralRepository,
        collateralService,
        null,
        null,
        creditworthinessItemService,
        loanApplicationRepository,
        overdraftService,
        overdraftRepository,
        null,
        new CMSLoanItemOverDraftMapperImpl()
    );
  }

  @Test
  void test_findByLoanApplicationId() {
    String uuid1 = "uuid1";
    List<LoanApplicationItemEntity> list1 = new ArrayList<>();
    doReturn(list1).when(loanApplicationItemRepository)
        .findByLoanApplicationIdOrderByCreatedAtAsc(uuid1);
    List<LoanApplicationItem> result = loanApplicationItemService.findByLoanApplicationIdOrderByCreatedAtAsc(
        uuid1);
    assertEquals(0, result.size());
  }

  @Test
  void test_save() {
    String uuid1 = null;
    String uuid1a = "uuid1a";

    LoanApplicationItem object1 = LoanApplicationItem.builder().uuid(uuid1).build();
    object1.setLoanAmount(123l);
    object1.setLoanAssetValue(124l);

    LoanApplicationItemEntity entity1 = LoanApplicationItemMapper.INSTANCE.toEntity(object1);

    entity1.setUuid(uuid1a);
    doReturn(entity1).when(loanApplicationItemRepository).save(entity1);

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn("EMAIL");
      LoanApplicationItem result = loanApplicationItemService.save(object1,0, any());

      assertEquals(uuid1a, result.getUuid());
    }
  }

  @Test
  void test_delete() {
    String uuid1 = "uuid1";

    LoanApplicationItemEntity object1 = LoanApplicationItemEntity.builder().uuid(uuid1)
        .loanApplicationId(LOAN_APPLICATION_ID).build();

    doNothing().when(creditworthinessItemService).deleteByConditions(any());
    doReturn(Optional.of(object1)).when(loanApplicationItemRepository).findById(uuid1);
    doNothing().when(loanApplicationItemRepository).deleteById(uuid1);
    loanApplicationItemService.delete(uuid1, ClientTypeEnum.LDP);
  }

  @Test
  void test_validateInput() {
    LoanApplicationItem loanApplicationItem = new LoanApplicationItem();
    loanApplicationItem.setDisbursementMethod(DisbursementMethodEnum.CASH);
    loanApplicationItem.setBeneficiaryBank("test");
    loanApplicationItem.setBeneficiaryAccount("123");
    loanApplicationItem.setBeneficiaryFullName("test");
    loanApplicationItem.setLoanAmount(123l);
    loanApplicationItem.setLoanAssetValue(124l);

    try {
      Method privateMethod = LoanApplicationItemServiceImpl.class.getDeclaredMethod("validateInput",
          LoanApplicationItem.class, ClientTypeEnum.class);
      privateMethod.setAccessible(true);
      privateMethod.invoke(loanApplicationItemService, loanApplicationItem, ClientTypeEnum.CMS);
    } catch (Exception e) {
      assertThat(e.getCause(), instanceOf(ApplicationException.class));
    }

    loanApplicationItem.setDisbursementMethod(DisbursementMethodEnum.TRANSFER);
    loanApplicationItem.setBeneficiaryBank(null);
    loanApplicationItem.setBeneficiaryAccount(null);
    loanApplicationItem.setBeneficiaryFullName(null);
    try {
      Method privateMethod = LoanApplicationItemServiceImpl.class.getDeclaredMethod("validateInput",
          LoanApplicationItem.class, ClientTypeEnum.class);
      privateMethod.setAccessible(true);
      privateMethod.invoke(loanApplicationItemService, loanApplicationItem, ClientTypeEnum.CMS);
    } catch (Exception e) {
      assertThat(e.getCause(), instanceOf(ApplicationException.class));
    }
  }


}
