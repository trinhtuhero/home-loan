package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import vn.com.msb.homeloan.core.constant.BusinessTypeEnum;
import vn.com.msb.homeloan.core.constant.CICRelationshipTypeEnum;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.constant.CardPriorityEnum;
import vn.com.msb.homeloan.core.constant.ContactPersonTypeEnum;
import vn.com.msb.homeloan.core.constant.CreditCardObjectEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.constant.cic.CicGroupEnum;
import vn.com.msb.homeloan.core.constant.cic.CicQueryStatusEnum;
import vn.com.msb.homeloan.core.entity.AssetEvaluateEntity;
import vn.com.msb.homeloan.core.entity.AssetEvaluateItemEntity;
import vn.com.msb.homeloan.core.entity.BusinessIncomeEntity;
import vn.com.msb.homeloan.core.entity.CollateralOwnerEntity;
import vn.com.msb.homeloan.core.entity.ContactPersonEntity;
import vn.com.msb.homeloan.core.entity.CreditAppraisalEntity;
import vn.com.msb.homeloan.core.entity.CreditCardEntity;
import vn.com.msb.homeloan.core.entity.CreditInstitutionEntity;
import vn.com.msb.homeloan.core.entity.CreditworthinessItemEntity;
import vn.com.msb.homeloan.core.entity.ExceptionItemEntity;
import vn.com.msb.homeloan.core.entity.FieldSurveyItemEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;
import vn.com.msb.homeloan.core.entity.OtherEvaluateEntity;
import vn.com.msb.homeloan.core.entity.OtherIncomeEntity;
import vn.com.msb.homeloan.core.entity.PlaceOfIssueIdCardEntity;
import vn.com.msb.homeloan.core.entity.SalaryIncomeEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CMSCicItem;
import vn.com.msb.homeloan.core.model.CMSGetCicInfo;
import vn.com.msb.homeloan.core.model.Collateral;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.CreditworthinessItem;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.model.LoanApplicationItem;
import vn.com.msb.homeloan.core.model.LoanItemCollateralDistribution;
import vn.com.msb.homeloan.core.model.mapper.CollateralMapper;
import vn.com.msb.homeloan.core.model.mapper.CollateralOwnerMapper;
import vn.com.msb.homeloan.core.model.mapper.CreditAppraisalMapper;
import vn.com.msb.homeloan.core.model.mapper.CreditworthinessItemMapper;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;

class LoanApplicationServiceExportToTrinhTest extends LoanApplicationServiceBaseTest {

  @Test
  @DisplayName("LoanApplicationService Test findById Success")
  void givenValidInput_ThenFindById_shouldReturnSuccess() {
    String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
    LoanApplicationEntity mockEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .fullName("Nguyen van A")
        .build();

    doReturn(Optional.of(mockEntity)).when(loanApplicationRepository)
        .findById(mockEntity.getUuid());

    LoanApplication loanApplication = loanApplicationService.findById(mockEntity.getUuid());
    assertTrue(loanApplication.getUuid().equalsIgnoreCase(mockEntity.getUuid()),
        String.valueOf(true));
  }

  @Test
  @DisplayName("LoanApplicationService Test findById fail")
  void givenValidInput_ThenFindById_shouldReturnFail() {
    LoanApplicationEntity mockEntity = LoanApplicationEntity.builder()
        .uuid("03f9e024-7ec4-4ed3-8f1f-330d232b6399")
        .fullName("Nguyen van A")
        .build();

    doReturn(Optional.empty()).when(loanApplicationRepository).findById(mockEntity.getUuid());

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.findById(mockEntity.getUuid());
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  @DisplayName("LoanApplicationService export 01")
  void givenValidInput_ThenExportProposalLetter01_shouldReturnSuccess() {
    LoanApplication entity = BuildData.buildLoanApplication(PROFILE_ID);
    entity.setUuid("LOAN_ID");
    doReturn(Optional.of(LoanApplicationMapper.INSTANCE.toEntity(entity))).when(
        loanApplicationRepository).findById(any());
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.exportProposalLetter(entity.getUuid());
    });
    assertEquals(exception.getCode().intValue(),
        ErrorEnum.EXPORT_PROPOSAL_LETTER_NOT_ALLOW.getCode());
  }

  @Test
  @DisplayName("LoanApplicationService export 01")
  void givenValidInput_ThenExportProposalLetter01_shouldReturnStatusNotAllow() {
    LoanApplication entity = BuildData.buildLoanApplication(PROFILE_ID);
    entity.setUuid("LOAN_ID");
    entity.setStatus(LoanInfoStatusEnum.RM_COMPLETED.getCode());
    doReturn(Optional.of(LoanApplicationMapper.INSTANCE.toEntity(entity))).when(
        loanApplicationRepository).findById(any());
    Map map = loanApplicationService.exportProposalLetter(entity.getUuid());
    assertEquals(map.size(), 2);
  }

  @Test
  @DisplayName("LoanApplicationService export")
  void givenValidInput_ThenExportProposalLetter_shouldReturnSuccess() {
    LoanApplication entity = BuildData.buildLoanApplication(PROFILE_ID);
    entity.setUuid("LOAN_ID");
    entity.setStatus(LoanInfoStatusEnum.RM_COMPLETED.getCode());
    doReturn(Optional.of(LoanApplicationMapper.INSTANCE.toEntity(entity))).when(
        loanApplicationRepository).findById(any());
    doReturn(Optional.of(PlaceOfIssueIdCardEntity.builder()
        .name("Công an Thành phố Hà Nội")
        .code("12121")
        .build())).when(placeOfIssueIdCardRepository).findByCode(any());

    ContactPersonEntity contactPersonEntity = ContactPersonEntity.builder()
        .loanId(entity.getUuid())
        .fullName("full_Name")
        .type(ContactPersonTypeEnum.HUSBAND)
        .phone(entity.getPhone())
        .build();
    doReturn(contactPersonEntity).when(contactPersonsRepository).findOneByLoanId(entity.getUuid());

    MarriedPersonEntity marriedPersonEntity = BuildData.buildMarriedPersonEntity(entity.getUuid());
    doReturn(marriedPersonEntity).when(marriedPersonsRepository).findOneByLoanId(entity.getUuid());

    List<BusinessIncomeEntity> businessIncomes = new ArrayList<>();
    businessIncomes.add(
        BuildData.buildBusinessIncome(entity.getUuid(), BusinessTypeEnum.ENTERPRISE));
    businessIncomes.add(
        BuildData.buildBusinessIncome(entity.getUuid(), BusinessTypeEnum.WITH_REGISTRATION));
    doReturn(businessIncomes).when(businessIncomeRepository)
        .findByLoanApplicationIdOrderByCreatedAtAsc(entity.getUuid());

    List<LoanPayerEntity> loanPayerEntities = new ArrayList<>();
    loanPayerEntities.add(BuildData.buildLoanPayer(entity.getUuid()));
    doReturn(loanPayerEntities).when(loanPayerRepository)
        .findByLoanIdOrderByCreatedAtAsc(entity.getUuid());

    List<SalaryIncomeEntity> salaryIncomeEntities = new ArrayList<>();
    salaryIncomeEntities.add(BuildData.buildSalaryIncome(entity.getUuid()));
    doReturn(salaryIncomeEntities).when(salaryIncomeRepository)
        .findByLoanApplicationIdOrderByCreatedAtAsc(entity.getUuid());

    List<OtherIncomeEntity> otherIncomeEntities = new ArrayList<>();
    otherIncomeEntities.add(BuildData.buiOtherIncome(entity.getUuid()));
    doReturn(otherIncomeEntities).when(otherIncomeRepository)
        .findByLoanApplicationIdOrderByCreatedAtAsc(entity.getUuid());
    String USER_ID = "035564";

    CreditAppraisalEntity appraisalEntity = BuildData.buiOtherCreditAppraisalEntity(
        entity.getUuid());
    doReturn(CreditAppraisalMapper.INSTANCE.toModel(appraisalEntity)).when(creditAppraisalService)
        .getByLoanId(entity.getUuid());

    List<CMSGetCicInfo> cics = new ArrayList<>();
    List<CMSCicItem> cicItems = new ArrayList<>();
    CMSCicItem cmsCicItem = CMSCicItem.builder()
        .cicId("cicId")
        .identityCard("identityCard")
        .typeDebt(CicGroupEnum.NHOM_1)
        .typeDebt12(CicGroupEnum.NHOM_2)
        .typeDebt24(CicGroupEnum.NHOM_3)
        .pass(true)
        .description(CicQueryStatusEnum.NO_CIC_NO_INFO)
        .amountLoanNonMsbSecure(10L)
        .amountLoanNonMsbUnsecure(10L)
        .amountLoanMsbSecure(10L)
        .amountLoanMsbUnsecure(10L)
        .amountCardNonMsb(10L)
        .amountCardMsb(10L)
        .amountOverDraftMsb(10L)
        .metaData("")
        .build();
    cicItems.add(cmsCicItem);
    cicItems.add(cmsCicItem);
    CMSGetCicInfo cmsGetCicInfo = CMSGetCicInfo.builder()
        .name("name")
        .relationshipType(CICRelationshipTypeEnum.LOAN_PAYER)
        .cicItems(cicItems)
        .build();
    cics.add(cmsGetCicInfo);
    doReturn(cics).when(cicService).getCic(entity.getUuid());
    List<String> temp = Arrays.asList(CMSTabEnum.SALARY_INCOME.getCode(),
        CMSTabEnum.PERSONAL_BUSINESS_INCOME.getCode(), CMSTabEnum.BUSINESS_INCOME.getCode(),
        CMSTabEnum.OTHERS_INCOME.getCode(), CMSTabEnum.ASSUMING_TOTAL_ASSETS_INCOME.getCode(),
        CMSTabEnum.ASSUMING_OTHERS_INCOME.getCode());
    CommonIncome commonIncome = CommonIncome.builder()
        .selectedIncomes(temp.toArray(new String[0]))
        .build();
    doReturn(commonIncome).when(commonIncomeService).findByLoanApplicationId(entity.getUuid());

    AssetEvaluateEntity assetEvaluate = BuildData.buildAssetEvaluate(entity.getUuid());
    doReturn(assetEvaluate).when(assetEvaluateRepository).findByLoanApplicationId(entity.getUuid());

    List<AssetEvaluateItemEntity> assetEvaluateItems = new ArrayList<>();
    assetEvaluateItems.add(BuildData.buildAssetEvaluateItem(entity.getUuid()));

    doReturn(assetEvaluateItems).when(assetEvaluateItemRepository)
        .findByAssetEvalIdOrderByCreatedAtAsc(assetEvaluate.getUuid());

    List<OtherEvaluateEntity> otherEvaluates = new ArrayList<>();
    OtherEvaluateEntity otherEvaluate = OtherEvaluateEntity.builder()
        .uuid(entity.getUuid())
        .loanApplicationId(entity.getUuid())
        .ownerType(OwnerTypeEnum.PAYER_PERSON)
        .legalRecord("legalRecord")
        .rmInputValue(10L)
        .rmReview("rmReview")
        .build();
    otherEvaluates.add(otherEvaluate);
    doReturn(otherEvaluates).when(otherEvaluateRepository)
        .findByLoanApplicationIdOrderByCreatedAtAsc(entity.getUuid());

    CollateralOwnerEntity collateralOwner = BuildData.buildCollateralOwner2(entity.getUuid());
    collateralOwner.setUuid("collateralOwnerId");
    List<CollateralOwnerEntity> collateralOwnerEntities = new ArrayList<>();
    collateralOwnerEntities.add(collateralOwner);

    Collateral collateral = CollateralMapper.INSTANCE.toModel(
        BuildData.buildCollateral("id", entity.getUuid()));
    collateral.setCollateralOwners(
        CollateralOwnerMapper.INSTANCE.toModels(collateralOwnerEntities));
    List<Collateral> collaterals = new ArrayList<>();
    collaterals.add(collateral);

    doReturn(collaterals).when(collateralService).findCollateralInfoByLoanId(entity.getUuid());

    List<FieldSurveyItemEntity> fieldSurveyItemEntities = new ArrayList<>();
    fieldSurveyItemEntities.add(BuildData.buildFieldSurveyItem(entity.getUuid()));
    doReturn(fieldSurveyItemEntities).when(fieldSurveyItemRepository)
        .findByLoanApplicationIdOrderByCreatedAt(entity.getUuid());

    List<ExceptionItemEntity> exceptionItemEntities = new ArrayList<>();
    exceptionItemEntities.add(ExceptionItemEntity.builder().build());
    doReturn(exceptionItemEntities).when(exceptionItemRepository)
        .findByLoanApplicationId(entity.getUuid());

    List<CreditCardEntity> creditCards = new ArrayList<>();
    CreditCardEntity creditCard = CreditCardEntity.builder()
        .loanId("LOAN_ID")
        .cardPriority(CardPriorityEnum.PRIMARY_CARD)
        .type("LINK_CARD_OR_CO_BRAND")
        .creditLimit(1000000000L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .build();
    CreditCardEntity creditCard1 = CreditCardEntity.builder()
        .loanId("LOAN_ID")
        .cardPriority(CardPriorityEnum.SECONDARY_CARD)
        .type("LINK_CARD_OR_CO_BRAND")
        .creditLimit(1000000000L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .build();
    CreditCardEntity creditCard2 = CreditCardEntity.builder()
        .loanId("LOAN_ID")
        .cardPriority(CardPriorityEnum.SECONDARY_CARD)
        .type("LINK_CARD_OR_CO_BRAND")
        .creditLimit(1000000000L)
        .object(CreditCardObjectEnum.CUSTOMER.getCode())
        .build();
    creditCards.add(creditCard);
    creditCards.add(creditCard1);
    creditCards.add(creditCard2);
    doReturn(creditCards).when(creditCardRepository)
        .findByLoanIdOrderByCreatedAtAsc(entity.getUuid());

    List<CreditworthinessItem> creditworthinessItems = new ArrayList<>();
    CreditworthinessItemEntity creditworthinessItemEntity0 = BuildData.buildCreditworthinessItem(
        entity.getUuid());
    creditworthinessItems.add(
        CreditworthinessItemMapper.INSTANCE.toModel(creditworthinessItemEntity0));
    doReturn(creditworthinessItems).when(creditworthinessItemService).getByLoanId(any());

    CreditInstitutionEntity creditInstitutionEntity = CreditInstitutionEntity.builder().build();
    doReturn(Optional.of(creditInstitutionEntity)).when(creditInstitutionRepository)
        .findByCode(any());

    List<LoanApplicationItem> loanApplicationItemEntities = new ArrayList<>();
    List<LoanItemCollateralDistribution> loanItemCollateralDistributions = new ArrayList<>();
    LoanItemCollateralDistribution loanItemCollateralDistribution = LoanItemCollateralDistribution.builder()
        .collateralId(collaterals.get(0).getUuid())
        .percent(10.0F)
        .build();
    loanItemCollateralDistributions.add(loanItemCollateralDistribution);
    loanApplicationItemEntities.add(LoanApplicationItem.builder()
        .loanItemCollateralDistributions(loanItemCollateralDistributions).build());
    doReturn(loanApplicationItemEntities).when(loanApplicationItemService)
        .getItemCollateralDistribute(any());

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(AuthorizationUtil::getUserId)
          .thenReturn(USER_ID);

      theMock.when(AuthorizationUtil::getEmail)
          .thenReturn("email");

      Map map = loanApplicationService.exportProposalLetter(entity.getUuid());

      assertEquals(map.size(), 2);
      byte[] data = (byte[]) map.get("data");
      assertEquals(data.length > 0, true);
    }
  }
}