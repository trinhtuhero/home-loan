package vn.com.msb.homeloan.core.model.cic.content;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerLoanInfo {

  private String marriedCode;
  private BigDecimal loanPropose;
  private BigDecimal incomePa;

  public boolean isCustomerMarried() {
    return "MARRIED".equalsIgnoreCase(marriedCode);
  }
}
