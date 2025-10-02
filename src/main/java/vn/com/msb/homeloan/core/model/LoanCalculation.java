package vn.com.msb.homeloan.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanCalculation {

  private Integer month;

  private Double interestRate;

  private String principalAmount;

  private Double prepaymentFee;

  private String principalAmountMonthly;

  private String interestMonthly;

  private String total;
}
