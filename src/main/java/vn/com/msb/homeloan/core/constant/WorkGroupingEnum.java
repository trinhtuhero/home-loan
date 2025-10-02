package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum WorkGroupingEnum {

  GE1("GE1",
      "Các đơn vị hưởng tương từ Ngân sách Nhà nước cấp Trung ương và Tỉnh/thành phố trực thuộc Trung ương",
      "GE1"),
  GE2("GE2",
      "Các đơn vị hưởng tương từ Ngân sách Nhà nước cấp địa phương (cấp thành phố trực thuộc Tỉnh, cấp Quận/Huyện… trở xuống)",
      "GE2"),
  AA1("AA1",
      "Bao gồm các đơn vị có quy mô hoạt động lớn, các Doanh nghiệp được xếp hạng/bình chọn bởi các đơn vị uy tín, được niêm yết trên các sàn chứng khoán, các đơn vị có dòng tiền/giao dịch lớn qua MSB, các Doanh nghiệp lớn đã được MSB thẩm định phê duyệt cấp tín dụng…",
      "AA1"),
  AA2("AA2",
      "Bao gồm các đơn vị có quy mô hoạt động lớn nhưng chưa đủ tiêu chuẩn phân vào nhóm AA1",
      "AA2"),
  BB1("BB1",
      "Bao gồm các đơn vị có quy mô hoạt động vừa và nhỏ, các đơn vị có dòng tiền/giao dịch qua MSB với quy mô giao dịch nhỏ hơn nhóm AA, các Doanh nghiệp vừa và nhỏ đã được MSB thẩm định phê duyệt cấp tín dụng với xếp hạng tín dụng kém hơn nhóm AA và các Doanh nghiệp siêu nhỏ.",
      "BB1"),
  BB2("BB2", "Các đơn vị tiềm năng có quy mô hoạt động vừa và nhỏ, hoặc siêu nhỏ", "BB2"),
  C_PLUS("C_PLUS",
      "Gồm tất cả các đơn vị còn lại, không thỏa tiêu chí để xác định phân nhóm, hoặc thỏa các tiêu chí phân nhóm nhưng không thỏa CIC",
      "C+");

  private String code;
  private String name;
  private String tt;

  WorkGroupingEnum(String code, String name, String tt) {
    this.name = name;
    this.code = code;
    this.tt = tt;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public String getTt() {
    return tt;
  }

  public static Map<String, String> toMap = Stream.of(values())
      .collect(Collectors.toMap(k -> k.getCode(), v -> v.getCode()));
}

