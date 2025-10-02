package vn.com.msb.homeloan.core.model.cic.content;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
public class ChiTiet {

  private List<DuNo> duNoList;

  private TSBD tsbd;
}
