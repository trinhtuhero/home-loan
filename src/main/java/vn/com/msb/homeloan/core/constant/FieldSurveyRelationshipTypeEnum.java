package vn.com.msb.homeloan.core.constant;

public enum FieldSurveyRelationshipTypeEnum {

  ME("ME", "Khách hàng"),

  MARRIED_PERSON("MARRIED_PERSON", "Người hôn phối"),

  PAYER_PERSON("PAYER_PERSON", "Người bảo lãnh trả nợ"),

  PROPERTY_OWNER("PROPERTY_OWNER", "Chủ tài sản"),

  ;

  private String code;
  private String name;

  FieldSurveyRelationshipTypeEnum(String code, String name) {
    this.name = name;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }
}
