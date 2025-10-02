package vn.com.msb.homeloan.core.constant;


public enum CriterionGroup1Enum {
  // Special risk
  SPECIAL_RISK("SPECIAL_RISK", "Rủi ro đặc biệt"),

  // High risk
  HIGH_RISK("HIGH_RISK", "Rủi ro cao"),

  // Important characteristic
  IMPORTANT_CHARACTERISTIC("IMPORTANT_CHARACTERISTIC", "Đặc thù trọng yếu"),

  // Unimportant characteristic
  UNIMPORTANT_CHARACTERISTIC("UNIMPORTANT_CHARACTERISTIC", "Đặc thù không trọng yếu"),

  // Orientation criteria
  ORIENTATION_CRITERIA("ORIENTATION_CRITERIA", "Tiêu chí định hướng"),

  // Difference appraisal instruction
  DIFFERENCE_APPRAISAL_INSTRUCTION("DIFFERENCE_APPRAISAL_INSTRUCTION",
      "Khác biệt hướng dẫn thẩm định"),

  // Exceeding policy
  EXCEEDING_POLICY("EXCEEDING_POLICY", "Vượt chính sách"),

  ;

  private String code;
  private String name;

  CriterionGroup1Enum(String code, String name) {
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
