package vn.com.msb.homeloan.core.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.model.overdraft.Overdraft;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanItemAndOverdraft {

  private List<LoanApplicationItem> loanApplicationItem;

  private List<Overdraft> overdraft;
}
