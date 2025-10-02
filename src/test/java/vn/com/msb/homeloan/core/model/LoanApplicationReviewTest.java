package vn.com.msb.homeloan.core.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LoanApplicationReviewTest {

  @Test
  void testCompareTwoObjects() {
    LoanApplicationReview object1 = LoanApplicationReview.builder().build();
    LoanApplicationReview object2 = LoanApplicationReview.builder().build();
    assertTrue(object1.equals(object2));

    LoanApplication loanApplication11 = new LoanApplication();
    loanApplication11.setUuid("123");

    ContactPerson contactPerson11 = new ContactPerson();
    contactPerson11.setUuid("123");

    MarriedPerson marriedPerson11 = new MarriedPerson();
    marriedPerson11.setUuid("123");

    LoanPayer loanPayer11 = new LoanPayer();
    loanPayer11.setUuid("123");
    LoanPayer loanPayer12 = new LoanPayer();
    loanPayer12.setUuid("456");
    List<LoanPayer> loanPayers1 = new ArrayList<>(Arrays.asList(loanPayer11, loanPayer12));

    SalaryIncome salaryIncome11 = new SalaryIncome();
    salaryIncome11.setUuid("123");
    SalaryIncome salaryIncome12 = new SalaryIncome();
    salaryIncome12.setUuid("456");
    List<SalaryIncome> salaryIncomes11 = new ArrayList<>(
        Arrays.asList(salaryIncome11, salaryIncome12));

    BusinessIncome businessIncome11 = new BusinessIncome();
    businessIncome11.setUuid("123");
    BusinessIncome businessIncome12 = new BusinessIncome();
    businessIncome12.setUuid("456");
    List<BusinessIncome> businessIncomes11 = new ArrayList<>(
        Arrays.asList(businessIncome11, businessIncome12));

    OtherIncome otherIncome1 = new OtherIncome();
    otherIncome1.setUuid("123");
    OtherIncome otherIncome2 = new OtherIncome();
    otherIncome2.setUuid("456");
    List<OtherIncome> otherIncomes11 = new ArrayList<>(Arrays.asList(otherIncome1, otherIncome2));

    Collateral collateral11 = new Collateral();
    collateral11.setUuid("123");
    Collateral collateral12 = new Collateral();
    collateral12.setUuid("456");
    List<Collateral> collaterals11 = new ArrayList<>(Arrays.asList(collateral11, collateral12));

    LoanApplicationReview loanApplicationReview1 = LoanApplicationReview.builder()
        .loanApplication(loanApplication11)
        .contactPerson(contactPerson11)
        .marriedPerson(marriedPerson11)
        .loanPayers(loanPayers1)
        .salaryIncomes(salaryIncomes11)
        .businessIncomes(businessIncomes11)
        .otherIncomes(otherIncomes11)
        .collaterals(collaterals11)
        .build();

    LoanApplication loanApplication21 = new LoanApplication();
    loanApplication21.setUuid("123");

    ContactPerson contactPerson21 = new ContactPerson();
    contactPerson21.setUuid("123");

    MarriedPerson marriedPerson21 = new MarriedPerson();
    marriedPerson21.setUuid("123");

    LoanPayer loanPayer21 = new LoanPayer();
    loanPayer21.setUuid("123");
    LoanPayer loanPayer22 = new LoanPayer();
    loanPayer22.setUuid("456");
    List<LoanPayer> loanPayers21 = new ArrayList<>(Arrays.asList(loanPayer21, loanPayer22));

    SalaryIncome salaryIncome21 = new SalaryIncome();
    salaryIncome21.setUuid("123");
    SalaryIncome salaryIncome22 = new SalaryIncome();
    salaryIncome22.setUuid("456");
    List<SalaryIncome> salaryIncomes21 = new ArrayList<>(
        Arrays.asList(salaryIncome21, salaryIncome22));

    BusinessIncome businessIncome21 = new BusinessIncome();
    businessIncome21.setUuid("123");
    BusinessIncome businessIncome22 = new BusinessIncome();
    businessIncome22.setUuid("456");
    List<BusinessIncome> businessIncomes21 = new ArrayList<>(
        Arrays.asList(businessIncome21, businessIncome22));

    OtherIncome otherIncome21 = new OtherIncome();
    otherIncome21.setUuid("123");
    OtherIncome otherIncome22 = new OtherIncome();
    otherIncome22.setUuid("456");
    List<OtherIncome> otherIncomes21 = new ArrayList<>(Arrays.asList(otherIncome21, otherIncome22));

    Collateral collateral21 = new Collateral();
    collateral21.setUuid("123");
    Collateral collateral22 = new Collateral();
    collateral22.setUuid("456");
    List<Collateral> collaterals21 = new ArrayList<>(Arrays.asList(collateral21, collateral22));

    LoanApplicationReview loanApplicationReview2 = LoanApplicationReview.builder()
        .loanApplication(loanApplication21)
        .contactPerson(contactPerson21)
        .marriedPerson(marriedPerson21)
        .loanPayers(loanPayers21)
        .salaryIncomes(salaryIncomes21)
        .businessIncomes(businessIncomes21)
        .otherIncomes(otherIncomes21)
        .collaterals(collaterals21)
        .build();

    assertTrue(loanApplicationReview1.equals(loanApplicationReview2));

    loanPayer22.setIdNo("123");
    assertTrue(!loanApplicationReview1.equals(loanApplicationReview2));
  }
}
