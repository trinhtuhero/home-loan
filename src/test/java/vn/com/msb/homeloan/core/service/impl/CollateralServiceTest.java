package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.CollateralTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.entity.CollateralEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.Collateral;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.repository.CollateralOwnerMapRepository;
import vn.com.msb.homeloan.core.repository.CollateralOwnerRepository;
import vn.com.msb.homeloan.core.repository.CollateralRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanItemCollateralDistributeRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.repository.MarriedPersonRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CollateralService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@ExtendWith(MockitoExtension.class)
class CollateralServiceTest {

  CollateralService collateralService;

  @Mock
  CollateralRepository collateralRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  CollateralOwnerRepository collateralOwnerRepository;

  @Mock
  EnvironmentProperties environmentProperties;

  @Mock
  LoanApplicationService loanApplicationService;

  @Mock
  CollateralOwnerMapRepository collateralOwnerMapRepository;

  @Mock
  CMSTabActionService cmsTabActionService;


  @Mock
  MarriedPersonRepository marriedPersonRepository;

  @Mock
  LoanPayerRepository loanPayerRepository;

  @Mock
  LoanItemCollateralDistributeRepository loanItemCollateralDistributeRepository;

  private final String COLLATERAL_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ITEM_COLLATERAL_DISTRIBUTION_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  @BeforeEach
  void setUp() {
    this.collateralService = new CollateralServiceImpl(
        collateralRepository,
        collateralOwnerRepository,
        loanApplicationRepository,
        collateralOwnerMapRepository,
        environmentProperties,
        loanApplicationService,
        cmsTabActionService,
        marriedPersonRepository,
        loanPayerRepository,
        loanItemCollateralDistributeRepository);
  }

  @Test
  void givenValidInput_ThenDeleteById_shouldReturnCollateralNotFound() {
    doReturn(Optional.empty()).when(collateralRepository).findById(COLLATERAL_ID);

    ApplicationException exception = assertThrows(ApplicationException.class,
        () -> collateralService.deleteById(COLLATERAL_ID, ClientTypeEnum.LDP));

    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenDeleteById_shouldLoanNotFound() {
    CollateralEntity mockEntity = CollateralEntity.builder()
        .uuid(COLLATERAL_ID)
        .loanId(LOAN_ID)
        .fullName("Nguyen van A")
        .build();

    doReturn(Optional.of(mockEntity)).when(collateralRepository).findById(mockEntity.getUuid());

    doReturn(Optional.empty()).when(loanApplicationRepository).findById(mockEntity.getLoanId());

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      collateralService.deleteById(COLLATERAL_ID, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenDeleteById_shouldReturnSuccess() throws IOException {
    CollateralEntity mockEntity = CollateralEntity.builder()
        .uuid(COLLATERAL_ID)
        .loanId(LOAN_ID)
        .fullName("Nguyen van A")
        .build();
    doReturn(Optional.of(mockEntity)).when(collateralRepository).findById(mockEntity.getUuid());
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(mockEntity.getLoanId());

    doReturn(new ArrayList<>()).when(loanItemCollateralDistributeRepository)
        .findByCollateralId(COLLATERAL_ID);

    collateralService.deleteById(COLLATERAL_ID, ClientTypeEnum.LDP);
    verify(collateralRepository, times(1)).deleteById(COLLATERAL_ID);
  }

  @Test
  void givenValidInput_ThenDeleteByIdCMS_shouldReturnSuccess() throws IOException {
    CollateralEntity mockEntity = CollateralEntity.builder()
        .uuid(COLLATERAL_ID)
        .loanId(LOAN_ID)
        .fullName("Nguyen van A")
        .build();
    doReturn(Optional.of(mockEntity)).when(collateralRepository).findById(mockEntity.getUuid());
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(mockEntity.getLoanId());

    doReturn(new ArrayList<>()).when(loanItemCollateralDistributeRepository)
        .findByCollateralId(COLLATERAL_ID);

    collateralService.deleteById(COLLATERAL_ID, ClientTypeEnum.CMS);
    verify(collateralRepository, times(1)).deleteById(COLLATERAL_ID);
  }

  @Test
  void givenValidInput_ThenFindById_shouldReturnSuccess() {
    CollateralEntity mockEntity = CollateralEntity.builder()
        .uuid(COLLATERAL_ID)
        .loanId(LOAN_ID)
        .fullName("Nguyen van A")
        .build();

    doReturn(Optional.of(mockEntity)).when(collateralRepository).findById(mockEntity.getUuid());

    Collateral collateral = collateralService.findById(mockEntity.getUuid());
    assertEquals(collateral.getUuid(), mockEntity.getUuid());
    assertEquals(collateral.getFullName(), mockEntity.getFullName());
    assertEquals(collateral.getLoanId(), mockEntity.getLoanId());
  }

  @Test
  void givenValidInput_ThenFindById_shouldReturnFail() {
    doReturn(Optional.empty()).when(collateralRepository).findById(COLLATERAL_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      collateralService.findById(COLLATERAL_ID);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.COLLATERAL_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenFindByLoanId_shouldReturnSuccess() {
    CollateralEntity mockEntity = CollateralEntity.builder()
        .uuid(COLLATERAL_ID)
        .loanId(LOAN_ID)
        .fullName("Nguyen van A")
        .build();
    List<CollateralEntity> entities = new ArrayList<>();
    entities.add(mockEntity);

    doReturn(entities).when(collateralRepository).findByLoanId(LOAN_ID);

    List<Collateral> collaterals = collateralService.findByLoanId(LOAN_ID);
    assertEquals(collaterals.size(), entities.size());
    assertEquals(collaterals.get(0).getFullName(), mockEntity.getFullName());
    assertEquals(collaterals.get(0).getLoanId(), mockEntity.getLoanId());
  }

//    @Test
//    void givenValidInput_ThenCmsFindByLoanId_shouldReturnSuccess() {
//        CollateralEntity mockEntity = CollateralEntity.builder()
//                .uuid(COLLATERAL_ID)
//                .loanId(LOAN_ID)
//                .fullName("Nguyen van A")
//                .status(CollateralStatusEnum.THIRD_PARTY)
//                .build();
//        CollateralEntity mockEntity1 = CollateralEntity.builder()
//                .uuid(COLLATERAL_ID)
//                .loanId(LOAN_ID)
//                .fullName("Nguyen van A")
//                .status(CollateralStatusEnum.OTHERS)
//                .build();
//        List<CollateralEntity> entities = new ArrayList<>();
//        entities.add(mockEntity);
//        entities.add(mockEntity1);
//        doReturn(entities).when(collateralRepository).findByLoanIdOrderByCreatedAtAsc(LOAN_ID);
//
//
//        List<CollateralOwnerEntity> collateralOwners = new ArrayList<>();
//        collateralOwners.add(CollateralOwnerEntity.builder()
//                .loanId(LOAN_ID)
//                .uuid(COLLATERAL_ID)
//                .fullName("Nguyen van a")
//                .build());
//        doReturn(collateralOwners).when(collateralOwnerRepository).findByLoanIdOrderByCreatedAtAsc(LOAN_ID);
//
//        List<CollateralOwnerMapEntity> collateralOwnerMapEntities = new ArrayList<>();
//        collateralOwnerMapEntities.add(CollateralOwnerMapEntity.builder()
//                .collateralId(COLLATERAL_ID)
//                .loanId(LOAN_ID)
//                .collateralOwnerId(COLLATERAL_ID)
//                .build());
//        doReturn(collateralOwnerMapEntities).when(collateralOwnerMapRepository).findByLoanId(LOAN_ID);
//
//        LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
//                .uuid(LOAN_ID)
//                .fullName("Nguyen van A")
//                .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
//                .status(LoanInfoStatusEnum.DRAFT)
//                .build();
//        doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(any());
//
//        List<Collateral> collaterals = collateralService.findCollateralInfoByLoanId(LOAN_ID);
//        assertEquals(collaterals.size(), 2);
//        assertEquals(collaterals.get(0).getCollateralOwners().size(), 1);
//    }

  @Test
  void givenValidInput_ThenTotalGuaranteedValues_shouldReturnSuccess() {
    doReturn(10L).when(collateralRepository).totalGuaranteedValues(LOAN_ID);

    Long result = collateralService.totalGuaranteedValues(LOAN_ID);
    assertEquals(result, 10L);
  }

  @Test
  void givenValidInput_ThenTotalValues_shouldReturnSuccess() {
    doReturn(10L).when(collateralRepository).totalValues(LOAN_ID);

    Long result = collateralService.totalValues(LOAN_ID);
    assertEquals(result, 10L);
  }

  @Test
  void givenValidInput_ThenValidateBeforeNextTab_shouldReturnTrue() {

    List<CollateralEntity> collaterals = new ArrayList<>();

    CollateralEntity collateralEntity1 = CollateralEntity.builder()
        .type(CollateralTypeEnum.ND)
        .build();
    collaterals.add(collateralEntity1);

    CollateralEntity collateralEntity2 = CollateralEntity.builder()
        .type(CollateralTypeEnum.GTCG)
        .build();
    collaterals.add(collateralEntity2);

    doReturn(collaterals).when(collateralRepository).findByLoanId(LOAN_ID);

    List<String> values = collateralService.validateBeforeNextTab(LOAN_ID);

    assertTrue(CollectionUtils.isEmpty(values));
  }

  @Test
  void givenValidInput_ThenValidateBeforeNextTab_shouldReturnFalse() {

    List<CollateralEntity> collaterals = new ArrayList<>();

    CollateralEntity collateralEntity1 = CollateralEntity.builder()
        .type(CollateralTypeEnum.GTCG)
        .build();
    collaterals.add(collateralEntity1);

    CollateralEntity collateralEntity2 = CollateralEntity.builder()
        .type(CollateralTypeEnum.PTVT)
        .build();
    collaterals.add(collateralEntity2);

    doReturn(collaterals).when(collateralRepository).findByLoanId(LOAN_ID);

    List<String> values = collateralService.validateBeforeNextTab(LOAN_ID);

    assertTrue(CollectionUtils.isNotEmpty(values));
  }
}
