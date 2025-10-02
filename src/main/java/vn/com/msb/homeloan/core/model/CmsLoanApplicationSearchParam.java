package vn.com.msb.homeloan.core.model;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CmsLoanApplicationSearchParam {

  Paging paging;
  String branchCode;
  List<String> channelCodes;
  Date receptionDateFrom;
  Date receptionDateTo;
  String loanProduct;
  Long amountFrom;
  Long amountTo;
  List<String> status;
  String keyWord;
}
