package vn.com.msb.homeloan.core.constant;

import lombok.Getter;

@Getter
public enum LoanCJEnum {

  REGISTER_INFORMATION("REGISTER_INFORMATION", "Đăng ký thông tin"),
  RECEIVE("RECEIVE", "Tiếp nhận"),
  EXPERTISE("EXPERTISE", "Thẩm định"),
  APPROVE("APPROVE", "Phê duyệt"),
  DISBURSEMENT("DISBURSEMENT", "Giải ngân"),
  ;

  private String code;
  private String name;

  LoanCJEnum(String code, String name) {
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
