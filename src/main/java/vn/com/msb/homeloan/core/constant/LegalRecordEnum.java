package vn.com.msb.homeloan.core.constant;

import lombok.Getter;

@Getter
public enum LegalRecordEnum {

  GIAO_DICH_PHI_TIN_DUNG("GIAO_DICH_PHI_TIN_DUNG", "Giao dịch phi tín dụng"),

  GIAO_DICH_TIN_DUNG("GIAO_DICH_TIN_DUNG", "Giao dịch tín dụng"),

  GIAO_DICH_THE_TIN_DUNG("GIAO_DICH_THE_TIN_DUNG", "Giao dịch thẻ tín dụng"),

  TIEN_GUI_TIET_KIEM("TIEN_GUI_TIET_KIEM", "Tiền gửi tiết kiệm"),

  LOI_THE_KHAI_THAC_TAI_SAN("LOI_THE_KHAI_THAC_TAI_SAN", "Lợi thế khai thác tài sản"),

  NGUON_LUC_SO_HUU_HE_SO_NHAN("NGUON_LUC_SO_HUU_HE_SO_NHAN", "Nguồn lực sở hữu (hệ số nhân)"),

  NGUON_LUC_SO_HUU("NGUON_LUC_SO_HUU", "Nguồn lực sở hữu tại các đơn vị đối tác chiến lược"),

  DUA_TREN_CHI_PHI_SINH_HOAT("DUA_TREN_CHI_PHI_SINH_HOAT", "Dựa trên chi phí sinh hoạt");

  private String code;
  private String name;

  LegalRecordEnum(String code, String name) {
    this.name = name;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public static String getByCode(String code) {
    try {
      LegalRecordEnum legalRecordEnum = LegalRecordEnum.valueOf(code);
      return legalRecordEnum.getName();
    } catch (Exception ex) {
      return "";
    }
  }
}
