package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.BusinessIncomeEntity;
import vn.com.msb.homeloan.core.entity.ContactPersonEntity;
import vn.com.msb.homeloan.core.entity.CreditInstitutionEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;
import vn.com.msb.homeloan.core.entity.OtherIncomeEntity;
import vn.com.msb.homeloan.core.entity.SalaryIncomeEntity;
import vn.com.msb.homeloan.core.model.Collateral;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.model.LoanApplicationItem;
import vn.com.msb.homeloan.core.model.LoanApplicationReview;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceReviewTest extends LoanApplicationServiceBaseTest {

  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String PROFILE_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  @Test
  @DisplayName("LoanApplicationService Test REVIEW function success")
  void givenValidInput_ThenReview_shouldReturnSuccess() {
    LoanApplication loanApplication = BuildData.buildLoanApplication(PROFILE_ID);
    loanApplication.setUuid(LOAN_ID);
    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        loanApplication);
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());
    //contactPersonEntity
    ContactPersonEntity contactPersonEntity = ContactPersonEntity.builder()
        .loanId(LOAN_ID)
        .build();
    doReturn(contactPersonEntity).when(contactPersonsRepository).findOneByLoanId(LOAN_ID);

    List<LoanApplicationItem> loanApplicationItemEntities = new ArrayList<>();
    LoanApplicationItem loanApplicationItemEntity = LoanApplicationItem.builder().build();
    loanApplicationItemEntity.setBeneficiaryBank("BeneficiaryBank");
    loanApplicationItemEntities.add(loanApplicationItemEntity);
    doReturn(loanApplicationItemEntities).when(loanApplicationItemService)
        .getItemCollateralDistribute(LOAN_ID);

    Map<String, CreditInstitutionEntity> creditInstitutionEntityMap = new HashMap<>();
    creditInstitutionEntityMap.put("Test", CreditInstitutionEntity.builder().build());
    doReturn(creditInstitutionEntityMap).when(creditInstitutionService).getMapByCodes(any());

    //marriedPersonEntity
    MarriedPersonEntity marriedPersonEntity = MarriedPersonEntity.builder()
        .loanId(LOAN_ID)
        .build();
    doReturn(marriedPersonEntity).when(marriedPersonsRepository).findOneByLoanId(LOAN_ID);

    //businessIncomeEntities
    List<BusinessIncomeEntity> businessIncomeEntities = new ArrayList<>();
    businessIncomeEntities.add(BusinessIncomeEntity.builder()
        .loanApplicationId(LOAN_ID)
        .build());
    doReturn(businessIncomeEntities).when(businessIncomeRepository)
        .findByLoanApplicationIdOrderByCreatedAtAsc(LOAN_ID);

    //collateralEntities
    List<Collateral> collaterals = new ArrayList<>();
    collaterals.add(Collateral.builder()
        .loanId(LOAN_ID)
        .uuid(LOAN_ID)
        .build());
    doReturn(collaterals).when(collateralService).findCollateralInfoByLoanId(LOAN_ID);

    //loanPayerEntities
    List<LoanPayerEntity> loanPayerEntities = new ArrayList<>();
    loanPayerEntities.add(LoanPayerEntity.builder()
        .loanId(LOAN_ID)
        .build());
    doReturn(loanPayerEntities).when(loanPayerRepository).findByLoanIdOrderByCreatedAtAsc(LOAN_ID);

    //salaryIncomes
    List<SalaryIncomeEntity> salaryIncomes = new ArrayList<>();
    salaryIncomes.add(SalaryIncomeEntity.builder()
        .loanApplicationId(LOAN_ID)
        .build());
    doReturn(salaryIncomes).when(salaryIncomeRepository)
        .findByLoanApplicationIdOrderByCreatedAtAsc(LOAN_ID);

    //salaryIncomes
    List<OtherIncomeEntity> otherIncomes = new ArrayList<>();
    otherIncomes.add(OtherIncomeEntity.builder()
        .loanApplicationId(LOAN_ID)
        .build());
    doReturn(otherIncomes).when(otherIncomeRepository)
        .findByLoanApplicationIdOrderByCreatedAtAsc(LOAN_ID);

    LoanApplicationReview result = loanApplicationService.review(LOAN_ID);

    assertEquals(result.getLoanApplication() != null, true);
    assertEquals(result.getMarriedPerson() != null, true);
    assertEquals(result.getContactPerson() != null, true);
    assertEquals(CollectionUtils.isEmpty(result.getBusinessIncomes()), false);
    assertEquals(CollectionUtils.isEmpty(result.getCollaterals()), false);
    assertEquals(CollectionUtils.isEmpty(result.getLoanPayers()), false);
    assertEquals(CollectionUtils.isEmpty(result.getSalaryIncomes()), false);
    assertEquals(CollectionUtils.isEmpty(result.getOtherIncomes()), false);
  }
}