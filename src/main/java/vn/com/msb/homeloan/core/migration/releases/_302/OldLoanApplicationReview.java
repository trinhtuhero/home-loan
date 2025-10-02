package vn.com.msb.homeloan.core.migration.releases._302;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.model.BusinessIncome;
import vn.com.msb.homeloan.core.model.Collateral;
import vn.com.msb.homeloan.core.model.CollateralOwner;
import vn.com.msb.homeloan.core.model.ContactPerson;
import vn.com.msb.homeloan.core.model.CreditCard;
import vn.com.msb.homeloan.core.model.LoanPayer;
import vn.com.msb.homeloan.core.model.MarriedPerson;
import vn.com.msb.homeloan.core.model.OtherIncome;
import vn.com.msb.homeloan.core.model.SalaryIncome;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OldLoanApplicationReview {

  OldLoanApplication loanApplication;
  ContactPerson contactPerson;
  MarriedPerson marriedPerson;
  List<BusinessIncome> businessIncomes;
  List<SalaryIncome> salaryIncomes;
  List<OtherIncome> otherIncomes;
  List<Collateral> collaterals;
  List<CollateralOwner> collateralOwners;
  List<LoanPayer> loanPayers;
  List<CreditCard> creditCards;
}
