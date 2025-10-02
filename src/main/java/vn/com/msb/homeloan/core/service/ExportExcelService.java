package vn.com.msb.homeloan.core.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vn.com.msb.homeloan.core.model.CMSLoanApplicationReview;

public interface ExportExcelService {

  XSSFWorkbook exportProposalLetter(CMSLoanApplicationReview data, XSSFWorkbook workbook);
}
