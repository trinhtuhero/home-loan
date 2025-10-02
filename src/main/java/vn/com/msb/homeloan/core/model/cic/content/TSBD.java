package vn.com.msb.homeloan.core.model.cic.content;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

@Data
public class TSBD {

  private boolean coTsdb;

  public TSBD(Element coTsdbElement) {
    coTsdb =
        (coTsdbElement != null && StringUtils.isNotEmpty(coTsdbElement.text()))
            && toBoolean(coTsdbElement.text());
  }

  private boolean toBoolean(String tsbd) {
    return "1".equals(tsbd);
  }
}
