package vn.com.msb.homeloan.core.constant;

public enum FormOfCreditEnum {

  // car loan
  // mortgage
  // signature loan
  // and line of credit.

  // Vay có TSBĐ
  SECURED_MORTGAGE("SECURED_MORTGAGE", "Vay có TSBĐ"),

  // Vay không TSBĐ
  UNSECURED_MORTGAGE("UNSECURED_MORTGAGE", "Vay không TSBĐ"),

  // Thấu chi có TSBĐ
  SECURED_OVERDRAFT("SECURED_OVERDRAFT", "Thấu chi có TSBĐ"),

  // Thấu chi không TSBĐ
  UNSECURED_OVERDRAFT("UNSECURED_OVERDRAFT", "Thấu chi không TSBĐ"),

  // Thẻ tín dụng
  CREDIT_CARD("CREDIT_CARD", "Thẻ tín dụng"),

  // Hạn mức tín dụng
  LINE_OF_CREDIT("LINE_OF_CREDIT", "Hạn mức tín dụng"),

  ;

  private String code;
  private String name;

  FormOfCreditEnum(String code, String name) {
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
