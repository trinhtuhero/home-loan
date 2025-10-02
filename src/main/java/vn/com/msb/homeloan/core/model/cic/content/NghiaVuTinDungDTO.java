package vn.com.msb.homeloan.core.model.cic.content;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NghiaVuTinDungDTO {

  private BigDecimal amountTraNo;
  private BigDecimal mue;
}
