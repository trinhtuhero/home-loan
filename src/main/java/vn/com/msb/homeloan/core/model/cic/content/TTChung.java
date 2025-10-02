package vn.com.msb.homeloan.core.model.cic.content;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Accessors(chain = true)
@NoArgsConstructor
public class TTChung {

  private LocalDate ngaySL;

  private String tenTCTD;

  private String maTCTD;
}
