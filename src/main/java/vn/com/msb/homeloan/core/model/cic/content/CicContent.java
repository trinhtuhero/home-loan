package vn.com.msb.homeloan.core.model.cic.content;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class CicContent {

  private TTChung ttchung;

  private DSTCTD dstctd;

  private List<HDTD> hdtdList;

  private List<DuNoTheTD> duNoTheTDList;

  public CicContent() {
    hdtdList = new ArrayList<>();
    duNoTheTDList = new ArrayList<>();
  }

  public void addHdtd(HDTD hdtd) {
    hdtdList.add(hdtd);
  }

  public void addDuNoTheTD(DuNoTheTD duNoTheTD) {
    duNoTheTDList.add(duNoTheTD);
  }
}
