package vn.com.msb.homeloan.core.constant;

public enum CreditEvaluationResultEnum {

  PASS("PASS", "Thỏa mãn điều kiện"),

  NOT_PASS("NOT_PASS", "Không thỏa mãn điều kiện");

  private String code;
  private String name;

  CreditEvaluationResultEnum(String code, String name) {
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
