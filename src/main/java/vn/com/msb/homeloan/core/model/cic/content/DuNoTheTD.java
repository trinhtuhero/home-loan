package vn.com.msb.homeloan.core.model.cic.content;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import vn.com.msb.homeloan.core.constant.Constants;

@Data
@Slf4j
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DuNoTheTD {

  public static final String TO_CHUC_TIN_DUNG_MSB = "Ngân hàng TMCP Hàng hải Việt Nam";

  private LocalDate ngaySL;
  private String tenTCTDPhatHanh;
  private BigDecimal hMTheTD;
  private BigDecimal soTienPhaiThanhToan;
  private BigDecimal soTienChamThanhToan;
  private Integer soNgayChamThanhToan;

  // Nghĩa vụ trả nợ thẻ tín dụng hàng tháng tại CIC = Tổng hạn mức thẻ tín dụng tại CIC (không bao
  // gồm của MSB) *5%
  public BigDecimal traNoTheTinDungCIC() {
    if (!TO_CHUC_TIN_DUNG_MSB.equals(tenTCTDPhatHanh)) {
      return this.hMTheTD.multiply(Constants.COEFFICIENT_CREDIT_CARD_CIC);
    }
    return BigDecimal.ZERO;
  }

  public BigDecimal hanMucTheTinDungCIC() {
    if (!TO_CHUC_TIN_DUNG_MSB.equals(tenTCTDPhatHanh)) {
      return this.hMTheTD;
    }
    return BigDecimal.ZERO;
  }
}
