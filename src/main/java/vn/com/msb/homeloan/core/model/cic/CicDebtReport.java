package vn.com.msb.homeloan.core.model.cic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.com.msb.homeloan.core.constant.cic.CicLoanTypeEnum;

/**
 * CIC code information
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CicDebtReport extends CicBaseModel {

  private LocalDate ngayCicTraKq;
  private Integer tongHanMucTheTd;
  private List<ThongTinTctd> thongTinTctds;

  @Data
  @NoArgsConstructor
  public static class ThongTinTctd {

    private String tenTctd;
    private Boolean coTsdb;
    private Map<CicLoanTypeEnum, Integer> thongTinDuNo;
    private List<LocalDate> ngayKtHdtd;
  }

}
