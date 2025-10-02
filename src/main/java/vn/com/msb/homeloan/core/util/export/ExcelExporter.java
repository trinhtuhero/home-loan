package vn.com.msb.homeloan.core.util.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.model.CmsFeedbackReport;
import vn.com.msb.homeloan.core.model.CmsLoanApplication;
import vn.com.msb.homeloan.core.model.CmsTATReport;
import vn.com.msb.homeloan.core.model.CmsUser;
import vn.com.msb.homeloan.core.util.ObjectUtil;
import vn.com.msb.homeloan.core.util.StringUtils;

@Slf4j
public class ExcelExporter {

  private XSSFWorkbook workbook;
  private XSSFSheet sheet;
  private List<CmsLoanApplication> lstCmsLoanApplication;
  private List<CmsUser> cmsUsersImport;
  public List<String> headers;
  private List<CmsTATReport> cmsTATReportExports;
  private List<CmsFeedbackReport> cmsFeedbackReports;

  public ExcelExporter(List<CmsLoanApplication> lstCmsLoanApplication, List<CmsUser> cmsUsersImport,
      List<String> headers) {
    this.lstCmsLoanApplication = lstCmsLoanApplication;
    this.cmsUsersImport = cmsUsersImport;
    this.headers = headers;
    workbook = new XSSFWorkbook();
  }

  public ExcelExporter(List list, List<String> headers) {
    if (!CollectionUtils.isEmpty(list) && list.get(0) instanceof CmsTATReport) {
      this.cmsTATReportExports = (List<CmsTATReport>) list;
    } else if (!CollectionUtils.isEmpty(list) && list.get(0) instanceof CmsFeedbackReport) {
      this.cmsFeedbackReports = (List<CmsFeedbackReport>) list;
    }
    this.headers = headers;
    workbook = new XSSFWorkbook();
  }

  private void writeHeaderLine() {
    List<String> lstHeader = new ArrayList<>(headers);
    if (lstCmsLoanApplication != null) {
      sheet = workbook.createSheet(Constants.ExportLoanApplication.SHEET_NAME);
    } else if (cmsUsersImport != null) {
      sheet = workbook.createSheet(Constants.ImportCmsUser.SHEET_NAME);
      lstHeader.add(Constants.ImportCmsUser.ERROR);
    } else if (cmsTATReportExports != null) {
      sheet = workbook.createSheet(Constants.ExportReportTAT.SHEET_NAME);
    } else if (cmsFeedbackReports != null) {
      sheet = workbook.createSheet(Constants.ExportReportFeedback.SHEET_NAME);
    } else {
      sheet = workbook.createSheet("NO_DATA");
    }

    Row row = sheet.createRow(0);

    CellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setBold(true);
    font.setFontHeight(16);
    style.setFont(font);

    for (int i = 0; i < lstHeader.size(); i++) {
      createCell(row, i, lstHeader.get(i), style);
      sheet.autoSizeColumn(i);
    }
  }

  private void createCell(Row row, int columnCount, Object value, CellStyle style) {
    // low performance
    // sheet.autoSizeColumn(columnCount);
    Cell cell = row.createCell(columnCount);
    if (value instanceof Integer) {
      cell.setCellValue((Integer) value);
    } else if (value instanceof Long) {
      cell.setCellValue((Long) value);
    } else if (value instanceof Boolean) {
      cell.setCellValue((Boolean) value);
    } else {
      cell.setCellValue((String) value);
    }
    cell.setCellStyle(style);
  }

  private void writeDataLines() {
    int rowCount = 1;
    CellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    CellStyle styleCurrencyFormat = workbook.createCellStyle();
    styleCurrencyFormat.setDataFormat(workbook.createDataFormat().getFormat("#,##"));
    font.setFontHeight(14);
    style.setFont(font);
    styleCurrencyFormat.setFont(font);
    String purpose;

    if (lstCmsLoanApplication != null) {
      for (CmsLoanApplication cmsLoanApplication : lstCmsLoanApplication) {
        Row row = sheet.createRow(rowCount++);
        int columnCount = 0;

        createCell(row, columnCount++, rowCount - 1, style);
        createCell(row, columnCount++, cmsLoanApplication.getLoanCode(), style);
        createCell(row, columnCount++, cmsLoanApplication.getFullName(), style);
        createCell(row, columnCount++, cmsLoanApplication.getPhone(), style);
        createCell(row, columnCount++, cmsLoanApplication.getIdNo(), style);
        purpose = null;
        if(!ObjectUtil.isEmpty(cmsLoanApplication.getLoanPurpose())){
          purpose =
            ObjectUtil.isEmpty(LoanPurposeEnum.asEnum(cmsLoanApplication.getLoanPurpose()))
              ? FormOfCreditEnum.valueOf(cmsLoanApplication.getLoanPurpose()).getName()
              : LoanPurposeEnum.valueOf(cmsLoanApplication.getLoanPurpose()).getName();
        }
        createCell(row, columnCount++, purpose, style);
        createCell(row, columnCount++, cmsLoanApplication.getLoanAmount(), styleCurrencyFormat);
        createCell(row, columnCount++, cmsLoanApplication.getPicRmEmail(), style);
        createCell(row, columnCount++, cmsLoanApplication.getBranchName(), style);
        createCell(row, columnCount++,
            LoanInfoStatusEnum.valueOf(cmsLoanApplication.getStatus()).getName(), style);
        createCell(row, columnCount++, cmsLoanApplication.getReceiveDate(), style);
      }
    } else if (cmsUsersImport != null) {
      for (CmsUser cmsUser : cmsUsersImport) {
        Row row = sheet.createRow(rowCount++);
        int columnCount = 0;

        createCell(row, columnCount++, rowCount - 1, style);
        createCell(row, columnCount++, cmsUser.getEmplId(), style);
        createCell(row, columnCount++, cmsUser.getFullName(), style);
        createCell(row, columnCount++, cmsUser.getEmail(), style);
        createCell(row, columnCount++, cmsUser.getPhone(), style);
        createCell(row, columnCount++, cmsUser.getBranchCode(), style);
        createCell(row, columnCount++, cmsUser.getLeaderEmail(), style);
        createCell(row, columnCount++, cmsUser.getRole(), style);
        createCell(row, columnCount++, cmsUser.getError(), style);
      }
    } else if (cmsTATReportExports != null) {
      for (CmsTATReport cmsTATReport : cmsTATReportExports) {
        Row row = sheet.createRow(rowCount++);
        int columnCount = 0;

        createCell(row, columnCount++, rowCount - 1, style);
        createCell(row, columnCount++, cmsTATReport.getLoanCode(), style);
        createCell(row, columnCount++, cmsTATReport.getBranchName(), style);
        purpose = null;
        if(!ObjectUtil.isEmpty(cmsTATReport.getLoanPurpose())){
          purpose =
            ObjectUtil.isEmpty(LoanPurposeEnum.asEnum(cmsTATReport.getLoanPurpose()))
              ? FormOfCreditEnum.valueOf(cmsTATReport.getLoanPurpose()).getName()
              : LoanPurposeEnum.valueOf(cmsTATReport.getLoanPurpose()).getName();
        }
        createCell(row, columnCount++, purpose, style);
        createCell(row, columnCount++, cmsTATReport.getFullName(), style);
        createCell(row, columnCount++, cmsTATReport.getIdNo(), style);
        createCell(row, columnCount++, cmsTATReport.getLoanAmount(), styleCurrencyFormat);
        createCell(row, columnCount++, cmsTATReport.getPicRmFullName(), style);
        createCell(row, columnCount++, cmsTATReport.getPicRmEmail(), style);
        createCell(row, columnCount++, cmsTATReport.getSubmittedDate(), style);
        createCell(row, columnCount++, cmsTATReport.getReceiveDate(), style);
        createCell(row, columnCount++, cmsTATReport.getFinishDate(), style);
        createCell(row, columnCount++,
            !StringUtils.isEmpty(cmsTATReport.getStatus()) ? LoanInfoStatusEnum.valueOf(
                cmsTATReport.getStatus()).getName() : null, style);
      }
    } else if (cmsFeedbackReports != null) {
      for (CmsFeedbackReport cmsFeedbackReport : cmsFeedbackReports) {
        Row row = sheet.createRow(rowCount++);
        int columnCount = 0;

        createCell(row, columnCount++, rowCount - 1, style);
        createCell(row, columnCount++, cmsFeedbackReport.getLoanCode(), style);
        createCell(row, columnCount++, cmsFeedbackReport.getBranchName(), style);
        purpose = null;
        if(!ObjectUtil.isEmpty(cmsFeedbackReport.getLoanPurpose())){
          purpose =
              ObjectUtil.isEmpty(LoanPurposeEnum.asEnum(cmsFeedbackReport.getLoanPurpose()))
                  ? FormOfCreditEnum.valueOf(cmsFeedbackReport.getLoanPurpose()).getName()
                  : LoanPurposeEnum.valueOf(cmsFeedbackReport.getLoanPurpose()).getName();
        }
        createCell(row, columnCount++, purpose, style);
        createCell(row, columnCount++, cmsFeedbackReport.getFullName(), style);
        createCell(row, columnCount++, cmsFeedbackReport.getIdNo(), style);
        createCell(row, columnCount++, cmsFeedbackReport.getLoanAmount(), styleCurrencyFormat);
        createCell(row, columnCount++, cmsFeedbackReport.getPicRmFullName(), style);
        createCell(row, columnCount++, cmsFeedbackReport.getPicRmEmail(), style);
        createCell(row, columnCount++, cmsFeedbackReport.getRate(), style);
        createCell(row, columnCount++, cmsFeedbackReport.getAdditionalComment(), style);
        createCell(row, columnCount++,
            !StringUtils.isEmpty(cmsFeedbackReport.getStatus()) ? LoanInfoStatusEnum.valueOf(
                cmsFeedbackReport.getStatus()).getName() : null, style);
      }
    }
  }

  public byte[] export() throws IOException {
    writeHeaderLine();
    writeDataLines();
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    byte[] bytes = null;
    try {
      workbook.write(bos);
      bytes = bos.toByteArray();
    } catch (IOException e) {
      log.error("Exception : {}", e.getMessage(), e);
    } finally {
      try {
        bos.close();
      } catch (Exception ignored) {
      }
    }
    return bytes;
  }
}
