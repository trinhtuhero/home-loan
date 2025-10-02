package vn.com.msb.homeloan.api.controllers.internal;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CmsExportReportRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CmsReportSearchRequestMapper;
import vn.com.msb.homeloan.api.dto.request.CMSExportReportRequest;
import vn.com.msb.homeloan.api.dto.request.CMSReportSearchRequest;
import vn.com.msb.homeloan.core.constant.ReportEnum;
import vn.com.msb.homeloan.core.model.CmsFeedbackReportSearch;
import vn.com.msb.homeloan.core.model.CmsTATReportSearch;
import vn.com.msb.homeloan.core.service.ReportService;
import vn.com.msb.homeloan.core.util.export.ExcelExporter;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/report")
@RequiredArgsConstructor
public class CMSReportController {

  private final ReportService reportService;

  @PostMapping("/search")
  public ResponseEntity<ApiResource> report(@Valid @RequestBody CMSReportSearchRequest request,
      HttpServletRequest httpRequest) throws IOException {
    ApiResource apiResource;
    if (ReportEnum.TAT.getApiCode().equals(request.getType())) {
      CmsTATReportSearch cmsTATReport = reportService.cmsTATReport(
          CmsReportSearchRequestMapper.INSTANCE.toModel(request), httpRequest);
      apiResource = new ApiResource(cmsTATReport, HttpStatus.OK.value());
    } else {
      CmsFeedbackReportSearch cmsFeedbackReport = reportService.cmsFeedbackReport(
          CmsReportSearchRequestMapper.INSTANCE.toModel(request), httpRequest);
      apiResource = new ApiResource(cmsFeedbackReport, HttpStatus.OK.value());
    }
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/export")
  public ResponseEntity<byte[]> exportToExcel(@RequestBody CMSExportReportRequest request,
      HttpServletRequest httpRequest) throws IOException {
    ExcelExporter excelExporter = reportService.cmsExportReport(
        CmsExportReportRequestMapper.INSTANCE.toModel(request), request.getType(), httpRequest);
    byte[] bytes = excelExporter.export();
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
    String currentDateTime = dateFormatter.format(new Date());
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=" + (ReportEnum.TAT.getApiCode().equals(request.getType())
                ? ReportEnum.TAT.getApiCode() : ReportEnum.FEEDBACK.getApiCode()) + "_"
                + currentDateTime + ".xlsx")
        .contentType(MediaType.parseMediaType("application/csv"))
        .body(bytes);
  }
}
