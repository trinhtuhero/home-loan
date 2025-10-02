package vn.com.msb.homeloan.api.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.model.Paging;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CMSLoanApplicationSearchRequest {

  Paging paging;
  String branchCode;
  List<String> channelCodes;
  String receptionDateFrom;
  String receptionDateTo;
  String loanProduct;
  Long amountFrom;
  Long amountTo;
  List<String> status;
  String keyWord;
}
