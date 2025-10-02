package vn.com.msb.homeloan.core.constant;

public enum LoanInfoStatusEnum {
  //RM_INIT_LOAN("RM_INIT_LOAN", "RM khởi tạo tờ trình"),

  DRAFT("DRAFT", "Nháp"),

  SUBMIT_LOAN_REQUEST("SUBMIT_LOAN_REQUEST", "Gửi ĐNVV"),

  ACCEPT_LOAN_REQUEST("ACCEPT_LOAN_REQUEST", "Tiếp nhận ĐNVV"),

  SUBMIT_LOAN_APPLICATION("SUBMIT_LOAN_APPLICATION", "Gửi HS vay"),

  ACCEPT_LOAN_APPLICATION("ACCEPT_LOAN_APPLICATION", "Tiếp nhận HS vay"),

  NEED_ADDITIONAL_INFO("NEED_ADDITIONAL_INFO", "Bổ sung HS vay"),

  PROCESSING("PROCESSING", "Đang xử lý"),

  WAITING_FOR_CONFIRM("WAITING_FOR_CONFIRM", "Chờ xác nhận hồ sơ"),

  DECLINED_TO_CONFIRM("DECLINED_TO_CONFIRM", "Từ chối xác nhận"),

  RM_COMPLETED("RM_COMPLETED", "Hoàn thành tờ trình"),

  CLOSED("CLOSED", "Đã đóng"),
  ;
  private String code;
  private String name;

  LoanInfoStatusEnum(String code, String name) {
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
