package vn.com.msb.homeloan.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import vn.com.msb.homeloan.api.dto.response.CMSCustomerInfoResponse;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.InterestedEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.model.CMSCustomerAndRelatedPersonInfo;
import vn.com.msb.homeloan.core.model.CMSLoanApplicationReview;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationExportExcelParam;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationSearch;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationSearchParam;
import vn.com.msb.homeloan.core.model.IncomeInfo;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.model.LoanApplicationReview;
import vn.com.msb.homeloan.core.model.response.DownloadPresignedUrlResponse;
import vn.com.msb.homeloan.core.util.export.ExcelExporter;

public interface LoanApplicationService {

  LoanApplication findById(String uuid);

  LoanApplication save(LoanApplication loanInformation, ClientTypeEnum clientType);

  LoanApplication updateCustomer(LoanApplication loanApplication);

  LoanApplicationReview review(String loanId);

  CMSLoanApplicationReview cmsReview(String loanId);

  LoanApplicationReview ldpReview(String loanId);

  void checkEditLoanApp(LoanApplicationEntity loanApplication, ClientTypeEnum clientType);

  int customerSubmitFile(String loanId, String status, String refEmail);

  List<LoanApplication> checkAndAssignLoanAppNeedToUploadZip();

  CmsLoanApplicationSearch cmsSearch(CmsLoanApplicationSearchParam search,
      HttpServletRequest httpRequest) throws IOException;

  ExcelExporter export(CmsLoanApplicationExportExcelParam search, HttpServletRequest httpRequest)
      throws IOException;

  DownloadPresignedUrlResponse preZipFile(String loanApplicationId);

  LoanApplication updateRmForLoan(String loanId, String key) throws NoSuchAlgorithmException;

  int closeLoanApplication(String loanId, String reason);

  List<LoanApplication> getLoansByProfileId(String loanCode, List<String> status,
      Long loanAmountFrom, Long loanAmountTo, Instant from, Instant to);

  Map exportProposalLetter(String loanId);

  CMSLoanApplicationReview cmsReviewAndFile(String loanApplicationId);

  void submitFeedback(String loanApplicationId);

  void zipLoans(List<String> list);

  void regenerateDNVV(String loanId);

  CMSCustomerInfoResponse makeProposalCustomerInfo(LoanApplication loanApplication);

  CMSCustomerAndRelatedPersonInfo getCustomerAndRelatedPersonInfo(String loanId);

  String updateStatus(String loanId, String status, String note, ClientTypeEnum clientType)
      throws JsonProcessingException;

  boolean customerApprovalChange(String loanId, String status, String note)
      throws JsonProcessingException;

  IncomeInfo getIncomeInfo(String loanId, String type);

  long totalIncome(String loanId);

  long totalIncomeExchange(String loanId);

  long totalIncomeActuallyReceived(String loanId);

  void deleteUnselectedIncomes(String loanId, String[] selectedIncomes);

  String getMobioLink(String loanId);

  String checkPhone(String phone);

  Map<String, String> loanInitialize(String phone, boolean isCopyLoan);

  void interestedInInsurance(String loanCode, InterestedEnum interestedEnum);

  void interestedInInsurance2(String loanId, InterestedEnum interestedEnum);
}
