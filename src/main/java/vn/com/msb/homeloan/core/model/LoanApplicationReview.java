package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.model.overdraft.Overdraft;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationReview {

  LoanApplication loanApplication;
  ContactPerson contactPerson;
  MarriedPerson marriedPerson;
  List<BusinessIncome> businessIncomes;
  List<SalaryIncome> salaryIncomes;
  List<OtherIncome> otherIncomes;
  List<Collateral> collaterals;
  List<LoanPayer> loanPayers;
  List<CreditCard> creditCards;
  List<LoanApplicationItem> loanApplicationItems;
  List<Overdraft> overdrafts;
  CommonIncome commonIncome;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoanApplicationReview that = (LoanApplicationReview) o;
    return HomeLoanUtil.compare(loanApplication, that.loanApplication)
        && HomeLoanUtil.compare(contactPerson, that.contactPerson)
        && HomeLoanUtil.compare(marriedPerson, that.marriedPerson)
        && HomeLoanUtil.compare(businessIncomes, that.businessIncomes)
        && HomeLoanUtil.compare(salaryIncomes, that.salaryIncomes)
        && HomeLoanUtil.compare(otherIncomes, that.otherIncomes)
        && HomeLoanUtil.compare(collaterals, that.collaterals)
        && HomeLoanUtil.compare(loanPayers, that.loanPayers)
        && HomeLoanUtil.compare(creditCards, that.creditCards)
        && HomeLoanUtil.compare(overdrafts, that.overdrafts)
        && HomeLoanUtil.compare(loanApplicationItems, that.loanApplicationItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(loanApplication.getUuid());
  }
}
