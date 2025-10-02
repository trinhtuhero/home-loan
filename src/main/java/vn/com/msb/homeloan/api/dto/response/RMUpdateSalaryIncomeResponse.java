package vn.com.msb.homeloan.api.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.api.dto.response.loanApplication.LoanApplicationResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RMUpdateSalaryIncomeResponse {

  List<CMSSalaryIncomeResponse> cmsSalaryIncomeResponses;

  LoanApplicationResponse loanApplicationResponse;
}
