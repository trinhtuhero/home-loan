package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.entity.AssetEvaluateEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.AssetEvaluate;
import vn.com.msb.homeloan.core.model.AssetEvaluateItem;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.mapper.AssetEvaluateMapper;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.repository.AssetEvaluateRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.service.AssetEvaluateItemService;
import vn.com.msb.homeloan.core.service.AssetEvaluateService;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CommonIncomeService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;

@ExtendWith(MockitoExtension.class)
class AssetEvaluateServiceTest {

  private final String ASSET_EVALUATE_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String PAYER_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b63aa";

  AssetEvaluateService assetEvaluateService;

  @Mock
  AssetEvaluateRepository assetEvaluateRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  AssetEvaluateItemService assetEvaluateItemService;

  @Mock
  LoanApplicationService loanApplicationService;

  @Mock
  CMSTabActionService cmsTabActionService;

  @Mock
  LoanPayerRepository loanPayerRepository;

  @Mock
  CommonIncomeService commonIncomeService;

  @BeforeEach
  void setUp() {
    this.assetEvaluateService = new AssetEvaluateServiceImpl(
        loanApplicationRepository,
        assetEvaluateRepository,
        assetEvaluateItemService,
        loanApplicationService,
        cmsTabActionService,
        loanPayerRepository,
        commonIncomeService);
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnSuccessUpdate() {
    AssetEvaluateEntity entity = AssetEvaluateEntity.builder()
        .uuid(ASSET_EVALUATE_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .payerId(PAYER_ID)
        .build();
    AssetEvaluate assetEvaluate = AssetEvaluateMapper.INSTANCE.toModel(entity);

    List<AssetEvaluateItem> assetEvaluateItems = new ArrayList<>();
    AssetEvaluateItem assetEvaluateItem = AssetEvaluateItem.builder()
        .assetDescription("description").build();
    assetEvaluateItems.add(assetEvaluateItem);
    assetEvaluate.setAssetEvaluateItems(assetEvaluateItems);

    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
      doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(Optional.of(entity)).when(assetEvaluateRepository).findByUuid(ASSET_EVALUATE_ID);

    LoanPayerEntity loanPayerEntity = LoanPayerEntity.builder().uuid(PAYER_ID).build();
    doReturn(Optional.of(loanPayerEntity)).when(loanPayerRepository).findByUuid(PAYER_ID);

    String[] strings = {"test"};
    loanApplicationService.deleteUnselectedIncomes(LOAN_ID, strings);

    doReturn(entity).when(assetEvaluateRepository).save(entity);

    AssetEvaluate result = assetEvaluateService.save(assetEvaluate);

    assertEquals(result.getUuid(), ASSET_EVALUATE_ID);
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnSuccessInsert() {
    AssetEvaluateEntity entity = AssetEvaluateEntity.builder()
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .payerId(PAYER_ID)
        .build();
    AssetEvaluate assetEvaluate = AssetEvaluateMapper.INSTANCE.toModel(entity);

    List<AssetEvaluateItem> assetEvaluateItems = new ArrayList<>();
    AssetEvaluateItem assetEvaluateItem = AssetEvaluateItem.builder()
        .assetDescription("description").build();
    assetEvaluateItems.add(assetEvaluateItem);
    assetEvaluate.setAssetEvaluateItems(assetEvaluateItems);

    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);

    LoanPayerEntity loanPayerEntity = LoanPayerEntity.builder().uuid(PAYER_ID).build();
    doReturn(Optional.of(loanPayerEntity)).when(loanPayerRepository).findByUuid(PAYER_ID);

    String[] strings = {"test"};
    loanApplicationService.deleteUnselectedIncomes(LOAN_ID, strings);

    doReturn(entity).when(assetEvaluateRepository).save(entity);

    CommonIncome commonIncome = CommonIncome.builder().build();

    loanApplicationService.deleteUnselectedIncomes(LOAN_ID, commonIncome.getSelectedIncomes());

    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(LOAN_ID);

    AssetEvaluate result = assetEvaluateService.save(assetEvaluate);

    assertEquals(result.getLoanApplicationId(), LOAN_ID);
  }

  @Test
  void givenValidInput_ThenUpdate_shouldReturnSuccess() {
    AssetEvaluateEntity entity = AssetEvaluateEntity.builder()
        .uuid(null)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .payerId(PAYER_ID)
        .build();
    AssetEvaluate assetEvaluate = AssetEvaluateMapper.INSTANCE.toModel(entity);

    AssetEvaluateItem assetEvaluateItem1 = AssetEvaluateItem.builder()
        .assetDescription("description").build();
    List<AssetEvaluateItem> assetEvaluateItems = Arrays.asList(assetEvaluateItem1,
        assetEvaluateItem1, assetEvaluateItem1, assetEvaluateItem1,
        assetEvaluateItem1, assetEvaluateItem1, assetEvaluateItem1, assetEvaluateItem1,
        assetEvaluateItem1, assetEvaluateItem1, assetEvaluateItem1);
    assetEvaluate.setAssetEvaluateItems(assetEvaluateItems);

    LoanPayerEntity loanPayerEntity = LoanPayerEntity.builder().uuid(PAYER_ID).build();
    doReturn(Optional.of(loanPayerEntity)).when(loanPayerRepository).findByUuid(PAYER_ID);

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn("EMAIL");
      LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
          .email("EMAIL")
          .picRm("emplId")
          .build();
      doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(any());
      ApplicationException exception = assertThrows(ApplicationException.class, () -> {
        assetEvaluateService.save(assetEvaluate);
      });

      assertEquals(exception.getCode().intValue(), ErrorEnum.ITEM_LIMIT.getCode());
    }
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnFailInvalidForm() {
    AssetEvaluateEntity entity = AssetEvaluateEntity.builder()
        .uuid(ASSET_EVALUATE_ID)
        .loanApplicationId(LOAN_ID)
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .build();
    AssetEvaluate assetEvaluate = AssetEvaluateMapper.INSTANCE.toModel(entity);

    List<AssetEvaluateItem> assetEvaluateItems = new ArrayList<>();
    AssetEvaluateItem assetEvaluateItem = AssetEvaluateItem.builder()
        .assetDescription("description").build();
    assetEvaluateItems.add(assetEvaluateItem);
    assetEvaluate.setAssetEvaluateItems(assetEvaluateItems);

    String[] strings = {"test"};
    loanApplicationService.deleteUnselectedIncomes(LOAN_ID, strings);

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn("EMAIL");
      LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
          .email("EMAIL")
          .picRm("emplId")
          .build();
      doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(any());

      ApplicationException exception = assertThrows(ApplicationException.class, () -> {
        assetEvaluateService.save(assetEvaluate);
      });

      assertEquals(exception.getCode().intValue(), ErrorEnum.INVALID_FORM.getCode());
    }
  }
}
