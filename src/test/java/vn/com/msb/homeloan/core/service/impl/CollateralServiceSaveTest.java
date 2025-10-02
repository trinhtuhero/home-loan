package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.CollateralOwnerMapTypeEnum;
import vn.com.msb.homeloan.core.constant.CollateralStatusEnum;
import vn.com.msb.homeloan.core.constant.CollateralTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.LoanCurrentStepEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.entity.CollateralEntity;
import vn.com.msb.homeloan.core.entity.CollateralOwnerEntity;
import vn.com.msb.homeloan.core.entity.CollateralOwnerMapEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.Collateral;
import vn.com.msb.homeloan.core.model.CollateralOwnerMap;
import vn.com.msb.homeloan.core.model.mapper.CollateralMapper;
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
class CollateralServiceSaveTest {

  CollateralService collateralService;

  @Mock
  CollateralRepository collateralRepository;

  @Mock
  CollateralOwnerRepository collateralOwnerRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  EnvironmentProperties environmentProperties;

  @Mock
  CollateralOwnerMapRepository collateralOwnerMapRepository;

  @Mock
  LoanApplicationService loanApplicationService;

  @Mock
  CMSTabActionService cmsTabActionService;

  @Mock
  MarriedPersonRepository marriedPersonRepository;

  @Mock
  LoanPayerRepository loanPayerRepository;

  @Mock
  LoanItemCollateralDistributeRepository loanItemCollateralDistributeRepository;

  private final String COLLATERAL_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  @BeforeEach
  void setUp() {
    this.environmentProperties = new EnvironmentProperties();
    this.environmentProperties.setProvinceSpecial(";01;79;");
    this.collateralService = new CollateralServiceImpl(
        collateralRepository,
        collateralOwnerRepository,
        loanApplicationRepository,
        collateralOwnerMapRepository,
        this.environmentProperties,
        loanApplicationService,
        cmsTabActionService,
        marriedPersonRepository,
        loanPayerRepository,
        loanItemCollateralDistributeRepository);
  }

  @Test
  void givenInValidInputTypeOthers_ThenSave_shouldReturnInvalidForm() {
    Collateral collateral = Collateral.builder()
        .relationship("wrong")
        .location("wrong")
        .uuid(COLLATERAL_ID)
        .loanId(LOAN_ID)
        .type(CollateralTypeEnum.OTHERS)
        .status(CollateralStatusEnum.THIRD_PARTY)
        .build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      collateralService.save(collateral, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode(), ErrorEnum.INVALID_FORM.getCode());
    assertTrue(exception.getErrors().size() > 0);
  }

  @Test
  void givenInValidInputStatusOther_ThenSave_shouldReturnInvalidForm() {
    Collateral collateral = Collateral.builder()
        .relationship("")
        .location("wrong")
        .uuid(COLLATERAL_ID)
        .loanId(LOAN_ID)
        .type(CollateralTypeEnum.OTHERS)
        .status(CollateralStatusEnum.OTHERS)
        .build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      collateralService.save(collateral, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode(), ErrorEnum.INVALID_FORM.getCode());
    assertTrue(exception.getErrors().size() > 0);
  }

  @Test
  void givenInValidInputTypeNDorCC_ThenSave_shouldReturnInvalidForm() {
    Collateral collateral = Collateral.builder()
        .relationship("wrong")
        .location("wrong")
        .uuid(COLLATERAL_ID)
        .loanId(LOAN_ID)
        .type(CollateralTypeEnum.ND)
        .status(CollateralStatusEnum.THIRD_PARTY)
        .build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      collateralService.save(collateral, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode(), ErrorEnum.INVALID_FORM.getCode());
    assertTrue(exception.getErrors().size() > 0);
  }

  @Test
  void givenInValidInputProvinceSpecial_ThenSave_shouldReturnInvalidForm() {
    Collateral collateral = Collateral.builder()
        .relationship("wrong")
        .uuid(COLLATERAL_ID)
        .loanId(LOAN_ID)
        .type(CollateralTypeEnum.ND)
        .province("01")

        .status(CollateralStatusEnum.THIRD_PARTY)
        .build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      collateralService.save(collateral, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode(), ErrorEnum.INVALID_FORM.getCode());
    assertTrue(exception.getErrors().size() > 0);
  }

  @Test
  void givenInValidInput_ThenUpdate_shouldReturnCollateralNotFound() {
    CollateralEntity entity = BuildData.buildCollateral(COLLATERAL_ID, LOAN_ID);
    Collateral collateral = CollateralMapper.INSTANCE.toModel(entity);

    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
        .status(LoanInfoStatusEnum.DRAFT)
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());

    doReturn(Optional.empty()).when(collateralRepository).findById(COLLATERAL_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      collateralService.save(collateral, ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode(), ErrorEnum.COLLATERAL_NOT_FOUND.getCode());
  }

  @Test
  void givenInputValid_ThenSaveInsert_shouldReturnSuccess() throws IOException {
    CollateralEntity entity = BuildData.buildCollateral(null, LOAN_ID);
    Collateral collateral = CollateralMapper.INSTANCE.toModel(entity);

    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
        .status(LoanInfoStatusEnum.DRAFT)
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());

    doReturn(entity).when(collateralRepository).save(entity);

    entity.setUuid(COLLATERAL_ID);
    Collateral result = collateralService.save(collateral, ClientTypeEnum.LDP);

    assertEquals(result.getUuid(), COLLATERAL_ID);
    assertEquals(result.getFullName(), entity.getFullName());
  }

  @Test
  void givenInputValid_ThenSaveLocationEmptyInsert_shouldReturnSuccess() throws IOException {
    CollateralEntity entity = BuildData.buildCollateral(null, LOAN_ID);
    entity.setProvince("10");
    entity.setRelationship(null);
    entity.setLocation(null);
    Collateral collateral = CollateralMapper.INSTANCE.toModel(entity);
    collateral.setRelationship("");
    collateral.setLocation("");

    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
        .status(LoanInfoStatusEnum.DRAFT)
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());

    doReturn(entity).when(collateralRepository).save(entity);

    entity.setUuid(COLLATERAL_ID);
    Collateral result = collateralService.save(collateral, ClientTypeEnum.LDP);

    assertEquals(result.getUuid(), COLLATERAL_ID);
    assertEquals(result.getFullName(), entity.getFullName());
  }


  @Test
  void givenInputValid_ThenSaves_shouldReturnSuccess() throws IOException {
    CollateralEntity entity = BuildData.buildCollateral(null, LOAN_ID);
    Collateral collateral = CollateralMapper.INSTANCE.toModel(entity);
    List<Collateral> collaterals = new ArrayList<>();
    collaterals.add(collateral);

    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
        .status(LoanInfoStatusEnum.DRAFT)
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());

    doReturn(entity).when(collateralRepository).save(entity);

    entity.setUuid(COLLATERAL_ID);
    List<Collateral> results = collateralService.saves(collaterals, ClientTypeEnum.LDP);

    assertEquals(results.size(), 1);
    assertEquals(results.get(0).getUuid(), COLLATERAL_ID);
    assertEquals(results.get(0).getFullName(), entity.getFullName());
  }

  @Test
  void givenInputValid_ThenSaveUpdate_shouldReturnSuccess() throws IOException {
    CollateralEntity entity = BuildData.buildCollateral(COLLATERAL_ID, LOAN_ID);
    Collateral collateral = CollateralMapper.INSTANCE.toModel(entity);

    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
        .status(LoanInfoStatusEnum.DRAFT)
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());

    doReturn(entity).when(collateralRepository).save(entity);
    doReturn(Optional.of(entity)).when(collateralRepository).findById(COLLATERAL_ID);

    entity.setUuid(COLLATERAL_ID);
    Collateral result = collateralService.save(collateral, ClientTypeEnum.LDP);

    assertEquals(result.getUuid(), COLLATERAL_ID);
    assertEquals(result.getFullName(), entity.getFullName());
  }

  @Test
  void givenInputValid_ThenSaveInsertCms_shouldReturnSuccess() throws IOException {
    CollateralEntity entity = BuildData.buildCollateral(null, LOAN_ID);
    entity.setLegalDoc("Test");
    entity.setDocIssuedOn(new Date());
    entity.setDocPlaceOfIssue("Test");

    Collateral collateral = CollateralMapper.INSTANCE.toModel(entity);

    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
        .status(LoanInfoStatusEnum.DRAFT)
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());

    doReturn(entity).when(collateralRepository).save(entity);

    entity.setUuid(COLLATERAL_ID);
    Collateral result = collateralService.save(collateral, ClientTypeEnum.CMS);

    assertEquals(result.getUuid(), COLLATERAL_ID);
    assertEquals(result.getFullName(), entity.getFullName());
  }

  @Test
  void givenInputValid_ThenSaveUpdateCms_shouldReturnSuccess() throws IOException {
    CollateralEntity entity = BuildData.buildCollateral(COLLATERAL_ID, LOAN_ID);
    entity.setLegalDoc("Test");
    entity.setDocIssuedOn(new Date());
    entity.setDocPlaceOfIssue("Test");
    entity.setStatus(CollateralStatusEnum.COUPLE);
    Collateral collateral = CollateralMapper.INSTANCE.toModel(entity);

    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
        .status(LoanInfoStatusEnum.DRAFT)
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());

    doReturn(entity).when(collateralRepository).save(entity);
    doReturn(Optional.of(entity)).when(collateralRepository).findById(COLLATERAL_ID);

    entity.setUuid(COLLATERAL_ID);
    Collateral result = collateralService.save(collateral, ClientTypeEnum.CMS);

    assertEquals(result.getUuid(), COLLATERAL_ID);
    assertEquals(result.getFullName(), entity.getFullName());
  }

  @Test
  void givenInputValid_ThenFindByLoanId_shouldReturnLoanNotFound() {
    doReturn(Optional.empty()).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      collateralService.findCollateralInfoByLoanId(LOAN_ID);
    });

    assertEquals(exception.getCode(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenInputValid_ThenFindByLoanId_shouldReturnSuccess() {
    CollateralEntity entity = BuildData.buildCollateral(COLLATERAL_ID, LOAN_ID);
    List<CollateralEntity> collateralEntities = new ArrayList<>();
    collateralEntities.add(entity);
    doReturn(collateralEntities).when(collateralRepository)
        .findByLoanIdOrderByCreatedAtAsc(LOAN_ID);

    List<CollateralOwnerEntity> collateralOwnerEntities = new ArrayList<>();

    CollateralOwnerEntity collateralOwner4 = BuildData.buildCollateralOwner("4");
    collateralOwner4.setLoanId(LOAN_ID);
    collateralOwnerEntities.add(collateralOwner4);

    doReturn(collateralOwnerEntities).when(collateralOwnerRepository)
        .findByLoanIdOrderByCreatedAtAsc(LOAN_ID);

    List<CollateralOwnerMapEntity> maps = new ArrayList<>();
    CollateralOwnerMapEntity map = CollateralOwnerMapEntity.builder()
        .collateralOwnerId("1")
        .collateralId(COLLATERAL_ID)
        .type(CollateralOwnerMapTypeEnum.ME)
        .loanId(LOAN_ID)
        .build();
    maps.add(map);
    CollateralOwnerMapEntity map1 = CollateralOwnerMapEntity.builder()
        .collateralOwnerId("2")
        .collateralId(COLLATERAL_ID)
        .type(CollateralOwnerMapTypeEnum.COUPLE)
        .loanId(LOAN_ID)
        .build();
    maps.add(map1);

    CollateralOwnerMapEntity map2 = CollateralOwnerMapEntity.builder()
        .collateralOwnerId("3")
        .collateralId(COLLATERAL_ID)
        .type(CollateralOwnerMapTypeEnum.LOAN_PAYER)
        .loanId(LOAN_ID)
        .build();
    maps.add(map2);

    CollateralOwnerMapEntity map3 = CollateralOwnerMapEntity.builder()
        .collateralOwnerId("4")
        .collateralId(COLLATERAL_ID)
        .type(CollateralOwnerMapTypeEnum.THIRD_PARTY)
        .loanId(LOAN_ID)
        .build();
    maps.add(map3);

    doReturn(maps).when(collateralOwnerMapRepository).findByLoanId(LOAN_ID);

    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .fullName("Nguyen van A")
        .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
        .status(LoanInfoStatusEnum.DRAFT)
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(any());

    LoanPayerEntity loanPayer = BuildData.buildLoanPayer(LOAN_ID);
    loanPayer.setUuid("3");
    List<LoanPayerEntity> loanPayerEntities = new ArrayList<>();
    loanPayerEntities.add(loanPayer);
    doReturn(loanPayerEntities).when(loanPayerRepository).findByLoanId(loanApplication.getUuid());

    MarriedPersonEntity marriedPersonEntity = BuildData.buildMarriedPersonEntity(LOAN_ID);
    doReturn(Optional.of(marriedPersonEntity)).when(marriedPersonRepository).findById(any());

    List<Collateral> collaterals = collateralService.findCollateralInfoByLoanId(LOAN_ID);

    assertEquals(collaterals.size(), 1);
    assertEquals(collaterals.get(0).getCollateralOwners().size(), 4);
  }

  @Test
  void givenInputValid_ThenSavesInsert_shouldReturnSuccess() throws IOException {
    CollateralEntity entity = BuildData.buildCollateral(null, LOAN_ID);
    Collateral collateral = CollateralMapper.INSTANCE.toModel(entity);
    List<Collateral> collaterals = new ArrayList<>();
    collaterals.add(collateral);

    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
        .status(LoanInfoStatusEnum.DRAFT)
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());

    doReturn(entity).when(collateralRepository).save(entity);

    entity.setUuid(COLLATERAL_ID);
    List<Collateral> result = collateralService.saves(collaterals, ClientTypeEnum.LDP);

    assertEquals(result.size(), 1);
    assertEquals(result.get(0).getUuid(), COLLATERAL_ID);
    assertEquals(result.get(0).getFullName(), entity.getFullName());
  }

  @Test
  void givenInputValid_ThenCMSSaves_shouldReturnSuccess() throws IOException {
    CollateralEntity entity = BuildData.buildCollateral(null, LOAN_ID);
    entity.setLegalDoc("Test");
    entity.setDocIssuedOn(new Date());
    entity.setDocPlaceOfIssue("Test");
    entity.setStatus(CollateralStatusEnum.COUPLE);
    Collateral collateral = CollateralMapper.INSTANCE.toModel(entity);
    List<CollateralOwnerMap> collateralOwnerMaps = new ArrayList<>();
    collateralOwnerMaps.add(CollateralOwnerMap.builder()
        .collateralOwnerId("collateralOwnerId")
        .type(CollateralOwnerMapTypeEnum.THIRD_PARTY)
        .build());
    collateral.setCollateralOwnerMaps(collateralOwnerMaps);
    List<Collateral> collaterals = new ArrayList<>();
    collaterals.add(collateral);

    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .currentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode())
        .status(LoanInfoStatusEnum.DRAFT)
        .build();
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());

    doReturn(entity).when(collateralRepository).save(entity);

    entity.setUuid(COLLATERAL_ID);
    List<Collateral> results = collateralService.saves(collaterals, ClientTypeEnum.CMS);

    assertEquals(results.size(), 1);
    assertEquals(results.get(0).getUuid(), COLLATERAL_ID);
    assertEquals(results.get(0).getFullName(), entity.getFullName());
  }

}