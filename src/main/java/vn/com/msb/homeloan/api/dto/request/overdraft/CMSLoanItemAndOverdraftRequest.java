package vn.com.msb.homeloan.api.dto.request.overdraft;

import java.util.List;
import javax.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.api.dto.request.CMSLoanApplicationItemRequest;

@Getter
@Setter
@ToString
public class CMSLoanItemAndOverdraftRequest {

  private List<@Valid CMSLoanApplicationItemRequest> loanApplicationItem;

  private List<@Valid CMSOverdraftRequest> overdraft;
}
