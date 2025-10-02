package vn.com.msb.homeloan.core.model;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.ReportEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CmsReportSearchParam {

  Paging paging;
  String loanCode;
  List<String> status;
  String branchCode;
  Date dateFrom;
  Date dateTo;
  ReportEnum type;
  String loanPurpose;
}
