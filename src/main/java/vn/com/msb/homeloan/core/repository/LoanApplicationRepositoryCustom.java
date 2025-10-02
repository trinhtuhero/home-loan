package vn.com.msb.homeloan.core.repository;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.model.CmsExportReportParam;
import vn.com.msb.homeloan.core.model.CmsFeedbackReport;
import vn.com.msb.homeloan.core.model.CmsFeedbackReportSearch;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationExportExcel;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationExportExcelParam;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationSearch;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationSearchParam;
import vn.com.msb.homeloan.core.model.CmsReportSearchParam;
import vn.com.msb.homeloan.core.model.CmsTATReport;
import vn.com.msb.homeloan.core.model.CmsTATReportSearch;
import vn.com.msb.homeloan.core.model.LoanApplication;

@Repository
public interface LoanApplicationRepositoryCustom {

  CmsLoanApplicationSearch cmsLoanApplicationSearch(CmsLoanApplicationSearchParam param,
      List<String> picRms);

  CmsLoanApplicationExportExcel cmsLoanApplicationExportExcel(
      CmsLoanApplicationExportExcelParam param, List<String> picRms);

  List<LoanApplication> getLoansByProfileId(String loanCode, List<String> status,
      Long loanAmountFrom, Long loanAmountTo, Instant from, Instant to, String userId);

  CmsTATReportSearch cmsTATReportSearch(CmsReportSearchParam param, List<String> picRms)
      throws IOException;

  CmsFeedbackReportSearch cmsFeedBackReportSearch(CmsReportSearchParam param, List<String> picRms)
      throws IOException;

  List<CmsTATReport> cmsTATReportExport(CmsExportReportParam param, List<String> picRms)
      throws IOException;

  List<CmsFeedbackReport> cmsFeedBackReportExport(CmsExportReportParam param, List<String> picRms)
      throws IOException;
}
