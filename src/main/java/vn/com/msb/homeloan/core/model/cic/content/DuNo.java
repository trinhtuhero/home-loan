package vn.com.msb.homeloan.core.model.cic.content;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import vn.com.msb.homeloan.core.constant.Constants;

@Getter
@Setter
@Slf4j
public abstract class DuNo {

  protected final Pattern endsWithVndPattern =
      Pattern.compile(".+(VND)$", Pattern.CASE_INSENSITIVE);

  private Elements elements;

  private BigDecimal totalAmount;
  private BigDecimal noXauKhacVND;
  private BigDecimal noXauKhacUSD;

  private LocalDate ngaySL;
  private LocalDate ngayKT;

  private String name;

  protected DuNo(Elements elements, String name) {
    this.elements = elements;
    this.name = name;
  }

  private BigDecimal sumOfElements(
      @NonNull final Elements elements, final Predicate<Element> filterCondition) {
    return elements.stream()
        .filter(filterCondition == null ? e -> true : filterCondition)
        .filter(e -> NumberUtils.isDigits(e.text()))
        .map(e -> new BigDecimal(e.text()))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public BigDecimal sumTotalAmount() {
    totalAmount = sumOfElements(elements, e -> endsWithVndPattern.matcher(e.tagName()).matches());
    totalAmount = totalAmount.multiply(Constants.ONE_MILLION);
    return totalAmount;
  }

  public BigDecimal noXauKhacVND() {
    noXauKhacVND = sumOfElements(elements, e -> "noxaukhacvnd".equals(e.tagName()));
    return noXauKhacVND;
  }

  public BigDecimal noXauKhacUSD() {
    noXauKhacUSD = sumOfElements(elements, e -> "noxaukhacusd".equals(e.tagName()));
    return noXauKhacUSD;
  }

  public abstract boolean isValidHdtd(HDTD hdtd);

  public abstract BigDecimal getDefaultThoiHanVayConLai();

  BigDecimal divide(BigDecimal tuso, BigDecimal mauso) {
    return tuso.divide(mauso, 2, RoundingMode.HALF_UP);
  }
}
