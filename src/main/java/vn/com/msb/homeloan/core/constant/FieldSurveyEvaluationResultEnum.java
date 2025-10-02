package vn.com.msb.homeloan.core.constant;

public enum FieldSurveyEvaluationResultEnum {

  PASS("PASS", "Phù hợp với thông tin kê khai"),

  NOT_PASS("NOT_PASS", "Không đủ thông tin xác minh");

  private String code;
  private String name;

  FieldSurveyEvaluationResultEnum(String code, String name) {
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
