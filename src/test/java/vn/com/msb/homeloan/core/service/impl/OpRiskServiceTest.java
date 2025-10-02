package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import feign.FeignException;
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
import vn.com.msb.homeloan.core.client.OpRiskClient;
import vn.com.msb.homeloan.core.constant.CollateralTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.oprisk.OpRiskCheckTypeEnum;
import vn.com.msb.homeloan.core.entity.CollateralEntity;
import vn.com.msb.homeloan.core.entity.CollateralOwnerEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;
import vn.com.msb.homeloan.core.entity.OpRiskEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CMSGetOpRiskCollateralInfo;
import vn.com.msb.homeloan.core.model.CMSGetOpRiskInfo;
import vn.com.msb.homeloan.core.model.OpRisk;
import vn.com.msb.homeloan.core.model.request.OpsRiskCheckCRequest;
import vn.com.msb.homeloan.core.model.request.OpsRiskCheckP;
import vn.com.msb.homeloan.core.model.request.OpsRiskCheckPRequest;
import vn.com.msb.homeloan.core.model.response.opRisk.OpsRiskCheckCResponse;
import vn.com.msb.homeloan.core.model.response.opRisk.OpsRiskCheckPResponse;
import vn.com.msb.homeloan.core.repository.CollateralOwnerRepository;
import vn.com.msb.homeloan.core.repository.CollateralRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.repository.MarriedPersonRepository;
import vn.com.msb.homeloan.core.repository.OpRiskRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.OpRiskService;
import vn.com.msb.homeloan.infras.configs.feign.OpRiskConfig;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@ExtendWith(MockitoExtension.class)
class OpRiskServiceTest {

  private final String LOAN_APPLICATION_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String OP_RISK_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String IDENTITY_CARD = "010002028";
  private final String IDENTITY_CARD_OTHER = "010002030";
  private final String IDENTITY_CARD_OLD = "010002029";
  private final String IDENTITY_CARD_OLD_OTHER = "010002031";
  private final String IDENTITY_CARD_OLD_2 = "010002032";
  private final String IDENTITY_CARD_OLD_3 = "010002033";
  private final String CLIENT_CODE = "client-code";

  OpRiskService opRiskService;

  @Mock
  OpRiskClient opRiskClient;

  @Mock
  OpRiskConfig opRiskConfig;

  @Mock
  OpRiskRepository opRiskRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  MarriedPersonRepository marriedPersonRepository;

  @Mock
  LoanPayerRepository loanPayerRepository;

  @Mock
  EnvironmentProperties envProperties;

  @Mock
  CMSTabActionService cmsTabActionService;

  @Mock
  CollateralOwnerRepository collateralOwnerRepository;

  @Mock
  CollateralRepository collateralRepository;

  @BeforeEach
  void setUp() {
    this.envProperties = new EnvironmentProperties();
    this.envProperties.setClientCode(CLIENT_CODE);
    this.opRiskConfig = new OpRiskConfig();
    this.opRiskConfig.setSrvCheckP("srv");
    this.opRiskConfig.setReqId("reqId");
    this.opRiskConfig.setReqApp("reqApp");
    this.opRiskConfig.setPassword("password");
    this.opRiskConfig.setAuthorizer("authorizer");
    this.opRiskService = new OpRiskServiceImpl(
        opRiskClient,
        opRiskConfig,
        opRiskRepository,
        loanApplicationRepository,
        marriedPersonRepository,
        loanPayerRepository,
        envProperties,
        cmsTabActionService,
        collateralOwnerRepository,
        collateralRepository);
  }

  @Test
  void givenValidInput_ThenCheckP_shouldReturnSuccess() throws Exception {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    Set<String> identityCards = new HashSet<>();
    identityCards.add("B9441912");
    identityCards.add("B9441912");
    List<OpsRiskCheckP> opsRiskCheckPs = new ArrayList<>();
    OpsRiskCheckP opsRiskCheckP = OpsRiskCheckP.builder()
        .identityCards(identityCards)
//                .birthday("14/11/1983")
//                .name("1742335")
        .endDate("15/09/2020").build();
    opsRiskCheckPs.add(opsRiskCheckP);
    OpsRiskCheckPRequest opsRiskCheckPRequest = OpsRiskCheckPRequest.builder()
        .opsRiskCheckP(opsRiskCheckP).build();

    String opRiskStr = "{\n" +
        "    \"data\": {\n" +
        "        \"checkBlackListP\": {\n" +
        "            \"respDomain\": {\n" +
        "                \"blackList4LosP\": {\n" +
        "                    \"address\": null,\n" +
        "                    \"branchInput\": null,\n" +
        "                    \"cusId\": null,\n" +
        "                    \"dateEx\": null,\n" +
        "                    \"dateInput\": null,\n" +
        "                    \"dob\": null,\n" +
        "                    \"dscn\": null,\n" +
        "                    \"dtcn\": null,\n" +
        "                    \"errorMess\": null,\n" +
        "                    \"id\": null,\n" +
        "                    \"inputUser\": null,\n" +
        "                    \"name\": null,\n" +
        "                    \"recordId\": null,\n" +
        "                    \"result\": \"False\",\n" +
        "                    \"version\": null\n" +
        "                }\n" +
        "            },\n" +
        "            \"respMessage\": {\n" +
        "                \"respCode\": \"0\",\n" +
        "                \"respDesc\": \"System ok!\"\n" +
        "            }\n" +
        "        }\n" +
        "    },\n" +
        "    \"status\": 200\n" +
        "}";
    Gson g = new Gson();
    OpsRiskCheckPResponse opsRiskCheckPResponse = g.fromJson(opRiskStr,
        OpsRiskCheckPResponse.class);
    doReturn(ResponseEntity.ok(opsRiskCheckPResponse)).when(opRiskClient)
        .checkP(opsRiskCheckPRequest, CLIENT_CODE);

    List<OpRisk> result = opRiskService.checkP(opsRiskCheckPs, LOAN_APPLICATION_ID);

    assertEquals(result.get(0).getLoanApplicationId(), LOAN_APPLICATION_ID);
  }

  @Test
  void givenValidInput_ThenCheckPExist_shouldReturnSuccess() throws Exception {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    OpRiskEntity opRiskEntity1 = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .loanApplicationId(LOAN_APPLICATION_ID)
        .identityCard("B9441912").build();
    OpRiskEntity opRiskEntity2 = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .loanApplicationId(LOAN_APPLICATION_ID)
        .identityCard("B9441913").build();
    List<OpRiskEntity> opRiskEntities = new ArrayList<>(
        Arrays.asList(opRiskEntity1, opRiskEntity2));
    doReturn(opRiskEntities).when(opRiskRepository)
        .findByLoanApplicationIdAndCheckType(LOAN_APPLICATION_ID, OpRiskCheckTypeEnum.CHECK_P);

    Set<String> identityCards = new HashSet<>();
    identityCards.add("B9441912");
    identityCards.add("B9441913");
    List<OpsRiskCheckP> opsRiskCheckPs = new ArrayList<>();
    OpsRiskCheckP opsRiskCheckP = OpsRiskCheckP.builder()
        .identityCards(identityCards)
//                .birthday("14/11/1983")
//                .name("1742335")
        .endDate("15/09/2020").build();
    opsRiskCheckPs.add(opsRiskCheckP);
    OpsRiskCheckPRequest opsRiskCheckPRequest = OpsRiskCheckPRequest.builder()
        .opsRiskCheckP(opsRiskCheckP).build();

    String opRiskStr = "{\n" +
        "    \"data\": {\n" +
        "        \"checkBlackListP\": {\n" +
        "            \"respDomain\": {\n" +
        "                \"blackList4LosP\": {\n" +
        "                    \"address\": null,\n" +
        "                    \"branchInput\": null,\n" +
        "                    \"cusId\": null,\n" +
        "                    \"dateEx\": null,\n" +
        "                    \"dateInput\": null,\n" +
        "                    \"dob\": null,\n" +
        "                    \"dscn\": null,\n" +
        "                    \"dtcn\": null,\n" +
        "                    \"errorMess\": null,\n" +
        "                    \"id\": null,\n" +
        "                    \"inputUser\": null,\n" +
        "                    \"name\": null,\n" +
        "                    \"recordId\": null,\n" +
        "                    \"result\": \"False\",\n" +
        "                    \"version\": null\n" +
        "                }\n" +
        "            },\n" +
        "            \"respMessage\": {\n" +
        "                \"respCode\": \"0\",\n" +
        "                \"respDesc\": \"System ok!\"\n" +
        "            }\n" +
        "        }\n" +
        "    },\n" +
        "    \"status\": 200\n" +
        "}";
    Gson g = new Gson();
    OpsRiskCheckPResponse opsRiskCheckPResponse = g.fromJson(opRiskStr,
        OpsRiskCheckPResponse.class);
    doReturn(ResponseEntity.ok(opsRiskCheckPResponse)).when(opRiskClient)
        .checkP(opsRiskCheckPRequest, CLIENT_CODE);

    List<OpRisk> result = opRiskService.checkP(opsRiskCheckPs, LOAN_APPLICATION_ID);

    assertEquals(result.get(0).getLoanApplicationId(), LOAN_APPLICATION_ID);
  }

  @Test
  void givenValidInput_ThenCheckPSetPassFalse_shouldReturnSuccess() throws Exception {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    OpRiskEntity opRiskEntity1 = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .loanApplicationId(LOAN_APPLICATION_ID)
        .identityCard("B9441912").build();
    OpRiskEntity opRiskEntity2 = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .loanApplicationId(LOAN_APPLICATION_ID)
        .identityCard("B9441913").build();
    List<OpRiskEntity> opRiskEntities = new ArrayList<>(
        Arrays.asList(opRiskEntity1, opRiskEntity2));
    doReturn(opRiskEntities).when(opRiskRepository)
        .findByLoanApplicationIdAndCheckType(LOAN_APPLICATION_ID, OpRiskCheckTypeEnum.CHECK_P);

    Set<String> identityCards = new HashSet<>();
    identityCards.add("B9441912");
    identityCards.add("B9441913");
    List<OpsRiskCheckP> opsRiskCheckPs = new ArrayList<>();
    OpsRiskCheckP opsRiskCheckP = OpsRiskCheckP.builder()
        .identityCards(identityCards)
//                .birthday("14/11/1983")
//                .name("1742335")
        .endDate("15/09/2020").build();
    opsRiskCheckPs.add(opsRiskCheckP);
    OpsRiskCheckPRequest opsRiskCheckPRequest = OpsRiskCheckPRequest.builder()
        .opsRiskCheckP(opsRiskCheckP).build();

    String opRiskStr = "{\n" +
        "    \"data\": {\n" +
        "        \"checkBlackListP\": {\n" +
        "            \"respDomain\": {\n" +
        "                \"blackList4LosP\": {\n" +
        "                    \"address\": null,\n" +
        "                    \"branchInput\": null,\n" +
        "                    \"cusId\": null,\n" +
        "                    \"dateEx\": null,\n" +
        "                    \"dateInput\": null,\n" +
        "                    \"dob\": null,\n" +
        "                    \"dscn\": null,\n" +
        "                    \"dtcn\": null,\n" +
        "                    \"errorMess\": null,\n" +
        "                    \"id\": null,\n" +
        "                    \"inputUser\": null,\n" +
        "                    \"name\": null,\n" +
        "                    \"recordId\": null,\n" +
        "                    \"result\": \"True\",\n" +
        "                    \"version\": null\n" +
        "                }\n" +
        "            },\n" +
        "            \"respMessage\": {\n" +
        "                \"respCode\": \"1\",\n" +
        "                \"respDesc\": \"System ok!\"\n" +
        "            }\n" +
        "        }\n" +
        "    },\n" +
        "    \"status\": 200\n" +
        "}";
    Gson g = new Gson();
    OpsRiskCheckPResponse opsRiskCheckPResponse = g.fromJson(opRiskStr,
        OpsRiskCheckPResponse.class);
    doReturn(ResponseEntity.ok(opsRiskCheckPResponse)).when(opRiskClient)
        .checkP(opsRiskCheckPRequest, CLIENT_CODE);

    List<OpRisk> result = opRiskService.checkP(opsRiskCheckPs, LOAN_APPLICATION_ID);

    assertEquals(result.get(0).getLoanApplicationId(), LOAN_APPLICATION_ID);
  }

  @Test
  void givenValidInput_ThenCheckPNoData_shouldReturnSuccess() throws Exception {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    OpRiskEntity opRiskEntity1 = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .loanApplicationId(LOAN_APPLICATION_ID)
        .identityCard("B9441914").build();
    OpRiskEntity opRiskEntity2 = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .loanApplicationId(LOAN_APPLICATION_ID)
        .identityCard("B9441915").build();
    List<OpRiskEntity> opRiskEntities = new ArrayList<>(
        Arrays.asList(opRiskEntity1, opRiskEntity2));
    doReturn(opRiskEntities).when(opRiskRepository)
        .findByLoanApplicationIdAndCheckType(LOAN_APPLICATION_ID, OpRiskCheckTypeEnum.CHECK_P);

    Set<String> identityCards = new HashSet<>();
    identityCards.add("B9441912");
    identityCards.add("B9441913");
    List<OpsRiskCheckP> opsRiskCheckPs = new ArrayList<>();
    OpsRiskCheckP opsRiskCheckP = OpsRiskCheckP.builder()
        .identityCards(identityCards)
//                .birthday("14/11/1983")
//                .name("1742335")
        .endDate("15/09/2020").build();
    opsRiskCheckPs.add(opsRiskCheckP);
    OpsRiskCheckPRequest opsRiskCheckPRequest = OpsRiskCheckPRequest.builder()
        .opsRiskCheckP(opsRiskCheckP).build();

    String opRiskStr = "{\n" +
        "    \"data\": null,\n" +
        "    \"status\": 200\n" +
        "}";
    Gson g = new Gson();
    OpsRiskCheckPResponse opsRiskCheckPResponse = g.fromJson(opRiskStr,
        OpsRiskCheckPResponse.class);
    doReturn(ResponseEntity.ok(opsRiskCheckPResponse)).when(opRiskClient)
        .checkP(opsRiskCheckPRequest, CLIENT_CODE);

    List<OpRisk> result = opRiskService.checkP(opsRiskCheckPs, LOAN_APPLICATION_ID);

    assertEquals(result.get(0).getLoanApplicationId(), LOAN_APPLICATION_ID);
  }

  @Test
  void givenValidInput_ThenCheckP_shouldReturnFailCallApi() throws Exception {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    Set<String> identityCards = new HashSet<>();
    identityCards.add("B9441912");
    identityCards.add("B9441912");
    List<OpsRiskCheckP> opsRiskCheckPs = new ArrayList<>();
    OpsRiskCheckP opsRiskCheckP = OpsRiskCheckP.builder()
        .identityCards(identityCards)
//                .birthday("14/11/1983")
//                .name("1742335")
        .endDate("15/09/2020").build();
    opsRiskCheckPs.add(opsRiskCheckP);
    OpsRiskCheckPRequest opsRiskCheckPRequest = OpsRiskCheckPRequest.builder()
        .opsRiskCheckP(opsRiskCheckP).build();

    when(opRiskClient.checkP(opsRiskCheckPRequest, CLIENT_CODE))
        .thenThrow(FeignException.class);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      opRiskService.checkP(opsRiskCheckPs, LOAN_APPLICATION_ID);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.CALL_API_OPRISK_CHECK_P_ERROR.getCode());
  }

  @Test
  void givenValidInput_ThenGetOpRisk_shouldReturnSuccess() {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID)
        .idNo(IDENTITY_CARD)
        .oldIdNo(IDENTITY_CARD_OLD)
        .oldIdNo2(IDENTITY_CARD_OLD_2)
        .oldIdNo3(IDENTITY_CARD_OLD_3).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findByUuid(LOAN_APPLICATION_ID);

    List<OpRiskEntity> opRiskEntities = new ArrayList<>();
    OpRiskEntity opRiskEntity = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .identityCard(IDENTITY_CARD)
        .build();
    opRiskEntities.add(opRiskEntity);
    doReturn(opRiskEntities).when(opRiskRepository)
        .findByLoanApplicationIdAndIdentityCardInAndActive(LOAN_APPLICATION_ID, new HashSet<>(
            Arrays.asList(IDENTITY_CARD, IDENTITY_CARD_OLD, IDENTITY_CARD_OLD_2,
                IDENTITY_CARD_OLD_3)), true);

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

    List<CMSGetOpRiskInfo> result = opRiskService.getOpRiskInfo(LOAN_APPLICATION_ID);

    assertEquals(result.get(0).getOpRisks().get(0).getUuid(), OP_RISK_ID);
  }

  @Test
  void givenValidInput_ThenGetOpRisk2_shouldReturnSuccess() {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID)
        .idNo(IDENTITY_CARD_OTHER)
        .oldIdNo(IDENTITY_CARD_OLD_OTHER)
        .oldIdNo2(IDENTITY_CARD_OLD_2)
        .oldIdNo3(IDENTITY_CARD_OLD_3).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findByUuid(LOAN_APPLICATION_ID);

    doReturn(new ArrayList()).when(opRiskRepository)
        .findByLoanApplicationIdAndIdentityCardInAndActive(LOAN_APPLICATION_ID, new HashSet<>(
            Arrays.asList(IDENTITY_CARD_OTHER, IDENTITY_CARD_OLD_OTHER, IDENTITY_CARD_OLD_2,
                IDENTITY_CARD_OLD_3)), true);

    MarriedPersonEntity marriedPersonEntity = MarriedPersonEntity.builder()
        .loanId(LOAN_APPLICATION_ID)
        .idNo(IDENTITY_CARD_OTHER)
        .oldIdNo(IDENTITY_CARD_OLD_OTHER).build();
    doReturn(marriedPersonEntity).when(marriedPersonRepository)
        .findOneByLoanId(LOAN_APPLICATION_ID);

    List<LoanPayerEntity> loanPayers = new ArrayList<>();
    LoanPayerEntity loanPayerEntity = LoanPayerEntity.builder()
        .loanId(LOAN_APPLICATION_ID)
        .idNo(IDENTITY_CARD_OTHER)
        .oldIdNo(IDENTITY_CARD_OLD_OTHER).build();
    loanPayers.add(loanPayerEntity);
    doReturn(loanPayers).when(loanPayerRepository).findByLoanId(LOAN_APPLICATION_ID);

    List<CollateralOwnerEntity> collateralOwners = new ArrayList<>();
    CollateralOwnerEntity collateralOwnerEntity = CollateralOwnerEntity.builder()
        .loanId(LOAN_APPLICATION_ID)
        .idNo(IDENTITY_CARD_OTHER)
        .oldIdNo(IDENTITY_CARD_OLD_OTHER).build();
    collateralOwners.add(collateralOwnerEntity);
    doReturn(collateralOwners).when(collateralOwnerRepository).getByLoanId(LOAN_APPLICATION_ID);

    List<CMSGetOpRiskInfo> result = opRiskService.getOpRiskInfo(LOAN_APPLICATION_ID);

    assertEquals(result.size(), 4);
  }

  @Test
  void givenValidInput_ThenCheckC_shouldReturnSuccess() throws Exception {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    Set<String> identifiesInfoSet = new HashSet<>();
    identifiesInfoSet.add("B9441912");
    identifiesInfoSet.add("B9441912");
    List<OpsRiskCheckCRequest.OpsRiskCheckC> opsRiskCheckCs = new ArrayList<>();
    OpsRiskCheckCRequest.OpsRiskCheckC opsRiskCheckC = new OpsRiskCheckCRequest.OpsRiskCheckC();
    opsRiskCheckC.setIdentifiesInfoSet(identifiesInfoSet);
    opsRiskCheckC.setCollateralType("T");
    opsRiskCheckCs.add(opsRiskCheckC);
    OpsRiskCheckCRequest opsRiskCheckCRequest = OpsRiskCheckCRequest.builder()
        .opsRiskCheckC(opsRiskCheckC).build();

    String opRiskStr = "{\n" +
        "    \"data\": {\n" +
        "        \"checkBlackListC\": {\n" +
        "            \"respDomain\": {\n" +
        "                \"blackList4LosC\": {\n" +
        "                    \"cid\": \"417\",\n" +
        "                    \"idcardPassportBusinessRegistration\": \"122423523\",\n" +
        "                    \"idcardPassport\": \"123331314\",\n" +
        "                    \"borrowersByThirdParty\": \"a b c\",\n" +
        "                    \"cId\": \"417\",\n" +
        "                    \"classification\": \"Danh sách đen\",\n" +
        "                    \"dateAdded\": \"22/12/2021\",\n" +
        "                    \"division\": \"Phòng Quản lý RR Hoạt động\",\n" +
        "                    \"endDate\": \"22/12/2031\",\n" +
        "                    \"errorMess\": null,\n" +
        "                    \"iDcard_passport\": \"123331314\",\n" +
        "                    \"iDcard_passportBusinessRegistration\": \"122423523\",\n" +
        "                    \"id\": null,\n" +
        "                    \"identificationInformation\": \"test\",\n" +
        "                    \"inputUser\": \"qlrr_oprisk\",\n" +
        "                    \"listedReasons\": \"Do gian lận (giả mạo,chỉnh sửa,lừa đảo,không trung thực...)\",\n"
        +
        "                    \"owner\": \"z\",\n" +
        "                    \"result\": \"True\",\n" +
        "                    \"propertyDescription\": null,\n" +
        "                    \"typeOfProperty\": \"Bất động sản\",\n" +
        "                    \"version\": null\n" +
        "                }\n" +
        "            },\n" +
        "            \"respMessage\": {\n" +
        "                \"respCode\": \"0\",\n" +
        "                \"respDesc\": \"System ok!\"\n" +
        "            }\n" +
        "        }\n" +
        "    },\n" +
        "    \"status\": 200\n" +
        "}";
    Gson g = new Gson();
    OpsRiskCheckCResponse opsRiskResponse = g.fromJson(opRiskStr, OpsRiskCheckCResponse.class);
    doReturn(ResponseEntity.ok(opsRiskResponse)).when(opRiskClient)
        .checkC(opsRiskCheckCRequest, CLIENT_CODE);

    List<OpRisk> result = opRiskService.checkC(opsRiskCheckCs, LOAN_APPLICATION_ID);

    assertEquals(result.get(0).getLoanApplicationId(), LOAN_APPLICATION_ID);
  }

  @Test
  void givenValidInput_ThenCheckCExist_shouldReturnSuccess() throws Exception {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    OpRiskEntity opRiskEntity1 = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .loanApplicationId(LOAN_APPLICATION_ID)
        .identifyInfo("B9441912").build();
    OpRiskEntity opRiskEntity2 = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .loanApplicationId(LOAN_APPLICATION_ID)
        .identifyInfo("B9441913").build();
    List<OpRiskEntity> opRiskEntities = new ArrayList<>(
        Arrays.asList(opRiskEntity1, opRiskEntity2));
    doReturn(opRiskEntities).when(opRiskRepository)
        .findByLoanApplicationIdAndCheckType(LOAN_APPLICATION_ID, OpRiskCheckTypeEnum.CHECK_C);

    Set<String> identifiesInfoSet = new HashSet<>();
    identifiesInfoSet.add("B9441912");
    identifiesInfoSet.add("B9441913");
    List<OpsRiskCheckCRequest.OpsRiskCheckC> opsRiskCheckCs = new ArrayList<>();
    OpsRiskCheckCRequest.OpsRiskCheckC opsRiskCheckC = new OpsRiskCheckCRequest.OpsRiskCheckC();
    opsRiskCheckC.setIdentifiesInfoSet(identifiesInfoSet);
    opsRiskCheckC.setCollateralType("T");
    opsRiskCheckCs.add(opsRiskCheckC);
    OpsRiskCheckCRequest opsRiskCheckCRequest = OpsRiskCheckCRequest.builder()
        .opsRiskCheckC(opsRiskCheckC).build();

    String opRiskStr = "{\n" +
        "    \"data\": {\n" +
        "        \"checkBlackListC\": {\n" +
        "            \"respDomain\": {\n" +
        "                \"blackList4LosC\": {\n" +
        "                    \"cid\": \"417\",\n" +
        "                    \"idcardPassportBusinessRegistration\": \"122423523\",\n" +
        "                    \"idcardPassport\": \"123331314\",\n" +
        "                    \"borrowersByThirdParty\": \"a b c\",\n" +
        "                    \"cId\": \"417\",\n" +
        "                    \"classification\": \"Danh sách đen\",\n" +
        "                    \"dateAdded\": \"22/12/2021\",\n" +
        "                    \"division\": \"Phòng Quản lý RR Hoạt động\",\n" +
        "                    \"endDate\": \"22/12/2031\",\n" +
        "                    \"errorMess\": null,\n" +
        "                    \"iDcard_passport\": \"123331314\",\n" +
        "                    \"iDcard_passportBusinessRegistration\": \"122423523\",\n" +
        "                    \"id\": null,\n" +
        "                    \"identificationInformation\": \"test\",\n" +
        "                    \"inputUser\": \"qlrr_oprisk\",\n" +
        "                    \"listedReasons\": \"Do gian lận (giả mạo,chỉnh sửa,lừa đảo,không trung thực...)\",\n"
        +
        "                    \"owner\": \"z\",\n" +
        "                    \"result\": \"True\",\n" +
        "                    \"propertyDescription\": null,\n" +
        "                    \"typeOfProperty\": \"Bất động sản\",\n" +
        "                    \"version\": null\n" +
        "                }\n" +
        "            },\n" +
        "            \"respMessage\": {\n" +
        "                \"respCode\": \"0\",\n" +
        "                \"respDesc\": \"System ok!\"\n" +
        "            }\n" +
        "        }\n" +
        "    },\n" +
        "    \"status\": 200\n" +
        "}";
    Gson g = new Gson();
    OpsRiskCheckCResponse opsRiskResponse = g.fromJson(opRiskStr, OpsRiskCheckCResponse.class);
    doReturn(ResponseEntity.ok(opsRiskResponse)).when(opRiskClient)
        .checkC(opsRiskCheckCRequest, CLIENT_CODE);

    List<OpRisk> result = opRiskService.checkC(opsRiskCheckCs, LOAN_APPLICATION_ID);

    assertEquals(result.get(0).getLoanApplicationId(), LOAN_APPLICATION_ID);
  }

  @Test
  void givenValidInput_ThenCheckCSetPassFalse_shouldReturnSuccess() throws Exception {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    OpRiskEntity opRiskEntity1 = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .loanApplicationId(LOAN_APPLICATION_ID)
        .identifyInfo("B9441912").build();
    OpRiskEntity opRiskEntity2 = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .loanApplicationId(LOAN_APPLICATION_ID)
        .identifyInfo("B9441913").build();
    List<OpRiskEntity> opRiskEntities = new ArrayList<>(
        Arrays.asList(opRiskEntity1, opRiskEntity2));
    doReturn(opRiskEntities).when(opRiskRepository)
        .findByLoanApplicationIdAndCheckType(LOAN_APPLICATION_ID, OpRiskCheckTypeEnum.CHECK_C);

    Set<String> identifiesInfoSet = new HashSet<>();
    identifiesInfoSet.add("B9441912");
    identifiesInfoSet.add("B9441913");
    List<OpsRiskCheckCRequest.OpsRiskCheckC> opsRiskCheckCs = new ArrayList<>();
    OpsRiskCheckCRequest.OpsRiskCheckC opsRiskCheckC = new OpsRiskCheckCRequest.OpsRiskCheckC();
    opsRiskCheckC.setIdentifiesInfoSet(identifiesInfoSet);
    opsRiskCheckC.setCollateralType("T");
    opsRiskCheckCs.add(opsRiskCheckC);
    OpsRiskCheckCRequest opsRiskCheckCRequest = OpsRiskCheckCRequest.builder()
        .opsRiskCheckC(opsRiskCheckC).build();

    String opRiskStr = "{\n" +
        "    \"data\": {\n" +
        "        \"checkBlackListC\": {\n" +
        "            \"respDomain\": {\n" +
        "                \"blackList4LosC\": {\n" +
        "                    \"cid\": \"417\",\n" +
        "                    \"idcardPassportBusinessRegistration\": \"122423523\",\n" +
        "                    \"idcardPassport\": \"123331314\",\n" +
        "                    \"borrowersByThirdParty\": \"a b c\",\n" +
        "                    \"cId\": \"417\",\n" +
        "                    \"classification\": \"Danh sách đen\",\n" +
        "                    \"dateAdded\": \"22/12/2021\",\n" +
        "                    \"division\": \"Phòng Quản lý RR Hoạt động\",\n" +
        "                    \"endDate\": \"22/12/2031\",\n" +
        "                    \"errorMess\": null,\n" +
        "                    \"iDcard_passport\": \"123331314\",\n" +
        "                    \"iDcard_passportBusinessRegistration\": \"122423523\",\n" +
        "                    \"id\": null,\n" +
        "                    \"identificationInformation\": \"test\",\n" +
        "                    \"inputUser\": \"qlrr_oprisk\",\n" +
        "                    \"listedReasons\": \"Do gian lận (giả mạo,chỉnh sửa,lừa đảo,không trung thực...)\",\n"
        +
        "                    \"owner\": \"z\",\n" +
        "                    \"result\": \"True\",\n" +
        "                    \"propertyDescription\": null,\n" +
        "                    \"typeOfProperty\": \"Bất động sản\",\n" +
        "                    \"version\": null\n" +
        "                }\n" +
        "            },\n" +
        "            \"respMessage\": {\n" +
        "                \"respCode\": \"0\",\n" +
        "                \"respDesc\": \"System ok!\"\n" +
        "            }\n" +
        "        }\n" +
        "    },\n" +
        "    \"status\": 200\n" +
        "}";
    Gson g = new Gson();
    OpsRiskCheckCResponse opsRiskResponse = g.fromJson(opRiskStr, OpsRiskCheckCResponse.class);
    doReturn(ResponseEntity.ok(opsRiskResponse)).when(opRiskClient)
        .checkC(opsRiskCheckCRequest, CLIENT_CODE);

    List<OpRisk> result = opRiskService.checkC(opsRiskCheckCs, LOAN_APPLICATION_ID);

    assertEquals(result.get(0).getLoanApplicationId(), LOAN_APPLICATION_ID);
  }

  @Test
  void givenValidInput_ThenCheckCNoData_shouldReturnSuccess() throws Exception {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    OpRiskEntity opRiskEntity1 = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .loanApplicationId(LOAN_APPLICATION_ID)
        .identifyInfo("B9441914").build();
    OpRiskEntity opRiskEntity2 = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .loanApplicationId(LOAN_APPLICATION_ID)
        .identifyInfo("B9441915").build();
    List<OpRiskEntity> opRiskEntities = new ArrayList<>(
        Arrays.asList(opRiskEntity1, opRiskEntity2));
    doReturn(opRiskEntities).when(opRiskRepository)
        .findByLoanApplicationIdAndCheckType(LOAN_APPLICATION_ID, OpRiskCheckTypeEnum.CHECK_C);

    Set<String> identifiesInfoSet = new HashSet<>();
    identifiesInfoSet.add("B9441912");
    identifiesInfoSet.add("B9441913");
    List<OpsRiskCheckCRequest.OpsRiskCheckC> opsRiskCheckCs = new ArrayList<>();
    OpsRiskCheckCRequest.OpsRiskCheckC opsRiskCheckC = new OpsRiskCheckCRequest.OpsRiskCheckC();
    opsRiskCheckC.setIdentifiesInfoSet(identifiesInfoSet);
    opsRiskCheckC.setCollateralType("T");
    opsRiskCheckCs.add(opsRiskCheckC);
    OpsRiskCheckCRequest opsRiskCheckCRequest = OpsRiskCheckCRequest.builder()
        .opsRiskCheckC(opsRiskCheckC).build();

    String opRiskStr = "{\n" +
        "    \"data\": null,\n" +
        "    \"status\": 200\n" +
        "}";
    Gson g = new Gson();
    OpsRiskCheckCResponse opsRiskResponse = g.fromJson(opRiskStr, OpsRiskCheckCResponse.class);
    doReturn(ResponseEntity.ok(opsRiskResponse)).when(opRiskClient)
        .checkC(opsRiskCheckCRequest, CLIENT_CODE);

    List<OpRisk> result = opRiskService.checkC(opsRiskCheckCs, LOAN_APPLICATION_ID);

    assertEquals(result.get(0).getLoanApplicationId(), LOAN_APPLICATION_ID);
  }

  @Test
  void givenValidInput_ThenCheckCSetPassFalse_shouldReturnFailCallApi() throws Exception {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    OpRiskEntity opRiskEntity1 = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .loanApplicationId(LOAN_APPLICATION_ID)
        .identifyInfo("B9441912").build();
    OpRiskEntity opRiskEntity2 = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .loanApplicationId(LOAN_APPLICATION_ID)
        .identifyInfo("B9441913").build();
    List<OpRiskEntity> opRiskEntities = new ArrayList<>(
        Arrays.asList(opRiskEntity1, opRiskEntity2));
    doReturn(opRiskEntities).when(opRiskRepository)
        .findByLoanApplicationIdAndCheckType(LOAN_APPLICATION_ID, OpRiskCheckTypeEnum.CHECK_C);

    Set<String> identifiesInfoSet = new HashSet<>();
    identifiesInfoSet.add("B9441912");
    identifiesInfoSet.add("B9441913");
    List<OpsRiskCheckCRequest.OpsRiskCheckC> opsRiskCheckCs = new ArrayList<>();
    OpsRiskCheckCRequest.OpsRiskCheckC opsRiskCheckC = new OpsRiskCheckCRequest.OpsRiskCheckC();
    opsRiskCheckC.setIdentifiesInfoSet(identifiesInfoSet);
    opsRiskCheckC.setCollateralType("T");
    opsRiskCheckCs.add(opsRiskCheckC);
    OpsRiskCheckCRequest opsRiskCheckCRequest = OpsRiskCheckCRequest.builder()
        .opsRiskCheckC(opsRiskCheckC).build();

    when(opRiskClient.checkC(opsRiskCheckCRequest, CLIENT_CODE))
        .thenThrow(FeignException.class);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      opRiskService.checkC(opsRiskCheckCs, LOAN_APPLICATION_ID);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.CALL_API_OPRISK_CHECK_C_ERROR.getCode());
  }

  @Test
  void givenValidInput_ThenGetOpRiskCollateralInfo_shouldReturnSuccess() {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findByUuid(LOAN_APPLICATION_ID);

    List<OpRiskEntity> opRiskEntities = new ArrayList<>();
    OpRiskEntity opRiskEntity = OpRiskEntity.builder()
        .uuid(OP_RISK_ID)
        .identifyInfo("identifyInfo")
        .checkType(OpRiskCheckTypeEnum.CHECK_C)
        .loanApplicationId(LOAN_APPLICATION_ID)
        .build();
    opRiskEntities.add(opRiskEntity);
    doReturn(opRiskEntities).when(opRiskRepository)
        .findByLoanApplicationIdAndCheckTypeAndActive(LOAN_APPLICATION_ID,
            OpRiskCheckTypeEnum.CHECK_C, true);

    List<CollateralEntity> collateralEntities = new ArrayList<>();
    CollateralEntity collateralEntity = CollateralEntity.builder()
        .loanId(LOAN_APPLICATION_ID)
        .type(CollateralTypeEnum.ND)
        .legalDoc("identifyInfo").build();
    collateralEntities.add(collateralEntity);
    doReturn(collateralEntities).when(collateralRepository).findByLoanId(LOAN_APPLICATION_ID);

    List<CMSGetOpRiskCollateralInfo> result = opRiskService.getOpRiskCollateralInfo(
        LOAN_APPLICATION_ID);

    assertEquals(result.get(0).getOpRisk().getUuid(), OP_RISK_ID);
  }
}
