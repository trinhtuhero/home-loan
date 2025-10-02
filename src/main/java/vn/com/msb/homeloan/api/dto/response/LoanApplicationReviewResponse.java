package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.api.dto.response.loanApplication.CommonIncomeResponse;
import vn.com.msb.homeloan.api.dto.response.loanApplication.LoanApplicationResponse;
import vn.com.msb.homeloan.api.dto.response.overdraft.OverdraftResponse;
import vn.com.msb.homeloan.core.model.ContactPerson;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanApplicationReviewResponse {

  LoanApplicationResponse loanApplication;
  ContactPerson contactPerson;
  MarriedPersonResponse marriedPerson;
  List<CRUBusinessIncomeResponse> businessIncomes;
  List<CRUSalaryIncomeResponse> salaryIncomes;
  List<CRUOtherIncomeResponse> otherIncomes;
  List<CollateralResponse> collaterals;
  List<LoanPayerResponse> loanPayers;
  List<CRUCreditCardResponse> creditCards;
  List<LoanApplicationItemResponse> loanApplicationItems;
  List<OverdraftResponse> overdrafts;
  CommonIncomeResponse commonIncome;
}
