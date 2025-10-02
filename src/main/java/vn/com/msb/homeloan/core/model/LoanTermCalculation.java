package vn.com.msb.homeloan.core.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoanTermCalculation {

  private Integer loanTime;
  private BigDecimal loanAmount;
}
