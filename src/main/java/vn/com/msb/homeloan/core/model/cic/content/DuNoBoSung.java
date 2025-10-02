package vn.com.msb.homeloan.core.model.cic.content;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;

@EqualsAndHashCode(callSuper = true)
@Getter
@Slf4j
public class DuNoBoSung extends DuNo {

  public DuNoBoSung(Elements elements, String name) {
    super(elements, name);
  }

  @Override
  public boolean isValidHdtd(HDTD hdtd) {
    Double days = hdtd.getDays();
    if (days == null) {
      return false;
    }
    return days / 30 <= 12;
  }

  @Override
  public BigDecimal getDefaultThoiHanVayConLai() {
    return BigDecimal.valueOf(12);
  }
}
