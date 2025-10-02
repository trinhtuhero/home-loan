package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum AssetCategoryEnum {

  RE1("RE1", "Chung cư"),
  RE2("RE2", "Đất SXKD trả tiền 1 lần"),
  RE3("RE3", "Đất ở/nông nghiệp (không bao gồm CTXD)"),
  RE4("RE4", "Đất SXKD trả tiền hàng năm"),
  RE5("RE5", "Đất ở và công trình xây dựng trên đất"),
  RE8("RE8", "BĐS hình thành trong tương lai"),
  RE9("RE9", "BĐS khác"),

  // VH2-Xe du lịch
  VH2("VH2", "Xe du lịch"),
  // VH3-Xe tải , xe ben, xe container, romooc
  VH3("VH3", "Xe tải , xe ben, xe container, romooc"),
  // VH4-Xe chuyên dùng
  VH4("VH4", "Xe chuyên dùng"),
  // VH9-Khác (ngoài Phương tiện giao thông đã nêu)
  VH9("VH9", "Khác (ngoài Phương tiện giao thông đã nêu)"),
  // DP1-Do MSB phát hành bằng VND
  DP1("DP1", "Do MSB phát hành bằng VND"),
  // DP3-Thẻ tiết kiệm/ chứng chỉ tiền gửi do TCTD khác phát hành có thời hạn còn lại dưới 1 năm
  DP3("DP3",
      "Thẻ tiết kiệm/ chứng chỉ tiền gửi do TCTD khác phát hành có thời hạn còn lại dưới 1 năm"),
  // DP4-Thẻ tiết kiệm/ chứng chỉ tiền gửi do TCTD khác phát hành có thời hạn còn lại từ 1-5 năm
  DP4("DP4",
      "Thẻ tiết kiệm/ chứng chỉ tiền gửi do TCTD khác phát hành có thời hạn còn lại từ 1-5 năm"),
  // DP5-Thẻ tiết kiệm/ chứng chỉ tiền gửi do TCTD khác phát hành có thời hạn còn lại trên 5 năm
  DP5("DP5",
      "Thẻ tiết kiệm/ chứng chỉ tiền gửi do TCTD khác phát hành có thời hạn còn lại trên 5 năm"),
  // DP9-Các chứng chỉ tiền gửi khác
  DP9("DP9", "Các chứng chỉ tiền gửi khác");

  private String code;
  private String name;

  AssetCategoryEnum(String code, String name) {
    this.name = name;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public static Map<String, String> toMap = Stream.of(values())
      .collect(Collectors.toMap(k -> k.getCode(), v -> v.getCode()));

}
