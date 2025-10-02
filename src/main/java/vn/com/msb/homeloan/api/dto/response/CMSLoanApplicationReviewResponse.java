package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.api.dto.response.creditAppraisal.CreditAppraisalResponse;
import vn.com.msb.homeloan.api.dto.response.creditAppraisal.CreditworthinessItemResponse;
import vn.com.msb.homeloan.api.dto.response.creditAppraisal.ExceptionItemResponse;
import vn.com.msb.homeloan.api.dto.response.creditAppraisal.FieldSurveyItemResponse;
import vn.com.msb.homeloan.api.dto.response.loanApplication.CommonIncomeResponse;
import vn.com.msb.homeloan.api.dto.response.loanApplication.LoanApplicationResponse;
import vn.com.msb.homeloan.api.dto.response.overdraft.CMSOverdraftResponse;
import vn.com.msb.homeloan.core.model.ContactPerson;
import vn.com.msb.homeloan.core.model.FileConfigCategory;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CMSLoanApplicationReviewResponse {

  LoanApplicationResponse loanApplication;
  ContactPerson contactPerson;
  MarriedPersonResponse marriedPerson;
  List<CMSBusinessIncomeResponse> businessIncomes;
  List<CMSSalaryIncomeResponse> salaryIncomes;
  List<CMSOtherIncomeResponse> otherIncomes;
  List<CMSCollateralResponse> collaterals;
  List<CMSLoanPayerResponse> loanPayers;
  List<CMSCreditCardResponse> creditCards;
  List<LoanApplicationItemResponse> loanApplicationItems;
  List<CMSOverdraftResponse> overdrafts;
  CommonIncomeResponse commonIncome;

  CreditAppraisalResponse creditAppraisal;
  List<CMSGetCicInfoResponse> cics;
  CMSAssetEvaluateResponse assetEvaluate;
  List<CMSOtherEvaluateResponse> otherEvaluates;
  List<FieldSurveyItemResponse> fieldSurveyItems;
  List<ExceptionItemResponse> exceptionItems;
  List<CreditworthinessItemResponse> creditworthinessItems;
  List<FileConfigCategory> files;
}
