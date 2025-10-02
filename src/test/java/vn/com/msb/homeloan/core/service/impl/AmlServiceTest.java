package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import vn.com.msb.homeloan.api.dto.request.CMSAmlCheckRequest;
import vn.com.msb.homeloan.core.entity.AmlEntity;
import vn.com.msb.homeloan.core.entity.CollateralOwnerEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;
import vn.com.msb.homeloan.core.model.Aml;
import vn.com.msb.homeloan.core.model.CMSGetAmlInfo;
import vn.com.msb.homeloan.core.model.response.aml.AmlResponse;
import vn.com.msb.homeloan.core.repository.AmlRepository;
import vn.com.msb.homeloan.core.repository.CollateralOwnerRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.repository.MarriedPersonRepository;
import vn.com.msb.homeloan.core.service.AmlService;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.util.Client;
import vn.com.msb.homeloan.infras.configs.feign.AmlConfig;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@ExtendWith(MockitoExtension.class)
class AmlServiceTest {

  private final String LOAN_APPLICATION_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String AML_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String CLIENT_CODE = "client-code";
  private final String ID_PASSPORT = "XI08411";
  private final String IDENTITY_CARD = "010002028";
  private final String IDENTITY_CARD_OTHER = "010002030";
  private final String IDENTITY_CARD_OLD = "010002029";
  private final String IDENTITY_CARD_OLD_OTHER = "010002031";
  private final String IDENTITY_CARD_OLD_2 = "010002032";
  private final String IDENTITY_CARD_OLD_3 = "010002033";

  AmlService amlService;

  @Mock
  Client client;

  @Mock
  AmlConfig amlConfig;

  @Mock
  EnvironmentProperties envProperties;

  @Mock
  AmlRepository amlRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  MarriedPersonRepository marriedPersonRepository;

  @Mock
  LoanPayerRepository loanPayerRepository;

  @Mock
  CollateralOwnerRepository collateralOwnerRepository;

  @Mock
  CMSTabActionService cmsTabActionService;

  @BeforeEach
  void setUp() {
    this.envProperties = new EnvironmentProperties();
    this.envProperties.setClientCode(CLIENT_CODE);
    this.amlConfig = new AmlConfig();
    this.amlConfig.setBaseUrl("https://common-api.dev.df.msb.com.vn/");
    this.amlConfig.setApiCheck("api/v1/customers/aml/check");
    this.amlService = new AmlServiceImpl(
        client,
        amlConfig,
        envProperties,
        amlRepository,
        loanApplicationRepository,
        marriedPersonRepository,
        loanPayerRepository,
        collateralOwnerRepository,
        cmsTabActionService);
  }

  @Test
  void givenValidInput_ThenAmls_shouldReturnSuccess() {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    List<CMSAmlCheckRequest.AmlRequest> checkAmls = new ArrayList<>();
    CMSAmlCheckRequest.AmlRequest customerInfo = new CMSAmlCheckRequest.AmlRequest();
    Set<String> idPassports = new HashSet<>();
    idPassports.add(ID_PASSPORT);
    customerInfo.setIdPassports(idPassports);
    checkAmls.add(customerInfo);

    AmlEntity amlEntity = AmlEntity.builder()
        .uuid(AML_ID)
        .loanApplicationId(LOAN_APPLICATION_ID).build();
    doReturn(amlEntity).when(amlRepository)
        .findByLoanApplicationIdAndIdPassport(LOAN_APPLICATION_ID, ID_PASSPORT);

    AmlResponse amlResponse = new AmlResponse();
    AmlResponse.DataAml dataAml = new AmlResponse.DataAml();
    dataAml.setStatus("500");
    amlResponse.setDataAml(dataAml);
    doReturn(ResponseEntity.ok(amlResponse)).when(client).getWithBody(any(), any(), any());

    List<Aml> result = amlService.checkAmls(LOAN_APPLICATION_ID, checkAmls);

    assertEquals(result.get(0).getLoanApplicationId(), LOAN_APPLICATION_ID);
  }

  @Test
  void givenValidInput_ThenAmls_shouldReturnSuccessWhenCallFirstTime() {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    List<CMSAmlCheckRequest.AmlRequest> checkAmls = new ArrayList<>();
    CMSAmlCheckRequest.AmlRequest customerInfo = new CMSAmlCheckRequest.AmlRequest();
    Set<String> idPassports = new HashSet<>();
    idPassports.add(ID_PASSPORT);
    customerInfo.setIdPassports(idPassports);
    checkAmls.add(customerInfo);

    AmlResponse amlResponse = new AmlResponse();
    AmlResponse.DataAml dataAml = new AmlResponse.DataAml();
    dataAml.setStatus("200");
    amlResponse.setDataAml(dataAml);
    doReturn(ResponseEntity.ok(amlResponse)).when(client).getWithBody(any(), any(), any());

    List<Aml> result = amlService.checkAmls(LOAN_APPLICATION_ID, checkAmls);

    assertEquals(result.get(0).getLoanApplicationId(), LOAN_APPLICATION_ID);
  }

  @Test
  void givenValidInput_ThenGetAML_shouldReturnSuccess() {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID)
        .idNo(IDENTITY_CARD)
        .oldIdNo(IDENTITY_CARD_OLD)
        .oldIdNo2(IDENTITY_CARD_OLD_2)
        .oldIdNo3(IDENTITY_CARD_OLD_3).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findByUuid(LOAN_APPLICATION_ID);

    List<AmlEntity> amlEntities = new ArrayList<>();
    AmlEntity amlEntity = AmlEntity.builder()
        .uuid(AML_ID)
        .idPassport(IDENTITY_CARD)
        .build();
    amlEntities.add(amlEntity);
    doReturn(amlEntities).when(amlRepository)
        .findByLoanApplicationIdAndIdPassportIn(LOAN_APPLICATION_ID, new HashSet<>(
            Arrays.asList(IDENTITY_CARD, IDENTITY_CARD_OLD, IDENTITY_CARD_OLD_2,
                IDENTITY_CARD_OLD_3)));

    MarriedPersonEntity marriedPersonEntity = MarriedPersonEntity.builder()
        .loanId(LOAN_APPLICATION_ID)
        .idNo(IDENTITY_CARD)
        .oldIdNo(IDENTITY_CARD_OLD).build();
    doReturn(marriedPersonEntity).when(marriedPersonRepository)
        .findOneByLoanId(LOAN_APPLICATION_ID);

    List<LoanPayerEntity> loanPayers = new ArrayList<>();
    LoanPayerEntity loanPayerEntity = LoanPayerEntity.builder()
        .loanId(LOAN_APPLICATION_ID)
        .idNo(IDENTITY_CARD)
        .oldIdNo(IDENTITY_CARD_OLD).build();
    loanPayers.add(loanPayerEntity);
    doReturn(loanPayers).when(loanPayerRepository).findByLoanId(LOAN_APPLICATION_ID);

    List<CollateralOwnerEntity> collateralOwners = new ArrayList<>();
    CollateralOwnerEntity collateralOwnerEntity = CollateralOwnerEntity.builder()
        .loanId(LOAN_APPLICATION_ID)
        .idNo(IDENTITY_CARD)
        .oldIdNo(IDENTITY_CARD_OLD).build();
    collateralOwners.add(collateralOwnerEntity);
    doReturn(collateralOwners).when(collateralOwnerRepository).getByLoanId(LOAN_APPLICATION_ID);

    List<CMSGetAmlInfo> result = amlService.getAmlInfo(LOAN_APPLICATION_ID);

    assertEquals(result.get(0).getAmls().get(0).getUuid(), AML_ID);
  }
}
