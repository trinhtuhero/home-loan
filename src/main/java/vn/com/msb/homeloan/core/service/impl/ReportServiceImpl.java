package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.constant.ReportEnum;
import vn.com.msb.homeloan.core.model.*;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.service.CmsUserService;
import vn.com.msb.homeloan.core.service.ReportService;
import vn.com.msb.homeloan.core.util.export.ExcelExporter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

  private LoanApplicationRepository loanApplicationRepository;
  private CmsUserService cmsUserService;

  @Override
  public CmsTATReportSearch cmsTATReport(CmsReportSearchParam cmsReportSearchParam,
      HttpServletRequest httpRequest) throws IOException {
    return loanApplicationRepository.cmsTATReportSearch(cmsReportSearchParam,
        cmsUserService.getAllChildrenUser(httpRequest));
  }

  @Override
  public CmsFeedbackReportSearch cmsFeedbackReport(CmsReportSearchParam cmsReportSearchParam,
      HttpServletRequest httpRequest) throws IOException {
    return loanApplicationRepository.cmsFeedBackReportSearch(cmsReportSearchParam,
        cmsUserService.getAllChildrenUser(httpRequest));
  }

  @Override
  public ExcelExporter cmsExportReport(CmsExportReportParam param, String reportEnum,
      HttpServletRequest httpRequest) throws IOException {
    List<String> picRms = cmsUserService.getAllChildrenUser(httpRequest);
    if (ReportEnum.TAT.getApiCode().equals(reportEnum)) {
      List<CmsTATReport> cmsTATReportExport = loanApplicationRepository.cmsTATReportExport(param,
          picRms);
      List<String> headers = Arrays.asList(Constants.ExportReportTAT.STT,
          Constants.ExportReportTAT.LOAN_CODE,
          Constants.ExportReportTAT.BRANCH_NAME,
          Constants.ExportReportTAT.LOAN_PURPOSE,
          Constants.ExportReportTAT.FULL_NAME,
          Constants.ExportReportTAT.ID_NO,
          Constants.ExportReportTAT.LOAN_AMOUNT,
          Constants.ExportReportTAT.PIC_RM,
          Constants.ExportReportTAT.PIC_RM_EMAIL,
          Constants.ExportReportTAT.REQUEST_DATE,
          Constants.ExportReportTAT.RECEIVE_DATE,
          Constants.ExportReportTAT.SUBMITTED_DATE,
          Constants.ExportReportTAT.STATUS);
      return new ExcelExporter(cmsTATReportExport, headers);
    } else {
      List<CmsFeedbackReport> cmsFeedbackReportExport = loanApplicationRepository.cmsFeedBackReportExport(
          param, picRms);
      List<String> headers = Arrays.asList(Constants.ExportReportFeedback.STT,
          Constants.ExportReportFeedback.LOAN_CODE,
          Constants.ExportReportFeedback.BRANCH_NAME,
          Constants.ExportReportFeedback.LOAN_PURPOSE,
          Constants.ExportReportFeedback.FULL_NAME,
          Constants.ExportReportFeedback.ID_NO,
          Constants.ExportReportFeedback.LOAN_AMOUNT,
          Constants.ExportReportFeedback.PIC_RM,
          Constants.ExportReportFeedback.PIC_RM_EMAIL,
          Constants.ExportReportFeedback.RATE,
          Constants.ExportReportFeedback.ADDITIONAL_COMMENT,
          Constants.ExportReportFeedback.STATUS);
      return new ExcelExporter(cmsFeedbackReportExport, headers);
    }
  }
}
