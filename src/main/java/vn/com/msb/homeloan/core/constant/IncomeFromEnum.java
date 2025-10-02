package vn.com.msb.homeloan.core.constant;

public enum IncomeFromEnum {

  SAVING_ACCOUNTS("SAVING_ACCOUNTS", "Lãi gửi tiết kiệm"),
  RENTAL_PROPERTIES("RENTAL_PROPERTIES", "Cho thuê tài sản (Ô tô, BĐS)"),
  GOM_XAY("GOM_XAY", "Giao dịch mua bán bất động sản (giao dịch gom xây)"),
  OTHERS("OTHERS", "Khác");

  private String code;
  private String name;

  IncomeFromEnum(String code, String name) {
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
