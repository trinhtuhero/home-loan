package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.LoanCurrentStepEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.entity.CollateralOwnerEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CollateralOwner;
import vn.com.msb.homeloan.core.model.mapper.CollateralOwnerMapper;
import vn.com.msb.homeloan.core.repository.CollateralOwnerRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.repository.MarriedPersonRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CollateralOwnerService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;

@ExtendWith(MockitoExtension.class)
class CollateralOwnerServiceTest {

  CollateralOwnerService collateralOwnerService;

  @Mock
  CollateralOwnerRepository collateralOwnerRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  CMSTabActionService cmsTabActionService;

  @Mock
  MarriedPersonRepository marriedPersonRepository;

  @Mock
  LoanPayerRepository loanPayerRepository;

  @Mock
  LoanApplicationService loanApplicationService;


  private final String UUID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String COLLATERAL_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  @BeforeEach
  void setUp() {
    this.collateralOwnerService = new CollateralOwnerServiceImpl(
        collateralOwnerRepository,
        loanApplicationRepository,
        cmsTabActionService,
        marriedPersonRepository,
        loanPayerRepository,
        loanApplicationService);
  }

  @Test
  void givenValidInput_ThenDeleteById_shouldReturnCollateralNotFound() {
    doReturn(Optional.empty()).when(collateralOwnerRepository).findById(UUID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      collateralOwnerService.deleteById(UUID, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.COLLATERAL_OWNER_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenDeleteByIdLdp_shouldReturnSuccess() {
    CollateralOwnerEntity mockEntity = CollateralOwnerEntity.builder()
        .uuid(UUID)
        .loanId(LOAN_ID)
        .fullName("Nguyen van A")
        .build();
    doReturn(Optional.of(mockEntity)).when(collateralOwnerRepository)
        .findById(mockEntity.getUuid());

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn("EMAIL");
      LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
          .email("EMAIL")
          .picRm("emplId")
          .build();
      doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(any());
      collateralOwnerService.deleteById(UUID, ClientTypeEnum.LDP);
      verify(collateralOwnerRepository, times(1)).deleteById(UUID);
    }
  }

  @Test
  void givenValidInput_ThenDeleteByIdCms_shouldReturnSuccess() {
    CollateralOwnerEntity mockEntity = CollateralOwnerEntity.builder()
        .uuid(UUID)
        .loanId(LOAN_ID)
        .fullName("Nguyen van A")
        .build();
    doReturn(Optional.of(mockEntity)).when(collateralOwnerRepository)
        .findById(mockEntity.getUuid());

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn("EMAIL");
      LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
          .email("EMAIL")
          .picRm("emplId")
          .build();
      doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(any());
      collateralOwnerService.deleteById(UUID, ClientTypeEnum.CMS);
      verify(collateralOwnerRepository, times(1)).deleteById(UUID);
    }
  }

  @Test
  void givenValidInput_ThenFindById_shouldReturnSuccess() {
    CollateralOwnerEntity mockEntity = CollateralOwnerEntity.builder()
        .uuid(UUID)
        .loanId(LOAN_ID)
        .fullName("Nguyen van A")
        .build();

    doReturn(Optional.of(mockEntity)).when(collateralOwnerRepository)
        .findById(mockEntity.getUuid());

    CollateralOwner collateral = collateralOwnerService.findById(mockEntity.getUuid());
    assertEquals(collateral.getUuid(), mockEntity.getUuid());
    assertEquals(collateral.getFullName(), mockEntity.getFullName());
    assertEquals(collateral.getLoanId(), mockEntity.getLoanId());
  }

  @Test
  void givenValidInput_ThenFindById_shouldReturnFail() {
    doReturn(Optional.empty()).when(collateralOwnerRepository).findById(UUID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      collateralOwnerService.findById(UUID);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.COLLATERAL_OWNER_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenFindByLoanId_shouldReturnSuccess() {
    CollateralOwnerEntity mockEntity = CollateralOwnerEntity.builder()
        .uuid(UUID)
        .loanId(LOAN_ID)
        .fullName("Nguyen van A")
        .build();
    List<CollateralOwnerEntity> entities = new ArrayList<>();
    entities.add(mockEntity);

    doReturn(entities).when(collateralOwnerRepository).findByLoanId(LOAN_ID);

    List<CollateralOwner> collaterals = collateralOwnerService.findByLoanId(LOAN_ID);
    assertEquals(collaterals.size(), entities.size());
    assertEquals(collaterals.get(0).getFullName(), mockEntity.getFullName());
    assertEquals(collaterals.get(0).getLoanId(), mockEntity.getLoanId());
  }

  @Test
  void givenInValidInput_ThenUpdate_shouldReturnCollateralNotFound() {
    CollateralOwnerEntity entity = BuildData.buildCollateralOwner(UUID);
    CollateralOwner collateralOwner = CollateralOwnerMapper.INSTANCE.toModel(entity);

    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
        .status(LoanInfoStatusEnum.DRAFT)
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(entity.getLoanId());

    doReturn(Optional.empty()).when(collateralOwnerRepository).findById(UUID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      collateralOwnerService.save(collateralOwner);
    });

    assertEquals(exception.getCode(), ErrorEnum.COLLATERAL_OWNER_NOT_FOUND.getCode());
  }

  @Test
  void givenInputValid_ThenSaveInsert_shouldReturnSuccess() {
    CollateralOwnerEntity entity = BuildData.buildCollateralOwner(null);
    CollateralOwner collateralOwner = CollateralOwnerMapper.INSTANCE.toModel(entity);

    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
        .status(LoanInfoStatusEnum.DRAFT)
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(entity.getLoanId());

    doReturn(entity).when(collateralOwnerRepository).save(entity);

    entity.setUuid(UUID);
    CollateralOwner result = collateralOwnerService.save(collateralOwner);

    assertEquals(result.getUuid(), UUID);
    assertEquals(result.getFullName(), entity.getFullName());
  }

  @Test
  void givenInputValid_ThenSaveUpdate_shouldReturnSuccess() {
    CollateralOwnerEntity entity = BuildData.buildCollateralOwner(UUID);
    CollateralOwner collateralOwner = CollateralOwnerMapper.INSTANCE.toModel(entity);
    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(entity.getLoanId())
        .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
        .status(LoanInfoStatusEnum.DRAFT)
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(entity.getLoanId());

    doReturn(entity).when(collateralOwnerRepository).save(entity);
    doReturn(Optional.of(entity)).when(collateralOwnerRepository).findById(UUID);

    entity.setUuid(UUID);
    CollateralOwner result = collateralOwnerService.save(collateralOwner);

    assertEquals(result.getUuid(), UUID);
    assertEquals(result.getFullName(), entity.getFullName());
  }

  @Test
  void givenInputValid_ThenSaves_shouldReturnSuccess() {
    CollateralOwnerEntity entity = BuildData.buildCollateralOwner(null);
    List<CollateralOwner> entities = new ArrayList<>();
    CollateralOwner collateralOwner = CollateralOwnerMapper.INSTANCE.toModel(entity);
    entities.add(collateralOwner);

    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
        .status(LoanInfoStatusEnum.DRAFT)
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(any());

    doReturn(entity).when(collateralOwnerRepository).save(entity);

    entity.setUuid(UUID);
    List<CollateralOwner> result = collateralOwnerService.saves(entities);

    assertEquals(result.size(), 1);
    assertEquals(result.get(0).getFullName(), entity.getFullName());
  }

  @Test
  void givenInputEmpty_ThenSaves_shouldReturnSuccess() {
    List<CollateralOwner> entities = new ArrayList<>();

    List<CollateralOwner> result = collateralOwnerService.saves(entities);

    assertEquals(result.size(), 0);
  }

  @Test
  void givenInputValid_ThenFindByLoanId_shouldReturnLoanNotFound() {
    doReturn(Optional.empty()).when(loanApplicationRepository).findById(any());
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      collateralOwnerService.getAllByLoanId(LOAN_ID);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenInputValid_ThenFindByLoanId_shouldReturnSuccess() {
    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .fullName("Nguyen van A")
        .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
        .status(LoanInfoStatusEnum.DRAFT)
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(any());

    MarriedPersonEntity marriedPersonEntity = BuildData.buildMarriedPersonEntity(LOAN_ID);
    doReturn(marriedPersonEntity).when(marriedPersonRepository).findOneByLoanId(LOAN_ID);

    LoanPayerEntity loanPayer = BuildData.buildLoanPayer(LOAN_ID);
    loanPayer.setUuid("3");
    List<LoanPayerEntity> loanPayerEntities = new ArrayList<>();
    loanPayerEntities.add(loanPayer);
    doReturn(loanPayerEntities).when(loanPayerRepository).findByLoanId(loanApplication.getUuid());

    List<CollateralOwnerEntity> collateralOwnerEntities = new ArrayList<>();
    CollateralOwnerEntity collateralOwner4 = BuildData.buildCollateralOwner("4");
    collateralOwner4.setLoanId(LOAN_ID);
    collateralOwnerEntities.add(collateralOwner4);
    doReturn(collateralOwnerEntities).when(collateralOwnerRepository).findByLoanId(LOAN_ID);

    List<CollateralOwner> collaterals = collateralOwnerService.getAllByLoanId(LOAN_ID);

    assertEquals(collaterals.size(), 4);
  }
}