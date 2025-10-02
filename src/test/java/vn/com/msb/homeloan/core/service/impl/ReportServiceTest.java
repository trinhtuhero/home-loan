package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import vn.com.msb.homeloan.core.constant.ReportEnum;
import vn.com.msb.homeloan.core.model.CmsExportReportParam;
import vn.com.msb.homeloan.core.model.CmsFeedbackReport;
import vn.com.msb.homeloan.core.model.CmsFeedbackReportSearch;
import vn.com.msb.homeloan.core.model.CmsReportSearchParam;
import vn.com.msb.homeloan.core.model.CmsTATReport;
import vn.com.msb.homeloan.core.model.CmsTATReportSearch;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.service.CmsUserService;
import vn.com.msb.homeloan.core.service.ReportService;
import vn.com.msb.homeloan.core.util.export.ExcelExporter;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

  ReportService reportService;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  CmsUserService cmsUserService;

  @Mock
  MockHttpServletRequest request;

  @BeforeEach
  void setUp() {
    this.reportService = new ReportServiceImpl(
        loanApplicationRepository,
        cmsUserService);
  }

  @Test
  void givenValidInput_ThenCmsExportTATReport_shouldReturnSuccess()
      throws IOException, NoSuchFieldException, IllegalAccessException {
    CmsExportReportParam param = new CmsExportReportParam();
    param.setType(ReportEnum.TAT);

    CmsTATReport cmsTATReport = CmsTATReport.builder()
        .branchCode("branchCode")
        .branchName("branchName")
        .fullName("fullName")
        .idNo("idNo")
        .loanAmount(1111L)
        .loanCode("loanCode")
        .loanPurpose("loanPurpose")
        .phone("phone")
        .picRmEmail("picRmEmail")
        .picRmFullName("picRmFullName").build();
    List<CmsTATReport> cmsTATReports = Arrays.asList(cmsTATReport);
    List<String> strings = new ArrayList<>();
    doReturn(strings).when(cmsUserService).getAllChildrenUser(request);
    CmsExportReportParam cmsReportSearchParam = new CmsExportReportParam();
    doReturn(cmsTATReports).when(loanApplicationRepository)
        .cmsTATReportExport(cmsReportSearchParam, strings);

    ExcelExporter result = reportService.cmsExportReport(cmsReportSearchParam,
        ReportEnum.TAT.getApiCode(), request);

    assertEquals(result.headers.size(), 13);
  }

  @Test
  void givenValidInput_ThenCmsExportFEEDBACKReport_shouldReturnSuccess() throws IOException {
    CmsExportReportParam param = new CmsExportReportParam();
    param.setType(ReportEnum.FEEDBACK);

    CmsFeedbackReport cmsFeedbackReport = CmsFeedbackReport.builder()
        .branchCode("branchCode")
        .branchName("branchName")
        .fullName("fullName")
        .idNo("idNo")
        .loanAmount(1111L)
        .loanCode("loanCode")
        .loanPurpose("loanPurpose")
        .phone("phone")
        .picRmEmail("picRmEmail")
        .picRmFullName("picRmFullName").build();
    List<CmsFeedbackReport> cmsFeedbackReports = Arrays.asList(cmsFeedbackReport);
    List<String> strings = new ArrayList<>();
    doReturn(strings).when(cmsUserService).getAllChildrenUser(request);
    CmsExportReportParam cmsExportReportParam = new CmsExportReportParam();
    doReturn(cmsFeedbackReports).when(loanApplicationRepository)
        .cmsFeedBackReportExport(cmsExportReportParam, strings);

    ExcelExporter result = reportService.cmsExportReport(cmsExportReportParam,
        ReportEnum.FEEDBACK.getApiCode(), request);

    assertEquals(result.headers.size(), 12);
  }

  @Test
  void givenValidInput_ThenCmsTATReport_shouldReturnSuccess() throws IOException {
    CmsReportSearchParam param = new CmsReportSearchParam();
    param.setType(ReportEnum.TAT);

    CmsTATReport cmsTATReport = CmsTATReport.builder()
        .branchCode("branchCode")
        .branchName("branchName")
        .fullName("fullName")
        .idNo("idNo")
        .loanAmount(1111L)
        .loanCode("loanCode")
        .loanPurpose("loanPurpose")
        .phone("phone")
        .picRmEmail("picRmEmail")
        .picRmFullName("picRmFullName").build();
    List<CmsTATReport> cmsTATReports = Arrays.asList(cmsTATReport);
    CmsTATReportSearch cmsTATReportSearch = CmsTATReportSearch.builder()
        .contents(cmsTATReports).build();
    List<String> strings = new ArrayList<>();
    doReturn(strings).when(cmsUserService).getAllChildrenUser(request);
    CmsReportSearchParam cmsReportSearchParam = new CmsReportSearchParam();
    doReturn(cmsTATReportSearch).when(loanApplicationRepository)
        .cmsTATReportSearch(cmsReportSearchParam, strings);

    CmsTATReportSearch result = reportService.cmsTATReport(cmsReportSearchParam, request);

    assertEquals(result.getContents(), cmsTATReports);
  }

  @Test
  void givenValidInput_ThenCmsFeedbackReport_shouldReturnSuccess()
      throws IOException, NoSuchFieldException, IllegalAccessException {
    CmsReportSearchParam param = new CmsReportSearchParam();
    param.setType(ReportEnum.FEEDBACK);

    CmsFeedbackReport cmsFeedbackReport = CmsFeedbackReport.builder()
        .branchCode("branchCode")
        .branchName("branchName")
        .fullName("fullName")
        .idNo("idNo")
        .loanAmount(1111L)
        .loanCode("loanCode")
        .loanPurpose("loanPurpose")
        .phone("phone")
        .picRmEmail("picRmEmail")
        .picRmFullName("picRmFullName").build();
    List<CmsFeedbackReport> cmsFeedbackReports = Arrays.asList(cmsFeedbackReport);
    CmsFeedbackReportSearch cmsFeedbackReportSearch = CmsFeedbackReportSearch.builder()
        .contents(cmsFeedbackReports).build();
    List<String> strings = new ArrayList<>();
    doReturn(strings).when(cmsUserService).getAllChildrenUser(request);
    CmsReportSearchParam cmsReportSearchParam = new CmsReportSearchParam();
    doReturn(cmsFeedbackReportSearch).when(loanApplicationRepository)
        .cmsFeedBackReportSearch(cmsReportSearchParam, strings);

    CmsFeedbackReportSearch result = reportService.cmsFeedbackReport(cmsReportSearchParam, request);

    assertEquals(result.getContents(), cmsFeedbackReports);
  }
}
