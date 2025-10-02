package vn.com.msb.homeloan.core.constant;

import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum CICRelationshipTypeEnum {
  CUSTOMER("CUSTOMER", "Khách hàng","006-001-001"),
  WIFE_HUSBAND("WIFE_HUSBAND", "Người hôn phối","006-001-002"),
  LOAN_PAYER("LOAN_PAYER", "Người bảo lãnh trả nợ","006-001-003"),
  COLLATERAL_OWNER("COLLATERAL_OWNER", "Chủ tài sản","006-001-005"),
  ENTERPRISE("ENTERPRISE", "Doanh nghiệp","006-001-004"),
  WITH_REGISTRATION("WITH_REGISTRATION", "Hộ kinh doanh có ĐKKD","006-001-004");

  private String code;
  private String name;
  private String cicCode;

  CICRelationshipTypeEnum(String code, String name,String cicCode) {
    this.name = name;
    this.code = code;
    this.cicCode=cicCode;
  }

  public static Map<String, String> toMap = Stream.of(values())
      .collect(Collectors.toMap(k -> k.getCode(), v -> v.getCode()));
}
