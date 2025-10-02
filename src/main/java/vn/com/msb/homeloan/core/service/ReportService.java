package vn.com.msb.homeloan.core.service;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import vn.com.msb.homeloan.core.model.CmsExportReportParam;
import vn.com.msb.homeloan.core.model.CmsFeedbackReportSearch;
import vn.com.msb.homeloan.core.model.CmsReportSearchParam;
import vn.com.msb.homeloan.core.model.CmsTATReportSearch;
import vn.com.msb.homeloan.core.util.export.ExcelExporter;

public interface ReportService {

  CmsTATReportSearch cmsTATReport(CmsReportSearchParam cmsReportSearchParam,
      HttpServletRequest httpRequest) throws IOException;

  CmsFeedbackReportSearch cmsFeedbackReport(CmsReportSearchParam cmsReportSearchParam,
      HttpServletRequest httpRequest) throws IOException;

  ExcelExporter cmsExportReport(CmsExportReportParam cmsExportReportParam, String reportEnum,
      HttpServletRequest httpRequest) throws IOException;
}
