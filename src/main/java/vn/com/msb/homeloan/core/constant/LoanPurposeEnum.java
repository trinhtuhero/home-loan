package vn.com.msb.homeloan.core.constant;

public enum LoanPurposeEnum {

  LAND("LAND", "Vay mua bất động sản"),
  TIEU_DUNG("TIEU_DUNG", "Vay tiêu dùng"),
  XAY_SUA_NHA("XAY_SUA_NHA", "Vay xây dựng, sửa chữa nhà"),
  HO_KINH_DOANH("HO_KINH_DOANH", "Vay hộ kinh doanh"),
  CREDIT_CARD("CREDIT_CARD", "Thẻ tín dụng"),

  THAU_CHI("THAU_CHI", "Vay thấu chi");

  private String code;
  private String name;

  LoanPurposeEnum(String code, String name) {
    this.name = name;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public static LoanPurposeEnum asEnum(String str) {
    for (LoanPurposeEnum me : LoanPurposeEnum.values()) {
      if (me.getCode().equalsIgnoreCase(str))
        return me;
    }
    return null;
  }
}
