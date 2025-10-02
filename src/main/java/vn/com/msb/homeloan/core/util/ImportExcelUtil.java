package vn.com.msb.homeloan.core.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

public class ImportExcelUtil {

  public static String getCellValue(Row row, int cellNo) {
    DataFormatter formatter = new DataFormatter();
    Cell cell = row.getCell(cellNo);
    return formatter.formatCellValue(cell).trim();
  }
}
