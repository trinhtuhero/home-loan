package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RecognitionMethod2Enum {

  NON_CREDIT_TRANSACTION("NON_CREDIT_TRANSACTION", "Giao dịch phi tín dụng"),
  CREDIT_TRANSACTION("CREDIT_TRANSACTION", "Giao dịch tín dụng"),
  CREDIT_CARD_TRANSACTION("CREDIT_CARD_TRANSACTION", "Giao dịch thẻ tín dụng"),
  SAVING("SAVING", "Tiền gửi tiết kiệm"),
  ADVANTAGE_OF_ASSET_MINING("ADVANTAGE_OF_ASSET_MINING", "Lợi thế khai thác tài sản"),
  ACCUMULATED_ASSETS("ACCUMULATED_ASSETS", "Tài sản tích lũy"),
  OWNERSHIP_RESOURCES("OWNERSHIP_RESOURCES", "Nguồn lực sở hữu"),
  RESOURCES_OWNED_AT_STRATEGIC_PARTNER("RESOURCES_OWNED_AT_STRATEGIC_PARTNER",
      "Nguồn lực sở hữu tại các đơn vị đối tác chiến lược"),
  BASED_ON_COST_OF_LIVING("Based on cost of living", "Dựa trên chi phí sinh hoạt");

  private String code;
  private String name;

  RecognitionMethod2Enum(String code, String name) {
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

