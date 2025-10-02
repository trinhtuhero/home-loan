package vn.com.msb.homeloan.core.parser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.model.cic.content.ChiTiet;
import vn.com.msb.homeloan.core.model.cic.content.CicContent;
import vn.com.msb.homeloan.core.model.cic.content.DSTCTD;
import vn.com.msb.homeloan.core.model.cic.content.DuNo;
import vn.com.msb.homeloan.core.model.cic.content.DuNoBoSung;
import vn.com.msb.homeloan.core.model.cic.content.DuNoDaiHan;
import vn.com.msb.homeloan.core.model.cic.content.DuNoKhac;
import vn.com.msb.homeloan.core.model.cic.content.DuNoNganHan;
import vn.com.msb.homeloan.core.model.cic.content.DuNoTheTD;
import vn.com.msb.homeloan.core.model.cic.content.DuNoTrungHan;
import vn.com.msb.homeloan.core.model.cic.content.HDTD;
import vn.com.msb.homeloan.core.model.cic.content.TCTD;
import vn.com.msb.homeloan.core.model.cic.content.TSBD;
import vn.com.msb.homeloan.core.model.cic.content.TTChung;

@Slf4j
public class ProcessCicParserImpl implements ProcessCicParser {

  private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  private final Document f5XmlDocument;
  private LocalDate responseTime;

  private ProcessCicParserImpl(final String f5XmlContent) {
    this.responseTime = LocalDate.now();
    if (StringUtils.isNotEmpty(f5XmlContent)) {
      this.f5XmlDocument = Jsoup.parse(f5XmlContent);
      final String thoiGian = this.f5XmlDocument.select("CicTemplate>Header>ThoiGian").text();
      if (StringUtils.isNotEmpty(thoiGian)) {
        this.responseTime = LocalDate.from(dateFormatter.parse(thoiGian));
      }
    } else {
      this.f5XmlDocument = null;
    }
  }

  public static ProcessCicParserImpl of(@NonNull final String f5XmlContent) {
    return new ProcessCicParserImpl(f5XmlContent);
  }

  private LocalDate convertToDate(String date) {
    if (StringUtils.isNotBlank(date)) {
      return LocalDate.from(dateFormatter.parse(date));
    }
    return null;
  }

  @Override
  public CicContent getCicContentFromXml() {
    CicContent cicContent = new CicContent();
    final Elements tctdElements = f5XmlDocument.select("DSTCTD>TCTD");
    DSTCTD dstctd = new DSTCTD();
    for (Element tctdElement : tctdElements) {
      final String tenTctd = tctdElement.select("TTChung>TenTCTD").text();
      final LocalDate ngaySL = convertToDate(tctdElement.select("TTChung>NgaySL").text());
      final String maTctd = tctdElement.select("TTChung>MaTCTD").text();
      TTChung ttChung = new TTChung().setTenTCTD(tenTctd).setNgaySL(ngaySL).setMaTCTD(maTctd);

      final Element coTsdbElement = tctdElement.selectFirst("TSBD>CoTSBD");
      TSBD tsbd = new TSBD(coTsdbElement);
      final Elements chiTietDuNoElements = tctdElement.select("ChiTiet>DuNo");
      DuNoNganHan duNoNganHan = new DuNoNganHan(chiTietDuNoElements.select("DuNoNganHan>*"),
          Constants.DU_NO_NGAN_HAN);
      DuNoTrungHan duNoTrungHan = new DuNoTrungHan(chiTietDuNoElements.select("DuNoTrungHan>*"),
          Constants.DU_NO_TRUNG_HAN);
      DuNoDaiHan duNoDaiHan = new DuNoDaiHan(chiTietDuNoElements.select("DuNoDaiHan>*"),
          Constants.DU_NO_DAI_HAN);
      DuNoKhac duNoKhac = new DuNoKhac(chiTietDuNoElements.select("DuNoKhac>*"),
          Constants.DU_NO_KHAC);
      DuNoBoSung duNoBoSung = new DuNoBoSung(chiTietDuNoElements.select("DuNoBoSung>*"),
          Constants.DU_NO_BO_SUNG);
      List<DuNo> duNoList =
          Stream.of(duNoBoSung, duNoDaiHan, duNoKhac, duNoNganHan, duNoTrungHan)
              .filter(duNo -> duNo.sumTotalAmount().compareTo(BigDecimal.ZERO) > 0)
              .collect(Collectors.toList());
      ChiTiet chiTiet = new ChiTiet(duNoList, tsbd);
      TCTD tctd = new TCTD(ttChung, chiTiet);
      dstctd.addTctd(tctd);
    }
    final Elements hdtdElements = f5XmlDocument.select("HDTD>CHITIET");
    for (Element e : hdtdElements) {
      final String tenTctd = e.select("TenTCTD").text();
      final LocalDate ngayky = convertToDate(e.select("NGAYKY").text());
      final LocalDate ngaykt = convertToDate(e.select("NGAYKT").text());
      HDTD hdtd = new HDTD(tenTctd).setNgayKT(ngaykt).setNgayKy(ngayky);
      cicContent.addHdtd(hdtd);
    }
    final Elements duNoTheTDElements = f5XmlDocument.select("DuNoTheTD>DuNoTheTDChiTiet>TCTD");
    for (Element e : duNoTheTDElements) {
      final LocalDate ngaySL = convertToDate(e.select("NgaySL").text());
      final String tenTCTDPhatHanh = e.select("TenTCTDPhatHanh").text();
      final BigDecimal hMTheTD = new BigDecimal(e.select("HMTheTD").text());
      final BigDecimal soTienPhaiThanhToan = new BigDecimal(e.select("SoTienPhaiThanhToan").text());
      final BigDecimal soTienChamThanhToan = new BigDecimal(e.select("SoTienChamThanhToan").text());
      final Integer soNgayChamThanhToan = Integer.valueOf(e.select("SoNgayChamThanhToan").text());
      DuNoTheTD duNoTheTD =
          new DuNoTheTD(
              ngaySL,
              tenTCTDPhatHanh,
              hMTheTD,
              soTienPhaiThanhToan,
              soTienChamThanhToan,
              soNgayChamThanhToan);
      cicContent.addDuNoTheTD(duNoTheTD);
    }
    cicContent.setTtchung(
        new TTChung().setNgaySL(convertToDate(f5XmlDocument.select("CicTemplate>TTChung").text())));
    cicContent.setDstctd(dstctd);
    return cicContent;
  }
}
