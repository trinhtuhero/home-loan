package vn.com.msb.homeloan.core.constant;

public enum TransactionStatusEnum {

  // Hủy đơn hàng/hợp đồng
  CANCEL("0", "CANCEL"),
  // chưa hoàn thành đăng ký
  DRAFT("10", "DRAFT"),
  // hoàn thành đăng ký(đối tác đang đánh giá rủi ro)
  RISK("20", "RISK"),
  // Chưa hoàn thành thanh toán/đang thanh toán
  WAITING_PAYMENT("30", "WAITING_PAYMENT"),
  // chưa phát hành/ thanh toán thành công
  PAYMENT_SUCCESS("35", "PAYMENT_SUCCESS"),
  // chưa phát hành/ thanh toán thất bại
  PAYMENT_FAIL("36", "PAYMENT_FAIL"),
  // Hiệu lực/đã phát hành thành công
  ISSUED("40", "ISSUED");

  private final String code;
  private final String value;

  TransactionStatusEnum(String code, String value) {
    this.code = code;
    this.value = value;
  }

  public String code() {
    return this.code;
  }

  public String value() {
    return this.value;
  }


}
