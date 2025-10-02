package vn.com.msb.homeloan.core.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ProductTextCodeEnum {

  LAND("LAND", "Cho vay mua BĐS"),
  M_HOUSING("M_HOUSING", "Cho vay M-Housing"),

  HV_DBS("HV_DBS", "Hoàn vốn mua BĐS"),
  GN_PT("GN_PT", "Giải ngân phong tỏa"),

  XAY_SUA_NHA("XAY_SUA_NHA", "Cho vay xây sửa nhà"),
  TIEU_DUNG_TSBĐ("TIEU_DUNG_TSBĐ", "Cho vay tiêu dùng có TSBĐ"),
  HOAN_THIEN_CHCC("HOAN_THIEN_CHCC", "Cho vay hoàn thiện CHCC"),

  //Type dùng cho thẻ chính của thẻ tín dụng
  CREDIT_CARD("CREDIT_CARD", "Thẻ tín dụng"),

  VAY_KINH_DOANH_HAN_MUC("VAY_KINH_DOANH_HAN_MUC", "Cho vay kinh doanh hạn mức"),
  VAY_KINH_DOANH_TUNG_LAN("VAY_KINH_DOANH_TUNG_LAN", "Cho vay kinh doanh từng lần"),

  // Vay thấu chi
  VAY_THAU_CHI_TSBĐ_ONLINE("VAY_THAU_CHI_TSBĐ_ONLINE", "Vay thấu chi có TSBĐ online");

  private String code;
  private String name;

  ProductTextCodeEnum(String code, String name) {
    this.name = name;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public static Map<String, String> toMap = Stream.of(values())
      .collect(Collectors.toMap(k -> k.getCode(), v -> v.getCode()));
}

