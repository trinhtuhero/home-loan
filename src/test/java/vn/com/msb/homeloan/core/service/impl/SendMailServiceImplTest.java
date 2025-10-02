package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import java.io.File;
import java.io.IOException;
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
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import vn.com.msb.homeloan.api.dto.response.ApiInternalResponse;
import vn.com.msb.homeloan.api.dto.response.DataCommonResponse;
import vn.com.msb.homeloan.core.client.NotificationFeignClient;
import vn.com.msb.homeloan.core.constant.CardPriorityEnum;
import vn.com.msb.homeloan.core.entity.CmsUserEntity;
import vn.com.msb.homeloan.core.entity.CreditCardEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationCommentEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationItemEntity;
import vn.com.msb.homeloan.core.entity.UploadFileCommentEntity;
import vn.com.msb.homeloan.core.entity.UploadFileStatusEntity;
import vn.com.msb.homeloan.core.repository.CmsUserRepository;
import vn.com.msb.homeloan.core.repository.CreditCardRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationItemRepository;
import vn.com.msb.homeloan.core.repository.NationalityRepository;
import vn.com.msb.homeloan.core.service.BusinessIncomeService;
import vn.com.msb.homeloan.core.service.CollateralService;
import vn.com.msb.homeloan.core.service.ContactPersonService;
import vn.com.msb.homeloan.core.service.FileConfigCategoryService;
import vn.com.msb.homeloan.core.service.FileConfigService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.LoanPayerService;
import vn.com.msb.homeloan.core.service.MarriedPersonService;
import vn.com.msb.homeloan.core.service.OtherIncomeService;
import vn.com.msb.homeloan.core.service.PlaceOfIssueIdCardService;
import vn.com.msb.homeloan.core.service.SalaryIncomeService;
import vn.com.msb.homeloan.core.service.SendMailService;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@ExtendWith({MockitoExtension.class})
class SendMailServiceImplTest {

  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  SendMailService sendMailService;

  @Mock
  LoanApplicationService loanApplicationService;

  @Mock
  MarriedPersonService marriedPersonService;

  @Mock
  LoanPayerService loanPayerService;

  @Mock
  ContactPersonService contactPersonService;

  @Mock
  SalaryIncomeService salaryIncomeService;

  @Mock
  BusinessIncomeService businessIncomeService;

  @Mock
  OtherIncomeService otherIncomeService;

  @Mock
  CollateralService collateralService;

  @Mock
  NotificationFeignClient notificationFeignClient;

  @Mock
  EnvironmentProperties environmentProperties;

  @Mock
  FileConfigService fileConfigService;

  @Mock
  PlaceOfIssueIdCardService placeOfIssueIdCardService;

  @Mock
  LoanApplicationItemRepository loanApplicationItemRepository;

  @Mock
  CreditCardRepository creditCardRepository;

  @Mock
  CmsUserRepository cmsUserRepository;

  @Mock
  FileConfigCategoryService fileConfigCategoryService;

  @Mock
  NationalityRepository nationalityRepository;

  @BeforeEach
  void setUp() throws IOException {
    FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
    Configuration configuration = new Configuration();
    configuration.setDirectoryForTemplateLoading(new File("src/test/resources/mail-templates/"));
    configuration.setObjectWrapper(new DefaultObjectWrapper());
    freeMarkerConfigurer.setConfiguration(configuration);
    this.sendMailService = new SendMailServiceImpl(
        freeMarkerConfigurer,
        loanApplicationService,
        marriedPersonService,
        loanPayerService,
        contactPersonService,
        salaryIncomeService,
        businessIncomeService,
        otherIncomeService,
        collateralService,
        notificationFeignClient,
        environmentProperties,
        fileConfigService,
        placeOfIssueIdCardService,
        creditCardRepository,
        null,
        cmsUserRepository,
        null,
        loanApplicationItemRepository,
        fileConfigCategoryService,
        null,
        nationalityRepository,
        null,
        null
    );
  }

  @Test
  void givenValidInput_ThenGenHtmlContentOfAttachFile_shouldReturnSuccess() {
    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder().build();
    java.util.List<LoanApplicationCommentEntity> loanApplicationCommentEntities = new ArrayList<>();
    List<UploadFileCommentEntity> uploadFileCommentEntities = new ArrayList<>();
    List<UploadFileStatusEntity> uploadFileStatusEntities = new ArrayList<>();
    sendMailService.sendMailWhenSubmitSubmitFeedback(loanApplication,
        loanApplicationCommentEntities, uploadFileCommentEntities, uploadFileStatusEntities);
    assertEquals(loanApplicationCommentEntities.size(), 0);
  }

  @Test
  void givenValidInput_ThenSendMailWhenSubmitLoanSuccess_shouldReturnSuccess() {
    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder().build();
    loanApplication.setUuid("uuid");

    try (MockedStatic<HtmlConverter> theMock = Mockito.mockStatic(HtmlConverter.class)) {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      PdfWriter writer = new PdfWriter(byteArrayOutputStream);
      PdfDocument pdfDocument = new PdfDocument(writer);
      String htmlContent = "";
      ConverterProperties converterProperties = new ConverterProperties();
      theMock.when(
              (MockedStatic.Verification) HtmlConverter.convertToDocument(htmlContent, pdfDocument,
                  converterProperties))
          .thenReturn(null);

      sendMailService.sendMailWhenSubmitLoanSuccess(loanApplication, new ArrayList<>());

      assertEquals(loanApplication.getUuid(), "uuid");
    }
  }

  @Test
  void givenValidInput_ThenSendMailWhenRMCompleteToTrinh_shouldReturnSuccess() {
    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder().build();
    loanApplication.setUuid("uuid");

    try (MockedStatic<HtmlConverter> theMock = Mockito.mockStatic(HtmlConverter.class)) {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      PdfWriter writer = new PdfWriter(byteArrayOutputStream);
      PdfDocument pdfDocument = new PdfDocument(writer);
      String htmlContent = "";
      ConverterProperties converterProperties = new ConverterProperties();
      theMock.when(
              (MockedStatic.Verification) HtmlConverter.convertToDocument(htmlContent, pdfDocument,
                  converterProperties))
          .thenReturn(null);

      sendMailService.sendMailWhenRMCompleteToTrinh(loanApplication, null);

      assertEquals(loanApplication.getUuid(), "uuid");
    }
  }

  @Test
  void givenValidInput_ThenSendMailToBMWhenRMCompleteToTrinh_shouldReturnSuccess() {
    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .picRm("123456").build();

    LoanApplicationItemEntity loanApplicationItemEntity = LoanApplicationItemEntity.builder()
        .loanAmount(1234L)
        .loanTime(23)
        .loanApplicationId(LOAN_ID).build();
    doReturn(Arrays.asList(loanApplicationItemEntity)).when(loanApplicationItemRepository)
        .findByLoanApplicationIdWithoutCreditCard(LOAN_ID);

    CreditCardEntity creditCardEntity = CreditCardEntity.builder()
        .creditLimit(1234L)
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .loanId(LOAN_ID).build();
    CreditCardEntity creditCardEntity2 = CreditCardEntity.builder()
        .creditLimit(1234L)
        .cardPriority(CardPriorityEnum.SECONDARY_CARD)
        .loanId(LOAN_ID).build();
    doReturn(Arrays.asList(creditCardEntity, creditCardEntity2)).when(creditCardRepository)
        .findByLoanId(LOAN_ID);

    CmsUserEntity rm = CmsUserEntity.builder()
        .emplId("123456")
        .email("picRmm@msb.com")
        .leaderEmail("leaderr@msb.com")
        .fullName("nameRM").build();
    doReturn(Optional.of(rm)).when(cmsUserRepository).findById("123456");

    CmsUserEntity bm = CmsUserEntity.builder()
        .emplId("1234567")
        .email("leaderr@msb.com")
        .fullName("nameBM").build();
    doReturn(Optional.of(bm)).when(cmsUserRepository).findByEmail("leaderr@msb.com");

    ApiInternalResponse apiInternalResponse = new ApiInternalResponse();
    DataCommonResponse dataCommonResponse = new DataCommonResponse();
    doReturn(apiInternalResponse.setData(dataCommonResponse)).when(notificationFeignClient)
        .sendMail(any(), any());

    try (MockedStatic<HtmlConverter> theMock = Mockito.mockStatic(HtmlConverter.class)) {

      sendMailService.sendMailToBMWhenRMCompleteToTrinh(loanApplication);

      assertEquals(loanApplication.getUuid(), LOAN_ID);
    }
  }

  @Test
  void givenValidInput_ThenSendMailWhenCustomerApprovalChange_shouldReturnSuccess() {
    LoanApplicationEntity loanApplication = LoanApplicationEntity.builder().build();
    loanApplication.setUuid("uuid");

    try (MockedStatic<HtmlConverter> theMock = Mockito.mockStatic(HtmlConverter.class)) {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      PdfWriter writer = new PdfWriter(byteArrayOutputStream);
      PdfDocument pdfDocument = new PdfDocument(writer);
      String htmlContent = "";
      ConverterProperties converterProperties = new ConverterProperties();
      theMock.when(
              (MockedStatic.Verification) HtmlConverter.convertToDocument(htmlContent, pdfDocument,
                  converterProperties))
          .thenReturn(null);

      sendMailService.sendMailWhenCustomerApprovalChange(loanApplication, new ArrayList<>());

      assertEquals(loanApplication.getUuid(), "uuid");
    }
  }
}