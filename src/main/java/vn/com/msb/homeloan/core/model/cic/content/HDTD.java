package vn.com.msb.homeloan.core.model.cic.content;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Accessors(chain = true)
public class HDTD {

  private String tenTCTD;
  private LocalDate ngayKy;
  private LocalDate ngayKT;

  public HDTD(String tenTCTD) {
    this.tenTCTD = tenTCTD;
  }

  public Double getDays() {
    if (ngayKT == null || ngayKy == null) {
      return null;
    }
    return Double.valueOf(ChronoUnit.DAYS.between(ngayKy, ngayKT));
  }
}
