package vn.com.msb.homeloan.core.model;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IncomeInfo {

  List<SalaryIncome> salaryIncomes;

  List<BusinessIncome> businessHouseholdIncomes;

  List<BusinessIncome> businessEnterpriseIncomes;

  List<OtherIncome> otherIncomes;

  LoanApplication loanApplication;

  AssetEvaluate assetEvaluate;

  List<OtherEvaluate> otherEvaluates;

  CommonIncome commonIncome;

  List<LoanApplicationItem> loanApplicationItems;

  List<CreditCard> creditCards;
}


