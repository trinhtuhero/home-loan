package vn.com.msb.homeloan.api.dto.response.overdraft;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.api.dto.response.CMSLoanApplicationItemResponse;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class CMSLoanItemAndOverdraftResponse {

  List<CMSLoanApplicationItemResponse> loanApplicationItem;
  List<CMSOverdraftResponse> overdraft;
}
