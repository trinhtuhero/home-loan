package vn.com.msb.homeloan.core.model.cic.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class DSTCTD {

  protected Set<String> msbCicCode =
      Collections.unmodifiableSet(
          Stream.of(
                  "01302001",
                  "31302001",
                  "31302002",
                  "31302001",
                  "01302003",
                  "01302004",
                  "01302002",
                  "01302006",
                  "01302007",
                  "01302005",
                  "01302002",
                  "79302001",
                  "79302004",
                  "79302003",
                  "79302002",
                  "79302005",
                  "79302001",
                  "22302002",
                  "22302002",
                  "48302001",
                  "49302001",
                  "92302001",
                  "92302001",
                  "77302001",
                  "56302001",
                  "56302001",
                  "01302005",
                  "01302006",
                  "26302001",
                  "36302001",
                  "74302001",
                  "74302001",
                  "34302001",
                  "35302001",
                  "37302001",
                  "25302001",
                  "27302001",
                  "24302001",
                  "30302001",
                  "38302001",
                  "40302001",
                  "66302001",
                  "19302001",
                  "75302001",
                  "75302001",
                  "46302001",
                  "52302001",
                  "68302001",
                  "68302001",
                  "72302001",
                  "42302001",
                  "44302001",
                  "54302001",
                  "60302001",
                  "80302002",
                  "80302002",
                  "91302002",
                  "91302002",
                  "89302001",
                  "89302002",
                  "89302001",
                  "87302001",
                  "96302001",
                  "82302001",
                  "33302001",
                  "70302001",
                  "83302001",
                  "58302001",
                  "64302001",
                  "84302001",
                  "20302001",
                  "95302001",
                  "93302001",
                  "10302001",
                  "86302001",
                  "51302001",
                  "62302001")
              .collect(Collectors.toSet()));
  private List<TCTD> tctdList;

  public DSTCTD() {
    this.tctdList = new ArrayList<>();
  }

  public void addTctd(TCTD tctd) {
    tctdList.add(tctd);
  }

  public Map<TraNoType, List<TCTD>> classifyTCTD() {
    EnumMap<TraNoType, List<TCTD>> cMap = new EnumMap<>(TraNoType.class);
    for (TCTD tctd : tctdList) {
      TraNoType type = getTraNoType(tctd);
      List<TCTD> cList = cMap.get(type);
      if (cList == null) {
        cList = new ArrayList<>();
      }
      cList.add(tctd);
      cMap.putIfAbsent(type, cList);
    }
    return cMap;
  }

  private TraNoType getTraNoType(TCTD tctd) {
    TraNoType result;
    String cCode = tctd.getTtChung().getMaTCTD();
    boolean secure = tctd.getChiTiet().getTsbd().isCoTsdb();
    if (msbCicCode.contains(cCode)) {
      if (secure) {
        result = TraNoType.MSB_SECURE;
      } else {
        result = TraNoType.MSB_UNSECURE;
      }
    } else {
      if (secure) {
        result = TraNoType.NON_MSB_SECURE;
      } else {
        result = TraNoType.NON_MSB_UNSECURE;
      }
    }
    return result;
  }
}
