package vn.com.msb.homeloan.api.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoanTermResponse {

  private Integer loanTime;
  private BigDecimal loanAmount;
}
