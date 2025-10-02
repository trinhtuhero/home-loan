package vn.com.msb.homeloan.core.constant.cic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * CIC query error code
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CicQueryErrorEnum {

  MISSING_FIELD("ML07", "Trường thông tin không được để trống"),
  PRODUCT_CODE_NOT_EXIST("ML99", "Mã sản phẩm không tồn tại");

  private final String code;
  private final String description;

  CicQueryErrorEnum(String code, String description) {
    this.code = code;
    this.description = description;
  }
}
