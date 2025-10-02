package vn.com.msb.homeloan.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import vn.com.msb.homeloan.core.client.NotificationFeignClient;
import vn.com.msb.homeloan.core.repository.AssetEvaluateItemRepository;
import vn.com.msb.homeloan.core.repository.AssetEvaluateRepository;
import vn.com.msb.homeloan.core.repository.BusinessIncomeRepository;
import vn.com.msb.homeloan.core.repository.CMSTabActionRepository;
import vn.com.msb.homeloan.core.repository.CardPolicyRepository;
import vn.com.msb.homeloan.core.repository.CardTypeRepository;
import vn.com.msb.homeloan.core.repository.CicItemRepository;
import vn.com.msb.homeloan.core.repository.CicRepository;
import vn.com.msb.homeloan.core.repository.CmsUserRepository;
import vn.com.msb.homeloan.core.repository.CollateralOwnerMapRepository;
import vn.com.msb.homeloan.core.repository.CollateralOwnerRepository;
import vn.com.msb.homeloan.core.repository.CollateralRepository;
import vn.com.msb.homeloan.core.repository.ContactPersonRepository;
import vn.com.msb.homeloan.core.repository.CreditAppraisalRepository;
import vn.com.msb.homeloan.core.repository.CreditCardRepository;
import vn.com.msb.homeloan.core.repository.CreditInstitutionRepository;
import vn.com.msb.homeloan.core.repository.CssRepository;
import vn.com.msb.homeloan.core.repository.ExceptionItemRepository;
import vn.com.msb.homeloan.core.repository.FieldSurveyItemRepository;
import vn.com.msb.homeloan.core.repository.FileConfigRepository;
import vn.com.msb.homeloan.core.repository.LoanAdviseCustomerRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationItemRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationSequenceRepository;
import vn.com.msb.homeloan.core.repository.LoanApprovalHisRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.repository.LoanPreApprovalRepository;
import vn.com.msb.homeloan.core.repository.LoanStatusChangeRepository;
import vn.com.msb.homeloan.core.repository.LoanUploadFileRepository;
import vn.com.msb.homeloan.core.repository.MarriedPersonRepository;
import vn.com.msb.homeloan.core.repository.OpRiskRepository;
import vn.com.msb.homeloan.core.repository.OrganizationRepository;
import vn.com.msb.homeloan.core.repository.OtherEvaluateRepository;
import vn.com.msb.homeloan.core.repository.OtherIncomeRepository;
import vn.com.msb.homeloan.core.repository.OverdraftRepository;
import vn.com.msb.homeloan.core.repository.PicRmHistoryRepository;
import vn.com.msb.homeloan.core.repository.PlaceOfIssueIdCardRepository;
import vn.com.msb.homeloan.core.repository.ProductCodeConfigRepository;
import vn.com.msb.homeloan.core.repository.ProfileRepository;
import vn.com.msb.homeloan.core.repository.ProvinceRepository;
import vn.com.msb.homeloan.core.repository.SalaryIncomeRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CardPolicyService;
import vn.com.msb.homeloan.core.service.CardTypeService;
import vn.com.msb.homeloan.core.service.CicService;
import vn.com.msb.homeloan.core.service.CmsUserService;
import vn.com.msb.homeloan.core.service.CollateralService;
import vn.com.msb.homeloan.core.service.CommonCMSService;
import vn.com.msb.homeloan.core.service.CommonIncomeService;
import vn.com.msb.homeloan.core.service.CreditAppraisalService;
import vn.com.msb.homeloan.core.service.CreditInstitutionService;
import vn.com.msb.homeloan.core.service.CreditworthinessItemService;
import vn.com.msb.homeloan.core.service.ExportExcelService;
import vn.com.msb.homeloan.core.service.FileService;
import vn.com.msb.homeloan.core.service.LandTransactionService;
import vn.com.msb.homeloan.core.service.LoanApplicationItemService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.LoanUploadFileService;
import vn.com.msb.homeloan.core.service.OverdraftService;
import vn.com.msb.homeloan.core.service.SendMailService;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@ExtendWith(MockitoExtension.class)
public abstract class LoanApplicationServiceBaseTest {

  LoanApplicationService loanApplicationService;

  ExportExcelService exportExcelService;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  MarriedPersonRepository marriedPersonsRepository;

  @Mock
  ContactPersonRepository contactPersonsRepository;

  @Mock
  ProfileRepository profileRepository;

  @Mock
  BusinessIncomeRepository businessIncomeRepository;

  @Mock
  SalaryIncomeRepository salaryIncomeRepository;

  @Mock
  OtherIncomeRepository otherIncomeRepository;

  @Mock
  CollateralRepository collateralRepository;

  @Mock
  LoanPayerRepository loanPayerRepository;

  @Mock
  LoanApplicationSequenceRepository sequenceRepository;

  @Mock
  CmsUserRepository cmsUserRepository;

  @Mock
  LoanStatusChangeRepository loanStatusChangeRepository;

  @Mock
  CmsUserService cmsUserService;

  @Mock
  EnvironmentProperties environmentProperties;

  ObjectMapper objectMapper;

  @Mock
  LoanPreApprovalRepository loanPreApprovalRepository;

  @Mock
  PlaceOfIssueIdCardRepository placeOfIssueIdCardRepository;

  @Mock
  SendMailService sendMailService;

  @Mock
  LoanApprovalHisRepository loanApprovalHisRepository;

  @Mock
  LoanUploadFileRepository loanUploadFileRepository;

  @Mock
  ExecutorService executorService;

  @Mock
  AssetEvaluateRepository assetEvaluateRepository;

  @Mock
  AssetEvaluateItemRepository assetEvaluateItemRepository;

  @Mock
  OtherEvaluateRepository otherEvaluateRepository;

  @Mock
  CMSTabActionService cmsTabActionService;

  @Mock
  CreditAppraisalRepository creditAppraisalRepository;

  @Mock
  CicService cicService;

  @Mock
  CollateralOwnerRepository collateralOwnerRepository;

  @Mock
  FieldSurveyItemRepository fieldSurveyItemRepository;

  @Mock
  OrganizationRepository organizationRepository;

  @Mock
  ExceptionItemRepository exceptionItemRepository;

  @Mock
  NotificationFeignClient notificationFeignClient;

  @Mock
  CicRepository cicRepository;

  @Mock
  CicItemRepository cicItemRepository;

  @Mock
  OpRiskRepository opRiskRepository;

  @Mock
  FileService fileService;

  @Mock
  CollateralOwnerMapRepository collateralOwnerMapRepository;

  @Mock
  CMSTabActionRepository cmsTabActionRepository;

  @Mock
  FileConfigRepository fileConfigRepository;

  @Mock
  ProvinceRepository provinceRepository;

  @Mock
  CssRepository cssRepository;

  @Mock
  CreditworthinessItemService creditworthinessItemService;

  @Mock
  CreditInstitutionService creditInstitutionService;

  @Mock
  CreditInstitutionRepository creditInstitutionRepository;

  @Mock
  ProductCodeConfigRepository productCodeConfigRepository;

  @Mock
  CreditCardRepository creditCardRepository;

  @Mock
  CardPolicyRepository cardPolicyRepository;

  @Mock
  CardTypeRepository cardTypeRepository;

  @Mock
  MarriedPersonRepository marriedPersonRepository;

  @Mock
  LoanApplicationItemService loanApplicationItemService;

  @Mock
  HomeLoanUtil homeLoanUtil;

  @Mock
  CollateralService collateralService;

  @Mock
  CommonIncomeService commonIncomeService;

  public final String PROFILE_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  @Mock
  LoanUploadFileService loanUploadFileService;

  @Mock
  CardTypeService cardTypeService;

  @Mock
  CardPolicyService cardPolicyService;

  @Mock
  CreditAppraisalService creditAppraisalService;

  @Mock
  CommonCMSService commonCMSService;

  @Mock
  CacheManager cacheManager;

  @Mock
  PicRmHistoryRepository picRmHistoryRepository;

  @Mock
  LoanAdviseCustomerRepository loanAdviseCustomerRepository;

  @Mock
  OverdraftRepository overdraftRepository;

  @Mock
  OverdraftService overdraftService;

  @Mock
  private LoanApplicationItemRepository loanApplicationItemRepository;

  @Mock
  private LandTransactionService landTransactionService;


  @BeforeEach
  void setUp() {
    this.environmentProperties = new EnvironmentProperties();
    this.environmentProperties.setProvinceSpecial(";01;79;");
    this.environmentProperties.setSha256Secret("CJ5@2022");
    List<String> strings = Collections.singletonList("PROCESSING");
    this.environmentProperties.setLoanStatusAllowClose(strings);
    this.exportExcelService = new ExportExcelServiceImpl(placeOfIssueIdCardRepository,
        provinceRepository, creditInstitutionRepository, productCodeConfigRepository, homeLoanUtil,
        null);
    this.objectMapper = new ObjectMapper();

    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    this.loanApplicationService = new LoanApplicationServiceImpl(loanApplicationItemRepository,
        loanApplicationRepository,
        marriedPersonsRepository,
        contactPersonsRepository,
        profileRepository,
        businessIncomeRepository,
        salaryIncomeRepository,
        otherIncomeRepository,
        collateralRepository,
        loanPayerRepository,
        sequenceRepository,
        sendMailService,
        environmentProperties,
        cmsUserRepository,
        loanUploadFileService,
        fileService,
        cmsUserService,
        loanStatusChangeRepository,
        exportExcelService,
        placeOfIssueIdCardRepository,
        null,
        null,
        null,
        null,
        null,
        loanUploadFileRepository,
        objectMapper,
        loanPreApprovalRepository,
        loanApprovalHisRepository,
        notificationFeignClient,
        executorService,
        assetEvaluateRepository,
        assetEvaluateItemRepository,
        otherEvaluateRepository,
        cmsTabActionService,
        creditAppraisalRepository,
        cicService,
        collateralOwnerRepository,
        fieldSurveyItemRepository,
        exceptionItemRepository,
        cmsTabActionRepository,
        cicRepository,
        cicItemRepository,
        opRiskRepository,
        collateralOwnerMapRepository,
        cssRepository,
        homeLoanUtil,
        fileConfigRepository,
        creditworthinessItemService,
        creditCardRepository,
        collateralService,
        loanApplicationItemService,
        commonIncomeService,
        organizationRepository,
        creditInstitutionService,
        cardTypeService,
        cardPolicyService,
        creditAppraisalService,
        provinceRepository,
        commonCMSService,
        cacheManager,
        picRmHistoryRepository,
        loanAdviseCustomerRepository,
        null,
        overdraftService,landTransactionService
    );
  }
}
