package vn.com.msb.homeloan.core.constant;

public enum LocationTypeEnum {

  // nơi cư trú
  NOI_CU_TRU("NOI_CU_TRU", "Nơi cư trú"),

  // nơi làm việc
  NOI_LAM_VIEC("NOI_LAM_VIEC", "Nơi làm việc"),

  // địa điểm kinh doanh
  ĐIA_DIEM_KINH_DOANH("ĐIA_DIEM_KINH_DOANH", "Địa điểm kinh doanh"),

  // nơi cho thuê
  TAI_SAN_CHO_THUE("TAI_SAN_CHO_THUE", "Tài sản cho thuê"),

  ;

  private String code;
  private String name;

  LocationTypeEnum(String code, String name) {
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
