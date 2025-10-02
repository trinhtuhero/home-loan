package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import com.google.gson.Gson;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import vn.com.msb.homeloan.core.client.CssClient;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.CssEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.Css;
import vn.com.msb.homeloan.core.model.CssParam;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.model.request.CssCommon;
import vn.com.msb.homeloan.core.model.request.CssInfo;
import vn.com.msb.homeloan.core.model.request.CssRequest;
import vn.com.msb.homeloan.core.model.response.css.CssResponse;
import vn.com.msb.homeloan.core.repository.CssRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.service.CssService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;
import vn.com.msb.homeloan.core.util.DateUtils;
import vn.com.msb.homeloan.infras.configs.feign.CssConfig;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@ExtendWith(MockitoExtension.class)
class CssServiceTest {

  private final String LOAN_APPLICATION_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String CSS_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String CLIENT_CODE = "client-code";

  CssService cssService;

  @Mock
  CssClient cssClient;

  @Mock
  CssConfig cssConfig;

  @Mock
  CssRepository cssRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  EnvironmentProperties envProperties;

  @BeforeEach
  void setUp() {
    this.envProperties = new EnvironmentProperties();
    this.envProperties.setClientCode(CLIENT_CODE);
    this.cssConfig = new CssConfig();
    this.cssConfig.setPassword("password");
    this.cssConfig.setChannel("channel");
    this.cssConfig.setSpecializedBank("specializedBank");
    this.cssConfig.setUserAuthen("userAuthen");
    this.cssService = new CssServiceImpl(
        cssClient,
        cssConfig,
        cssRepository,
        loanApplicationRepository,
        envProperties);
  }

  @Test
  void givenValidInput_ThenGetScore_shouldReturnSuccess()
      throws ApplicationException, ParseException {
    CssParam cssParam = CssParam.builder()
        .profileId(49540)
        .legalDocNo("361692938")
        .loanApplicationId(LOAN_APPLICATION_ID).build();

    SimpleDateFormat format = new SimpleDateFormat(DateUtils.REQUEST_TIME_FORMAT);
    String requestDate = format.format(Date.from(Instant.now()));
    CssCommon common = CssCommon.builder()
        .channel("channel")
        .password("password")
        .userAuthen("userAuthen")
        .requestTime(requestDate).build();
    CssInfo info = CssInfo.builder()
        .customerType("specializedBank")
        .legalDocNo(BuildData.buildLoanApplication(LOAN_APPLICATION_ID).getIdNo())
        .profileId(49540).build();
    CssRequest cssRequest = CssRequest.builder()
        .common(common)
        .info(info).build();

    String cssStr = "{\n" +
        "            \"data\": {\n" +
        "        \"response\": {\n" +
        "            \"code\": \"CSS_000\",\n" +
        "                    \"scoringTime\": \"16/05/2022 17:34:29 PM\",\n" +
        "                    \"message\": \"Success\",\n" +
        "                    \"timestamp\": \"2022-09-13T16:44:36.078Z\"\n" +
        "        },\n" +
        "        \"EB\": {\n" +
        "            \"groupGeneralFinancialTarget\": null,\n" +
        "                    \"groupEffectiveIndicatorReview\": null,\n" +
        "                    \"groupCustomerInfo\": null\n" +
        "        },\n" +
        "        \"RB\": {\n" +
        "            \"scoreRM\": [\n" +
        "            \"0.0352974400877\"\n" +
        "            ],\n" +
        "            \"approvalScore\": [\n" +
        "            \"0.0352974400877\"\n" +
        "            ],\n" +
        "            \"rankRM\": [\n" +
        "            \"B18\"\n" +
        "            ],\n" +
        "            \"approvalRank\": [\n" +
        "            \"B18\"\n" +
        "            ]\n" +
        "        }\n" +
        "    },\n" +
        "            \"status\": 200\n" +
        "}";
    Gson g = new Gson();
    CssResponse cssResponse = g.fromJson(cssStr, CssResponse.class);
    doReturn(ResponseEntity.ok(cssResponse)).when(cssClient).getScore(cssRequest, CLIENT_CODE);

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_APPLICATION_ID));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    Css result = cssService.getScore(cssParam, requestDate);

    assertEquals(result.getLoanApplicationId(), LOAN_APPLICATION_ID);
  }

  @Test
  void givenValidInput_ThenGetScore_shouldReturnFailAPI_CSS_FAIL() throws ApplicationException {
    CssParam cssParam = CssParam.builder()
        .profileId(49540)
        .legalDocNo("361692938")
        .loanApplicationId(LOAN_APPLICATION_ID).build();

    SimpleDateFormat format = new SimpleDateFormat(DateUtils.REQUEST_TIME_FORMAT);
    String requestDate = format.format(Date.from(Instant.now()));
    CssCommon common = CssCommon.builder()
        .channel("channel")
        .password("password")
        .userAuthen("userAuthen")
        .requestTime(requestDate).build();
    CssInfo info = CssInfo.builder()
        .customerType("specializedBank")
        .legalDocNo(BuildData.buildLoanApplication(LOAN_APPLICATION_ID).getIdNo())
        .profileId(49540).build();
    CssRequest cssRequest = CssRequest.builder()
        .common(common)
        .info(info).build();

    doReturn(null).when(cssClient).getScore(cssRequest, CLIENT_CODE);

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_APPLICATION_ID));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      cssService.getScore(cssParam, requestDate);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.API_CSS_FAIL.getCode());
  }

  @Test
  void givenValidInput_ThenGetCss_shouldReturnSuccess() throws ApplicationException {
    CssEntity cssEntity = CssEntity.builder()
        .uuid(CSS_ID)
        .loanApplicationId(LOAN_APPLICATION_ID).build();
    doReturn(cssEntity).when(cssRepository).findByLoanApplicationId(LOAN_APPLICATION_ID);

    Css result = cssService.getCss(LOAN_APPLICATION_ID);

    assertEquals(result.getLoanApplicationId(), LOAN_APPLICATION_ID);
  }
}
