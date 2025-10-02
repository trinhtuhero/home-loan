package vn.com.msb.homeloan.core.constant.cic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * CIC query result status
 */
@Getter
public enum CicQueryStatusEnum {

  WAITING_RESPONSE("WAITING_RESPONSE", 5, "Đang chờ trả lời"),
  GOT_DATA("GOT_DATA", 6, "Có dữ liệu"),
  GOT_CIC_NO_INFO("GOT_CIC_NO_INFO", 7, "Khách có mã CIC, không có thông tin"),
  NO_CIC_NO_INFO("NO_CIC_NO_INFO", 8, "Khách không có mã CIC, không có thông tin"),
  NO_CIC_CODE("NO_CIC_CODE", 99999, "Không có mã CIC"),
  OTHER("OTHER", 9, "Khác"),
  SYNC_ERROR("SYNC_ERROR", 99, "Đồng bộ tin lỗi");

  private final String code;
  private final int value;
  private final String description;

  CicQueryStatusEnum(String code, int value, String description) {
    this.code = code;
    this.value = value;
    this.description = description;
  }

  @JsonValue
  public int getValue() {
    return value;
  }

  @JsonCreator
  public static CicQueryStatusEnum forValue(final int value) {
    for (final CicQueryStatusEnum s : CicQueryStatusEnum.values()) {
      if (s.value == value) {
        return s;
      }
    }
    return null;
  }

  public static boolean isOkData(CicQueryStatusEnum status, String content) {
    boolean isResult = false;
    if (StringUtils.isNotBlank(content)) {
      if (GOT_DATA.equals(status) || GOT_CIC_NO_INFO.equals(status) || NO_CIC_NO_INFO.equals(status)
          || OTHER.equals(status)) {
        isResult = true;
      }
    } else {
      if (GOT_CIC_NO_INFO.equals(status) || NO_CIC_NO_INFO.equals(status)) {
        isResult = true;
      }
    }
    return isResult;
  }

}
