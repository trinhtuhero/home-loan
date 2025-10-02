package vn.com.msb.homeloan.core.model.cic.content;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.util.ObjectUtil;

@Slf4j
@Data
@AllArgsConstructor
public class TCTD {

  private TTChung ttChung;

  private ChiTiet chiTiet;

  public void addInfoToDuNo(CicContent c) {
    List<HDTD> hdtdList = c.getHdtdList();
    List<HDTD> hdtds =
        hdtdList.stream()
            .filter(h -> ttChung.getTenTCTD().equals(h.getTenTCTD()))
            .collect(Collectors.toList());
    List<DuNo> duNoList = chiTiet.getDuNoList();
    if (hdtds.size() == 1 && duNoList.size() == 1) {
      duNoList.get(0).setNgayKT(hdtds.get(0).getNgayKT());
      duNoList.get(0).setNgaySL(c.getTtchung().getNgaySL());
    } else {
      for (DuNo d : duNoList) {
        d.setNgaySL(c.getTtchung().getNgaySL());
        if (d.getName().equals(Constants.DU_NO_KHAC) || d.getName()
            .equals(Constants.DU_NO_BO_SUNG)) {
          d.setNgayKT(null);
        } else {
          List<HDTD> hdtdOfDuNo = hdtds.stream().filter(d::isValidHdtd)
              .collect(Collectors.toList());
          if (ObjectUtil.isNotEmpty(hdtdOfDuNo)) {
            double ave =
                hdtdOfDuNo.stream()
                    .mapToLong(
                        x ->
                            x.getNgayKT()
                                .atStartOfDay(ZoneId.systemDefault())
                                .toInstant()
                                .toEpochMilli())
                    .average()
                    .orElse(0D);
            LocalDate ngayKT =
                Instant.ofEpochMilli(Math.round(ave)).atZone(ZoneId.systemDefault()).toLocalDate();
            d.setNgayKT(ngayKT);
          }
        }
      }
    }
  }
}
