package vn.com.msb.homeloan.api.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CMSLoanApplicationExportExcelRequest {

  String branchCode;
  List<String> channelCodes;
  String receptionDateFrom;
  String receptionDateTo;
  String loanProduct;
  Long amountFrom;
  Long amountTo;
  List<String> status;
  String keyWord;
  List<String> loanIds;
}
