package vn.com.msb.homeloan.core.model.parser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.constant.cic.CicGroupEnum;
import vn.com.msb.homeloan.core.constant.cic.CicLoanTypeEnum;
import vn.com.msb.homeloan.core.helper.CalcCicHelper;
import vn.com.msb.homeloan.core.model.cic.CicDebtReport;
import vn.com.msb.homeloan.core.model.cic.content.CicContent;
import vn.com.msb.homeloan.core.model.cic.content.DuNo;
import vn.com.msb.homeloan.core.model.cic.content.TCTD;

/**
 * CIC content parser
 * <p>
 * It parse CIC content (XML) to necessary information.
 */
@Slf4j
public class XmlCicParser implements CicParser {

  private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  private final Document f5XmlDocument;
  private LocalDate responseTime;

  private XmlCicParser(final String f5XmlContent) {
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

  public static XmlCicParser of(@NonNull final String f5XmlContent) {
    return new XmlCicParser(f5XmlContent);
  }

  public static XmlCicParser ofNullable(final String f5XmlContent) {
    return new XmlCicParser(f5XmlContent);
  }

  @Override
  public CicGroupEnum findCurrentDebtGroupNaturalPerson() {
    if (this.f5XmlDocument == null) {
      return CicGroupEnum.defaultGroup();
    }
    final Elements elements = f5XmlDocument.select("DuNo>*>*");
    final InternalCicGroupNaturalPerson currentGroup = elements.stream()
        .filter(e -> StringUtils.isNotEmpty(e.text())
            && NumberUtils.isDigits(e.text()) && NumberUtils.toInt(e.text()) > 0)
        .map(e -> InternalCicGroupNaturalPerson.ofXml(e.tagName()))
        .filter(Objects::nonNull)
        .max(Comparator.comparingInt(InternalCicGroupNaturalPerson::getValue))
        .orElse(InternalCicGroupNaturalPerson.NHOM1_VND);
    final InternalCicGroupNaturalPerson latePaymentGroup = InternalCicGroupNaturalPerson.ofLatePaymentDays(
        timSoNgayChamThanhToan());
    final InternalCicGroupNaturalPerson finalGroup =
        (latePaymentGroup.getValue() > currentGroup.getValue()) ? latePaymentGroup : currentGroup;
    return CicGroupEnum.of(finalGroup.getValue());
  }

  @Override
  public CicGroupEnum findCurrentDebtGroupLegalPerson() {
    if (this.f5XmlDocument == null) {
      return CicGroupEnum.defaultGroup();
    }
    final Elements elements = f5XmlDocument.select("DuNo>*>*");
    final InternalCicGroupLegalPerson currentGroup = elements.stream()
        .filter(e -> StringUtils.isNotEmpty(e.text())
            && NumberUtils.isDigits(e.text()) && NumberUtils.toInt(e.text()) > 0)
        .map(e -> InternalCicGroupLegalPerson.ofXml(e.tagName()))
        .filter(Objects::nonNull)
        .max(Comparator.comparingInt(InternalCicGroupLegalPerson::getValue))
        .orElse(InternalCicGroupLegalPerson.NHOM1_VND);
    final InternalCicGroupLegalPerson latePaymentGroup = InternalCicGroupLegalPerson.ofLatePaymentDays(
        timSoNgayChamThanhToan());
    final InternalCicGroupLegalPerson finalGroup =
        (latePaymentGroup.getValue() > currentGroup.getValue()) ? latePaymentGroup : currentGroup;
    return CicGroupEnum.of(finalGroup.getValue());
  }

  @Override
  public CicGroupEnum findLastXMonthsDebtGroupNaturalPerson(final int months, String xmlContent) {
    if (this.f5XmlDocument == null) {
      return CicGroupEnum.defaultGroup();
    }
    Elements elements = f5XmlDocument.select("NoCanChuYTrong12Thang>NoCanChuYChiTiet>*");
    final InternalCicGroupNaturalPerson last12Months = elements.stream()
        .map(e -> timNhomNoCanChuYChiTietNaturalPerson(e))
        .max(Comparator.comparingInt(InternalCicGroupNaturalPerson::getValue))
        .orElse(InternalCicGroupNaturalPerson.NHOM1_VND);

    elements = f5XmlDocument.select("NoXau36Thang>NoXauChiTiet>TCTD");
    final InternalCicGroupNaturalPerson last60Months = elements.stream()
        .map(e -> timNhomNoXThangNaturalPerson(e, months, xmlContent))
        .max(Comparator.comparingInt(InternalCicGroupNaturalPerson::getValue))
        .orElse(InternalCicGroupNaturalPerson.NHOM1_VND);

    elements = f5XmlDocument.select("LichSuChamTTTheTD36Thang>TCTD");
    final InternalCicGroupNaturalPerson chamTTThe = elements.stream()
        .map(e -> timNhomNoChamThanhToanXThangNaturalPerson(e, months))
        .max(Comparator.comparingInt(InternalCicGroupNaturalPerson::getValue))
        .orElse(InternalCicGroupNaturalPerson.NHOM1_VND);

    final InternalCicGroupNaturalPerson finalGroup = InternalCicGroupNaturalPerson.max(last12Months,
        last60Months, chamTTThe);
    return CicGroupEnum.of(finalGroup.getValue());
  }

  @Override
  public CicGroupEnum findLastXMonthsDebtGroupLegalPerson(int months, String xmlContent) {
    if (this.f5XmlDocument == null) {
      return CicGroupEnum.defaultGroup();
    }
    Elements elements = f5XmlDocument.select("NoCanChuYTrong12Thang>NoCanChuYChiTiet>*");
    final InternalCicGroupLegalPerson last12Months = elements.stream()
        .map(e -> timNhomNoCanChuYChiTietLegalPerson(e))
        .max(Comparator.comparingInt(InternalCicGroupLegalPerson::getValue))
        .orElse(InternalCicGroupLegalPerson.NHOM1_VND);

    elements = f5XmlDocument.select("NoXau36Thang>NoXauChiTiet>TCTD");
    final InternalCicGroupLegalPerson last60Months = elements.stream()
        .map(e -> timNhomNoXThangLegalPerson(e, months, xmlContent))
        .max(Comparator.comparingInt(InternalCicGroupLegalPerson::getValue))
        .orElse(InternalCicGroupLegalPerson.NHOM1_VND);

    elements = f5XmlDocument.select("LichSuChamTTTheTD36Thang>TCTD");
    final InternalCicGroupLegalPerson chamTTThe = elements.stream()
        .map(e -> timNhomNoChamThanhToanXThangLegalPerson(e, months))
        .max(Comparator.comparingInt(InternalCicGroupLegalPerson::getValue))
        .orElse(InternalCicGroupLegalPerson.NHOM1_VND);

    final InternalCicGroupLegalPerson finalGroup = InternalCicGroupLegalPerson.max(last12Months,
        last60Months, chamTTThe);
    return CicGroupEnum.of(finalGroup.getValue());
  }

  @Override
  public CicDebtReport findCurrentTotalDebtReport() {
    if (this.f5XmlDocument == null) {
      return CicDebtReport.builder().ngayCicTraKq(responseTime).build();
    }

    final CicDebtReport cicDebtReport = CicDebtReport.builder().ngayCicTraKq(responseTime).build();

    final Elements tctdElements = f5XmlDocument.select("DSTCTD>TCTD");
    final Elements theTdElements = f5XmlDocument.select("DuNoTheTD>DuNoTheTDChiTiet>TCTD>HMTheTD");

    final int tongHanmuc = sumOfElements(theTdElements, null);
    cicDebtReport.setTongHanMucTheTd(tongHanmuc);

    final List<CicDebtReport.ThongTinTctd> listTctd = new ArrayList<>();
    for (Element tctdElement : tctdElements) {
      final Elements chiTietDuNoElements = tctdElement.select("ChiTiet>DuNo");
      if (chiTietDuNoElements.size() > 0) {
        final String tenTctd = tctdElement.select("TTChung>TenTCTD").text();
        if (StringUtils.isNotEmpty(tenTctd)) {
          final CicDebtReport.ThongTinTctd report = new CicDebtReport.ThongTinTctd();

          final Element coTsdbElement = tctdElement.selectFirst("TSBD>CoTSBD");
          final boolean coTsdb =
              (coTsdbElement != null && StringUtils.isNotEmpty(coTsdbElement.text()))
                  && BooleanUtils.toBoolean(coTsdbElement.text());

          //VND
          final Pattern endsWithVndPattern = Pattern.compile(".+(VND)$", Pattern.CASE_INSENSITIVE);

          final Elements duNoNganHanVndElements = chiTietDuNoElements.select("DuNoNganHan>*");
          final int tongNganHanVnd = sumOfElements(duNoNganHanVndElements,
              e -> endsWithVndPattern.matcher(e.tagName()).matches());

          final Elements duNoTrungHanVndElements = chiTietDuNoElements.select("DuNoTrungHan>*");
          final int tongTrungHanVnd = sumOfElements(duNoTrungHanVndElements,
              e -> endsWithVndPattern.matcher(e.tagName()).matches());

          final Elements duNoDaiHanVndElements = chiTietDuNoElements.select("DuNoDaiHan>*");
          final int tongDaiHanVnd = sumOfElements(duNoDaiHanVndElements,
              e -> endsWithVndPattern.matcher(e.tagName()).matches());

          final Elements duNoKhacVndElements = chiTietDuNoElements.select("DuNoKhac>*");
          final int tongKhacVnd = sumOfElements(duNoKhacVndElements,
              e -> endsWithVndPattern.matcher(e.tagName()).matches());

          final Elements duNoBoSungVndElements = chiTietDuNoElements.select("DuNoBoSung>*");
          final int tongBoSungVnd = sumOfElements(duNoBoSungVndElements,
              e -> endsWithVndPattern.matcher(e.tagName()).matches());

          //USD
          final Pattern endsWithUsdPattern = Pattern.compile(".+(USD)$", Pattern.CASE_INSENSITIVE);

          final Elements duNoNganHanUsdElements = chiTietDuNoElements.select("DuNoNganHan>*");
          final int tongNganHanUsd = sumOfElements(duNoNganHanUsdElements,
              e -> endsWithUsdPattern.matcher(e.tagName()).matches());

          final Elements duNoTrungHanUsdElements = chiTietDuNoElements.select("DuNoTrungHan>*");
          final int tongTrungHanUsd = sumOfElements(duNoTrungHanUsdElements,
              e -> endsWithUsdPattern.matcher(e.tagName()).matches());

          final Elements duNoDaiHanUsdElements = chiTietDuNoElements.select("DuNoDaiHan>*");
          final int tongDaiHanUsd = sumOfElements(duNoDaiHanUsdElements,
              e -> endsWithUsdPattern.matcher(e.tagName()).matches());

          final Elements duNoKhacUsdElements = chiTietDuNoElements.select("DuNoKhac>*");
          final int tongKhacUsd = sumOfElements(duNoKhacUsdElements,
              e -> endsWithUsdPattern.matcher(e.tagName()).matches());

          final Elements duNoBoSungUsdElements = chiTietDuNoElements.select("DuNoBoSung>*");
          final int tongBoSungUsd = sumOfElements(duNoBoSungUsdElements,
              e -> endsWithUsdPattern.matcher(e.tagName()).matches());

          final Map<CicLoanTypeEnum, Integer> listLoan = new HashMap<>();
          listLoan.put(CicLoanTypeEnum.NGAN_HAN_VND, tongNganHanVnd);
          listLoan.put(CicLoanTypeEnum.TRUNG_HAN_VND, tongTrungHanVnd);
          listLoan.put(CicLoanTypeEnum.DAI_HAN_VND, tongDaiHanVnd);
          listLoan.put(CicLoanTypeEnum.KHAC_VND, tongKhacVnd);
          listLoan.put(CicLoanTypeEnum.BO_SUNG_VND, tongBoSungVnd);
          listLoan.put(CicLoanTypeEnum.NGAN_HAN_USD, tongNganHanUsd);
          listLoan.put(CicLoanTypeEnum.TRUNG_HAN_USD, tongTrungHanUsd);
          listLoan.put(CicLoanTypeEnum.DAI_HAN_USD, tongDaiHanUsd);
          listLoan.put(CicLoanTypeEnum.KHAC_USD, tongKhacUsd);
          listLoan.put(CicLoanTypeEnum.BO_SUNG_USD, tongBoSungUsd);

          final Elements ngayKtHdtdElements = f5XmlDocument.select("HDTD>CHITIET");
          final List<LocalDate> listNgayKtHdtd = ngayKtHdtdElements.stream()
              .filter(e -> {
                final Elements tctd = e.getElementsByTag("TenTCTD");
                return tenTctd.equalsIgnoreCase(tctd.text());
              })
              .map(e -> e.getElementsByTag("NGAYKT"))
              .filter(e -> StringUtils.isNotEmpty(e.text()))
              .map(e -> LocalDate.from(dateFormatter.parse(e.text())))
              .collect(Collectors.toList());

          report.setTenTctd(tenTctd);
          report.setCoTsdb(coTsdb);
          report.setThongTinDuNo(listLoan);
          report.setNgayKtHdtd(listNgayKtHdtd);
          listTctd.add(report);
        } else {
          log.warn("TenTCTD is empty.");
        }
      }
    }
    cicDebtReport.setThongTinTctds(listTctd);
    return cicDebtReport;
  }

  private int sumOfElements(@NonNull final Elements elements,
      final Predicate<Element> filterCondition) {
    return elements.stream()
        .filter(filterCondition == null ? e -> true : filterCondition)
        .filter(e -> NumberUtils.isDigits(e.text()))
        .mapToInt(e -> NumberUtils.toInt(e.text()))
        .sum();
  }

  private InternalCicGroupNaturalPerson timNhomNoXThangNaturalPerson(final @NonNull Element tctd,
      final int months, String xmlContent) {
    InternalCicGroupNaturalPerson result = InternalCicGroupNaturalPerson.NHOM1_VND;
    final LocalDate lastMonths = this.responseTime.minusMonths(months);
    final Element ngayPsNoXauCuoiCung = tctd.getElementsByTag("NgayPhatSinhNoXauCuoiCung").first();
    if (ngayPsNoXauCuoiCung != null && ngayPsNoXauCuoiCung.hasText()) {
      final boolean isInRange = LocalDate.from(dateFormatter.parse(ngayPsNoXauCuoiCung.text()))
          .isAfter(lastMonths);
      if (isInRange) {
        final Element nhomNoCaoNhat = tctd.getElementsByTag("NhomNoCaoNhat").first();
        final Element nhomNo = tctd.getElementsByTag("NhomNo").first();
        if (nhomNo != null && nhomNo.hasText() && nhomNoCaoNhat != null
            && nhomNoCaoNhat.hasText()) {
          final String nhomNoText = nhomNo.text();
          if (NumberUtils.isDigits(nhomNoText)) {
            result = InternalCicGroupNaturalPerson.of(NumberUtils.toInt(nhomNoText));
          }
        } else if ((nhomNo == null || !nhomNo.hasText()) && nhomNoCaoNhat != null
            && nhomNoCaoNhat.hasText()) {
          final Element tenTCTD = tctd.getElementsByTag("TenTCTD").first();
          if (tenTCTD != null && tenTCTD.hasText()) {
            CicContent cicContent = CalcCicHelper.getCicContent(xmlContent);
            List<TCTD> tctdList = cicContent.getDstctd().getTctdList();
            List<TCTD> tctdMatch = tctdList.stream()
                .filter(c -> c.getTtChung().getTenTCTD().equals(tenTCTD.text()))
                .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(tctdMatch)) {
              final String nhomNoCaoNhatText = nhomNoCaoNhat.text();
              return InternalCicGroupNaturalPerson.of(NumberUtils.toInt(nhomNoCaoNhatText));
            }
            List<DuNo> duNoList = tctdMatch.get(0).getChiTiet().getDuNoList();
            List<DuNo> duNoListMatch = duNoList.stream()
                .filter(dn -> dn.getName().equals(Constants.DU_NO_BO_SUNG))
                .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(duNoListMatch)) {
              final String nhomNoCaoNhatText = nhomNoCaoNhat.text();
              result = InternalCicGroupNaturalPerson.of(NumberUtils.toInt(nhomNoCaoNhatText));
              return result;
            }
            final Element soTienVND = tctd.getElementsByTag("SoTienVND").first();
            if (soTienVND != null && soTienVND.hasText()) {
              if (duNoListMatch.get(0).noXauKhacVND().equals(new BigDecimal(soTienVND.text()))) {
                result = InternalCicGroupNaturalPerson.NHOM5_VND;
              } else {
                final String nhomNoCaoNhatText = nhomNoCaoNhat.text();
                result = InternalCicGroupNaturalPerson.of(NumberUtils.toInt(nhomNoCaoNhatText));
              }
            }
          }
        }
      }
    }
    return result;
  }

  private InternalCicGroupNaturalPerson timNhomNoCanChuYChiTietNaturalPerson(
      final @NonNull Element NoCanChuYChiTiet) {
    InternalCicGroupNaturalPerson result = InternalCicGroupNaturalPerson.NHOM1_VND;
    final Element ngayPsNoXauCuoiCung = NoCanChuYChiTiet.getElementsByTag("TCTD").first();
    String t1vnd = ngayPsNoXauCuoiCung.getElementsByTag("t1vnd").text();
    String t2vnd = ngayPsNoXauCuoiCung.getElementsByTag("t2vnd").text();
    String t3vnd = ngayPsNoXauCuoiCung.getElementsByTag("t3vnd").text();
    String t4vnd = ngayPsNoXauCuoiCung.getElementsByTag("t4vnd").text();
    String t5vnd = ngayPsNoXauCuoiCung.getElementsByTag("t5vnd").text();
    String t6vnd = ngayPsNoXauCuoiCung.getElementsByTag("t6vnd").text();
    String t7vnd = ngayPsNoXauCuoiCung.getElementsByTag("t7vnd").text();
    String t8vnd = ngayPsNoXauCuoiCung.getElementsByTag("t8vnd").text();
    String t9vnd = ngayPsNoXauCuoiCung.getElementsByTag("t9vnd").text();
    String t10vnd = ngayPsNoXauCuoiCung.getElementsByTag("t10vnd").text();
    String t11vnd = ngayPsNoXauCuoiCung.getElementsByTag("t11vnd").text();
    String t12vnd = ngayPsNoXauCuoiCung.getElementsByTag("t12vnd").text();
    if (checkTxvnd(t1vnd) || checkTxvnd(t2vnd) || checkTxvnd(t3vnd) || checkTxvnd(t4vnd)
        || checkTxvnd(t5vnd) || checkTxvnd(t6vnd) || checkTxvnd(t7vnd) ||
        checkTxvnd(t8vnd) || checkTxvnd(t9vnd) || checkTxvnd(t10vnd) || checkTxvnd(t11vnd)
        || checkTxvnd(t12vnd)) {
      result = InternalCicGroupNaturalPerson.NHOM2_VND;
    }
    return result;
  }

  private InternalCicGroupLegalPerson timNhomNoCanChuYChiTietLegalPerson(
      final @NonNull Element NoCanChuYChiTiet) {
    InternalCicGroupLegalPerson result = InternalCicGroupLegalPerson.NHOM1_VND;
    final Element ngayPsNoXauCuoiCung = NoCanChuYChiTiet.getElementsByTag("TCTD").first();
    String t1vnd = ngayPsNoXauCuoiCung.getElementsByTag("t1vnd").text();
    String t2vnd = ngayPsNoXauCuoiCung.getElementsByTag("t2vnd").text();
    String t3vnd = ngayPsNoXauCuoiCung.getElementsByTag("t3vnd").text();
    String t4vnd = ngayPsNoXauCuoiCung.getElementsByTag("t4vnd").text();
    String t5vnd = ngayPsNoXauCuoiCung.getElementsByTag("t5vnd").text();
    String t6vnd = ngayPsNoXauCuoiCung.getElementsByTag("t6vnd").text();
    String t7vnd = ngayPsNoXauCuoiCung.getElementsByTag("t7vnd").text();
    String t8vnd = ngayPsNoXauCuoiCung.getElementsByTag("t8vnd").text();
    String t9vnd = ngayPsNoXauCuoiCung.getElementsByTag("t9vnd").text();
    String t10vnd = ngayPsNoXauCuoiCung.getElementsByTag("t10vnd").text();
    String t11vnd = ngayPsNoXauCuoiCung.getElementsByTag("t11vnd").text();
    String t12vnd = ngayPsNoXauCuoiCung.getElementsByTag("t12vnd").text();
    if (checkTxvnd(t1vnd) || checkTxvnd(t2vnd) || checkTxvnd(t3vnd) || checkTxvnd(t4vnd)
        || checkTxvnd(t5vnd) || checkTxvnd(t6vnd) || checkTxvnd(t7vnd) ||
        checkTxvnd(t8vnd) || checkTxvnd(t9vnd) || checkTxvnd(t10vnd) || checkTxvnd(t11vnd)
        || checkTxvnd(t12vnd)) {
      result = InternalCicGroupLegalPerson.NHOM2_VND;
    }
    return result;
  }

  public static boolean checkTxvnd(String str) {
    if (str != null && !str.trim().isEmpty() && !str.trim().equals("0")) {
      return true;
    } else {
      return false;
    }
  }

  private InternalCicGroupLegalPerson timNhomNoXThangLegalPerson(final @NonNull Element tctd,
      final int months, String xmlContent) {
    InternalCicGroupLegalPerson result = InternalCicGroupLegalPerson.NHOM1_VND;
    final LocalDate lastMonths = this.responseTime.minusMonths(months);
    final Element ngayPsNoXauCuoiCung = tctd.getElementsByTag("NgayPhatSinhNoXauCuoiCung").first();
    if (ngayPsNoXauCuoiCung != null && ngayPsNoXauCuoiCung.hasText()) {
      final boolean isInRange = LocalDate.from(dateFormatter.parse(ngayPsNoXauCuoiCung.text()))
          .isAfter(lastMonths);
      if (isInRange) {
        final Element nhomNoCaoNhat = tctd.getElementsByTag("NhomNoCaoNhat").first();
        final Element nhomNo = tctd.getElementsByTag("NhomNo").first();
        if (nhomNo != null && nhomNo.hasText() && nhomNoCaoNhat != null
            && nhomNoCaoNhat.hasText()) {
          final String nhomNoText = nhomNo.text();
          if (NumberUtils.isDigits(nhomNoText)) {
            result = InternalCicGroupLegalPerson.of(NumberUtils.toInt(nhomNoText));
          }
        } else if ((nhomNo == null || !nhomNo.hasText()) && nhomNoCaoNhat != null
            && nhomNoCaoNhat.hasText()) {
          final Element tenTCTD = tctd.getElementsByTag("TenTCTD").first();
          if (tenTCTD != null && tenTCTD.hasText()) {
            CicContent cicContent = CalcCicHelper.getCicContent(xmlContent);
            List<TCTD> tctdList = cicContent.getDstctd().getTctdList();
            List<TCTD> tctdMatch = tctdList.stream()
                .filter(c -> c.getTtChung().getTenTCTD().equals(tenTCTD.text()))
                .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(tctdMatch)) {
              final String nhomNoCaoNhatText = nhomNoCaoNhat.text();
              return InternalCicGroupLegalPerson.of(NumberUtils.toInt(nhomNoCaoNhatText));
            }
            List<DuNo> duNoList = tctdMatch.get(0).getChiTiet().getDuNoList();
            List<DuNo> duNoListMatch = duNoList.stream()
                .filter(dn -> dn.getName().equals(Constants.DU_NO_BO_SUNG))
                .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(duNoListMatch)) {
              final String nhomNoCaoNhatText = nhomNoCaoNhat.text();
              result = InternalCicGroupLegalPerson.of(NumberUtils.toInt(nhomNoCaoNhatText));
              return result;
            }
            final Element soTienVND = tctd.getElementsByTag("SoTienVND").first();
            final Element soTienUSD = tctd.getElementsByTag("SoTienUSD").first();
            if (soTienVND != null && soTienVND.hasText() && soTienUSD != null
                && soTienUSD.hasText()) {
              if (duNoListMatch.get(0).noXauKhacVND().equals(new BigDecimal(soTienVND.text())) ||
                  duNoListMatch.get(0).noXauKhacUSD().equals(new BigDecimal(soTienUSD.text()))) {
                result = InternalCicGroupLegalPerson.NHOM5_VND;
              } else {
                final String nhomNoCaoNhatText = nhomNoCaoNhat.text();
                result = InternalCicGroupLegalPerson.of(NumberUtils.toInt(nhomNoCaoNhatText));
              }
              return result;
            }
          }
        }
      }
    }
    return result;
  }

  private int timSoNgayChamThanhToan() {
    final Elements elements = f5XmlDocument.select("DuNoTheTDChiTiet>TCTD>SoNgayChamThanhToan");
    return elements.stream()
        .filter(e -> NumberUtils.isDigits(e.text()))
        .mapToInt(e -> NumberUtils.toInt(e.text()))
        .max().orElse(0);
  }

  private InternalCicGroupNaturalPerson timNhomNoChamThanhToanXThangNaturalPerson(
      @NonNull final Element tctd, final int months) {
    final LocalDate lastMonths = this.responseTime.minusMonths(months);
    final Elements dsChamTheTD = tctd.select("DSChamTTTheTD>ChamTTTheTD");
    final int soNgayCaoNhat = dsChamTheTD.stream()
        .filter(e -> {
          final Element ngayChamTT = e.getElementsByTag("NgayChamTT").first();
          try {
            return ngayChamTT != null && StringUtils.isNotEmpty(ngayChamTT.text())
                && LocalDate.from(dateFormatter.parse(ngayChamTT.text())).isAfter(lastMonths);
          } catch (DateTimeParseException ex) {
            log.warn("Cannot parse this date tag: {}", ngayChamTT.text());
          }
          return false;
        })
        .map(e -> e.getElementsByTag("SoNgayChamTT").first())
        .filter(Objects::nonNull)
        .filter(e -> NumberUtils.isDigits(e.text()))
        .mapToInt(e -> NumberUtils.toInt(e.text()))
        .max().orElse(0);

    return InternalCicGroupNaturalPerson.ofLatePaymentDays(soNgayCaoNhat);
  }

  private InternalCicGroupLegalPerson timNhomNoChamThanhToanXThangLegalPerson(
      @NonNull final Element tctd, final int months) {
    final LocalDate lastMonths = this.responseTime.minusMonths(months);
    final Elements dsChamTheTD = tctd.select("DSChamTTTheTD>ChamTTTheTD");
    final int soNgayCaoNhat = dsChamTheTD.stream()
        .filter(e -> {
          final Element ngayChamTT = e.getElementsByTag("NgayChamTT").first();
          try {
            return ngayChamTT != null && StringUtils.isNotEmpty(ngayChamTT.text())
                && LocalDate.from(dateFormatter.parse(ngayChamTT.text())).isAfter(lastMonths);
          } catch (DateTimeParseException ex) {
            log.warn("Cannot parse this date tag: {}", ngayChamTT.text());
          }
          return false;
        })
        .map(e -> e.getElementsByTag("SoNgayChamTT").first())
        .filter(Objects::nonNull)
        .filter(e -> NumberUtils.isDigits(e.text()))
        .mapToInt(e -> NumberUtils.toInt(e.text()))
        .max().orElse(0);

    return InternalCicGroupLegalPerson.ofLatePaymentDays(soNgayCaoNhat);
  }

  @RequiredArgsConstructor
  @Getter
  private enum InternalCicGroupNaturalPerson {
    NHOM1_VND(1, "Nhóm 1 (Đồng)", "NoDuTieuChuanVND"),
    NHOM2_VND(2, "Nhóm 2 (Đồng)", "NoCanChuYVND"),
    NHOM3_VND(3, "Nhóm 3 (Đồng)", "NoDuoiTieuChuanVND"),
    NHOM4_VND(4, "Nhóm 4 (Đồng)", "NoNghiNgoVND"),
    NHOM5_VND(5, "Nhóm 5 (Đồng)", "NoCoKhaNangMatVonVND"),
    NHOM5_KHAC_VND(5, "Nhóm 5 Khac (Đồng)", "NoXauKhacVND");

    private final int value;
    private final String name;
    private final String xmlElement;

    public static InternalCicGroupNaturalPerson max(
        @NonNull final InternalCicGroupNaturalPerson... list) {
      return Stream.of(list).max(Comparator.comparingInt(InternalCicGroupNaturalPerson::getValue))
          .orElse(InternalCicGroupNaturalPerson.NHOM1_VND);
    }

    public static InternalCicGroupNaturalPerson of(final int value) {
      return Stream.of(InternalCicGroupNaturalPerson.values())
          .filter(group -> group.value == value)
          .findFirst().orElse(null);
    }

    public static InternalCicGroupNaturalPerson ofXml(@NonNull final String xmlElement) {
      return Stream.of(InternalCicGroupNaturalPerson.values())
          .filter(group -> group.xmlElement.equalsIgnoreCase(xmlElement))
          .findFirst().orElse(null);
    }

    public static InternalCicGroupNaturalPerson ofLatePaymentDays(final int days) {
      InternalCicGroupNaturalPerson result = NHOM5_VND;
      if (days < 10) {
        result = NHOM1_VND;
      } else if (days <= 90) {
        result = NHOM2_VND;
      } else if (days <= 180) {
        result = NHOM3_VND;
      } else if (days <= 360) {
        result = NHOM4_VND;
      }
      return result;
    }
  }

  @RequiredArgsConstructor
  @Getter
  private enum InternalCicGroupLegalPerson {
    NHOM1_VND(1, "Nhóm 1 (Đồng)", "NoDuTieuChuanVND"),
    NHOM2_VND(2, "Nhóm 2 (Đồng)", "NoCanChuYVND"),
    NHOM3_VND(3, "Nhóm 3 (Đồng)", "NoDuoiTieuChuanVND"),
    NHOM4_VND(4, "Nhóm 4 (Đồng)", "NoNghiNgoVND"),
    NHOM5_VND(5, "Nhóm 5 (Đồng)", "NoCoKhaNangMatVonVND"),
    NHOM5_KHAC_VND(5, "Nhóm 5 Khac (Đồng)", "NoXauKhacVND"),
    //        NHOM1_USD(1, "Nhóm 1 (USD)", "NoDuTieuChuanUSD"),
//        NHOM2_USD(2, "Nhóm 2 (USD)", "NoCanChuYUSD"),
//        NHOM3_USD(3, "Nhóm 3 (USD)", "NoDuoiTieuChuanUSD"),
//        NHOM4_USD(4, "Nhóm 4 (USD)", "NoNghiNgoUSD"),
//        NHOM5_USD(5, "Nhóm 5 (USD)", "NoCoKhaNangMatVonUSD"),
    NHOM5_KHAC_USD(5, "Nhóm 5 Khac (USD)", "NoXauKhacUSD");

    private final int value;
    private final String name;
    private final String xmlElement;

    public static InternalCicGroupLegalPerson max(
        @NonNull final InternalCicGroupLegalPerson... list) {
      return Stream.of(list).max(Comparator.comparingInt(InternalCicGroupLegalPerson::getValue))
          .orElse(InternalCicGroupLegalPerson.NHOM1_VND);
    }

    public static InternalCicGroupLegalPerson of(final int value) {
      return Stream.of(InternalCicGroupLegalPerson.values())
          .filter(group -> group.value == value)
          .findFirst().orElse(null);
    }

    public static InternalCicGroupLegalPerson ofXml(@NonNull final String xmlElement) {
      return Stream.of(InternalCicGroupLegalPerson.values())
          .filter(group -> group.xmlElement.equalsIgnoreCase(xmlElement))
          .findFirst().orElse(null);
    }

    public static InternalCicGroupLegalPerson ofLatePaymentDays(final int days) {
      InternalCicGroupLegalPerson result = NHOM5_VND;
      if (days < 10) {
        result = NHOM1_VND;
      } else if (days <= 90) {
        result = NHOM2_VND;
      } else if (days <= 180) {
        result = NHOM3_VND;
      } else if (days <= 360) {
        result = NHOM4_VND;
      }
      return result;
    }
  }
}
